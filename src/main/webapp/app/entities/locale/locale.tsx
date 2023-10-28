import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate, useParams } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Col, Image, Modal, Row } from 'react-bootstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { ILocale } from 'app/shared/model/locale.model';
import { getEntities, getEntity, updateEntity } from './locale.reducer';

export const Locale = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const localeList = useAppSelector(state => state.locale.entities);
  const loading = useAppSelector(state => state.locale.loading);
  const updating = useAppSelector(state => state.locale.updating);
  const updateSuccess = useAppSelector(state => state.locale.updateSuccess);

  const [selectedLocale, setSelectedLocale] = useState(null);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };
  const { id } = useParams<'id'>();
  const localeEntity = useAppSelector(state => state.locale.entity);
  const [show, setShow] = useState(false);
  const [showview, setShowView] = useState(false);
  const handleClose = () => setShow(false);
  const handleCloseView = () => setShowView(false);
  const handleShowView = locale => {
    setSelectedLocale(locale);
    setShowView(true);
  };
  const handleSetShow = locale => {
    setSelectedLocale(locale);
    dispatch(getEntity(locale.id)); // Déplacez la logique ici
    setShow(true);
  };

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...localeEntity,
      ...values,
    };

    dispatch(updateEntity(entity));

  };

  const defaultValues = () => localeEntity;

  return (
    <div>
      <h2 id="locale-heading" data-cy="LocaleHeading">
        Locals
        <div className="d-flex justify-content-end ">
          <Button className="me-2 btn-light custom-button-refresh" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/locale/new" className="btn btn-light jh-create-entity custom-button-new" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Local
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {localeList && localeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Numero</th>
                <th>Type Local</th>
                <th>Etage</th>
                <th style={{ textAlign: 'center' }}>      Action</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {localeList.map((locale, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                      {locale.id}
                  </td>
                  <td>{locale.numero}</td>
                  <td>{locale.typeLocal}</td>
                  <td>{locale.etage ? <Link to={`/etage/${locale.etage.id}`}>{locale.etage.id}</Link> : ''}</td>
                  <td style={{ textAlign: 'center' }}>
                    <div className="btn-group flex-btn-group-container">
                      <Button onClick={() => handleShowView(locale)} size="sm" data-cy="entityDetailsButton" className="custom-button-view">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button onClick={() => handleSetShow(locale)}  size="sm" data-cy="entityEditButton" className="custom-button-edit">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/locale/${locale.id}/delete`} size="sm" data-cy="entityDeleteButton" className="custom-button-delete">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Locales found</div>
        )}
      </div>
      <Modal show={showview} onHide={handleCloseView}>
        <Modal.Header closeButton>
          <Modal.Title> Locale Details</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <table className="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Numero</th>
                <th>Type Local</th>
                <th>Etage</th>

              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{selectedLocale?.id}</td>

                <td>{selectedLocale?.numero}</td>

                <td>{selectedLocale?.typeLocal}</td>

                <td>{selectedLocale?.etage}</td>

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
          <Modal.Title>Edit Locale</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Row className="justify-content-center">
            <Col md="8">
              {loading ? (
                <p>Loading...</p>
              ) : (
                <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>

                  <ValidatedField label="Numero" id="locale-numero" name="numero" data-cy="numero" type="text" />
                  <ValidatedField label="Type Local" id="locale-typeLocale" name="typeLocal" data-cy="typeLocale" type="text" />
                  <ValidatedField label="Etage" id="locale-etage" name="etage" data-cy="etage" type="text" />

                  <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/locale" replace color="info">
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

export default Locale;
