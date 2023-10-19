import { IConsommation } from 'app/shared/model/consommation.model';
import { IAnomalie } from 'app/shared/model/anomalie.model';
import { IPrediction } from 'app/shared/model/prediction.model';

export interface IEnergie {
  id?: number;
  nomSystemEnergitique?: string | null;
  consommations?: IConsommation[] | null;
  anomalies?: IAnomalie[] | null;
  predictions?: IPrediction[] | null;
}

export const defaultValue: Readonly<IEnergie> = {};
