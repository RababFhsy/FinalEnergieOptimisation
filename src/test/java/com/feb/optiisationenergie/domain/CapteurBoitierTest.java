package com.feb.optiisationenergie.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.feb.optiisationenergie.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CapteurBoitierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CapteurBoitier.class);
        CapteurBoitier capteurBoitier1 = new CapteurBoitier();
        capteurBoitier1.setId(1L);
        CapteurBoitier capteurBoitier2 = new CapteurBoitier();
        capteurBoitier2.setId(capteurBoitier1.getId());
        assertThat(capteurBoitier1).isEqualTo(capteurBoitier2);
        capteurBoitier2.setId(2L);
        assertThat(capteurBoitier1).isNotEqualTo(capteurBoitier2);
        capteurBoitier1.setId(null);
        assertThat(capteurBoitier1).isNotEqualTo(capteurBoitier2);
    }
}
