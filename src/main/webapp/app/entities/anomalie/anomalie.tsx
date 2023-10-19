import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAnomalie } from 'app/shared/model/anomalie.model';
import { getEntities } from './anomalie.reducer';

export const Anomalie = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const anomalieList = useAppSelector(state => state.anomalie.entities);
  const loading = useAppSelector(state => state.anomalie.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="anomalie-heading" data-cy="AnomalieHeading">
        Anomalies
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/anomalie/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Anomalie
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {anomalieList && anomalieList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Zone Normale Min</th>
                <th>Zone Normale Max</th>
                <th>Date Anomalie</th>
                <th>Description Anomalie</th>
                <th>Locale</th>
                <th>Energie</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {anomalieList.map((anomalie, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/anomalie/${anomalie.id}`} color="link" size="sm">
                      {anomalie.id}
                    </Button>
                  </td>
                  <td>{anomalie.zoneNormaleMin}</td>
                  <td>{anomalie.zoneNormaleMax}</td>
                  <td>
                    {anomalie.dateAnomalie ? <TextFormat type="date" value={anomalie.dateAnomalie} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{anomalie.descriptionAnomalie}</td>
                  <td>{anomalie.locale ? <Link to={`/locale/${anomalie.locale.id}`}>{anomalie.locale.id}</Link> : ''}</td>
                  <td>{anomalie.energie ? <Link to={`/energie/${anomalie.energie.id}`}>{anomalie.energie.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/anomalie/${anomalie.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/anomalie/${anomalie.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/anomalie/${anomalie.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Anomalies found</div>
        )}
      </div>
    </div>
  );
};

export default Anomalie;
