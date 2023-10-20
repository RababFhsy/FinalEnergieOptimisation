import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICapteur } from 'app/shared/model/capteur.model';
import { getEntities } from './capteur.reducer';
import './capteur.scss';

export const Capteur = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const capteurList = useAppSelector(state => state.capteur.entities);
  const loading = useAppSelector(state => state.capteur.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="capteur-heading" data-cy="CapteurHeading">
        Sensors
        <div className="d-flex justify-content-end">
        <Button
  className="me-2"
  
  onClick={handleSyncList}
  disabled={loading}
  style={{
    borderColor: '#0077B6', // Set the border color
    borderWidth: '2px', // Set the border width
    borderStyle: 'solid', // Set the border style to solid
    backgroundColor: '#90E0EF', // Make the background transparent
    color: '#0077B6', // Set the text color to the desired color
    borderRadius: '20px',
  }}
>
  <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
</Button>

<Link
  to="/capteur/new"
  className="btn btn-light jh-create-entity"
  id="jh-create-entity"
  data-cy="entityCreateButton"
  style={{
    borderColor: '#0077B6', // Set the border color
    borderWidth: '2px', // Set the border width
    borderStyle: 'solid', // Set the border style to solid
    backgroundColor: '#90E0EF', // Make the background transparent
    color: '#0077B6', // Set the text color to the desired color
    borderRadius: '20px',
  }}
>
  <FontAwesomeIcon icon="plus" />
  &nbsp;  new Sensor
</Link>

        </div>
      </h2>
      <div className="custom-table" >
        {capteurList && capteurList.length > 0 ? (
          <Table   responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Sensor Reference</th>
                <th>Type</th>
                <th>Image</th>
                <th>Min Value</th>
                <th> Max Value</th>
                <th style={{ textAlign: 'center' }}>      Action</th>
               
              </tr>
            </thead>
            <tbody>
              {capteurList.map((capteur, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/capteur/${capteur.id}`} color="link" size="sm">
                      {capteur.id}
                    </Button>
                  </td>
                  <td>{capteur.capteurReference}</td>
                  <td>{capteur.type}</td>
                  <td>
                    {capteur.photo ? (
                      <div>
                        {capteur.photoContentType ? (
                          <a onClick={openFile(capteur.photoContentType, capteur.photo)}>
                            <img src={`data:${capteur.photoContentType};base64,${capteur.photo}`} style={{ maxHeight: '80px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        {/* <span>
                          {capteur.photoContentType}, {byteSize(capteur.photo)}
                        </span> */}
                      </div>
                    ) : null}
                  </td>
                  <td>{capteur.valeurMin}</td>
                  <td>{capteur.valeurMax}</td>
                  <td >
                    <div className="btn-group flex-btn-group-container">
                    <Button
  tag={Link}
  to={`/capteur/${capteur.id}`}
  
  size="btn-md"
  data-cy="entityDetailsButton"
  style={{
    borderColor: '#00B4D8', // Set the border color
    borderRadius: '20px',
    borderWidth: '2px', // Set the border width
    borderStyle: 'solid', // Set the border style to solid
    backgroundColor: 'transparent', // Make the background transparent
    color: '#00B4D8', // Set the text color to the desired color
    marginRight: '10px'
  }}
>
  <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
</Button>


<Button
  tag={Link}
  to={`/capteur/${capteur.id}/edit`}
 
  size="btn-md"
  data-cy="entityEditButton"
  style={{
    borderColor: '#0077B6', // Set the border color
    borderWidth: '2px', // Set the border width
    borderRadius: '20px',
    borderStyle: 'solid', // Set the border style to solid
    backgroundColor: 'transparent', // Make the background transparent
    color: '#0077B6', // Set the text color to the desired color
    marginRight: '10px'
  }}
>
  <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
</Button>

                      <Button tag={Link} to={`/capteur/${capteur.id}/delete`} 
                      size="btn-md" data-cy="entityDeleteButton"
                      
  
  style={{
    borderColor: '#03045E', // Set the border color
    borderWidth: '2px', // Set the border width
    borderStyle: 'solid', // Set the border style to solid
    borderRadius: '20px',
    marginRight: '10px',
    backgroundColor: 'transparent', // Make the background transparent
    color: '#03045E', // Set the text color to the desired color
    
  }}>
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Sensors found</div>
        )}
      </div>
    </div>
  );
};

export default Capteur;
