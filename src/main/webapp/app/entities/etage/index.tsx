import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Etage from './etage';
import EtageDetail from './etage-detail';
import EtageUpdate from './etage-update';
import EtageDeleteDialog from './etage-delete-dialog';

const EtageRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Etage />} />
    <Route path="new" element={<EtageUpdate />} />
    <Route path=":id">
      <Route index element={<EtageDetail />} />
      <Route path="edit" element={<EtageUpdate />} />
      <Route path="delete" element={<EtageDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EtageRoutes;
