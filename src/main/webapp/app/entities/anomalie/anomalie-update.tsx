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
import { IAnomalie } from 'app/shared/model/anomalie.model';
import { getEntity, updateEntity, createEntity, reset } from './anomalie.reducer';

export const AnomalieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const locales = useAppSelector(state => state.locale.entities);
  const energies = useAppSelector(state => state.energie.entities);
  const anomalieEntity = useAppSelector(state => state.anomalie.entity);
  const loading = useAppSelector(state => state.anomalie.loading);
  const updating = useAppSelector(state => state.anomalie.updating);
  const updateSuccess = useAppSelector(state => state.anomalie.updateSuccess);

  const handleClose = () => {
    navigate('/anomalie');
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
      ...anomalieEntity,
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
          ...anomalieEntity,
          locale: anomalieEntity?.locale?.id,
          energie: anomalieEntity?.energie?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="feOptimisationEnergieApp.anomalie.home.createOrEditLabel" data-cy="AnomalieCreateUpdateHeading">
            Create or edit a Anomalie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="anomalie-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Zone Normale Min"
                id="anomalie-zoneNormaleMin"
                name="zoneNormaleMin"
                data-cy="zoneNormaleMin"
                type="text"
              />
              <ValidatedField
                label="Zone Normale Max"
                id="anomalie-zoneNormaleMax"
                name="zoneNormaleMax"
                data-cy="zoneNormaleMax"
                type="text"
              />
              <ValidatedField label="Date Anomalie" id="anomalie-dateAnomalie" name="dateAnomalie" data-cy="dateAnomalie" type="date" />
              <ValidatedField
                label="Description Anomalie"
                id="anomalie-descriptionAnomalie"
                name="descriptionAnomalie"
                data-cy="descriptionAnomalie"
                type="text"
              />
              <ValidatedField id="anomalie-locale" name="locale" data-cy="locale" label="Locale" type="select">
                <option value="" key="0" />
                {locales
                  ? locales.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="anomalie-energie" name="energie" data-cy="energie" label="Energie" type="select">
                <option value="" key="0" />
                {energies
                  ? energies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/anomalie" replace color="info">
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

export default AnomalieUpdate;
