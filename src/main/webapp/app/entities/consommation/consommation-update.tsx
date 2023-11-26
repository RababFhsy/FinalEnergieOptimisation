import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getLocales } from 'app/entities/locale/locale.reducer';
import { getEntities as getEnergies } from 'app/entities/energie/energie.reducer';
import { getEntity, updateEntity, createEntity, reset } from './consommation.reducer';

export const ConsommationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.entities);
  const locales = useAppSelector(state => state.locale.entities);
  const energies = useAppSelector(state => state.energie.entities);
  const consommationEntity = useAppSelector(state => state.consommation.entity);
  const loading = useAppSelector(state => state.consommation.loading);
  const updating = useAppSelector(state => state.consommation.updating);
  const updateSuccess = useAppSelector(state => state.consommation.updateSuccess);

  const handleClose = () => {
    navigate('/consommation');
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
      ...consommationEntity,
      ...values,
      locale: locales.find(it => it.id.toString() === values.locale.toString()),
      energie: energies.find(it => it.id.toString() === values.energie.toString()),
      user: users.find(it => it.id.toString() === values.user.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () => ({
    ...(isNew ? {} : consommationEntity),
    locale: consommationEntity?.locale?.id,
    energie: consommationEntity?.energie?.id,
    user: consommationEntity?.user?.id,
  });

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="feOptimisationEnergieApp.consommation.home.createOrEditLabel" data-cy="ConsommationCreateUpdateHeading">
            {isNew ? 'Create' : 'Edit'} a Consommation
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="consommation-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Energie Consommation"
                id="consommation-energieConsommation"
                name="energieConsommation"
                data-cy="energieConsommation"
                type="text"
              />
              <ValidatedField
                label="Date Consommation"
                id="consommation-dateConsommation"
                name="dateConsommation"
                data-cy="dateConsommation"
                type="date"
              />
              <ValidatedField id="consommation-locale" name="locale" data-cy="locale" label="Locale" type="select">
                <option value="" key="0" />
                {locales
                  ? locales.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="consommation-energie" name="energie" data-cy="energie" label="Energie" type="select">
                <option value="" key="0" />
                {energies
                  ? energies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nomSystemEnergitique}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="consommation-user" name="user" data-cy="user" label="User" type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/consommation" replace color="info">
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

export default ConsommationUpdate;
