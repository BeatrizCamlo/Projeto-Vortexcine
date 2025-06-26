package entities;

import java.util.Objects;

public class Coordenada {
    private String linha;
    private int coluna;

    public Coordenada(String linha, int coluna){
        this.linha = linha;
        this.coluna = coluna;
    }

    public String getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void exibirCoordenada(){
        System.out.println(linha + coluna);
    }

    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (!(obj instanceof Coordenada)) {
            return false;
        }
        Coordenada outra = (Coordenada) obj;
        return linha.equals(outra.linha) && coluna == outra.coluna;
    }
    
    public int hashCode() {
        return Objects.hash(linha, coluna);
    }
}
