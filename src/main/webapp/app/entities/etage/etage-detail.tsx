import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './etage.reducer';

export const EtageDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const etageEntity = useAppSelector(state => state.etage.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="etageDetailsHeading">Etage</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{etageEntity.id}</dd>
          <dt>
            <span id="etageNumero">Etage Numero</span>
          </dt>
          <dd>{etageEntity.etageNumero}</dd>
          <dt>Batiment</dt>
          <dd>{etageEntity.batiment ? etageEntity.batiment.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/etage" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/etage/${etageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EtageDetail;
