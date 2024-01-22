const WebSocket = require('ws');
const { SerialPort } = require('serialport');
const { ByteLengthParser } = require('@serialport/parser-byte-length');
const parser = new ByteLengthParser({ length: 2 });
const mysql = require('mysql2');

global.myGlobalVar = 0;
let lastInsertTime = 0;

// Configuration de la base de données
const dbConfig = {
  host: 'localhost',
  user: 'root',
  password: '',
  database: 'feoptimisationenergie'
};

const dbConnection = mysql.createConnection(dbConfig);

const config = {
  path: 'COM3',
  baudRate: 9600,
  dataBits: 8,
  parity: 'none',
  stopBits: 1,
  autoOpen: false,
};

const port = new SerialPort(config);
port.open((err) => {
  if (err) {
    console.log('Erreur lors de l\'ouverture du port : ' + err.messages);
  }
});

port.pipe(parser);
parser.on('data', (data) => {
  global.myGlobalVar = data;
  const sensorValue = global.myGlobalVar.toString(); // Convertir en chaîne de caractères
  console.log(sensorValue);

  // Vérifier s'il est temps d'insérer
  const currentTime = new Date().getTime();
  const timeSinceLastInsert = currentTime - lastInsertTime;

  if (timeSinceLastInsert >= 20000) { // Limite à deux fois par minute (60 000 ms par minute / 2 = 30 000 ms)
    // Insérer les données dans la base de données
    insertData(sensorValue);
    lastInsertTime = currentTime;
  }
});

function insertData(sensorValue) {
  const insertDataQuery = 'INSERT INTO consommation (date_consommation, energie_consommation,locale_id, energie_id,user_id) VALUES (CURRENT_TIMESTAMP,?,15, 12,2)';
  dbConnection.query(insertDataQuery, [sensorValue], (err, results) => {
    if (err) {
      console.error('Erreur lors de l\'insertion des données dans la base de données :', err);
    } else {
      console.log('Données insérées avec succès dans la base de données.');
    }
  });
}

const wss = new WebSocket.Server({ port: 8089 });

wss.on('connection', function connection(ws) {
  console.log('Client WebSocket connecté');

  setInterval(() => {
    const sensorValue = global.myGlobalVar.toString();
    ws.send(sensorValue);
  }, 1000);
});
