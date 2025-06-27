package repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.Sala;
import entities.Sessao;

public class RepositorioSalas {
    private List<Sala> salasTotais;
    private HashMap<Sessao, Sala> salasPorSessao;
    private char[][] assentosSalaPadrao;

    public RepositorioSalas() {
        salasTotais = new ArrayList<>();
        salasPorSessao = new HashMap<>();
    }

    public void ocuparSalaComSessao(Sala sala, Sessao sessao) {
        if (sala == null || sessao == null) {
            throw new IllegalArgumentException("Sala ou sessão não podem ser nulos.");
        }
        if (!salasTotais.contains(sala)) {
            throw new IllegalArgumentException("Sala não encontrada.");
        }
        if (!salasPorSessao.containsKey(sessao)) {
            salasPorSessao.put(sessao, sala);
        } else {
           throw new IllegalArgumentException("Sessão já está ocupada em outra sala.");
        }
    }

    public Sala buscarSalaPorSessao(Sessao sessao) {
        if (sessao == null) {
            throw new IllegalArgumentException("Sessão não pode ser nula.");
        }
        return  salasPorSessao.get(sessao);
    }
    
     public void inicializarAssentos() {
        assentosSalaPadrao = new char[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assentosSalaPadrao[i][j] = 'A'; // 'A' representa assento livre
            }
        }
    }

     public char[][] getAssentosSalaPadrao() {
        return assentosSalaPadrao;
    }

    public void setAssentosSalaPadrao(char[][] assentosAtualizados) {
        this.assentosSalaPadrao = assentosAtualizados;
    }
    
}
