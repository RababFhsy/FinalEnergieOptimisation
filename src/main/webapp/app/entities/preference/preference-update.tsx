import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IEnergie } from 'app/shared/model/energie.model';
import { getEntities as getEnergies } from 'app/entities/energie/energie.reducer';
import { IPreference } from 'app/shared/model/preference.model';
import { getEntity, updateEntity, createEntity, reset } from './preference.reducer';

export const PreferenceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const energies = useAppSelector(state => state.energie.entities);
  const preferenceEntity = useAppSelector(state => state.preference.entity);
  const loading = useAppSelector(state => state.preference.loading);
  const updating = useAppSelector(state => state.preference.updating);
  const updateSuccess = useAppSelector(state => state.preference.updateSuccess);

  const handleClose = () => {
    navigate('/preference');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getEnergies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...preferenceEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
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
          ...preferenceEntity,
          user: preferenceEntity?.user?.id,
          energie: preferenceEntity?.energie?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="feOptimisationEnergieApp.preference.home.createOrEditLabel" data-cy="PreferenceCreateUpdateHeading">
            Create or edit a Preference
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="preference-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Temp Min Value" id="preference-tempMinValue" name="tempMinValue" data-cy="tempMinValue" type="text" />
              <ValidatedField label="Temp Max Value" id="preference-tempMaxValue" name="tempMaxValue" data-cy="tempMaxValue" type="text" />
              <ValidatedField label="Plage Horaire" id="preference-plageHoraire" name="plageHoraire" data-cy="plageHoraire" type="text" />
              <ValidatedField id="preference-user" name="user" data-cy="user" label="User" type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="preference-energie" name="energie" data-cy="energie" label="Energie" type="select">
                <option value="" key="0" />
                {energies
                  ? energies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nomSystemEnergitique}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/preference" replace color="info">
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

export default PreferenceUpdate;
