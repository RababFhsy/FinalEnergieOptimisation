import { IEtage } from 'app/shared/model/etage.model';

export interface ILocale {
  id?: number;
  numero?: number | null;
  typeLocal?: string | null;
  etage?: IEtage | null;
}

export const defaultValue: Readonly<ILocale> = {};
