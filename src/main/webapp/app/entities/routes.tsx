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
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="preference/*" element={<Preference />} />
        <Route path="energie/*" element={<Energie />} />
        <Route path="consommation/*" element={<Consommation />} />
        <Route path="anomalie/*" element={<Anomalie />} />
        <Route path="prediction/*" element={<Prediction />} />
        <Route path="etage/*" element={<Etage />} />
        <Route path="locale/*" element={<Locale />} />
        <Route path="batiment/*" element={<Batiment />} />
        <Route path="capteur/*" element={<Capteur />} />
        <Route path="boitier/*" element={<Boitier />} />
        <Route path="locale-boitier/*" element={<LocaleBoitier />} />
        <Route path="capteur-boitier/*" element={<CapteurBoitier />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
