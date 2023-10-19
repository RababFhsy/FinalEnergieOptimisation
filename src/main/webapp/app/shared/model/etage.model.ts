import { IBatiment } from 'app/shared/model/batiment.model';

export interface IEtage {
  id?: number;
  etageNumero?: number | null;
  batiment?: IBatiment | null;
}

export const defaultValue: Readonly<IEtage> = {};
