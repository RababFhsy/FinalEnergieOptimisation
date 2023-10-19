import preference from 'app/entities/preference/preference.reducer';
import energie from 'app/entities/energie/energie.reducer';
import consommation from 'app/entities/consommation/consommation.reducer';
import anomalie from 'app/entities/anomalie/anomalie.reducer';
import prediction from 'app/entities/prediction/prediction.reducer';
import etage from 'app/entities/etage/etage.reducer';
import locale from 'app/entities/locale/locale.reducer';
import batiment from 'app/entities/batiment/batiment.reducer';
import capteur from 'app/entities/capteur/capteur.reducer';
import boitier from 'app/entities/boitier/boitier.reducer';
import localeBoitier from 'app/entities/locale-boitier/locale-boitier.reducer';
import capteurBoitier from 'app/entities/capteur-boitier/capteur-boitier.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  preference,
  energie,
  consommation,
  anomalie,
  prediction,
  etage,
  locale,
  batiment,
  capteur,
  boitier,
  localeBoitier,
  capteurBoitier,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
