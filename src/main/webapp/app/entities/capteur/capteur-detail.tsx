import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Modal, Row, Col, Image } from 'react-bootstrap';
import { openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './capteur.reducer';

export const CapteurDetail = () => {
  const dispatch = useAppDispatch();
  const { id } = useParams<{ id: string }>();
  const [show, setShow] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const capteurEntity = useAppSelector(state => state.capteur.entity);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  return (
    <div>
      <h2 data-cy="capteurDetailsHeading">Capteur</h2>
      <Button variant="primary" onClick={handleShow}>
        Open Details Popup
      </Button>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Capteur Details</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Row>
            <Col md="12">
              <dl className="jh-entity-details">
                <dt>
                  <span id="id">ID</span>
                </dt>
                <dd>{capteurEntity.id}</dd>
                <dt>
                  <span id="capteurReference">Capteur Reference</span>
                </dt>
                <dd>{capteurEntity.capteurReference}</dd>
                <dt>
                  <span id="type">Type</span>
                </dt>
                <dd>{capteurEntity.type}</dd>
                <dt>
                  <span id="photo">Photo</span>
                </dt>
                <dd>
                  {capteurEntity.photo ? (
                    <div>
                      {capteurEntity.photoContentType ? (
                        <Image src={`data:${capteurEntity.photoContentType};base64,${capteurEntity.photo}`} style={{ maxHeight: '200px' }} />
                      ) : null}
                      <span>
                        {capteurEntity.photoContentType}, {byteSize(capteurEntity.photo)}
                      </span>
                    </div>
                  ) : null}
                </dd>
                <dt>
                  <span id="valeurMin">Valeur Min</span>
                </dt>
                <dd>{capteurEntity.valeurMin}</dd>
                <dt>
                  <span id="valeurMax">Valeur Max</span>
                </dt>
                <dd>{capteurEntity.valeurMax}</dd>
              </dl>
            </Col>
          </Row>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
          <Link to={`/capteur/${capteurEntity.id}/edit`} replace className="btn btn-primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Link>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default CapteurDetail;
