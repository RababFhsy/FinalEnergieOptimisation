import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import 'app/shared/layout/customStyles/customStyles.scss';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import {Col, Image, Modal, Row} from "react-bootstrap";
import { ICapteurBoitier } from 'app/shared/model/capteur-boitier.model';
import { getEntities } from './capteur-boitier.reducer';
import './capteur-boitier.scss';

export const CapteurBoitier = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const capteurBoitierList = useAppSelector(state => state.capteurBoitier.entities);
  console.log(capteurBoitierList)
  const loading = useAppSelector(state => state.capteurBoitier.loading);


  const [selectedCapteurBoitier, setSelectedCapteurBoitier] = useState(null);
  const [show, setShow] = useState(false);
  const [showview, setShowView] = useState(false);
  const handleClose = () => setShow(false);
  const handleCloseView = () => setShowView(false);
  const handleShowView = (capteurBoitier) => {
    setSelectedCapteurBoitier(capteurBoitier);
    setShowView(true);
  };
  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="capteur-boitier-heading" data-cy="CapteurBoitierHeading">
        Assign Sensor to Boitier
        <div className="d-flex justify-content-end">
          <Button   onClick={handleSyncList} disabled={loading}
          className="me-2 btn-light custom-button-refresh">
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/capteur-boitier/new" className="btn btn-light jh-create-entity custom-button-new" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Assign Sensor to Boitier
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {capteurBoitierList && capteurBoitierList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Boitier</th>
                <th>Branch</th>
                <th>Sensor</th>


                <th style={{ textAlign: 'center' }}>      Action</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {capteurBoitierList.map((capteurBoitier, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>

                      {capteurBoitier.id}

                  </td>
                  <td>
  {capteurBoitier.boitier ? capteurBoitier.boitier.type  : ''}
</td>
<td>
  {capteurBoitier.boitier ? capteurBoitier.boitier.nbrBranche  : ''}
</td>

                  <td>
  {capteurBoitier.capteur ? capteurBoitier.capteur.type : ''}
</td>




                  <td style={{ textAlign: 'center' }}>
                    <div className="btn-group flex-btn-group-container">
                      <Button onClick={()=>handleShowView(capteurBoitier)} size="sm" data-cy="entityDetailsButton" className="custom-button-view">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/capteur-boitier/${capteurBoitier.id}/edit`}
                        size="sm"
                        data-cy="entityEditButton" className="custom-button-edit" >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/capteur-boitier/${capteurBoitier.id}/delete`}
                        size="sm"
                        data-cy="entityDeleteButton"
                        className="custom-button-delete"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Capteur Boitiers found</div>
        )}
      </div>
      <Modal show={showview} onHide={handleCloseView}>
  <Modal.Header closeButton>
    <Modal.Title>Boitier Details</Modal.Title>
  </Modal.Header>
  <Modal.Body>
    <table className="table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Boitier</th>
          <th>Branch</th>
          <th>Sensor</th>

        </tr>
      </thead>
      <tbody>
        <tr>
          <td>{selectedCapteurBoitier?.id}</td>
          <td>{selectedCapteurBoitier?.boitier?.type}</td>
          <td>{selectedCapteurBoitier?.branche}</td>
          <td>{selectedCapteurBoitier?.capteur?.type}</td>

        </tr>
      </tbody>
    </table>
  </Modal.Body>
  <Modal.Footer>
    <Button variant="secondary" onClick={handleCloseView}>
      Close
    </Button>
  </Modal.Footer>
</Modal>

    </div>
  );
};

export default CapteurBoitier;
