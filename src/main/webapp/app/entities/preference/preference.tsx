import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { AUTHORITIES } from 'app/config/constants';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPreference } from 'app/shared/model/preference.model';
import { getEntities, getEntitiesByUser } from './preference.reducer';
import { hasAnyAuthority } from 'app/shared/auth/private-route';


export const Preference = () => {

  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const preferenceList = useAppSelector(state => state.preference.entities);
  const preferenceListByUser = useAppSelector(state => state.preference.entitiesByUser);

  const loading = useAppSelector(state => state.preference.loading);
  const [searchQuery, setSearchQuery] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 5; // Set the number of items per page
  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value);
    setCurrentPage(1); // Reset to the first page when the search query changes
  };

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;

  const filteredPreferenceList = preferenceList
    .filter((preference) =>
    preference.user.login.toLowerCase().includes(searchQuery.toLowerCase())
    )
    .slice(indexOfFirstItem, indexOfLastItem);

  const paginate = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  useEffect(() => {
    dispatch(getEntities({}));
    dispatch(getEntitiesByUser({}))
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
    dispatch(getEntitiesByUser({}))

  };

  return (

    <div>
  <h2 id="preference-heading" data-cy="PreferenceHeading">
    Preferences
    <div className="d-flex justify-content-end">
      <Button className="me-2 btn-light custom-button-refresh" onClick={handleSyncList} disabled={loading}>
        <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
      </Button>
      <Link to="/preference/new" className="btn btn-light jh-create-entity custom-button-new" id="jh-create-entity" data-cy="entityCreateButton">
        <FontAwesomeIcon icon="plus" />
        &nbsp; new Preference
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
          {isAdmin ? (
            preferenceList.map((preference, i) => (
              <tr key={`entity-${i}`} data-cy="entityTable">
                <td>{preference.id}</td>
                <td>{preference.tempMinValue}</td>
                <td>{preference.tempMaxValue}</td>
                <td>{preference.plageHoraire}</td>
                <td>{preference.user ? preference.user.login : ''}</td>
                <td>{preference.energie ? preference.energie.nomSystemEnergitique : ''}</td>
                <td className="text-end">
                  <div className="btn-group flex-btn-group-container">
                    <Button tag={Link} to={`/preference/${preference.id}`} className="custom-button-view" size="sm" data-cy="entityDetailsButton">
                      <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                    </Button>
                    <Button tag={Link} to={`/preference/${preference.id}/edit`} className="custom-button-edit" size="sm" data-cy="entityEditButton">
                      <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                    </Button>
                    <Button tag={Link} to={`/preference/${preference.id}/delete`} className="custom-button-delete" size="sm" data-cy="entityDeleteButton">
                      <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                    </Button>
                  </div>
                </td>
              </tr>
            )
            )
          ) : (
            preferenceListByUser.map((preference, i) => (
              <tr key={`entity-${i}`} data-cy="entityTable">
                <td>{preference.id}</td>
                <td>{preference.tempMinValue}</td>
                <td>{preference.tempMaxValue}</td>
                <td>{preference.plageHoraire}</td>
                <td>{preference.user ? preference.user.login : ''}</td>
                <td>{preference.energie ? preference.energie.nomSystemEnergitique : ''}</td>
                <td className="text-end">
                  <div className="btn-group flex-btn-group-container">
                    <Button tag={Link} to={`/preference/${preference.id}`} className="custom-button-view" size="sm" data-cy="entityDetailsButton">
                      <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                    </Button>
                    <Button tag={Link} to={`/preference/${preference.id}/edit`} className="custom-button-edit" size="sm" data-cy="entityEditButton">
                      <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                    </Button>
                    <Button tag={Link} to={`/preference/${preference.id}/delete`} className="custom-button-delete" size="sm" data-cy="entityDeleteButton">
                      <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                    </Button>
                  </div>
                </td>
              </tr>
            )
            )
          )}
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
