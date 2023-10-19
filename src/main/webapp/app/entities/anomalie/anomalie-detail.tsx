import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './anomalie.reducer';

export const AnomalieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const anomalieEntity = useAppSelector(state => state.anomalie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="anomalieDetailsHeading">Anomalie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{anomalieEntity.id}</dd>
          <dt>
            <span id="zoneNormaleMin">Zone Normale Min</span>
          </dt>
          <dd>{anomalieEntity.zoneNormaleMin}</dd>
          <dt>
            <span id="zoneNormaleMax">Zone Normale Max</span>
          </dt>
          <dd>{anomalieEntity.zoneNormaleMax}</dd>
          <dt>
            <span id="dateAnomalie">Date Anomalie</span>
          </dt>
          <dd>
            {anomalieEntity.dateAnomalie ? (
              <TextFormat value={anomalieEntity.dateAnomalie} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="descriptionAnomalie">Description Anomalie</span>
          </dt>
          <dd>{anomalieEntity.descriptionAnomalie}</dd>
          <dt>Locale</dt>
          <dd>{anomalieEntity.locale ? anomalieEntity.locale.id : ''}</dd>
          <dt>Energie</dt>
          <dd>{anomalieEntity.energie ? anomalieEntity.energie.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/anomalie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/anomalie/${anomalieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AnomalieDetail;
