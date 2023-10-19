import { ICapteur } from 'app/shared/model/capteur.model';
import { IBoitier } from 'app/shared/model/boitier.model';

export interface ICapteurBoitier {
  id?: number;
  branche?: string | null;
  capteur?: ICapteur | null;
  boitier?: IBoitier | null;
}

export const defaultValue: Readonly<ICapteurBoitier> = {};
