export interface IBoitier {
  id?: number;
  boitierReference?: string | null;
  type?: string | null;
  nbrBranche?: number | null;
}

export const defaultValue: Readonly<IBoitier> = {};
