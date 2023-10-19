export interface IBatiment {
  id?: number;
  adresse?: string | null;
  batimentNom?: string | null;
}

export const defaultValue: Readonly<IBatiment> = {};
