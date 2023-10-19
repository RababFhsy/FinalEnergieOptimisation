import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBatiment } from 'app/shared/model/batiment.model';
import { getEntity, updateEntity, createEntity, reset } from './batiment.reducer';

export const BatimentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const batimentEntity = useAppSelector(state => state.batiment.entity);
  const loading = useAppSelector(state => state.batiment.loading);
  const updating = useAppSelector(state => state.batiment.updating);
  const updateSuccess = useAppSelector(state => state.batiment.updateSuccess);

  const handleClose = () => {
    navigate('/batiment');
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
      ...batimentEntity,
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
          ...batimentEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="feOptimisationEnergieApp.batiment.home.createOrEditLabel" data-cy="BatimentCreateUpdateHeading">
            Create or edit a Batiment
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="batiment-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Adresse" id="batiment-adresse" name="adresse" data-cy="adresse" type="text" />
              <ValidatedField label="Batiment Nom" id="batiment-batimentNom" name="batimentNom" data-cy="batimentNom" type="text" />
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
    </div>
  );
};

export default BatimentUpdate;
