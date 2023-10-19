import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Consommation from './consommation';
import ConsommationDetail from './consommation-detail';
import ConsommationUpdate from './consommation-update';
import ConsommationDeleteDialog from './consommation-delete-dialog';

const ConsommationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Consommation />} />
    <Route path="new" element={<ConsommationUpdate />} />
    <Route path=":id">
      <Route index element={<ConsommationDetail />} />
      <Route path="edit" element={<ConsommationUpdate />} />
      <Route path="delete" element={<ConsommationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ConsommationRoutes;
