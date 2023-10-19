import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Locale from './locale';
import LocaleDetail from './locale-detail';
import LocaleUpdate from './locale-update';
import LocaleDeleteDialog from './locale-delete-dialog';

const LocaleRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Locale />} />
    <Route path="new" element={<LocaleUpdate />} />
    <Route path=":id">
      <Route index element={<LocaleDetail />} />
      <Route path="edit" element={<LocaleUpdate />} />
      <Route path="delete" element={<LocaleDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LocaleRoutes;
