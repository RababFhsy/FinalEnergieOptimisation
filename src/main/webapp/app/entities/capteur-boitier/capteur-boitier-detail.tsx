import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './capteur-boitier.reducer';

export const CapteurBoitierDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const capteurBoitierEntity = useAppSelector(state => state.capteurBoitier.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="capteurBoitierDetailsHeading">Capteur Boitier</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{capteurBoitierEntity.id}</dd>
          <dt>
            <span id="branche">Branche</span>
          </dt>
          <dd>{capteurBoitierEntity.branche}</dd>
          <dt>Capteur</dt>
          <dd>{capteurBoitierEntity.capteur ? capteurBoitierEntity.capteur.id : ''}</dd>
          <dt>Boitier</dt>
          <dd>{capteurBoitierEntity.boitier ? capteurBoitierEntity.boitier.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/capteur-boitier" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/capteur-boitier/${capteurBoitierEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CapteurBoitierDetail;
