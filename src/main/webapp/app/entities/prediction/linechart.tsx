import React, { useEffect, useRef } from 'react';
import Chart, { ChartOptions } from 'chart.js/auto';
import 'chartjs-adapter-moment';

interface LineChartProps {
  predictionList: {
    dateDebut: string;
    consommationPredit: number;
  }[];
  reelList: {
    dateAnomalie: string;
    zoneNormaleMin: number;
  }[];
}

const LineChart: React.FC<LineChartProps> = ({ predictionList, reelList }) => {
  const chartRef = useRef<HTMLCanvasElement | null>(null);
  const chartInstance = useRef<Chart | null>(null);

  useEffect(() => {
    if (chartRef.current) {
      const ctx = chartRef.current.getContext('2d');

      if (ctx) {
        const data = {
          labels: predictionList.map((prediction) => new Date(prediction.dateDebut).toLocaleDateString()),
          datasets: [
            {
              label: 'Predicted Temperature',
              data: predictionList.map((prediction) => prediction.consommationPredit),
              borderColor: 'rgb(255, 165, 0)',
              borderWidth: 2,
              fill: true,
            },
          
            {
              label: 'Real Temperature',
              data: reelList.map((reel) => reel.zoneNormaleMin),
              borderColor: 'rgba(0, 0, 255)', // Change color for real values
              borderWidth: 2,
              fill: true,
            },
          ],
        };

        
      

        const options: ChartOptions = {
          scales: {
            x: {
              display: true,
              title: {
                display: true,
                text: 'Date',
              },
            },
            y: {
              type: 'linear',
              title: {
                display: true,
                text: 'Temperature°C',
              },
            },
          },
        };

        // Check if chart instance exists and destroy it before creating a new one
        if (chartInstance.current) {
          chartInstance.current.destroy();
        }

        chartInstance.current = new Chart(ctx, {
          type: 'line',
          data: {
            labels: [...data.labels],
            datasets: [...data.datasets],
          },
          options,
        });
      }
    }

    // Cleanup function to destroy the chart when the component is unmounted
    return () => {
      if (chartInstance.current) {
        chartInstance.current.destroy();
      }
    };
  }, [predictionList, reelList]);

  return (
    <div>
      <canvas id="myLineChart" ref={chartRef} width="900" height="200"></canvas>
    </div>
  );
};

export default LineChart;
