import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate,useParams  } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEnergie } from 'app/shared/model/energie.model';
import { getEntities } from './energie.reducer';
import {  Pagination,Form,FormControl } from 'react-bootstrap';
import {   Modal ,Col,Row } from 'react-bootstrap';
import { getEntity, updateEntity, createEntity, reset } from './energie.reducer';




export const Energie = () => {
  const dispatch = useAppDispatch();
  const { id } = useParams<'id'>();
  const isNew = id === undefined;
  const location = useLocation();
  const navigate = useNavigate();
  const energieEntity = useAppSelector(state => state.energie.entity);
  const energies = useAppSelector(state => state.energie.entities);
  const energieList = useAppSelector(state => state.energie.entities);
  const loading = useAppSelector(state => state.energie.loading);
  const [selectedEnergie, setSelectedEnergie] = useState(null);
  const handleSetShow = energie => {
    setSelectedEnergie(energie );
    dispatch(getEntity(energie.id)); // Déplacez la logique ici
    setShow(true);
  };
  const [show, setShow] = useState(false);
  const [showview, setShowView] = useState(false);
  const handleClose = () => setShow(false);
  const handleCloseView = () => setShowView(false);
  const [searchQuery, setSearchQuery] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 5; // Set the number of items per page
  const updating = useAppSelector(state => state.energie.updating);
  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value);
    setCurrentPage(1); // Reset to the first page when the search query changes
  };

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;

  const filteredenergieList = energieList
    .filter((energie) =>
    energie.nomSystemEnergitique.toLowerCase().includes(searchQuery.toLowerCase())
    )
    .slice(indexOfFirstItem, indexOfLastItem);

  const paginate = (pageNumber) => {
    setCurrentPage(pageNumber);
  };
  const handleShowView = energie => {
    setSelectedEnergie(energie);
    setShowView(true);
  };

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };
 


 
  const saveEntity = values => {
    const entity = {
      ...energieEntity,
      ...values,
    };
  
    dispatch(updateEntity(entity));
  };

  const defaultValues = () => energieEntity;

  return (
    <div>
      <h2 id="energie-heading" data-cy="EnergieHeading">
        Energies
        <div className="d-flex justify-content-end">
          <Button className="me-2 btn-light custom-button-refresh" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/energie/new" className="btn btn-light jh-create-entity custom-button-new"  id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; new Energie
          </Link>
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
        {filteredenergieList && filteredenergieList.length > 0 ? (
          <Table responsive>
            
            <thead>
              <tr>
                
                <th>Energy System Name</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {filteredenergieList.map((energie, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                
                  <td>{energie.nomSystemEnergitique}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      {/* <Button  className="custom-button-view" onClick={() => handleShowView(energie)} size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button> */}
                      <Button className="custom-button-edit"  onClick={() => handleSetShow(energie)} size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button  className="custom-button-delete" tag={Link} to={`/energie/${energie.id}/delete`}  size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Energies found</div>
        )}
        <Pagination>
          {Array.from({ length: Math.ceil(energieList.length / itemsPerPage) }).map(
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
          <Modal.Title>Edit Energy System</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Row className="justify-content-center">
            <Col md="8">
              {loading ? (
                <p>Loading...</p>
              ) : (
                <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              
              <ValidatedField
                label="Nom System Energitique"
                id="energie-nomSystemEnergitique"
                name="nomSystemEnergitique"
                data-cy="nomSystemEnergitique"
                type="text"
              />
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

export default Energie;
