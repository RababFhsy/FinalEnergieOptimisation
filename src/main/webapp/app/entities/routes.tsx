import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Preference from './preference';
import Energie from './energie';
import Consommation from './consommation';
import Anomalie from './anomalie';
import Prediction from './prediction';
import Etage from './etage';
import Locale from './locale';
import Batiment from './batiment';
import Capteur from './capteur';
import Boitier from './boitier';
import LocaleBoitier from './locale-boitier';
import CapteurBoitier from './capteur-boitier';
import PrivateRoute from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';
import EntitiesRoutes from 'app/entities/routes';

/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ role }) => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}

        <Route
          path="boitier/*"
          element={
            <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN]}>
              <Boitier />
            </PrivateRoute>
          }
        />

        <Route
          path="etage/*"
          element={
            <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN]}>
              <Etage />
            </PrivateRoute>
          }
        />

        <Route
          path="locale/*"
          element={
            <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN]}>
              <Locale />
            </PrivateRoute>
          }
        />

        <Route
          path="batiment/*"
          element={
            <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN]}>
              <Batiment />
            </PrivateRoute>
          }
        />
        <Route
          path="capteur/*"
          element={
            <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN]}>
              <Capteur />
            </PrivateRoute>
          }
        />
        <Route
          path="locale-boitier/*"
          element={
            <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN]}>
              <LocaleBoitier />
            </PrivateRoute>
          }
        />
        <Route
          path="capteur-boitier/*"
          element={
            <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN]}>
              <CapteurBoitier />
            </PrivateRoute>
          }
        />
        <Route path="preference/*" element={<Preference />} />
        <Route path="energie/*" element={<Energie />} />
        <Route path="consommation/*" element={<Consommation />} />
        <Route path="anomalie/*" element={<Anomalie />} />
        <Route path="prediction/*" element={<Prediction />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
