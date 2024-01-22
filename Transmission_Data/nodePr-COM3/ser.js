const {SerialPort} = require("serialport");
const Readline = require('@serialport/parser-readline');

const portName = process.argv[2];

if (!portName) {
  console.error('Please provide the serial port path as a command-line argument.');
  process.exit(1);
}

const myPort = new SerialPort(portName, {
  baudRate: 9600,
});

const parser = myPort.pipe(new Readline({ delimiter: '\r\n' }));

myPort.on('open', onOpen);
parser.on('data', onData);

function onOpen() {
  console.log(`Open connection on port: ${portName}`);
}

function onData(data) {
  console.log("on Data: " + data);
}
