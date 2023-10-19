import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Anomalie from './anomalie';
import AnomalieDetail from './anomalie-detail';
import AnomalieUpdate from './anomalie-update';
import AnomalieDeleteDialog from './anomalie-delete-dialog';

const AnomalieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Anomalie />} />
    <Route path="new" element={<AnomalieUpdate />} />
    <Route path=":id">
      <Route index element={<AnomalieDetail />} />
      <Route path="edit" element={<AnomalieUpdate />} />
      <Route path="delete" element={<AnomalieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AnomalieRoutes;
