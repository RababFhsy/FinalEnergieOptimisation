package com.feb.optiisationenergie.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.feb.optiisationenergie.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BatimentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Batiment.class);
        Batiment batiment1 = new Batiment();
        batiment1.setId(1L);
        Batiment batiment2 = new Batiment();
        batiment2.setId(batiment1.getId());
        assertThat(batiment1).isEqualTo(batiment2);
        batiment2.setId(2L);
        assertThat(batiment1).isNotEqualTo(batiment2);
        batiment1.setId(null);
        assertThat(batiment1).isNotEqualTo(batiment2);
    }
}
