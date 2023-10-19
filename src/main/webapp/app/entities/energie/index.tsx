import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Energie from './energie';
import EnergieDetail from './energie-detail';
import EnergieUpdate from './energie-update';
import EnergieDeleteDialog from './energie-delete-dialog';

const EnergieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Energie />} />
    <Route path="new" element={<EnergieUpdate />} />
    <Route path=":id">
      <Route index element={<EnergieDetail />} />
      <Route path="edit" element={<EnergieUpdate />} />
      <Route path="delete" element={<EnergieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EnergieRoutes;
