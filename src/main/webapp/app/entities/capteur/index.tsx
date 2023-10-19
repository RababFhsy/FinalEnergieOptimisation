import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Capteur from './capteur';
import CapteurDetail from './capteur-detail';
import CapteurUpdate from './capteur-update';
import CapteurDeleteDialog from './capteur-delete-dialog';

const CapteurRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Capteur />} />
    <Route path="new" element={<CapteurUpdate />} />
    <Route path=":id">
      <Route index element={<CapteurDetail />} />
      <Route path="edit" element={<CapteurUpdate />} />
      <Route path="delete" element={<CapteurDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CapteurRoutes;
