import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Preference from './preference';
import PreferenceDetail from './preference-detail';
import PreferenceUpdate from './preference-update';
import PreferenceDeleteDialog from './preference-delete-dialog';

const PreferenceRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Preference />} />
    <Route path="new" element={<PreferenceUpdate />} />
    <Route path=":id">
      <Route index element={<PreferenceDetail />} />
      <Route path="edit" element={<PreferenceUpdate />} />
      <Route path="delete" element={<PreferenceDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PreferenceRoutes;
