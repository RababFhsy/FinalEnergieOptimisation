const WebSocket = require('ws');
const wss = new WebSocket.Server({ port: 2222 });
const { SerialPort } = require('serialport');
const { ByteLengthParser } = require('@serialport/parser-byte-length');
const parser1 = new ByteLengthParser({ length: 2 });
const parser2 = new ByteLengthParser({ length: 2 });


global.myGlobalVar1 = 0;
global.myGlobalVar2 = 0;

// Configuration for COM3
const config = {
  path: 'COM3',
  baudRate: 9600,
  dataBits: 8,
  parity: 'none',
  stopBits: 1,
  autoOpen: false,
};

// Configuration for COM4
const config2 = {
  path: 'COM4',
  baudRate: 9600,
  dataBits: 8,
  parity: 'none',
  stopBits: 1,
  autoOpen: false,
};

const port1 = new SerialPort(config);
port1.open((err) => {
  if (err) {
    console.log('Error opening the port for COM3: ' + err.message);
  }
});

const port2 = new SerialPort(config2);
port2.open((err) => {
  if (err) {
    console.log('Error opening the port for COM4: ' + err.message);
  }
});

port1.pipe(parser1);
parser1.on('data', (data) => {
  global.myGlobalVar1 = data;
  const parsedValue = parseInt(global.myGlobalVar1);
  if (!isNaN(parsedValue)) {
    console.log('Temperature:', parsedValue);
  }
});

port2.pipe(parser2);
parser2.on('data', (data) => {
  global.myGlobalVar2 = data;
  const parsedValue = parseInt(global.myGlobalVar2);
  if (!isNaN(parsedValue)) {
    console.log('Gaz:', parsedValue);
  }
});

wss.on('connection', function connection(ws) {
  console.log('WebSocket client connected');

  setInterval(() => {
    const sensorValue1 = parseInt(global.myGlobalVar1);
    const sensorValue2 = parseInt(global.myGlobalVar2);

    // Check if values are valid before sending
    if (!isNaN(sensorValue1) && !isNaN(sensorValue2)) {
      // Send data as a JSON object
      ws.send(JSON.stringify({ sensorValue1, sensorValue2 }));
    }
  }, 1000);
});