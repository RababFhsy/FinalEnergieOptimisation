import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBoitier } from 'app/shared/model/boitier.model';
import { getEntity, updateEntity, createEntity, reset } from './boitier.reducer';

export const BoitierUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const boitierEntity = useAppSelector(state => state.boitier.entity);
  const loading = useAppSelector(state => state.boitier.loading);
  const updating = useAppSelector(state => state.boitier.updating);
  const updateSuccess = useAppSelector(state => state.boitier.updateSuccess);

  const handleClose = () => {
    navigate('/boitier');
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
      ...boitierEntity,
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
          ...boitierEntity,
        };

  return (
    <div>
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
              {!isNew ? <ValidatedField name="id" required readOnly id="boitier-id" label="ID" validate={{ required: true }} /> : null}
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
    </div>
  );
};

export default BoitierUpdate;