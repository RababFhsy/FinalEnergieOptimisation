import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate, useParams } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {Col, Image, Modal, Row} from "react-bootstrap";
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';

import 'app/shared/layout/customStyles/customStyles.scss';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICapteur } from 'app/shared/model/capteur.model';
import { getEntities, getEntity, updateEntity } from './capteur.reducer';
import './capteur.scss';

export const Capteur = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const capteurList = useAppSelector(state => state.capteur.entities);
  const loading = useAppSelector(state => state.capteur.loading);
  

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const capteurEntity = useAppSelector((state) => state.capteur.entity);
  const { id } = useParams<{ id: string }>();
  const [show, setShow] = useState(false);
  const [showUpdate, setShowUpdate] = useState(false);
  const [selectedCapteur, setSelectedCapteur] = useState(null);

  const handleClose = () => setShow(false);
  const handleShow = (capteur) => {
    setSelectedCapteur(capteur);
    setShow(true);
  };

  const handleCloseUpdate = () => setShowUpdate(false);
  const handleShowUpdate = (capteur) => {
    setSelectedCapteur(capteur);
    dispatch(getEntity(capteur.id)); // Déplacez la logique ici
    setShowUpdate(true);
  };

  

  const updating = useAppSelector(state => state.capteur.updating);
  const updateSuccess = useAppSelector(state => state.capteur.updateSuccess);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...capteurEntity,
      ...values,
    };
  
    dispatch(updateEntity(entity));
  };
    const defaultValues = () => capteurEntity;

  return (
    <div>
      <h2 id="capteur-heading" data-cy="CapteurHeading">
        Sensors
        <div className="d-flex justify-content-end">
        <Button className="me-2 btn-light custom-button-refresh" onClick={handleSyncList} disabled={loading}>
  <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
</Button>

<Link
  to="/capteur/new" className="btn btn-light jh-create-entity custom-button-new" id="jh-create-entity" data-cy="entityCreateButton" >
  <FontAwesomeIcon icon="plus" />
  &nbsp;  New Sensor
</Link>

        </div>
      </h2>
      <div className="custom-table" >
        {capteurList && capteurList.length > 0 ? (
          <Table   responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Sensor Reference</th>
                <th>Type</th>
                <th>Image</th>
                <th>Min Value</th>
                <th> Max Value</th>
                <th style={{ textAlign: 'center' }}>      Action</th>
               
              </tr>
            </thead>
            <tbody>
              {capteurList.map((capteur, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                      {capteur.id}
                  </td>
                  <td>{capteur.capteurReference}</td>
                  <td>{capteur.type}</td>
                  <td>
                    {capteur.photo ? (
                      <div>
                        {capteur.photoContentType ? (
                          <a onClick={openFile(capteur.photoContentType, capteur.photo)}>
                            <img src={`data:${capteur.photoContentType};base64,${capteur.photo}`}  />
                            &nbsp;
                          </a>
                        ) : null}
                        {/* <span>
                          {capteur.photoContentType}, {byteSize(capteur.photo)}
                        </span> */}
                      </div>
                    ) : null}
                  </td>
                  <td>{capteur.valeurMin}</td>
                  <td>{capteur.valeurMax}</td>
                  <td style={{ textAlign: 'center' }}>
                    <div className="btn-group flex-btn-group-container">
                    <Button onClick={() => handleShow(capteur)} size="sm" data-cy="entityDetailsButton" className="custom-button-view" >
                      <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                    </Button>


                      <Button onClick={() => handleShowUpdate(capteur)} size="sm" data-cy="entityEditButton" className="custom-button-edit">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>

                      <Button tag={Link} to={`/capteur/${capteur.id}/delete`}  size="sm" data-cy="entityDeleteButton" className="custom-button-delete">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Sensors found</div>
        )}
      </div>
      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Sonsor Details</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Row>
            <Col md="12">
              <dl className="jh-entity-details">
                <dt>ID</dt>
                <dd>{selectedCapteur?.id}</dd>
                <dt>Sonsor Reference</dt>
                <dd>{selectedCapteur?.capteurReference}</dd>
                <dt>Sonsor Type</dt>
                <dd>{selectedCapteur?.type}</dd>
                <dt>Sonsor Image</dt>
                <dd>
                  {selectedCapteur?.photo ? (
                    <div>
                      {selectedCapteur.photoContentType ? (
                        <Image src={`data:${selectedCapteur.photoContentType};base64,${selectedCapteur.photo}`} style={{ maxHeight: '200px' }} />
                      ) : null}

                    </div>
                  ) : null}
                </dd>
                <dt>Min Value</dt>
                <dd>{selectedCapteur?.valeurMin}</dd>
                <dt>Max Value</dt>
                <dd>{selectedCapteur?.valeurMax}</dd>
              </dl>
            </Col>
          </Row>
        </Modal.Body>
        <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          Close
        </Button>
        </Modal.Footer>
      </Modal>


      <Modal show={showUpdate} onHide={handleCloseUpdate}>
      <Modal.Header closeButton>
        <Modal.Title>Edit a Sensor</Modal.Title>
      </Modal.Header>
      <Modal.Body>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={(values) => { saveEntity(values); handleCloseUpdate(); }}>
              <ValidatedField
                label="Capteur Reference"
                id="capteur-capteurReference"
                name="capteurReference"
                data-cy="capteurReference"
                type="text"
              />
              <ValidatedField label="Type" id="capteur-type" name="type" data-cy="type" type="text" />
              <ValidatedBlobField label="Photo" id="capteur-photo" name="photo" data-cy="photo" isImage accept="image/*" />
              <ValidatedField label="Valeur Min" id="capteur-valeurMin" name="valeurMin" data-cy="valeurMin" type="text" />
              <ValidatedField label="Valeur Max" id="capteur-valeurMax" name="valeurMax" data-cy="valeurMax" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/capteur" replace color="info">
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

export default Capteur;
