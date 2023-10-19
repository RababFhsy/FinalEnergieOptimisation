import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Prediction from './prediction';
import PredictionDetail from './prediction-detail';
import PredictionUpdate from './prediction-update';
import PredictionDeleteDialog from './prediction-delete-dialog';

const PredictionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Prediction />} />
    <Route path="new" element={<PredictionUpdate />} />
    <Route path=":id">
      <Route index element={<PredictionDetail />} />
      <Route path="edit" element={<PredictionUpdate />} />
      <Route path="delete" element={<PredictionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PredictionRoutes;
