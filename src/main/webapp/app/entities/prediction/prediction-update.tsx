import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILocale } from 'app/shared/model/locale.model';
import { getEntities as getLocales } from 'app/entities/locale/locale.reducer';
import { IEnergie } from 'app/shared/model/energie.model';
import { getEntities as getEnergies } from 'app/entities/energie/energie.reducer';
import { IPrediction } from 'app/shared/model/prediction.model';
import { getEntity, updateEntity, createEntity, reset } from './prediction.reducer';

export const PredictionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const locales = useAppSelector(state => state.locale.entities);
  const energies = useAppSelector(state => state.energie.entities);
  const predictionEntity = useAppSelector(state => state.prediction.entity);
  const loading = useAppSelector(state => state.prediction.loading);
  const updating = useAppSelector(state => state.prediction.updating);
  const updateSuccess = useAppSelector(state => state.prediction.updateSuccess);

  const handleClose = () => {
    navigate('/prediction');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLocales({}));
    dispatch(getEnergies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...predictionEntity,
      ...values,
      locale: locales.find(it => it.id.toString() === values.locale.toString()),
      energie: energies.find(it => it.id.toString() === values.energie.toString()),
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
          ...predictionEntity,
          locale: predictionEntity?.locale?.id,
          energie: predictionEntity?.energie?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="feOptimisationEnergieApp.prediction.home.createOrEditLabel" data-cy="PredictionCreateUpdateHeading">
            Create or edit a Prediction
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="prediction-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Date Debut" id="prediction-dateDebut" name="dateDebut" data-cy="dateDebut" type="date" />
              <ValidatedField label="Date Fin" id="prediction-dateFin" name="dateFin" data-cy="dateFin" type="date" />
              <ValidatedField
                label="Consommation Predit"
                id="prediction-consommationPredit"
                name="consommationPredit"
                data-cy="consommationPredit"
                type="text"
              />
              <ValidatedField label="Precision" id="prediction-precision" name="precision" data-cy="precision" type="text" />
              <ValidatedField id="prediction-locale" name="locale" data-cy="locale" label="Locale" type="select">
                <option value="" key="0" />
                {locales
                  ? locales.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="prediction-energie" name="energie" data-cy="energie" label="Energie" type="select">
                <option value="" key="0" />
                {energies
                  ? energies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/prediction" replace color="info">
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

export default PredictionUpdate;
