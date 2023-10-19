package com.feb.optiisationenergie.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.feb.optiisationenergie.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConsommationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Consommation.class);
        Consommation consommation1 = new Consommation();
        consommation1.setId(1L);
        Consommation consommation2 = new Consommation();
        consommation2.setId(consommation1.getId());
        assertThat(consommation1).isEqualTo(consommation2);
        consommation2.setId(2L);
        assertThat(consommation1).isNotEqualTo(consommation2);
        consommation1.setId(null);
        assertThat(consommation1).isNotEqualTo(consommation2);
    }
}
