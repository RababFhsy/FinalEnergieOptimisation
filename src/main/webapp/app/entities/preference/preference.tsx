import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPreference } from 'app/shared/model/preference.model';
import { getEntities } from './preference.reducer';

export const Preference = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const preferenceList = useAppSelector(state => state.preference.entities);
  const loading = useAppSelector(state => state.preference.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="preference-heading" data-cy="PreferenceHeading">
        Preferences
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/preference/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Preference
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {preferenceList && preferenceList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Temp Min Value</th>
                <th>Temp Max Value</th>
                <th>Plage Horaire</th>
                <th>User</th>
                <th>Energie</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {preferenceList.map((preference, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/preference/${preference.id}`} color="link" size="sm">
                      {preference.id}
                    </Button>
                  </td>
                  <td>{preference.tempMinValue}</td>
                  <td>{preference.tempMaxValue}</td>
                  <td>{preference.plageHoraire}</td>
                  <td>{preference.user ? preference.user.login : ''}</td>
                  <td>{preference.energie ? <Link to={`/energie/${preference.energie.id}`}>{preference.energie.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/preference/${preference.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/preference/${preference.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/preference/${preference.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Preferences found</div>
        )}
      </div>
    </div>
  );
};

export default Preference;
