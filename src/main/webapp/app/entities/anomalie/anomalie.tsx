import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAnomalie } from 'app/shared/model/anomalie.model';
import { getEntities } from './anomalie.reducer';
import {  Pagination,Form,FormControl } from 'react-bootstrap';

export const Anomalie = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const anomalieList = useAppSelector(state => state.anomalie.entities);
  const loading = useAppSelector(state => state.anomalie.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };
  const [searchQuery, setSearchQuery] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 5; // Set the number of items per page
  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value);
    setCurrentPage(1); // Reset to the first page when the search query changes
  };

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;

  const filteredAnomalieList = anomalieList
    .filter((anomalie) =>
    anomalie.energie.nomSystemEnergitique.toLowerCase().includes(searchQuery.toLowerCase())
    )
    .slice(indexOfFirstItem, indexOfLastItem);

  const paginate = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  return (
    <div>
      <h2 id="anomalie-heading" data-cy="AnomalieHeading">
        Anomalies
        <div className="d-flex justify-content-end">
        <Button className="me-2 btn-light custom-button-refresh" onClick={handleSyncList} disabled={loading}>            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
         {/* <Link to="/anomalie/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; New Anomalie
  </Link> */}
        </div>
      </h2>
      <div className="table-responsive">
      <Form >
        <FormControl
          type="text"
          placeholder="Search Energy System..."
          className="mr-sm-2"
          value={searchQuery}
          onChange={handleSearchChange}
        />
      </Form>
      <br></br>
      {filteredAnomalieList && filteredAnomalieList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                
                <th>Min of zone normal </th>
                <th>Max of zone normale </th>
                <th>Anomalie date</th>
                <th>Anomalie description </th>
                <th>Local</th>
                <th>Building</th>
                <th>Energy</th>
                <th style={{ textAlign: 'center' }}>Action</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {filteredAnomalieList.map((anomalie, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  {/*<td>
                    <Button tag={Link} to={`/anomalie/${anomalie.id}`} color="link" size="sm">
                      {anomalie.id}
                    </Button>
              </td>*/}
                  <td>{anomalie.zoneNormaleMin}</td>
                  <td>{anomalie.zoneNormaleMax}</td>
                  <td>
                    {anomalie.dateAnomalie ? <TextFormat type="date" value={anomalie.dateAnomalie} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{anomalie.descriptionAnomalie}</td>
                  <td>{anomalie.locale ? anomalie.locale.numero: ''}</td>
                  <td>{anomalie.locale ? anomalie.locale.batiment.batimentNom: ''}</td>
                  <td>{anomalie.energie ? anomalie.energie.nomSystemEnergitique: ''}</td>
                  <td style={{ textAlign: 'center' }}>
                    <div className="btn-group flex-btn-group-container">
                      {/* <Button tag={Link} to={`/anomalie/${anomalie.id}`} className="custom-button-view" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button> */}
                      {/* <Button tag={Link} to={`/anomalie/${anomalie.id}/edit`} className="custom-button-edit" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button> */}
                      <Button tag={Link} to={`/anomalie/${anomalie.id}/delete`} className="custom-button-delete" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Anomalies found</div>
        )}
        <Pagination>
          {Array.from({ length: Math.ceil(anomalieList.length / itemsPerPage) }).map(
            (_, index) => (
              <Pagination.Item
                key={index + 1}
                active={index + 1 === currentPage}
                onClick={() => paginate(index + 1)}
              >
                {index + 1}
              </Pagination.Item>
            )
          )}
        </Pagination>
      </div>
    </div>
  );
};

export default Anomalie;
