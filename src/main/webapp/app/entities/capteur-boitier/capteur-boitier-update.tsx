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
import { getEntity, updateEntity, createEntity, reset , getEntities } from './capteur-boitier.reducer';

export const CapteurBoitierUpdate = () => {
  const dispatch = useAppDispatch();
  
  const [usedBranches, setUsedBranches] = useState([]);
  const [availableBranches, setAvailableBranches] = useState([]);
  const [remainingBranches, setRemainingBranches] = useState(0);
  const [tableData, setTableData] = useState([]);
  const [selectedBoitier, setSelectedBoitier] = useState(null); // State to track selected Boitier
  const [boitierDisabled, setBoitierDisabled] = useState(false);
  const [generatedOptions, setGeneratedOptions] = useState([]);

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;
  const capteurs = useAppSelector(state => state.capteur.entities);
  const boitiers = useAppSelector(state => state.boitier.entities);
  const capteurBoitierEntity = useAppSelector(state => state.capteurBoitier.entity);
  
  const capteurBoitierList = useAppSelector(state => state.capteurBoitier.entities);

  const loading = useAppSelector(state => state.capteurBoitier.loading);
  const updating = useAppSelector(state => state.capteurBoitier.updating);
  const updateSuccess = useAppSelector(state => state.capteurBoitier.updateSuccess);
  const [errorMessage, setErrorMessage] = useState('');
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

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

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

        const handleAddRow = () => {
          if (selectedBoitier) {
            const newRow = { ...formData, boitier: selectedBoitier };
            const exists = tableData.some(
              (data) => data.boitier === newRow.boitier && data.branche === newRow.branche
            );
            if (!exists) {
              setTableData([...tableData, newRow]);
              setErrorMessage('');
              // Reset branch and sensor
              setFormData({
                ...formData,
                branche: '',
                capteur: '',
              });
            } else {
              setErrorMessage('This branch has already been used. Please select another one.');
            }
          }
        };
        
        
        
      


  const mapBoitierType = (boitierId) => {
    const boitier = boitiers.find((entity) => entity.id.toString() === boitierId.toString());
    return boitier ? boitier.boitierReference : '';
  };

  const mapCapteurType = (capteurId) => {
    const capteur = capteurs.find((entity) => entity.id.toString() === capteurId.toString());
    return capteur ? capteur.capteurReference : '';
  };



  // const generateBranchOptions = () => {
  //   const selectedBoitierId = defaultValues().boitier;
  //   const selectedBoitierUsedBranches = usedBranches[selectedBoitierId] || [];
  //   const options = [];
  //   for (let i = 0; i < remainingBranches; i++) {
  //     const branch = `A${i}`;
  //     console.log("Branch:", branch);
  //     console.log("Selected Boitier Used Branches:", selectedBoitierUsedBranches);
  //     if (!selectedBoitierUsedBranches.includes(branch) || !tableData.some(data => data.branche === branch)) {
  //       options.push(<option value={branch} key={branch}>{branch}</option>);
  //     }
  //   }
  //   return options;
  // };
  
  
  
  
  

  const handleInputChange = (event) => {
    const selectedBoitierId = event.target.value;
    const selectedBoitier = boitiers.find((it) => it.id.toString() === selectedBoitierId);
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

      if (selectedBoitier) {
        const usedBranchesForSelectedBoitier = capteurBoitierList
          .filter(item => item.boitier.id === parseInt(selectedBoitierId))
          .map(item => item.branche);
              
        // console.log('usedBranchesForSelectedBoitier:', usedBranchesForSelectedBoitier); // Add this log
    
        const remaining = selectedBoitier.nbrBranche - usedBranchesForSelectedBoitier.length;
        setRemainingBranches(remaining);
        setUsedBranches(usedBranchesForSelectedBoitier);
      const options = [];
      for (let i = 0; i < selectedBoitier.nbrBranche; i++) {
        const branch = `A${i}`;
        // console.log('Branch:', branch); // Add this log
        // console.log('Checking includes:', usedBranchesForSelectedBoitier.includes(branch)); // Add this log
        if (!usedBranchesForSelectedBoitier.includes(branch) ){
          options.push(<option value={branch} key={branch}>{branch}</option>);
        }
      }
      setGeneratedOptions(options)   
    }
  };
  
  
  const removeRow = (index) => {
    const updatedTableData = [...tableData];
    updatedTableData.splice(index, 1);
    setTableData(updatedTableData);
  };

  const resetForm = () => {
    setFormData({
      boitier: '',
      branche: '',
      capteur: '',
    });
    setRemainingBranches(0); // Reset the remainingBranches field
    setBoitierDisabled(false);
  };


  const handleSubmitAllRows = () => {
    tableData.forEach((row) => {
      const values = {
        boitier: row.boitier,
        branche: row.branche,
        capteur: row.capteur,
      };
      saveEntity(values);
    });
    setTableData([]); // Clear the table after submission
    resetForm(); // Reset the form fields
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
                      {otherEntity.boitierReference}
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
                value={formData.branche}
              >
                <option value="" key="0" />
                {generatedOptions}
              </ValidatedField>
              {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
              <ValidatedField
              id="capteur-boitier-capteur" onChange={handleInputChange}
               name="capteur" data-cy="capteur" label="Sensor" type="select" value={formData.capteur} >
                <option value="" key="0" />
                {capteurs
                  ? capteurs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.type}>
                        {otherEntity.capteurReference}
                      </option>
                    ))
                  : null}
              </ValidatedField>

              <Button tag={Link} id="cancel-save" color="primary" data-cy="entityCreateCancelButton" to="/capteur-boitier" replace >
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
              }}
              disabled={updating}
            >

              <span className="d-none d-md-inline">Add</span>
            </Button>
            <br/>
            <br/>
            <Button
          color="primary"
          id="submit-all-rows"
          data-cy="submitAllRowsButton"
          type="button"
          onClick={handleSubmitAllRows}
          disabled={updating || tableData.length === 0} // Disable the button if there are no rows in the table
        >
          Submit All
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
                <td style={{ textAlign: 'center' }}>{mapBoitierType(rowData.boitier)}  </td>
                <td style={{ textAlign: 'center' }}>{rowData.branche}  </td>
                <td style={{ textAlign: 'center' }}>{mapCapteurType(rowData.capteur)} </td>
                <td style={{ textAlign: 'center' }}><button className="btn btn-danger btn-sm" onClick={() => removeRow(index)}>Remove</button></td>
              </tr>
            ))}
          </tbody>
        </table>

  </div>
    </div>
  );
};

export default CapteurBoitierUpdate;
