import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPrediction } from 'app/shared/model/prediction.model';
import { getEntities } from './prediction.reducer';

export const Prediction = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const predictionList = useAppSelector(state => state.prediction.entities);
  const loading = useAppSelector(state => state.prediction.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="prediction-heading" data-cy="PredictionHeading">
        Predictions
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/prediction/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Prediction
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {predictionList && predictionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Date Debut</th>
                <th>Date Fin</th>
                <th>Consommation Predit</th>
                <th>Precision</th>
                <th>Locale</th>
                <th>Energie</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {predictionList.map((prediction, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/prediction/${prediction.id}`} color="link" size="sm">
                      {prediction.id}
                    </Button>
                  </td>
                  <td>
                    {prediction.dateDebut ? <TextFormat type="date" value={prediction.dateDebut} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {prediction.dateFin ? <TextFormat type="date" value={prediction.dateFin} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{prediction.consommationPredit}</td>
                  <td>{prediction.precision}</td>
                  <td>{prediction.locale ? <Link to={`/locale/${prediction.locale.id}`}>{prediction.locale.id}</Link> : ''}</td>
                  <td>{prediction.energie ? <Link to={`/energie/${prediction.energie.id}`}>{prediction.energie.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/prediction/${prediction.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/prediction/${prediction.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/prediction/${prediction.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Predictions found</div>
        )}
      </div>
    </div>
  );
};

export default Prediction;
