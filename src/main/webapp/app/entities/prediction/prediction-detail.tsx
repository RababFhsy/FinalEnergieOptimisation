import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './prediction.reducer';

export const PredictionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const predictionEntity = useAppSelector(state => state.prediction.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="predictionDetailsHeading">Prediction</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{predictionEntity.id}</dd>
          <dt>
            <span id="dateDebut">Date Debut</span>
          </dt>
          <dd>
            {predictionEntity.dateDebut ? (
              <TextFormat value={predictionEntity.dateDebut} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dateFin">Date Fin</span>
          </dt>
          <dd>
            {predictionEntity.dateFin ? <TextFormat value={predictionEntity.dateFin} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="consommationPredit">Consommation Predit</span>
          </dt>
          <dd>{predictionEntity.consommationPredit}</dd>
          <dt>
            <span id="precision">Precision</span>
          </dt>
          <dd>{predictionEntity.precision}</dd>
          <dt>Locale</dt>
          <dd>{predictionEntity.locale ? predictionEntity.locale.id : ''}</dd>
          <dt>Energie</dt>
          <dd>{predictionEntity.energie ? predictionEntity.energie.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/prediction" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/prediction/${predictionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PredictionDetail;
