export interface ICapteur {
  id?: number;
  capteurReference?: string | null;
  type?: string | null;
  photoContentType?: string | null;
  photo?: string | null;
  valeurMin?: number | null;
  valeurMax?: number | null;
}

export const defaultValue: Readonly<ICapteur> = {};
