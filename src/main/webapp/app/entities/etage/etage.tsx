import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate, useParams } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Col, Image, Modal, Row } from 'react-bootstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getBatiments } from 'app/entities/batiment/batiment.reducer';

import { IEtage } from 'app/shared/model/etage.model';
import { getEntities, getEntity, updateEntity } from './etage.reducer';

export const Etage = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();



  const etageList = useAppSelector(state => state.etage.entities);
  const loading = useAppSelector(state => state.etage.loading);
  const updating = useAppSelector(state => state.etage.updating);
  const updateSuccess = useAppSelector(state => state.etage.updateSuccess);

  const [selectedEtage, setSelectedEtage] = useState(null);
  const batiments = useAppSelector(state => state.batiment.entities);
  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { id } = useParams<'id'>();

  const etageEntity = useAppSelector(state => state.etage.entity);
  const [show, setShow] = useState(false);
  const [showview, setShowView] = useState(false);
  const handleClose = () => setShow(false);
  const handleCloseView = () => setShowView(false);
  const handleShowView = etage => {
    setSelectedEtage(etage);
    setShowView(true);
  };
  const handleSetShow = etage => {
    setSelectedEtage(etage);
    dispatch(getEntity(etage.id)); // Déplacez la logique ici
    setShow(true);
  };

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
    dispatch(getBatiments({}));
  }, [updateSuccess]);



  const saveEntity = values => {
    const entity = {
      ...etageEntity,
      ...values,
      batiment: batiments.find(it => it.id.toString() === values.batiment.toString()),

    };

    dispatch(updateEntity(entity));
  };

  const defaultValues = () => etageEntity;

  return (
    <div>
      <h2 id="etage-heading" data-cy="EtageHeading">
        Floors
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/etage/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; new Floor
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {etageList && etageList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Floor Number</th>
                <th>Building</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {etageList.map((etage, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>{etage.id}</td>
                  <td>{etage.etageNumero}</td>
                  <td>{etage.batiment ? etage.batiment.batimentNom : ''}</td>

                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button onClick={() => handleShowView(etage)} size="sm" data-cy="entityDetailsButton" className="custom-button-view">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button onClick={() => handleSetShow(etage)} size="sm" data-cy="entityEditButton" className="custom-button-edit">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/etage/${etage.id}/delete`}
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
          !loading && <div className="alert alert-warning">No Floors found</div>
        )}
      </div>
      <Modal show={showview} onHide={handleCloseView}>
        <Modal.Header closeButton>
          <Modal.Title>Floor Details</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Row>
            <Col md="12">
              <dl className="jh-entity-details">
                <dt>ID</dt>
                <dd>{selectedEtage?.id}</dd>
                <dt>Floor Number</dt>
                <dd>{selectedEtage?.etageNumero}</dd>
                <dt>Building</dt>
                <dd>{selectedEtage?.batiment.batimentNom}</dd>
              </dl>
            </Col>
          </Row>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseView}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Edit Floor</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Row className="justify-content-center">
            <Col md="8">
              {loading ? (
                <p>Loading...</p>
              ) : (
                <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>

                  <ValidatedField label="Floor Number" id="etage-etageNumero" name="etageNumero" data-cy="etageNumero" type="text" />
                  <ValidatedField id="etage-batiment" name="batiment" data-cy="batiment" label="Building" type="select">
                    <option value="" key="0" />
                    {batiments
                      ? batiments.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.batimentNom}
                          </option>
                        ))
                      : null}
                  </ValidatedField>
                  <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/etage" replace color="info">
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

export default Etage;
