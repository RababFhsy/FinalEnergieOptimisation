import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate, useParams } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { AUTHORITIES } from 'app/config/constants';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntities as getEnergies } from 'app/entities/energie/energie.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPreference } from 'app/shared/model/preference.model';
import { getEntities, getEntitiesByUser } from './preference.reducer';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import {  Pagination,Form,FormControl } from 'react-bootstrap';
import {   Modal ,Col,Row } from 'react-bootstrap';
import { getEntity, updateEntity, createEntity, reset } from './preference.reducer';


export const Preference = () => {

  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));
  const dispatch = useAppDispatch();
  const { id } = useParams<'id'>();
  const isNew = id === undefined;
  const location = useLocation();
  const navigate = useNavigate();
  const [show, setShow] = useState(false);
  const [showview, setShowView] = useState(false);
  const handleClose = () => setShow(false);
  const handleCloseView = () => setShowView(false);
  const updating = useAppSelector(state => state.preference.updating);
  const energies = useAppSelector(state => state.energie.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const preferenceEntity = useAppSelector(state => state.preference.entity);
  const preferenceList = useAppSelector(state => state.preference.entities);
  const [selectedPreference, setSelectedPreference] = useState(null);
  const preferenceListByUser = useAppSelector(state => state.preference.entitiesByUser);
  const handleSetShow = preference => {
    setSelectedPreference(preference);
    dispatch(getEntity(preference.id)); // Déplacez la logique ici
    setShow(true);
  };

  const loading = useAppSelector(state => state.preference.loading);
  const [searchQuery, setSearchQuery] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 5; // Set the number of items per page
  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value);
    setCurrentPage(1); // Reset to the first page when the search query changes
  };
  const saveEntity = values => {
    const entity = {
      ...preferenceEntity,
      ...values,
    
      energie: energies.find(it => it.id.toString() === values.energie.toString()),
    };
  
    dispatch(updateEntity(entity));
  };


  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...preferenceEntity,
       
          energie: preferenceEntity?.energie?.id,
        };
        console.log(JSON.stringify("hhhhhhhhhhhhhhhhhh"+energies, null, 2));
      

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;

  const filteredPreferenceList = preferenceList
    .filter((preference) =>
    preference.energie.nomSystemEnergitique.toLowerCase().includes(searchQuery.toLowerCase())
    )
    .slice(indexOfFirstItem, indexOfLastItem);

  const paginate = (pageNumber) => {
    setCurrentPage(pageNumber);
  };
  const filteredPreferenceListByUser =  preferenceListByUser
  .filter((preference) =>
  preference.energie.nomSystemEnergitique.toLowerCase().includes(searchQuery.toLowerCase())
  )
  .slice(indexOfFirstItem, indexOfLastItem);

  useEffect(() => {
    dispatch(getEntities({}));
    dispatch(getEntitiesByUser({}));
    dispatch(getEnergies({}));
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
      {!isAdmin && (
            <Link to="/preference/new" className="btn btn-light jh-create-entity custom-button-new" id="jh-create-entity" data-cy="entityCreateButton">
              <FontAwesomeIcon icon="plus" />
              &nbsp; New Preference
            </Link>
          )}
    </div>
  </h2>
  <div className="table-responsive">
  <Form >
        <FormControl
          type="text"
          placeholder="Search Energy System..."
          className="mr-sm-2"
          value={searchQuery}
          onChange={handleSearchChange}
        />
      </Form>
      <br></br>
      {filteredPreferenceList && filteredPreferenceList.length > 0 ? (
   
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
            filteredPreferenceList.map((preference, i) => (
              <tr key={`entity-${i}`} data-cy="entityTable">
                <td>{preference.id}</td>
                <td>{preference.tempMinValue}</td>
                <td>{preference.tempMaxValue}</td>
                <td>{preference.plageHoraire}</td>
                <td>{preference.user ? preference.user.login : ''}</td>
                <td>{preference.energie ? preference.energie.nomSystemEnergitique : ''}</td>
                <td className="text-end">
                  <div className="btn-group flex-btn-group-container">
                    {/* <Button tag={Link} to={`/preference/${preference.id}`} className="custom-button-view" size="sm" data-cy="entityDetailsButton">
                      <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                    </Button>
                    <Button tag={Link} to={`/preference/${preference.id}/edit`} className="custom-button-edit" size="sm" data-cy="entityEditButton">
                      <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                    </Button> */}
                    <Button tag={Link} to={`/preference/${preference.id}/delete`} className="custom-button-delete" size="sm" data-cy="entityDeleteButton">
                      <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                    </Button>
                  </div>
                </td>
              </tr>
            )
            )
          ) : (
            filteredPreferenceListByUser.map((preference, i) => (
              
              <tr key={`entity-${i}`} data-cy="entityTable">
                <td>{preference.id}</td>
                <td>{preference.tempMinValue}</td>
                <td>{preference.tempMaxValue}</td>
                <td>{preference.plageHoraire}</td>
                <td>{preference.user ? preference.user.login : ''}</td>
                <td>{preference.energie ? preference.energie.nomSystemEnergitique : ''}</td>
                <td className="text-end">
                  <div className="btn-group flex-btn-group-container">
                    {/* <Button tag={Link} to={`/preference/${preference.id}`} className="custom-button-view" size="sm" data-cy="entityDetailsButton">
                      <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                    </Button> */}
                    <Button  onClick={() => handleSetShow(preference)} className="custom-button-edit" size="sm" data-cy="entityEditButton">
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
    <Pagination>
          {Array.from({ length: Math.ceil(preferenceList.length / itemsPerPage) }).map(
            (_, index) => (
              <Pagination.Item
                key={index + 1}
                active={index + 1 === currentPage}
                onClick={() => paginate(index + 1)}
              >
                {index + 1}
              </Pagination.Item>
            )
          )}
        </Pagination>
  </div>
  <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Edit Preference</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Row className="justify-content-center">
            <Col md="8">
              {loading ? (
                <p>Loading...</p>
              ) : (
                <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              
              <ValidatedField label="Temp Min Value" id="preference-tempMinValue" name="tempMinValue" data-cy="tempMinValue" type="text" />
              <ValidatedField label="Temp Max Value" id="preference-tempMaxValue" name="tempMaxValue" data-cy="tempMaxValue" type="text" />
              <ValidatedField label="Plage Horaire" id="preference-plageHoraire" name="plageHoraire" data-cy="plageHoraire" type="text" />
              <ValidatedField id="preference-energie" name="energie" data-cy="energie" label="Energie" type="select">
                <option value="" key="0" />
                {energies
                  ? energies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nomSystemEnergitique}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/energie" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
              )}
            </Col>
          </Row>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
</div>

  );
};

export default Preference;
