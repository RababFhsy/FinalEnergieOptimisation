import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICapteur } from 'app/shared/model/capteur.model';
import { getEntity, updateEntity, createEntity, reset } from './capteur.reducer';

export const CapteurUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const capteurEntity = useAppSelector(state => state.capteur.entity);
  const loading = useAppSelector(state => state.capteur.loading);
  const updating = useAppSelector(state => state.capteur.updating);
  const updateSuccess = useAppSelector(state => state.capteur.updateSuccess);

  const handleClose = () => {
    navigate('/capteur');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

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

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...capteurEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="feOptimisationEnergieApp.capteur.home.createOrEditLabel" data-cy="CapteurCreateUpdateHeading">
            Create or edit a Capteur
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="capteur-id" label="ID" validate={{ required: true }} /> : null}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" className="custom-button-save-back"  to="/capteur" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" className="custom-button-save-back"  type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CapteurUpdate;
