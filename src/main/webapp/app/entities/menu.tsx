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
      Consumption
      </MenuItem>
      <MenuItem icon="asterisk" to="/anomalie">
        Anomalie
      </MenuItem>
      <MenuItem icon="asterisk" to="/prediction">
        Prediction
      </MenuItem>
      <MenuItem icon="asterisk" to="/etage">
        Floor
      </MenuItem>
      <MenuItem icon="asterisk" to="/locale">
        Local
      </MenuItem>
      <MenuItem icon="asterisk" to="/batiment">
      Building
      </MenuItem>
      <MenuItem icon="asterisk" to="/capteur">
        Sensor
      </MenuItem>
      <MenuItem icon="asterisk" to="/boitier">
        Boitier
      </MenuItem>
      <MenuItem icon="asterisk" to="/locale-boitier">
        Local Boitier
      </MenuItem>
      <MenuItem icon="asterisk" to="/capteur-boitier">
        Assign Sensor to Boitier
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
