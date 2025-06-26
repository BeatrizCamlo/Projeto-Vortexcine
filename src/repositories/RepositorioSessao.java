package repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.Sessao;

public class RepositorioSessao {
    private List<Sessao> sessoesDisponiveis;
    private HashMap<String, Sessao> sessoesPorFilme;

    public RepositorioSessao() {
        this.sessoesDisponiveis = new ArrayList<>();
        this.sessoesPorFilme = new HashMap<>();
    }

    public void adicionarSessao(Sessao sessao) {
        if (sessao == null) {
            throw new IllegalArgumentException("Sessão não pode ser nula.");
        }
        if (sessoesDisponiveis.contains(sessao)) {
            throw new IllegalArgumentException("Sessão já cadastrada.");
        }
        if (sessoesPorFilme.containsKey(sessao.getFilme().getNome())) {
            throw new IllegalArgumentException("Já existe uma sessão cadastrada para este filme.");
        }
        sessoesDisponiveis.add(sessao);
        sessoesPorFilme.put(sessao.getFilme().getNome(), sessao);
    }

    public void modificarSalaSessao(Sessao sessao, String novaSala) {
        if (sessao == null || novaSala == null || novaSala.isEmpty()) {
            throw new IllegalArgumentException("Sessão ou sala não podem ser nulos ou vazios.");
        }
        if (!sessoesDisponiveis.contains(sessao)) {
            throw new IllegalArgumentException("Sessão não encontrada.");
        }
        sessao.setSala(novaSala);
    }

    public void modificarFilmeSessao(Sessao sessao, String novoFilme) {
        if (sessao == null || novoFilme == null || novoFilme.isEmpty()) {
            throw new IllegalArgumentException("Sessão ou filme não podem ser nulos ou vazios.");
        }
        if (!sessoesDisponiveis.contains(sessao)) {
            throw new IllegalArgumentException("Sessão não encontrada.");
        }
        sessao.setFilme(novoFilme);
    }
    
    public void modificarDataSessao(Sessao sessao, String novaData) {
        if (sessao == null || novaData == null || novaData.isEmpty()) {
            throw new IllegalArgumentException("Sessão ou data não podem ser nulos ou vazios.");
        }
        if (!sessoesDisponiveis.contains(sessao)) {
            throw new IllegalArgumentException("Sessão não encontrada.");
        }
        sessao.setData(novaData);
    }
}
