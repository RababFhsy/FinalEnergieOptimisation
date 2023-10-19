import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CapteurBoitier from './capteur-boitier';
import CapteurBoitierDetail from './capteur-boitier-detail';
import CapteurBoitierUpdate from './capteur-boitier-update';
import CapteurBoitierDeleteDialog from './capteur-boitier-delete-dialog';

const CapteurBoitierRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CapteurBoitier />} />
    <Route path="new" element={<CapteurBoitierUpdate />} />
    <Route path=":id">
      <Route index element={<CapteurBoitierDetail />} />
      <Route path="edit" element={<CapteurBoitierUpdate />} />
      <Route path="delete" element={<CapteurBoitierDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CapteurBoitierRoutes;
