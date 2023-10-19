import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './preference.reducer';

export const PreferenceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const preferenceEntity = useAppSelector(state => state.preference.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="preferenceDetailsHeading">Preference</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{preferenceEntity.id}</dd>
          <dt>
            <span id="tempMinValue">Temp Min Value</span>
          </dt>
          <dd>{preferenceEntity.tempMinValue}</dd>
          <dt>
            <span id="tempMaxValue">Temp Max Value</span>
          </dt>
          <dd>{preferenceEntity.tempMaxValue}</dd>
          <dt>
            <span id="plageHoraire">Plage Horaire</span>
          </dt>
          <dd>{preferenceEntity.plageHoraire}</dd>
          <dt>User</dt>
          <dd>{preferenceEntity.user ? preferenceEntity.user.login : ''}</dd>
          <dt>Energie</dt>
          <dd>{preferenceEntity.energie ? preferenceEntity.energie.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/preference" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/preference/${preferenceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PreferenceDetail;
