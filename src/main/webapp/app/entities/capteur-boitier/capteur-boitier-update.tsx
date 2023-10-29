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
  const [numberOfBranches, setNumberOfBranches] = useState(0);
  const [usedBranches, setUsedBranches] = useState<{ [key: string]: string[] }>({});
  const [availableBranches, setAvailableBranches] = useState([]);
  const [remainingBranches, setRemainingBranches] = useState(0);
  const [tableData, setTableData] = useState([]);
  const [selectedBoitier, setSelectedBoitier] = useState(null); // State to track selected Boitier
  const [boitierDisabled, setBoitierDisabled] = useState(false);

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
  const [formData, setFormData] = useState({
    // Initialize with default values if needed
    boitier: '',
    branche: '',
    capteur: '',
  });


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
  // const handleBoitierChange = (event) => {
  //   const selectedBoitierId = event.target.value;
  //   const selectedBoitier = boitiers.find((it) => it.id.toString() === selectedBoitierId);
  //   if (selectedBoitier) {
  //     const usedBranchesForSelectedBoitier = capteurBoitierList
  //       .filter((item) => item.boitier.id === selectedBoitierId)
  //       .map((item) => item.branche);
  //     const remaining = selectedBoitier.nbrBranche - usedBranchesForSelectedBoitier.length;
  //     setRemainingBranches(remaining);
  //     setNumberOfBranches(remaining);
  //     setUsedBranches({ ...usedBranches, [selectedBoitierId]: usedBranchesForSelectedBoitier });
  //     console.log(remaining)
  //   }
  //   generateBranchOptions();
  // };
  
  const handleAddRow = () => {
    if (selectedBoitier) {
      const newRow = { ...formData, boitier: selectedBoitier };
      setTableData([...tableData, newRow]);
      // Reset only "branch" and "sensor"
      setFormData({
        ...formData,
        branche: '',
        capteur: '',
      });
      
    }
  };

  const handleInputChange = (event) => {
    const selectedBoitierId = event.target.value;
    const selectedBoitier = boitiers.find((it) => it.id.toString() === selectedBoitierId);
  
    if (selectedBoitier) {
      const usedBranchesForSelectedBoitier = capteurBoitierList
        .filter((item) => item.boitier.id === selectedBoitierId)
        .map((item) => item.branche);
      const remaining = selectedBoitier.nbrBranche - usedBranchesForSelectedBoitier.length;
  
      // Set "remaining" in your state (if needed)
      setRemainingBranches(remaining);
  
      // Set "numberOfBranches" in your state
      setNumberOfBranches(remaining);
  
      // Set "usedBranches" in your state
      setUsedBranches({ ...usedBranches, [selectedBoitierId]: usedBranchesForSelectedBoitier });
  
    }
  
    // Call the "generateBranchOptions" function if needed
    generateBranchOptions();
  
    // The following code is for handling form input changes
    const { name, value } = event.target;
    if (name === 'boitier') {
      setSelectedBoitier(value);
      setBoitierDisabled(true); // Disable "boitier" selection
    }
    setFormData({
      ...formData,
      [name]: value,
    });
  };
  
  
  const mapBoitierType = (boitierId) => {
    const boitier = boitiers.find((entity) => entity.id.toString() === boitierId.toString());
    return boitier ? boitier.type : '';
  };
  
  const mapCapteurType = (capteurId) => {
    const capteur = capteurs.find((entity) => entity.id.toString() === capteurId.toString());
    return capteur ? capteur.type : '';
  };
  

  
  const generateBranchOptions = () => {
  const selectedBoitierId = defaultValues().boitier;
  const selectedBoitierUsedBranches = usedBranches[selectedBoitierId] || [];
  const options = [];
  for (let i = 0; i < remainingBranches; i++) {
    const branch = `A${i}`;
    if (!selectedBoitierUsedBranches.includes(branch)) {
      options.push(<option value={branch}>{branch}</option>);
    }
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
              <ValidatedField name="id" required readOnly id="capteur-boitier-id" label="ID" validate={{ required: true }}  onChange={handleInputChange} />
            ) : null}
             <ValidatedField id="capteur-boitier-boitier" name="boitier" data-cy="boitier" label="Boitier" type="select"  disabled={boitierDisabled}   onChange={handleInputChange}>
              <option value="" key="0" />
              {boitiers
                ? boitiers.map(otherEntity => (
                    <option value={otherEntity.id} key={otherEntity.type}>
                      {otherEntity.type}
                    </option>
                  ))
                : null}
            </ValidatedField>

              <ValidatedField id="capteur-boitier-remaining-branches"
                name="remainingBranches"
                data-cy="remainingBranches"
                label="Remaining Branches"
                type="text"
                onChange={handleInputChange}
                value={remainingBranches}
                disabled>
              </ValidatedField>
              <ValidatedField
                label="Branch"
                id="capteur-boitier-branche"
                name="branche"
                data-cy="branche"
                type="select"
                onChange={handleInputChange}
              >
                {generateBranchOptions()}
              </ValidatedField>
              <ValidatedField 
              id="capteur-boitier-capteur" onChange={handleInputChange}
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
              <Button
              color="primary"
              id="save-entity"
              data-cy="entityCreateSaveButton"
              type="button"  // Change the type to 'button' to prevent form submission
              onClick={() => {
                handleAddRow(); // Add the row to the table
                // Submit the form data here if needed
              }}
              disabled={updating}
            >
              <FontAwesomeIcon icon="save" />
              &nbsp; Add
            </Button>



            </ValidatedForm>
          )}
        </Col>
       
     
    </div>
    <div className="container-left">
    <table className="responsive-table">
          <thead className="table-header">
            <tr>
              <th className="col col-1" style={{ textAlign: 'center' }}>Boitier</th>
              <th className="col col-1" style={{ textAlign: 'center' }}>Branche</th>
              <th className="col col-1" style={{ textAlign: 'center' }}>Sensor</th>
              <th className="col col-1" style={{ textAlign: 'center' }}>Action</th>
            </tr>
          </thead>
          <tbody>
            {tableData.map((rowData, index) => (
              <tr key={index}>
                <td>{mapBoitierType(rowData.boitier)}  </td>
                <td>{rowData.branche}  </td>
                <td>{mapCapteurType(rowData.capteur)} </td>
              </tr>
            ))}
          </tbody>
        </table>

  </div>
    </div>
  );
};

export default CapteurBoitierUpdate;
