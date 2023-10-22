import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import 'app/shared/layout/customStyles/customStyles.scss';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICapteurBoitier } from 'app/shared/model/capteur-boitier.model';
import { getEntities } from './capteur-boitier.reducer';
import './capteur-boitier.scss';

export const CapteurBoitier = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const capteurBoitierList = useAppSelector(state => state.capteurBoitier.entities);
  const loading = useAppSelector(state => state.capteurBoitier.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="capteur-boitier-heading" data-cy="CapteurBoitierHeading">
        Assign Sensor to Boitier
        <div className="d-flex justify-content-end">
          <Button   onClick={handleSyncList} disabled={loading}
          className="me-2 btn-light custom-button-refresh"
        >
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/capteur-boitier/new" className="btn btn-light jh-create-entity custom-button-new" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Assign Sensor to Boitier
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {capteurBoitierList && capteurBoitierList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Branche</th>
                <th>Capteur</th>
                <th>Boitier</th>
                <th style={{ textAlign: 'center' }}>      Action</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {capteurBoitierList.map((capteurBoitier, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    
                      {capteurBoitier.id}
                    
                  </td>
                  <td>{capteurBoitier.branche}</td>
                  <td>
                    {capteurBoitier.capteur ? <Link to={`/capteur/${capteurBoitier.capteur.id}`}>{capteurBoitier.capteur.id}</Link> : ''}
                  </td>
                  <td>
                    {capteurBoitier.boitier ? <Link to={`/boitier/${capteurBoitier.boitier.id}`}>{capteurBoitier.boitier.id}</Link> : ''}
                  </td>
                  <td style={{ textAlign: 'center' }}>
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/capteur-boitier/${capteurBoitier.id}`} size="sm" data-cy="entityDetailsButton" className="custom-button-view">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/capteur-boitier/${capteurBoitier.id}/edit`}
                        size="sm"
                        data-cy="entityEditButton" className="custom-button-edit" >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/capteur-boitier/${capteurBoitier.id}/delete`}
                        size="sm"
                        data-cy="entityDeleteButton" 
                        className="custom-button-delete"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Capteur Boitiers found</div>
        )}
      </div>
    </div>
  );
};

export default CapteurBoitier;
