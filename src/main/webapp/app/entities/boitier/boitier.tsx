import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate, useParams } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {Col, Image, Modal, Row} from "react-bootstrap";
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBoitier } from 'app/shared/model/boitier.model';
import { getEntities, getEntity, updateEntity } from './boitier.reducer';

export const Boitier = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const boitierList = useAppSelector(state => state.boitier.entities);
  const loading = useAppSelector(state => state.boitier.loading);

  const [selectedBoitier, setSelectedBoitier] = useState(null);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };


  const { id } = useParams<'id'>();

  const boitierEntity = useAppSelector(state => state.boitier.entity);
  const updating = useAppSelector(state => state.boitier.updating);
  const updateSuccess = useAppSelector(state => state.boitier.updateSuccess);
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleSetShow = (boitier) => {
    setSelectedBoitier(boitier);
    dispatch(getEntity(boitier.id)); // Déplacez la logique ici
    setShow(true);
  };

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


  return (
    <div>
      <h2 id="boitier-heading" data-cy="BoitierHeading">
        Boitiers
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/boitier/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Boitier
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {boitierList && boitierList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Boitier Reference</th>
                <th>Type</th>
                <th>Nbr Branche</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {boitierList.map((boitier, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/boitier/${boitier.id}`} color="link" size="sm">
                      {boitier.id}
                    </Button>
                  </td>
                  <td>{boitier.boitierReference}</td>
                  <td>{boitier.type}</td>
                  <td>{boitier.nbrBranche}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/boitier/${boitier.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button onClick={() => handleSetShow(boitier)} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/boitier/${boitier.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
      </div>
      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Sonsor Details</Modal.Title>
        </Modal.Header>
        <Modal.Body>
        <Row className="justify-content-center">
        <Col md="8">
          <h2 id="feOptimisationEnergieApp.boitier.home.createOrEditLabel" data-cy="BoitierCreateUpdateHeading">
            Create or edit a Boitier
          </h2>
        </Col>
      </Row>
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
              <ValidatedField label="Nbr Branche" id="boitier-nbrBranche" name="nbrBranche" data-cy="nbrBranche" type="text" />
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
        </Modal>
    </div>
  );
};

export default Boitier;
