package repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import entities.Sessao;

public class RepositorioSessao {
    private List<Sessao> sessoesDisponiveis;
    private HashMap<String, List<Sessao>> sessoesPorFilme;

    public RepositorioSessao() {
        this.sessoesDisponiveis = new ArrayList<>();
        this.sessoesPorFilme = new HashMap<>();
    }

    public void adicionarSessao(Sessao sessao) {
        sessoesDisponiveis.add(sessao);
        String nomeFilme = sessao.getFilme().getNome();
        sessoesPorFilme.computeIfAbsent(nomeFilme, k -> new ArrayList<>()).add(sessao);
    }

    public List<Sessao> obterTodasSessoes() {
        return new ArrayList<>(sessoesDisponiveis);
    }

    public List<Sessao> buscarSessoesPorFilme(String nomeFilme) {
        return sessoesPorFilme.getOrDefault(nomeFilme, new ArrayList<>());
    }

    public boolean existeSessao(Sessao sessao) {
        return sessoesDisponiveis.contains(sessao);
    }
}
