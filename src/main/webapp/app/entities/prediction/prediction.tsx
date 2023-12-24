/* eslint-disable @typescript-eslint/no-misused-promises */
import React, { useState } from 'react';

const Prediction: React.FC = () => {
  const [tempMin, setTempMin] = useState(0);
  const [tempMax, setTempMax] = useState(0);
  const [pressure, setPressure] = useState(0);
  const [humidity, setHumidity] = useState(0);
  const [windSpeed, setWindSpeed] = useState(0);
  const [lon, setLon] = useState(0);
  const [lat, setLat] = useState(0);
  const [prediction, setPrediction] = useState<number | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8000/predict', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          Temp_Min: tempMin,
          Temp_Max: tempMax,
          Pressure: pressure,
          Humidity: humidity,
          Wind_Speed: windSpeed,
          Lon: lon,
          Lat: lat,
          // Ajoutez d'autres fonctionnalités au besoin
        }),
      });

      const data = await response.json();
      setPrediction(data.prediction);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  return (
    <div className="container mt-5">
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">Température Min:</label>
          <input
            type="number"
            className="form-control"
            value={tempMin}
            onChange={(e) => setTempMin(parseFloat(e.target.value))}
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Température Max:</label>
          <input
            type="number"
            className="form-control"
            value={tempMax}
            onChange={(e) => setTempMax(parseFloat(e.target.value))}
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Pression:</label>
          <input
            type="number"
            className="form-control"
            value={pressure}
            onChange={(e) => setPressure(parseFloat(e.target.value))}
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Humidité:</label>
          <input
            type="number"
            className="form-control"
            value={humidity}
            onChange={(e) => setHumidity(parseFloat(e.target.value))}
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Vitesse du vent:</label>
          <input
            type="number"
            className="form-control"
            value={windSpeed}
            onChange={(e) => setWindSpeed(parseFloat(e.target.value))}
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Longitude:</label>
          <input
            type="number"
            className="form-control"
            value={lon}
            onChange={(e) => setLon(parseFloat(e.target.value))}
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Latitude:</label>
          <input
            type="number"
            className="form-control"
            value={lat}
            onChange={(e) => setLat(parseFloat(e.target.value))}
          />
        </div>
        {/* Ajoutez d'autres champs de saisie pour les fonctionnalités au besoin */}
        <button type="submit" className="btn btn-primary">
          Soumettre
        </button>
      </form>

      <h1 className="mt-5">Prédiction :</h1>
      {prediction !== null ? (
        <p>La température prédite est : {prediction}</p>
      ) : (
        <p>Chargement en cours...</p>
      )}
    </div>
  );
};

export default Prediction
