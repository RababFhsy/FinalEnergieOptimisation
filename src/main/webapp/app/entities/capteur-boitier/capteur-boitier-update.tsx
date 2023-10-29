import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';


import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICapteur } from 'app/shared/model/capteur.model';
import { getEntities as getCapteurs } from 'app/entities/capteur/capteur.reducer';
import { IBoitier } from 'app/shared/model/boitier.model';
import { getEntities as getBoitiers } from 'app/entities/boitier/boitier.reducer';
import { ICapteurBoitier } from 'app/shared/model/capteur-boitier.model';
import { getEntity, updateEntity, createEntity, reset } from './capteur-boitier.reducer';

export const CapteurBoitierUpdate = () => {
  const dispatch = useAppDispatch();
  const [numberOfBranches, setNumberOfBranches] = useState('');
  const [usedBranches, setUsedBranches] = useState<{ [key: string]: string[] }>({});
  const [availableBranches, setAvailableBranches] = useState([]);
  
  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const capteurs = useAppSelector(state => state.capteur.entities);
  const boitiers = useAppSelector(state => state.boitier.entities);
  const capteurBoitierEntity = useAppSelector(state => state.capteurBoitier.entity);
  const loading = useAppSelector(state => state.capteurBoitier.loading);
  const updating = useAppSelector(state => state.capteurBoitier.updating);
  const updateSuccess = useAppSelector(state => state.capteurBoitier.updateSuccess);
  const capteurBoitierList = useAppSelector(state => state.capteurBoitier.entities);

  const handleClose = () => {
    navigate('/capteur-boitier/new');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCapteurs({}));
    dispatch(getBoitiers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...capteurBoitierEntity,
      ...values,
      capteur: capteurs.find(it => it.id.toString() === values.capteur.toString()),
      boitier: boitiers.find(it => it.id.toString() === values.boitier.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
      const selectedBoitierId = values.boitier.toString();
      const updatedUsedBranches = { ...usedBranches };
      updatedUsedBranches[selectedBoitierId] = [...usedBranches[selectedBoitierId], values.branche];
      setUsedBranches(updatedUsedBranches);

      const updatedAvailableBranches = availableBranches.filter(branch => branch !== values.branche);
      setAvailableBranches(updatedAvailableBranches);
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...capteurBoitierEntity,
          capteur: capteurBoitierEntity?.capteur?.id,
          boitier: capteurBoitierEntity?.boitier?.id,
        };
  const handleBoitierChange = (event) => {
    const selectedBoitierId = event.target.value;
    const selectedBoitier = boitiers.find((it) => it.id.toString() === selectedBoitierId);
    if (selectedBoitier) {
      const usedBranchesForSelectedBoitier = capteurBoitierList
        .filter((item) => item.boitier.id === selectedBoitierId)
        .map((item) => item.branche);
      const remainingBranches = selectedBoitier.nbrBranche - usedBranchesForSelectedBoitier.length;
      setNumberOfBranches(remainingBranches.toString());
      setUsedBranches({ ...usedBranches, [selectedBoitierId]: usedBranchesForSelectedBoitier });
    }
  };
  
  const generateBranchOptions = () => {
    const options = [];
    for (let i = 0; i < parseInt(numberOfBranches); i++) {
      options.push(<option value={`A${i}`}>{`A${i}`}</option>);
    }
    return options;
  };


  return (
    <div className="page-container">
    <div className="container-right" >
          <h3 id="feOptimisationEnergieApp.capteurBoitier.home.createOrEditLabel" data-cy="CapteurBoitierCreateUpdateHeading">
          Assign sensor to Boitier
          </h3>
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="capteur-boitier-id" label="ID" validate={{ required: true }} />
              ) : null}
               <ValidatedField id="capteur-boitier-boitier" name="boitier" data-cy="boitier" label="Boitier" type="select" onChange={handleBoitierChange}>
                <option value="" key="0" />
                {boitiers
                  ? boitiers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.type}>
                        {otherEntity.type}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="capteur-boitier-boitier" name="nbrBranch" data-cy="nbrBranch" label="Number of branches" type="text" value={numberOfBranches} disabled>
              </ValidatedField>
              <ValidatedField
                label="Branch"
                id="capteur-boitier-branche"
                name="branche"
                data-cy="branche"
                type="select"
              >
                {generateBranchOptions()}
              </ValidatedField>
              <ValidatedField 
              id="capteur-boitier-capteur"
               name="capteur" data-cy="capteur" label="Sensor" type="select">
                <option value="" key="0" />
                {capteurs
                  ? capteurs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.type}>
                        {otherEntity.type}
                      </option>
                    ))
                  : null}
              </ValidatedField>
             
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/capteur-boitier" replace color="info">
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
       
     
    </div>
    <div className="container-left">
   <ul className="responsive-table">
    <li className="table-header">
    <div className="col col-1" style={{ textAlign: 'center' }}> Boitier</div>
    <div className="col col-2" style={{ textAlign: 'center' }}>Branch</div>
    <div className="col col-1" style={{ textAlign: 'center' }}> Sensor</div>
    <div className="col col-3" style={{ textAlign: 'center' }}>Action</div>
    </li>
  </ul>
  </div>
    </div>
  );
};

export default CapteurBoitierUpdate;
