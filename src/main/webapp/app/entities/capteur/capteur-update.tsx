import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Form, FormGroup, Label, Input, Col } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
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
    <div className="custom-container" style={{ maxWidth: '1000px', margin: '0 auto', padding: '50px' }}>
      <h2 style={{ marginBottom: '20px' }}>Create Sensor</h2>
      <div className="row justify-content-center">
        <div className="col-md-8">
          <Form>
            {!isNew ? (
              <FormGroup>
                <Label for="capteur-id">ID</Label>
                <Input type="text" name="id" id="capteur-id" readOnly />
              </FormGroup>
            ) : null}
            <FormGroup>
              <Label for="capteur-capteurReference">Sensor Reference</Label>
              <Input type="text" name="capteurReference" id="capteur-capteurReference" />
            </FormGroup>
            <FormGroup>
              <Label for="capteur-type">Type</Label>
              <Input type="text" name="type" id="capteur-type" />
            </FormGroup>
            <FormGroup>
              <Label for="capteur-photo">Sensor Image</Label>
              <Input type="file" name="photo" id="capteur-photo" accept="image/*" />
            </FormGroup>
            <FormGroup>
              <Label for="capteur-valeurMin">Min Value</Label>
              <Input type="text" name="valeurMin" id="capteur-valeurMin" />
            </FormGroup>
            <FormGroup>
              <Label for="capteur-valeurMax">Max Value</Label>
              <Input type="text" name="valeurMax" id="capteur-valeurMax" />
            </FormGroup>
            <Button tag={Link} to="/capteur" replace color="info">
              <FontAwesomeIcon icon="arrow-left" />
              &nbsp;
              <span>Back</span>
            </Button>
            &nbsp;
            <Button color="primary" type="submit" disabled={updating}>
              <FontAwesomeIcon icon="save" />
              &nbsp; Save
            </Button>
          </Form>
        </div>
      </div>
    </div>
  );
};

export default CapteurUpdate;
