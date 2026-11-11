package com.gaspo.api.util;

/**
 * CPF guardado e comparado sempre só com os 11 dígitos.
 */
public final class CpfUtils {

    private CpfUtils() {
    }

    /** Remove pontos, traço e espaços: "529.982.247-25" -> "52998224725". */
    public static String normalizar(String cpf) {
        return cpf == null ? "" : cpf.replaceAll("\\D", "");
    }

    /** "52998224725" -> "529.982.247-25" (para achar registros antigos gravados com máscara). */
    public static String formatar(String cpf) {
        String d = normalizar(cpf);
        if (d.length() != 11) {
            return d;
        }
        return d.substring(0, 3) + "." + d.substring(3, 6) + "." + d.substring(6, 9) + "-" + d.substring(9);
    }

    /** 11 dígitos, não repetidos, com os dígitos verificadores corretos. */
    public static boolean valido(String cpf) {
        String d = normalizar(cpf);
        if (d.length() != 11 || d.chars().distinct().count() == 1) {
            return false;
        }
        return digito(d, 9) == d.charAt(9) - '0' && digito(d, 10) == d.charAt(10) - '0';
    }

    private static int digito(String d, int tamanho) {
        int soma = 0;
        for (int i = 0; i < tamanho; i++) {
            soma += (d.charAt(i) - '0') * (tamanho + 1 - i);
        }
        int resto = (soma * 10) % 11;
        return resto == 10 ? 0 : resto;
    }
}
