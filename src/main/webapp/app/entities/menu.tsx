import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = ({ isAdmin }) => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/preference">
        Preference
      </MenuItem>
      <MenuItem icon="asterisk" to="/consommation">
        Consumption
      </MenuItem>
      {isAdmin && (
        <MenuItem icon="asterisk" to="/energie">
          Energy System
        </MenuItem>
      )}

      {/* {isAdmin && (
        <MenuItem icon="asterisk" to="/anomalie">
          Anomaly
        </MenuItem>
      )} */}
      {isAdmin && (
        <MenuItem icon="asterisk" to="/prediction">
          Prediction
        </MenuItem>
      )}

      {/* {isAdmin && (
        <MenuItem icon="asterisk" to="/etage">
          Floor
        </MenuItem>
      )} */}
      {isAdmin && (
        <MenuItem icon="asterisk" to="/locale">
          Local
        </MenuItem>
      )}
      {isAdmin && (
        <MenuItem icon="asterisk" to="/batiment">
          Building
        </MenuItem>
      )}
      {isAdmin && (
        <MenuItem icon="asterisk" to="/capteur">
          Sensor
        </MenuItem>
      )}
      {isAdmin && (
        <MenuItem icon="asterisk" to="/boitier">
          Boitier
        </MenuItem>
      )}
      {isAdmin && (
        <MenuItem icon="asterisk" to="/locale-boitier/new">
          Assign Boitier to Local
        </MenuItem>
      )}
      {isAdmin && (
        <MenuItem icon="asterisk" to="/capteur-boitier/new">
          Assign Sensor to Boitier
        </MenuItem>
      )}

      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
