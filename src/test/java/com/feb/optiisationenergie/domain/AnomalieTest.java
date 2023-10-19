package com.feb.optiisationenergie.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.feb.optiisationenergie.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnomalieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Anomalie.class);
        Anomalie anomalie1 = new Anomalie();
        anomalie1.setId(1L);
        Anomalie anomalie2 = new Anomalie();
        anomalie2.setId(anomalie1.getId());
        assertThat(anomalie1).isEqualTo(anomalie2);
        anomalie2.setId(2L);
        assertThat(anomalie1).isNotEqualTo(anomalie2);
        anomalie1.setId(null);
        assertThat(anomalie1).isNotEqualTo(anomalie2);
    }
}
