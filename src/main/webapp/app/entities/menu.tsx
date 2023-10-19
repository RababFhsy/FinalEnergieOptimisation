import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/preference">
        Preference
      </MenuItem>
      <MenuItem icon="asterisk" to="/energie">
        Energie
      </MenuItem>
      <MenuItem icon="asterisk" to="/consommation">
        Consommation
      </MenuItem>
      <MenuItem icon="asterisk" to="/anomalie">
        Anomalie
      </MenuItem>
      <MenuItem icon="asterisk" to="/prediction">
        Prediction
      </MenuItem>
      <MenuItem icon="asterisk" to="/etage">
        Etage
      </MenuItem>
      <MenuItem icon="asterisk" to="/locale">
        Locale
      </MenuItem>
      <MenuItem icon="asterisk" to="/batiment">
        Batiment
      </MenuItem>
      <MenuItem icon="asterisk" to="/capteur">
        Capteur
      </MenuItem>
      <MenuItem icon="asterisk" to="/boitier">
        Boitier
      </MenuItem>
      <MenuItem icon="asterisk" to="/locale-boitier">
        Locale Boitier
      </MenuItem>
      <MenuItem icon="asterisk" to="/capteur-boitier">
        Capteur Boitier
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
