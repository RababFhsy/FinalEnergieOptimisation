import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Batiment from './batiment';
import BatimentDetail from './batiment-detail';
import BatimentUpdate from './batiment-update';
import BatimentDeleteDialog from './batiment-delete-dialog';

const BatimentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Batiment />} />
    <Route path="new" element={<BatimentUpdate />} />
    <Route path=":id">
      <Route index element={<BatimentDetail />} />
      <Route path="edit" element={<BatimentUpdate />} />
      <Route path="delete" element={<BatimentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BatimentRoutes;
