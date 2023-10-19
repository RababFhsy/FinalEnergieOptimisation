import dayjs from 'dayjs';
import { ILocale } from 'app/shared/model/locale.model';
import { IEnergie } from 'app/shared/model/energie.model';

export interface IAnomalie {
  id?: number;
  zoneNormaleMin?: number | null;
  zoneNormaleMax?: number | null;
  dateAnomalie?: string | null;
  descriptionAnomalie?: string | null;
  locale?: ILocale | null;
  energie?: IEnergie | null;
}

export const defaultValue: Readonly<IAnomalie> = {};
