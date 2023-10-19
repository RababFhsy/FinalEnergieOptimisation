import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Boitier from './boitier';
import BoitierDetail from './boitier-detail';
import BoitierUpdate from './boitier-update';
import BoitierDeleteDialog from './boitier-delete-dialog';

const BoitierRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Boitier />} />
    <Route path="new" element={<BoitierUpdate />} />
    <Route path=":id">
      <Route index element={<BoitierDetail />} />
      <Route path="edit" element={<BoitierUpdate />} />
      <Route path="delete" element={<BoitierDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BoitierRoutes;
