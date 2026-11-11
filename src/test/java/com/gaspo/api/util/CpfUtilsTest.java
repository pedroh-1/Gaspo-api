package com.gaspo.api.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CpfUtilsTest {

    @Test
    void normalizaEFormata() {
        assertEquals("52998224725", CpfUtils.normalizar("529.982.247-25"));
        assertEquals("529.982.247-25", CpfUtils.formatar("52998224725"));
        assertEquals("", CpfUtils.normalizar(null));
    }

    @Test
    void confereDigitosVerificadores() {
        assertTrue(CpfUtils.valido("529.982.247-25"));
        assertFalse(CpfUtils.valido("529.982.247-24"));
        assertFalse(CpfUtils.valido("111.111.111-11"));
        assertFalse(CpfUtils.valido("1234"));
    }
}
