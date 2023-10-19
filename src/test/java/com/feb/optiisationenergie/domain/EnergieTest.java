package com.feb.optiisationenergie.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.feb.optiisationenergie.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EnergieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Energie.class);
        Energie energie1 = new Energie();
        energie1.setId(1L);
        Energie energie2 = new Energie();
        energie2.setId(energie1.getId());
        assertThat(energie1).isEqualTo(energie2);
        energie2.setId(2L);
        assertThat(energie1).isNotEqualTo(energie2);
        energie1.setId(null);
        assertThat(energie1).isNotEqualTo(energie2);
    }
}
