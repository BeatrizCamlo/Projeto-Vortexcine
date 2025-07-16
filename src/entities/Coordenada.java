package entities;

import exceptions.CampoInvalido;

public class Coordenada {
    private String cadeira;

    public Coordenada(String cadeira) {
        if (cadeira == null || cadeira.length() < 2) {
            throw new CampoInvalido("Coordenada inválida.");
        }
        char linha = Character.toUpperCase(cadeira.charAt(0));
        if (linha < 'A' || linha > 'E') {
            throw new CampoInvalido("Linha inválida. Deve ser de A a E.");
        }
        String numeroStr = cadeira.substring(1);
        int numero;
        try {
            numero = Integer.parseInt(numeroStr);
        } catch (NumberFormatException e) {
            throw new CampoInvalido("Número inválido no assento.");
        }
        if (numero < 1 || numero > 10) {
            throw new CampoInvalido("Número inválido. Deve ser entre 1 e 10.");
        }

        this.cadeira = linha + String.valueOf(numero);
    }

    public String getCadeira() {
        return cadeira;
    }

    public char getLinha() {
        return cadeira.charAt(0);
    }

    public int getColuna() {
        return Integer.parseInt(cadeira.substring(1));
    }

    @Override
    public String toString() {
        return cadeira;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordenada)) return false;
        Coordenada that = (Coordenada) o;
        return cadeira.equalsIgnoreCase(that.cadeira);
    }

    @Override
    public int hashCode() {
        return cadeira.toUpperCase().hashCode();
    }
}
