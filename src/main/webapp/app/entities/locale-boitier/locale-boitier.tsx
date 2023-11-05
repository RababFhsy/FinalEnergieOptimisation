import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILocaleBoitier } from 'app/shared/model/locale-boitier.model';
import { getEntities } from './locale-boitier.reducer';


export const LocaleBoitier = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const localeBoitierList = useAppSelector(state => state.localeBoitier.entities);
  const loading = useAppSelector(state => state.localeBoitier.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };
  

  return (
    <div>
      <h2 id="locale-boitier-heading" data-cy="LocaleBoitierHeading">
        Locale Boitiers
        <div className="d-flex justify-content-end">
          <Button className="me-2 btn-light custom-button-refresh" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/locale-boitier/new" className="btn btn-light jh-create-entity custom-button-new" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; new Locale Boitier
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {localeBoitierList && localeBoitierList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Date Debut</th>
                <th>Date Fin</th>
                <th>Locale Number</th>
                <th>Boitier Reference</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {localeBoitierList.map((localeBoitier, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                   
                      {localeBoitier.id}
                    
                  </td>
                  <td>
                    {localeBoitier.dateDebut ? (
                      <TextFormat type="date" value={localeBoitier.dateDebut} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {localeBoitier.dateFin ? <TextFormat type="date" value={localeBoitier.dateFin} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{localeBoitier.locale ? localeBoitier.locale.numero : ''}</td>
                  <td>
                  <td>{localeBoitier.locale ? localeBoitier.boitier?.boitierReference: ''}</td>
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/locale-boitier/${localeBoitier.id}`} size="sm" data-cy="entityDetailsButton"  className="custom-button-view">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/locale-boitier/${localeBoitier.id}/edit`}
                      
                        size="sm"
                        data-cy="entityEditButton"
                        className="custom-button-view"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/locale-boitier/${localeBoitier.id}/delete`}
                        
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
          !loading && <div className="alert alert-warning">No Locale Boitiers found</div>
        )}
      </div>
    </div>
  );
};

export default LocaleBoitier;
