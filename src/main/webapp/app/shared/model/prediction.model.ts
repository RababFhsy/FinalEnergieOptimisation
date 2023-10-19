import dayjs from 'dayjs';
import { ILocale } from 'app/shared/model/locale.model';
import { IEnergie } from 'app/shared/model/energie.model';

export interface IPrediction {
  id?: number;
  dateDebut?: string | null;
  dateFin?: string | null;
  consommationPredit?: number | null;
  precision?: string | null;
  locale?: ILocale | null;
  energie?: IEnergie | null;
}

export const defaultValue: Readonly<IPrediction> = {};
