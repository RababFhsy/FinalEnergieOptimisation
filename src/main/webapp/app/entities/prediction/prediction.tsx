import React, { useState, useEffect } from 'react';
import { Button } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities } from './prediction.reducer';
import { Pagination } from 'react-bootstrap';
import { VictoryChart, VictoryLine, VictoryAxis } from 'victory';

export const Prediction = () => {
  const dispatch = useAppDispatch();
  const predictionList = useAppSelector(state => state.prediction.entities);
  const loading = useAppSelector(state => state.prediction.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 5;

  const paginate = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  return (
    <div>
      <h2 id="prediction-heading" data-cy="PredictionHeading">
        Predictions

      </h2>

      <div className="graph-container">
        {predictionList && predictionList.length > 0 ? (
          <div>
            <VictoryChart
              domainPadding={20}
              width={600}
              height={300}
              scale={{ x: 'time' }}
            >
              <VictoryAxis
                tickFormat={(x) => new Date(x).toLocaleDateString()}
                label="Date"
              />
              <VictoryAxis dependentAxis label="Temperature (°C)" />
              <VictoryLine
                data={predictionList.map((prediction) => ({
                  x: new Date(prediction.dateDebut),
                  y: prediction.consommationPredit,
                }))}
              />
            </VictoryChart>


          </div>
        ) : (
          !loading && <div className="alert alert-warning">No Predictions found</div>
        )}
      </div>
    </div>
  );
};

export default Prediction;
