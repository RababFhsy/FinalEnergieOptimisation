import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IConsommation } from 'app/shared/model/consommation.model';
import { getEntities } from './consommation.reducer';

export const Consommation = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const consommationList = useAppSelector(state => state.consommation.entities);
  const loading = useAppSelector(state => state.consommation.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="consommation-heading" data-cy="ConsommationHeading">
        Consommations
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/consommation/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Consommation
          </Link>
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
                <th>Locale</th>
                <th>Energie</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {consommationList.map((consommation, i) => (
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
                  <td>{consommation.locale ? <Link to={`/locale/${consommation.locale.id}`}>{consommation.locale.id}</Link> : ''}</td>
                  <td>{consommation.energie ? <Link to={`/energie/${consommation.energie.id}`}>{consommation.energie.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/consommation/${consommation.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/consommation/${consommation.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
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
