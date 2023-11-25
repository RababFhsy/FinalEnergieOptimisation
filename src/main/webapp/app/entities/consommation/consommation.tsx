import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { getEntities, getEntitiesByUser } from './consommation.reducer';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT, AUTHORITIES } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { hasAnyAuthority } from 'app/shared/auth/private-route';

export const Consommation = () => {
  const dispatch = useAppDispatch();
  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));

  const location = useLocation();
  const navigate = useNavigate();

  const consommationList = useAppSelector(state => state.consommation.entities);
  const consommationListByUser = useAppSelector(state => state.consommation.entitiesByUser);

  const loading = useAppSelector(state => state.consommation.loading);

  useEffect(() => {
    dispatch(getEntities({}));
    dispatch(getEntitiesByUser({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
    dispatch(getEntitiesByUser({}));
  };

  return (
    <div>
      <h2 id="consommation-heading" data-cy="ConsommationHeading">
        Consommations
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
        </div>
      </h2>
      <div className="table-responsive">
        {consommationList && consommationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Energie Consommation</th>
                <th>Date Consommation</th>
                <th>Local</th>
                <th>Building</th>
                <th>Energy</th>
                <th>User</th>

                <th />
              </tr>
            </thead>
            <tbody>
              {isAdmin
                ? consommationList.map((consommation, i) => (
                    <tr key={`entity-${i}`} data-cy="entityTable">
                      <td>
                        <Button tag={Link} to={`/consommation/${consommation.id}`} color="link" size="sm">
                          {consommation.id}
                        </Button>
                      </td>
                      <td>{consommation.energieConsommation}</td>
                      <td>
                        {consommation.dateConsommation ? (
                          <TextFormat type="date" value={consommation.dateConsommation} format={APP_LOCAL_DATE_FORMAT} />
                        ) : null}
                      </td>
                      <td>{consommation.locale ? consommation.locale.numero : ''}</td>
                      <td>{consommation.locale && consommation.locale.batiment ? consommation.locale.batiment.batimentNom : ''}</td>

                      <td>{consommation.energie ? consommation.energie.nomSystemEnergitique : ''}</td>
                      <td>{consommation.user ? consommation.user.login : ''}</td>

                      <td className="text-end">
                        <div className="btn-group flex-btn-group-container">
                          <Button tag={Link} to={`/consommation/${consommation.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                            <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                          </Button>

                          <Button tag={Link} to={`/consommation/${consommation.id}/delete`}  color="danger"  size="sm" data-cy="entityDeleteButton">
                            <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                          </Button>
                        </div>
                      </td>
                    </tr>
                  ))
                : consommationListByUser.map((consommation, i) => (
                    <tr key={`entity-${i}`} data-cy="entityTable">
                      <td>
                        <Button tag={Link} to={`/consommation/${consommation.id}`} color="link" size="sm">
                          {consommation.id}
                        </Button>
                      </td>
                      <td>{consommation.energieConsommation}</td>
                      <td>
                        {consommation.dateConsommation ? (
                          <TextFormat type="date" value={consommation.dateConsommation} format={APP_LOCAL_DATE_FORMAT} />
                        ) : null}
                      </td>
                      <td>{consommation.locale ? consommation.locale.numero : ''}</td>
                      <td>{consommation.locale && consommation.locale.batiment ? consommation.locale.batiment.batimentNom : ''}</td>

                      <td>{consommation.energie ? consommation.energie.nomSystemEnergitique : ''}</td>
                      <td>{consommation.user ? consommation.user.login : ''}</td>

                      <td className="text-end">
                        <div className="btn-group flex-btn-group-container">
                          <Button tag={Link} to={`/consommation/${consommation.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                            <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                          </Button>

                          <Button
                            tag={Link}
                            to={`/consommation/${consommation.id}/delete`}
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
          !loading && <div className="alert alert-warning">No Consommations found</div>
        )}
      </div>
    </div>
  );
};

export default Consommation;
