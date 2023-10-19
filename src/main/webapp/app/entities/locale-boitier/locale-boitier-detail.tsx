import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './locale-boitier.reducer';

export const LocaleBoitierDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const localeBoitierEntity = useAppSelector(state => state.localeBoitier.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="localeBoitierDetailsHeading">Locale Boitier</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{localeBoitierEntity.id}</dd>
          <dt>
            <span id="dateDebut">Date Debut</span>
          </dt>
          <dd>
            {localeBoitierEntity.dateDebut ? (
              <TextFormat value={localeBoitierEntity.dateDebut} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dateFin">Date Fin</span>
          </dt>
          <dd>
            {localeBoitierEntity.dateFin ? (
              <TextFormat value={localeBoitierEntity.dateFin} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Locale</dt>
          <dd>{localeBoitierEntity.locale ? localeBoitierEntity.locale.id : ''}</dd>
          <dt>Boitier</dt>
          <dd>{localeBoitierEntity.boitier ? localeBoitierEntity.boitier.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/locale-boitier" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/locale-boitier/${localeBoitierEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LocaleBoitierDetail;
