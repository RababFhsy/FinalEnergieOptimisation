/* eslint-disable no-console */
import React, { useEffect, useState, useRef } from 'react';
import { Chart } from 'chart.js';

interface SensorData {
  sensorValue1: number;
  sensorValue2: number;
}

const WebSocketClient: React.FC = () => {
  const [sensorValues, setSensorValues] = useState<SensorData[]>([]);
  const [chart1Instance, setChart1Instance] = useState<Chart | null>(null);
  const [chart2Instance, setChart2Instance] = useState<Chart | null>(null);
  const chart1Ref = useRef<HTMLCanvasElement>(null);
  const chart2Ref = useRef<HTMLCanvasElement>(null);

  useEffect(() => {
    const ws = new WebSocket('ws://localhost:2222');

    ws.onopen = () => {
      console.log('Connected to WebSocket server');
    };

    ws.onmessage = (event) => {
      try {
        const data: SensorData = JSON.parse(event.data);
        const { sensorValue1, sensorValue2 } = data;

        console.log('Received sensor values:', sensorValue1, sensorValue2);

        setSensorValues((prevValues) => [
          ...prevValues,
          { sensorValue1, sensorValue2 },
        ]);

        // Cleanup chart instances before creating new ones
        if (chart1Instance) {
          chart1Instance.destroy();
          setChart1Instance(null);
        }

        if (chart2Instance) {
          chart2Instance.destroy();
          setChart2Instance(null);
        }

        // Create new chart instances
        const chart1Canvas = chart1Ref.current as HTMLCanvasElement;
        const chart1Ctx = chart1Canvas.getContext('2d') as CanvasRenderingContext2D;
        const newChart1Instance = new Chart(chart1Ctx, {
          type: 'line',
          data: {
            labels: Array(sensorValues.length).fill(''),
            datasets: [
              {
                label: 'Temperature Sensor graph',
                backgroundColor: 'rgba(75,192,192,0.4)',
                borderColor: 'rgba(75,192,192,1)',
                borderWidth: 1,
                data: sensorValues.map(({ sensorValue1 }) => sensorValue1),
              },
            ],
          },
          options: {
            scales: {
              y: {
                beginAtZero: true,
              },
            },
          },
        });

        setChart1Instance(newChart1Instance);

        const chart2Canvas = chart2Ref.current as HTMLCanvasElement;
        const chart2Ctx = chart2Canvas.getContext('2d') as CanvasRenderingContext2D;
        const newChart2Instance = new Chart(chart2Ctx, {
          type: 'line',
          data: {
            labels: Array(sensorValues.length).fill(''),
            datasets: [
              {
                label: 'Gaz Sensor Graph',
                backgroundColor: 'rgba(255,99,132,0.4)',
                borderColor: 'rgba(255,99,132,1)',
                borderWidth: 1,
                data: sensorValues.map(({ sensorValue2 }) => sensorValue2),
              },
            ],
          },
          options: {
            scales: {
              y: {
                beginAtZero: true,
              },
            },
          },
        });

        setChart2Instance(newChart2Instance);
      } catch (error) {
        console.error('Error parsing JSON data:', error);
      }
    };

    ws.onclose = () => {
      console.log('Disconnected from WebSocket server');
    };

    ws.onerror = (error) => {
      console.error('WebSocket error:', error);
    };

    return () => {
      ws.close();
    };
  }, [sensorValues, chart1Instance, chart2Instance]);

  return (
    <div>
      <h1>Sensor Values</h1>
      <div>
        <h2>Temperature Sensor graph</h2>
        <canvas ref={chart1Ref} width="400" height="150"></canvas>
      </div>
      <div>
        <h2>Gaz Sensor Graph</h2>
        <canvas ref={chart2Ref} width="400" height="150"></canvas>
      </div>
    </div>
  );
};

export default WebSocketClient;
