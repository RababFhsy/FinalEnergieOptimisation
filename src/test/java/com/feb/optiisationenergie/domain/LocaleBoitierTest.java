package com.feb.optiisationenergie.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.feb.optiisationenergie.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LocaleBoitierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocaleBoitier.class);
        LocaleBoitier localeBoitier1 = new LocaleBoitier();
        localeBoitier1.setId(1L);
        LocaleBoitier localeBoitier2 = new LocaleBoitier();
        localeBoitier2.setId(localeBoitier1.getId());
        assertThat(localeBoitier1).isEqualTo(localeBoitier2);
        localeBoitier2.setId(2L);
        assertThat(localeBoitier1).isNotEqualTo(localeBoitier2);
        localeBoitier1.setId(null);
        assertThat(localeBoitier1).isNotEqualTo(localeBoitier2);
    }
}
