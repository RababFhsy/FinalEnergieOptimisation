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
import { getEntity,getEntities, updateEntity, createEntity, reset } from './locale-boitier.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { Translate, TextFormat } from 'react-jhipster';

export const LocaleBoitierUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const locales = useAppSelector(state => state.locale.entities);
  const boitiers = useAppSelector(state => state.boitier.entities);
  const localeBoitierList = useAppSelector(state => state.localeBoitier.entities);
  const localeBoitierEntity = useAppSelector(state => state.localeBoitier.entity);
  const loading = useAppSelector(state => state.localeBoitier.loading);
  const updating = useAppSelector(state => state.localeBoitier.updating);
  const updateSuccess = useAppSelector(state => state.localeBoitier.updateSuccess);
  const [selectedLocale, setSelectedLocale] = useState(null);
  const [selectedBoitier, setSelectedBoitier] = useState(null);
  const [localeDisabled, setLocaleDisabled] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [tableData, setTableData] = useState([]);

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
    dispatch(getEntities({}));
  }, []);

  

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);


  const [formData, setFormData] = useState({
    // Initialize with default values if needed
    locale:'',
    boitier: '',
    dateDebut: '',
    dateFin: '',
  });
  const handleAddRow = () => {
    if (selectedLocale) {
      const newRow = { ...formData, locale: selectedLocale };
      const exists = tableData.some(
        (data) => data.boitier === newRow.boitier && data.locale === newRow.locale
      );
      if (!exists) {
      setTableData([...tableData, newRow]);
      setErrorMessage('');
      setFormData({
        ...formData,
        boitier: '',
        dateDebut: currentDate,
        dateFin: '',
      });
    } else {
      setErrorMessage('You have already chosen this Box. Please select a different one.');
    }
    }
  };
  
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    if (name === 'locale') {
      setSelectedLocale(value);
      setLocaleDisabled(true); // Désactiver la sélection du "boitier"
    } else if (name === 'boitier') {
      const currentDate = new Date();
      const selectedBoitierId = parseInt(value);
  
      const boitierExistence = localeBoitierList.some((item) => {
        if (item.boitier && item.dateFin) {
          const dateFinBoitier = new Date(item.dateFin);
          return (
            item.boitier.id === selectedBoitierId &&
            currentDate.getTime() - dateFinBoitier.getTime() < 0
          );
        }
        return false;
      });
  
      if (boitierExistence) {
        setErrorMessage('Ce boitier n\'est pas encore disponible.');
      } else {
        setErrorMessage(''); // Réinitialiser le message d'erreur s'il est déjà présent
      }
    }
  
    setFormData({
      ...formData,
      [name]: value,
    });
  };
  
  
  
  
  
  


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

  const removeRow = (index) => {
    const updatedTableData = [...tableData];
    updatedTableData.splice(index, 1);
    setTableData(updatedTableData);
  };

  const resetForm = () => {
    setFormData({
      boitier: '',
      locale: '',
      dateDebut: currentDate,
      dateFin:'',
    }); // Reset the remainingBranches field
    setLocaleDisabled(false);
  };

  const handleSubmitAllRows = () => {
    tableData.forEach((row) => {
      const values = {
        boitier: row.boitier,
        locale: row.locale,
        dateDebut: currentDate,
        dateFin: row.dateFin,
      };
      saveEntity(values);
    });
    setTableData([]); // Clear the table after submission
    resetForm(); // Reset the form fields
  };
  const currentDate = new Date().toISOString().split('T')[0];


  return (
    <div className="page-container">
    <div className="container-right" >
          <h3 id="feOptimisationEnergieApp.capteurBoitier.home.createOrEditLabel" data-cy="CapteurBoitierCreateUpdateHeading">
          Assign Box to Local
          </h3>
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="locale-boitier-id" label="ID" onChange={handleInputChange} validate={{ required: true }} />
              ) : null}
              <ValidatedField id="locale-boitier-locale" name="locale" data-cy="locale" label="Locale" type="select" disabled={localeDisabled} onChange={handleInputChange}>
                <option value="" key="0" />
                {locales
                  ? locales.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="locale-boitier-boitier" name="boitier" data-cy="boitier" label="Boitier" type="select" onChange={handleInputChange} value={formData.boitier}>
                <option value="" key="0" />
                {boitiers
                  ? boitiers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
              <ValidatedField label="Date Debut" id="locale-boitier-dateDebut" name="dateDebut" data-cy="dateDebut" type="date" value={currentDate} // Set the value to the current date
                onChange={handleInputChange}  disabled />
              <ValidatedField label="Date Fin" id="locale-boitier-dateFin" name="dateFin" data-cy="dateFin" type="date" onChange={handleInputChange} value={formData.dateFin}/>
              
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/locale-boitier" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" 
              disabled={updating} onClick={handleAddRow}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Add
              </Button>
              <br/>
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
              <th className="col col-1" style={{ textAlign: 'center' }}>Local</th>
              <th className="col col-1" style={{ textAlign: 'center' }}>Box</th>
              <th className="col col-1" style={{ textAlign: 'center' }}>First Date</th>
              <th className="col col-1" style={{ textAlign: 'center' }}>Last Date</th>
              <th className="col col-1" style={{ textAlign: 'center' }}>Action</th>
            </tr>
          </thead>
          <tbody>
          {tableData.map((rowData, index) => (
              <tr key={index}>
                <td style={{ textAlign: 'center' }}>{rowData.locale}</td>
                <td style={{ textAlign: 'center' }}>{rowData.boitier}</td>
                <td style={{ textAlign: 'center' }}>
                 <TextFormat type="date" value={currentDate} format={APP_LOCAL_DATE_FORMAT} /> 
                </td>
                <td style={{ textAlign: 'center' }} >
                {rowData.dateFin ? <TextFormat type="date" value={rowData.dateFin} format={APP_LOCAL_DATE_FORMAT} /> : null}
                </td>
                <td style={{ textAlign: 'center' }}><button className="btn btn-danger btn-sm" onClick={() => removeRow(index)}>Remove</button></td>
              </tr>
            ))}
          </tbody>
        </table>

  </div>
    </div>
  );
};

export default LocaleBoitierUpdate;
