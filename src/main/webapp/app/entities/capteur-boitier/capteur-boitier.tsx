import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICapteurBoitier } from 'app/shared/model/capteur-boitier.model';
import { getEntities } from './capteur-boitier.reducer';

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
        Capteur Boitiers
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/capteur-boitier/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Capteur Boitier
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
                <th />
              </tr>
            </thead>
            <tbody>
              {capteurBoitierList.map((capteurBoitier, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/capteur-boitier/${capteurBoitier.id}`} color="link" size="sm">
                      {capteurBoitier.id}
                    </Button>
                  </td>
                  <td>{capteurBoitier.branche}</td>
                  <td>
                    {capteurBoitier.capteur ? <Link to={`/capteur/${capteurBoitier.capteur.id}`}>{capteurBoitier.capteur.id}</Link> : ''}
                  </td>
                  <td>
                    {capteurBoitier.boitier ? <Link to={`/boitier/${capteurBoitier.boitier.id}`}>{capteurBoitier.boitier.id}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/capteur-boitier/${capteurBoitier.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/capteur-boitier/${capteurBoitier.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/capteur-boitier/${capteurBoitier.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
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
