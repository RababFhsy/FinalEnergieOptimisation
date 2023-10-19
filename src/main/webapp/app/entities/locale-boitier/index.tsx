import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LocaleBoitier from './locale-boitier';
import LocaleBoitierDetail from './locale-boitier-detail';
import LocaleBoitierUpdate from './locale-boitier-update';
import LocaleBoitierDeleteDialog from './locale-boitier-delete-dialog';

const LocaleBoitierRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LocaleBoitier />} />
    <Route path="new" element={<LocaleBoitierUpdate />} />
    <Route path=":id">
      <Route index element={<LocaleBoitierDetail />} />
      <Route path="edit" element={<LocaleBoitierUpdate />} />
      <Route path="delete" element={<LocaleBoitierDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LocaleBoitierRoutes;
