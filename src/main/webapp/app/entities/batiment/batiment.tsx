import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate, useParams } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Col, Image, Modal, Row } from 'react-bootstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBatiment } from 'app/shared/model/batiment.model';
import { getEntities, getEntity, updateEntity } from './batiment.reducer';

export const Batiment = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const batimentList = useAppSelector(state => state.batiment.entities);
  const loading = useAppSelector(state => state.batiment.loading);
  const updating = useAppSelector(state => state.batiment.updating);
  const updateSuccess = useAppSelector(state => state.batiment.updateSuccess);

  const [selectedBatiment, setSelectedBatiment] = useState(null);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { id } = useParams<'id'>();
  const batimentEntity = useAppSelector(state => state.batiment.entity);
  const [show, setShow] = useState(false);
  const [showview, setShowView] = useState(false);
  const handleClose = () => setShow(false);
  const handleCloseView = () => setShowView(false);
  const handleShowView = batiment => {
    setSelectedBatiment(batiment);
    setShowView(true);
  };
  const handleSetShow = batiment => {
    setSelectedBatiment(batiment);
    dispatch(getEntity(batiment.id)); // Déplacez la logique ici
    setShow(true);
  };

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...batimentEntity,
      ...values,
    };

    dispatch(updateEntity(entity));

  };

  const defaultValues = () => batimentEntity;


  return (
    <div>
      <h2 id="batiment-heading" data-cy="BatimentHeading">
        Batiments
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/batiment/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Batiment
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {batimentList && batimentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Adresse</th>
                <th>Batiment Nom</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {batimentList.map((batiment, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/batiment/${batiment.id}`} color="link" size="sm">
                      {batiment.id}
                    </Button>
                  </td>
                  <td>{batiment.adresse}</td>
                  <td>{batiment.batimentNom}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button onClick={() => handleShowView(batiment)} size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button onClick={() => handleSetShow(batiment)} size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/batiment/${batiment.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Batiments found</div>
        )}
      </div>
      <Modal show={showview} onHide={handleCloseView}>
        <Modal.Header closeButton>
          <Modal.Title>Batiment Details</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <table className="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Adresse</th>
                <th>Nom du Batiment</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{selectedBatiment?.id}</td>

                <td>{selectedBatiment?.adresse}</td>

                <td>{selectedBatiment?.batimentNom}</td>
              </tr>
            </tbody>
          </table>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseView}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Edit Batiment</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Row className="justify-content-center">
            <Col md="8">
              {loading ? (
                <p>Loading...</p>
              ) : (
                <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>

                  <ValidatedField label="Adresse" id="batiment-adresse" name="adresse" data-cy="adresse" type="text" />
                  <ValidatedField label="Nom du Batiment" id="batiment-batimentNom" name="batimentNom" data-cy="batimentNom" type="text" />
                  <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/batiment" replace color="info">
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

export default Batiment;
