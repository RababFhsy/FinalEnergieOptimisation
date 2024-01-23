#define GreenLed 13
#define Sensor1 A1
#include "SoftwareSerial.h"

SoftwareSerial Serial1(2,3);
const int sensor = A0;

void setup() {
  pinMode(GreenLed, OUTPUT);  // Use GreenLed instead of 13
  pinMode(Sensor1, INPUT);     // Use Sensor instead of AO
  Serial1.begin(9600);
  Serial.begin(9600);
  delay(1000);
}

void loop() {
  int vout = analogRead(sensor);
  vout = (vout * 4.88) / 10;
  int tempc = vout;

  // Send temperature value to Node.js server

  Serial.println(tempc);

  delay(0);
  int value = analogRead(Sensor1);  // Use Sensor instead of A0
  
  Serial1.println(value);
  delay(0);

  if (value > 600) {
    digitalWrite(GreenLed, HIGH);  // Use GreenLed instead of 13
  } else {
    digitalWrite(GreenLed, LOW);   // Use GreenLed instead of 13
  }

  delay(20);
}