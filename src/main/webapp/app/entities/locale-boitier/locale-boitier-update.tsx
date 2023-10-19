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
import { IBoitier } from 'app/shared/model/boitier.model';
import { getEntities as getBoitiers } from 'app/entities/boitier/boitier.reducer';
import { ILocaleBoitier } from 'app/shared/model/locale-boitier.model';
import { getEntity, updateEntity, createEntity, reset } from './locale-boitier.reducer';

export const LocaleBoitierUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const locales = useAppSelector(state => state.locale.entities);
  const boitiers = useAppSelector(state => state.boitier.entities);
  const localeBoitierEntity = useAppSelector(state => state.localeBoitier.entity);
  const loading = useAppSelector(state => state.localeBoitier.loading);
  const updating = useAppSelector(state => state.localeBoitier.updating);
  const updateSuccess = useAppSelector(state => state.localeBoitier.updateSuccess);

  const handleClose = () => {
    navigate('/locale-boitier');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLocales({}));
    dispatch(getBoitiers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...localeBoitierEntity,
      ...values,
      locale: locales.find(it => it.id.toString() === values.locale.toString()),
      boitier: boitiers.find(it => it.id.toString() === values.boitier.toString()),
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
          ...localeBoitierEntity,
          locale: localeBoitierEntity?.locale?.id,
          boitier: localeBoitierEntity?.boitier?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="feOptimisationEnergieApp.localeBoitier.home.createOrEditLabel" data-cy="LocaleBoitierCreateUpdateHeading">
            Create or edit a Locale Boitier
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="locale-boitier-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Date Debut" id="locale-boitier-dateDebut" name="dateDebut" data-cy="dateDebut" type="date" />
              <ValidatedField label="Date Fin" id="locale-boitier-dateFin" name="dateFin" data-cy="dateFin" type="date" />
              <ValidatedField id="locale-boitier-locale" name="locale" data-cy="locale" label="Locale" type="select">
                <option value="" key="0" />
                {locales
                  ? locales.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="locale-boitier-boitier" name="boitier" data-cy="boitier" label="Boitier" type="select">
                <option value="" key="0" />
                {boitiers
                  ? boitiers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/locale-boitier" replace color="info">
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

export default LocaleBoitierUpdate;
