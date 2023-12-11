const WebSocket = require('ws');
const wss = new WebSocket.Server({ port: 8080 });
const { SerialPort } = require('serialport');
const { ByteLengthParser } = require('@serialport/parser-byte-length');
const parser = new ByteLengthParser({ length: 2 });

global.myGlobalVar =0;

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
    console.log('error opening the port' + err.message);
  }
});


port.pipe(parser);
parser.on('data', (data) => {
  global.myGlobalVar=data;
  console.log(parseInt(global.myGlobalVar));
  
});

wss.on('connection', function connection(ws) {
  console.log('WebSocket client connected');
  
  setInterval(() => {
    
    const sensorValue = parseInt(global.myGlobalVar); 
    ws.send(sensorValue); 

  }, 1000); 
});