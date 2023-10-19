import dayjs from 'dayjs';
import { ILocale } from 'app/shared/model/locale.model';
import { IBoitier } from 'app/shared/model/boitier.model';

export interface ILocaleBoitier {
  id?: number;
  dateDebut?: string | null;
  dateFin?: string | null;
  locale?: ILocale | null;
  boitier?: IBoitier | null;
}

export const defaultValue: Readonly<ILocaleBoitier> = {};
