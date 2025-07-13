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


    public List<Sessao> obterTodasSessoes() {
        return new ArrayList<>(sessoesDisponiveis);
    }

    public boolean existeSessao(Sessao sessao) {
        return sessoesDisponiveis.contains(sessao);
    }
    public void adicionarSessao(Sessao sessao) {
    sessoesDisponiveis.add(sessao);
    String nomeFilme = sessao.getFilme().getNome().toLowerCase().trim();
    sessoesPorFilme.computeIfAbsent(nomeFilme, k -> new ArrayList<>()).add(sessao);
    }

    public List<Sessao> buscarSessoesPorFilme(String nomeFilme) {
        return sessoesPorFilme.getOrDefault(nomeFilme.toLowerCase().trim(), new ArrayList<>());
    }

}
