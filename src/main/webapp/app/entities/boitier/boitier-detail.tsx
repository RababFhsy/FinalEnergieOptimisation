import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './boitier.reducer';

export const BoitierDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const boitierEntity = useAppSelector(state => state.boitier.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="boitierDetailsHeading">Boitier</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{boitierEntity.id}</dd>
          <dt>
            <span id="boitierReference">Boitier Reference</span>
          </dt>
          <dd>{boitierEntity.boitierReference}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{boitierEntity.type}</dd>
          <dt>
            <span id="nbrBranche">Nbr Branche</span>
          </dt>
          <dd>{boitierEntity.nbrBranche}</dd>
        </dl>
        <Button tag={Link} to="/boitier" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/boitier/${boitierEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BoitierDetail;
