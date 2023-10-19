import dayjs from 'dayjs';
import { ILocale } from 'app/shared/model/locale.model';
import { IEnergie } from 'app/shared/model/energie.model';

export interface IConsommation {
  id?: number;
  energieConsommation?: string | null;
  dateConsommation?: string | null;
  locale?: ILocale | null;
  energie?: IEnergie | null;
}

export const defaultValue: Readonly<IConsommation> = {};
