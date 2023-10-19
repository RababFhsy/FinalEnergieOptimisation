import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './capteur.reducer';

export const CapteurDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const capteurEntity = useAppSelector(state => state.capteur.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="capteurDetailsHeading">Capteur</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{capteurEntity.id}</dd>
          <dt>
            <span id="capteurReference">Capteur Reference</span>
          </dt>
          <dd>{capteurEntity.capteurReference}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{capteurEntity.type}</dd>
          <dt>
            <span id="photo">Photo</span>
          </dt>
          <dd>
            {capteurEntity.photo ? (
              <div>
                {capteurEntity.photoContentType ? (
                  <a onClick={openFile(capteurEntity.photoContentType, capteurEntity.photo)}>
                    <img src={`data:${capteurEntity.photoContentType};base64,${capteurEntity.photo}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {capteurEntity.photoContentType}, {byteSize(capteurEntity.photo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="valeurMin">Valeur Min</span>
          </dt>
          <dd>{capteurEntity.valeurMin}</dd>
          <dt>
            <span id="valeurMax">Valeur Max</span>
          </dt>
          <dd>{capteurEntity.valeurMax}</dd>
        </dl>
        <Button tag={Link} to="/capteur" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/capteur/${capteurEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CapteurDetail;
