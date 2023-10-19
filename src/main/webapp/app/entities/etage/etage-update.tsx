import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBatiment } from 'app/shared/model/batiment.model';
import { getEntities as getBatiments } from 'app/entities/batiment/batiment.reducer';
import { IEtage } from 'app/shared/model/etage.model';
import { getEntity, updateEntity, createEntity, reset } from './etage.reducer';

export const EtageUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const batiments = useAppSelector(state => state.batiment.entities);
  const etageEntity = useAppSelector(state => state.etage.entity);
  const loading = useAppSelector(state => state.etage.loading);
  const updating = useAppSelector(state => state.etage.updating);
  const updateSuccess = useAppSelector(state => state.etage.updateSuccess);

  const handleClose = () => {
    navigate('/etage');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBatiments({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...etageEntity,
      ...values,
      batiment: batiments.find(it => it.id.toString() === values.batiment.toString()),
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
          ...etageEntity,
          batiment: etageEntity?.batiment?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="feOptimisationEnergieApp.etage.home.createOrEditLabel" data-cy="EtageCreateUpdateHeading">
            Create or edit a Etage
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="etage-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Etage Numero" id="etage-etageNumero" name="etageNumero" data-cy="etageNumero" type="text" />
              <ValidatedField id="etage-batiment" name="batiment" data-cy="batiment" label="Batiment" type="select">
                <option value="" key="0" />
                {batiments
                  ? batiments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
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
    </div>
  );
};

export default EtageUpdate;
