import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './locale.reducer';

export const LocaleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const localeEntity = useAppSelector(state => state.locale.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="localeDetailsHeading">Locale</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{localeEntity.id}</dd>
          <dt>
            <span id="numero">Number</span>
          </dt>
          <dd>{localeEntity.numero}</dd>
          <dt>
            <span id="typeLocal">Type Local</span>
          </dt>
          <dd>{localeEntity.typeLocal}</dd>

          <dt>Building</dt>
          <dd>{localeEntity.batiment ? localeEntity.batiment.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/locale" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/locale/${localeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LocaleDetail;
