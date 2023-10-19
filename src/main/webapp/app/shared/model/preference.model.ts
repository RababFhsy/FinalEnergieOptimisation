import { IUser } from 'app/shared/model/user.model';
import { IEnergie } from 'app/shared/model/energie.model';

export interface IPreference {
  id?: number;
  tempMinValue?: number | null;
  tempMaxValue?: number | null;
  plageHoraire?: number | null;
  user?: IUser | null;
  energie?: IEnergie | null;
}

export const defaultValue: Readonly<IPreference> = {};
