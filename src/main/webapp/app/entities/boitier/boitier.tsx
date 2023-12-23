import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate, useParams } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import 'app/shared/layout/customStyles/customStyles.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {Col, Modal, Row} from "react-bootstrap";
import {  ValidatedField, ValidatedForm } from 'react-jhipster';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities, getEntity, updateEntity } from './boitier.reducer';
import { getEntities as getBoitiersDetail } from 'app/entities/capteur-boitier/capteur-boitier.reducer';
import {  Pagination ,Form, FormControl} from 'react-bootstrap';





export const Boitier = () => {
  const dispatch = useAppDispatch();
  const boitierList = useAppSelector(state => state.boitier.entities);
  const loading = useAppSelector(state => state.boitier.loading);
  const updating = useAppSelector(state => state.boitier.updating);
  const updateSuccess = useAppSelector(state => state.boitier.updateSuccess);
  const [selectedBoitier, setSelectedBoitier] = useState(null);
  const capteurBoitierList = useAppSelector(state => state.capteurBoitier.entities);
  const [searchQuery, setSearchQuery] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 5; // Set the number of items per page
  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value);
    setCurrentPage(1); // Reset to the first page when the search query changes
  };

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;

  const filteredBoitierList = boitierList
    .filter((boitier) =>
      boitier.boitierReference.toLowerCase().includes(searchQuery.toLowerCase())
    )
    .slice(indexOfFirstItem, indexOfLastItem);

  const paginate = (pageNumber) => {
    setCurrentPage(pageNumber);
  };


  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };
  useEffect(() => {
    dispatch(getBoitiersDetail({}));
  }, []);
  const handleSyncListBoitiersDetail = () => {
    dispatch(getBoitiersDetail({}));
  };
 

 


  const { id } = useParams<'id'>();
  const boitierEntity = useAppSelector(state => state.boitier.entity);
  const [show, setShow] = useState(false);
  const [showview, setShowView] = useState(false);
  const handleClose = () => setShow(false);
  const handleCloseView = () => setShowView(false);
  const handleShowView = (boitier) => {
    setSelectedBoitier(boitier);
    setShowView(true);
  };
  const handleSetShow = (boitier) => {
    setSelectedBoitier(boitier);
    dispatch(getEntity(boitier.id)); // Déplacez la logique ici
    setShow(true);
  };
  // console.log(capteurBoitierList);
 

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...boitierEntity,
      ...values,
    };

    dispatch(updateEntity(entity));

  };

  const defaultValues = () => boitierEntity;
  // console.log("capteurBoitierList: " + JSON.stringify(capteurBoitierList, null, 2));
  

  


  return (
    <div>
      <h2 id="boitier-heading" data-cy="BoitierHeading">
        Boitiers
        <div className="d-flex justify-content-end">
          <Button className="me-2 btn-light custom-button-refresh" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/boitier/new" className="btn btn-light jh-create-entity custom-button-new" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; New Boitier
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
      <Form >
        <FormControl
          type="text"
          placeholder="Search Boitiers..."
          className="mr-sm-2"
          value={searchQuery}
          onChange={handleSearchChange}
        />
      </Form>
      <br></br>
      {filteredBoitierList && filteredBoitierList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Boitier reference</th>
                <th>Type</th>
                <th>Branch number  </th>
                <th style={{ textAlign: 'center' }}>Action </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {filteredBoitierList.map((boitier, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                      {boitier.id}
                  </td>
                  <td>{boitier.boitierReference}</td>
                  <td>{boitier.type}</td>
                  <td>{boitier.nbrBranche}</td>
                  <td style={{ textAlign: 'center' }}>
                    <div className="btn-group flex-btn-group-container">
                      <Button  onClick={()=>handleShowView(boitier)} size="sm" className="custom-button-view"  data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button onClick={() => handleSetShow(boitier)} size="sm" className="custom-button-edit" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/boitier/${boitier.id}/delete`} size="sm" className="custom-button-delete" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
          
        ) : (
          !loading && <div className="alert alert-warning">No Boitiers found</div>
        )}
        <Pagination>
          {Array.from({ length: Math.ceil(boitierList.length / itemsPerPage) }).map(
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



      <Modal show={showview} onHide={handleCloseView}>
        <Modal.Header closeButton>
          <Modal.Title>Boitier Details</Modal.Title>
        </Modal.Header>
        <Modal.Body>
        <table className="table">
  <thead >
    <tr>
      
      <th className="header-table">Sensor Type</th>
      <th className="header-table">Sensor Reference</th>
      <th  className="header-table">Branch</th>
      <th className="header-table">Image</th>
     
    </tr>
  </thead>

 
  <tbody>
  {capteurBoitierList.map((capteurBoitier, i) => {
    // Check if a matching boitier was found
    if (selectedBoitier && capteurBoitier.boitier.id === selectedBoitier.id) {
      return (
        <tr key={`entity-${i}`} data-cy="entityTable">
          
          <td>{capteurBoitier.capteur?.type || ''}</td>
          <td>{capteurBoitier.capteur?.capteurReference || ''}</td>
          <td>{capteurBoitier.branche || ''}</td>
          <td>
  {capteurBoitier.capteur?.photo ? (
    <img
      src={`data:${capteurBoitier.capteur.photoContentType};base64,${capteurBoitier.capteur.photo}`}
      alt="Sensor Photo"
      style={{ maxWidth: '100px', maxHeight: '100px' }} // Adjust the values as needed
    />
  ) : null}
</td>

        </tr>
      );
    }
    return null; // Return null for non-matching entries
  })}
</tbody>



</table>

          
          
        </Modal.Body>
        <Modal.Footer>
        <Button color="primary" onClick={handleCloseView}>
          Close
        </Button>
        </Modal.Footer>
      </Modal>


      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Edit Boitier</Modal.Title>
        </Modal.Header>
        <Modal.Body>

      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              <ValidatedField
                label="Boitier Reference"
                id="boitier-boitierReference"
                name="boitierReference"
                data-cy="boitierReference"
                type="text"
              />
              <ValidatedField label="Type" id="boitier-type" name="type" data-cy="type" type="text" />
              <ValidatedField label="Branchs number" id="boitier-nbrBranche" name="nbrBranche" data-cy="nbrBranche" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/boitier" replace color="info">
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
        <Button  color="primary" onClick={handleClose}>
          Close
        </Button>
        </Modal.Footer>
        </Modal>
    </div>
  );
};

export default Boitier;
