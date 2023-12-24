import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import {  TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { getEntities, getEntitiesByUser } from './consommation.reducer';

import { APP_LOCAL_DATE_FORMAT, AUTHORITIES } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import {  Pagination,Form,FormControl } from 'react-bootstrap';

export const Consommation = () => {
  const dispatch = useAppDispatch();
  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));
  const consommationList = useAppSelector(state => state.consommation.entities);

  const loading = useAppSelector(state => state.consommation.loading);
  const [searchQuery, setSearchQuery] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 5; // Set the number of items per page
  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value);
    setCurrentPage(1); // Reset to the first page when the search query changes
  };

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;

  const filteredconsommationList = consommationList
    .filter((consommation) =>
    consommation.energie.nomSystemEnergitique.toLowerCase().includes(searchQuery.toLowerCase())
    )
    .slice(indexOfFirstItem, indexOfLastItem);
    const filteredconsommationListByUser = consommationList
    .filter((consommation) =>
    consommation.energie.nomSystemEnergitique.toLowerCase().includes(searchQuery.toLowerCase())
    )
    .slice(indexOfFirstItem, indexOfLastItem);  

  const paginate = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  useEffect(() => {
    dispatch(getEntities({}));
    dispatch(getEntitiesByUser({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
    dispatch(getEntitiesByUser({}));
  };

  return (
    <div>
      <h2 id="consommation-heading" data-cy="ConsommationHeading">
        consumptions
        <div className="d-flex justify-content-end">
          <Button className="me-2 btn-light custom-button-refresh" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
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
      {filteredconsommationList && filteredconsommationList.length > 0 ? (
       
          <Table responsive>
            <thead>
              <tr>
                <th>Energy type</th>
                <th>Quantity of energy Consumption</th>
                <th>Consumption date</th>
                <th>Building</th>
                <th>Local Number</th>
                <th>User</th>
                <th style={{ textAlign: 'center' }}>Action</th>

                <th />
              </tr>
            </thead>
            <tbody>
              {isAdmin
                ? filteredconsommationList.map((consommation, i) => (
                    <tr key={`entity-${i}`} data-cy="entityTable">
                      {/* <td>
                        <Button tag={Link} to={`/consommation/${consommation.id}`} color="link" size="sm">
                          {consommation.id}
                        </Button>
                      </td> */}
                      <td>{consommation.energie ? consommation.energie.nomSystemEnergitique : ''}</td>
                      <td>{consommation.energieConsommation}</td>
                      <td>
                        {consommation.dateConsommation ? (
                          <TextFormat type="date" value={consommation.dateConsommation} format={APP_LOCAL_DATE_FORMAT} />
                        ) : null}
                      </td>
                      <td>{consommation.locale && consommation.locale.batiment ? consommation.locale.batiment.batimentNom : ''}</td>
                      <td>N°{consommation.locale ? consommation.locale.numero : ''}</td>
                      <td>{consommation.user ? consommation.user.login : ''}</td>

                      <td style={{ textAlign: 'center' }}>
                        <div className="btn-group flex-btn-group-container">
                          {/* <Button tag={Link} to={`/consommation/${consommation.id}`} className="custom-button-view" size="sm" data-cy="entityDetailsButton">
                            <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                          </Button> */}

                          <Button tag={Link} to={`/consommation/${consommation.id}/delete`}  className="custom-button-delete" size="sm" data-cy="entityDeleteButton">
                            <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                          </Button>
                        </div>
                      </td>
                    </tr>
                  ))
                : filteredconsommationListByUser.map((consommation, i) => (
                    <tr key={`entity-${i}`} data-cy="entityTable">
                        <td>{consommation.energie ? consommation.energie.nomSystemEnergitique : ''}</td>
                      <td>{consommation.energieConsommation}</td>
                      <td>
                        {consommation.dateConsommation ? (
                          <TextFormat type="date" value={consommation.dateConsommation} format={APP_LOCAL_DATE_FORMAT} />
                        ) : null}
                      </td>
                      <td>{consommation.locale && consommation.locale.batiment ? consommation.locale.batiment.batimentNom : ''}</td>
                    
                      <td>{consommation.locale ? consommation.locale.numero : ''}</td>
                     
                      

                      <td>{consommation.user ? consommation.user.login : ''}</td>

                      <td className="text-end">
                        <div className="btn-group flex-btn-group-container">
                          {/* <Button tag={Link} to={`/consommation/${consommation.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                            <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                          </Button>
                           */}

                          <Button
                            tag={Link}
                            to={`/consommation/${consommation.id}/delete`}
                            color="danger"
                            size="sm"
                            data-cy="entityDeleteButton"
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
          !loading && <div className="alert alert-warning">No Consumptions found</div>
        )}
        <Pagination>
          {Array.from({ length: Math.ceil(consommationList.length / itemsPerPage) }).map(
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

export default Consommation;
