import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './consommation.reducer';

export const ConsommationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const consommationEntity = useAppSelector(state => state.consommation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="consommationDetailsHeading">Consommation</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{consommationEntity.id}</dd>
          <dt>
            <span id="energieConsommation">Energie Consommation</span>
          </dt>
          <dd>{consommationEntity.energieConsommation}</dd>
          <dt>
            <span id="dateConsommation">Date Consommation</span>
          </dt>
          <dd>
            {consommationEntity.dateConsommation ? (
              <TextFormat value={consommationEntity.dateConsommation} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Locale</dt>
          <dd>{consommationEntity.locale ? consommationEntity.locale.id : ''}</dd>
          <dt>Energie</dt>
          <dd>{consommationEntity.energie ? consommationEntity.energie.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/consommation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/consommation/${consommationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ConsommationDetail;
