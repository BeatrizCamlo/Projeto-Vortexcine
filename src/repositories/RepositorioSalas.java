package repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.Sala;
import entities.Sessao;

public class RepositorioSalas {
    private List<Sala> salasTotais;
    private HashMap<Sessao, Sala> salasPorSessao;

    public RepositorioSalas() {
        this.salasTotais = new ArrayList<>();
        this.salasPorSessao = new HashMap<>();
    }

    public void adicionar(Sala sala) {
        salasTotais.add(sala);
    }

    public void associarSessao(Sala sala, Sessao sessao) {
        salasPorSessao.put(sessao, sala);
    }

    public Sala buscarPorSessao(Sessao sessao) {
        return salasPorSessao.get(sessao);
    }

    public boolean contem(Sala sala) {
        return salasTotais.contains(sala);
    }

    public List<Sala> obterTodas() {
        return new ArrayList<>(salasTotais);
    }

    public boolean sessaoJaAssociada(Sessao sessao) {
        return salasPorSessao.containsKey(sessao);
    }

    public Sala buscarPorNumero(int numero) {
        for (Sala sala : salasTotais) {
            if (sala.getNumeroSala() == numero) {
                return sala;
            }
        }
        return null;
    }
}
