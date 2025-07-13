package services;

import entities.Filme;
import entities.Sala;
import entities.Sessao;
import exceptions.CampoInvalido;
import repositories.RepositorioSessao;

import java.util.List;

public class SessaoService {
    private RepositorioSessao repositorio;

    public SessaoService(RepositorioSessao repositorio) {
        this.repositorio = repositorio;
    }

    public void criarSessao(Sessao sessao) {
        if (sessao == null) {
            throw new CampoInvalido("Sessão não pode ser nula.");
        }
        if (repositorio.existeSessao(sessao)) {
            throw new CampoInvalido("Sessão já cadastrada.");
        }
        repositorio.adicionarSessao(sessao);
    }

    public void alterarSalaSessao(Sessao sessao, Sala novaSala) {
        if (sessao == null || novaSala == null) {
            throw new CampoInvalido("Sessão ou sala inválidas.");
        }
        if (!repositorio.existeSessao(sessao)) {
            throw new CampoInvalido("Sessão não encontrada.");
        }
        sessao.setSala(novaSala);
    }

    public void alterarFilmeSessao(Sessao sessao, Filme novoFilme) {
        if (sessao == null || novoFilme == null) {
            throw new CampoInvalido("Sessão ou filme inválidos.");
        }
        if (!repositorio.existeSessao(sessao)) {
            throw new CampoInvalido("Sessão não encontrada.");
        }
        sessao.setFilme(novoFilme);
    }

    public void alterarDataSessao(Sessao sessao, String novaData) {
        if (sessao == null || novaData == null || novaData.isEmpty()) {
            throw new CampoInvalido("Sessão ou data inválidas.");
        }
        if (!repositorio.existeSessao(sessao)) {
            throw new CampoInvalido("Sessão não encontrada.");
        }
        sessao.setData(novaData);
    }

    public void exibirSessoes() {
        List<Sessao> sessoes = repositorio.obterTodasSessoes();
        System.out.println("==== Filmes em Cartaz ====");
        for (Sessao sessao : sessoes) {
            System.out.println("Filme: " + sessao.getFilme().getNome());
            System.out.println("Sala: " + sessao.getSala().getNumeroSala());
            System.out.println("Data: " + sessao.getDataHora());
            System.out.println("-------------------------");
        }
    }

    public List<Sessao> buscarSessoesPorFilme(String nomeFilme) {
        if (nomeFilme == null || nomeFilme.trim().isEmpty()) {
            throw new CampoInvalido("Nome do filme não pode ser vazio.");
        }
        return repositorio.buscarSessoesPorFilme(nomeFilme);
    }

    public void exibirSessoesPorFilme(String nomeFilme) {
        List<Sessao> sessoes = repositorio.buscarSessoesPorFilme(nomeFilme);
        if (sessoes.isEmpty()) {
            System.out.println("Nenhuma sessão encontrada para o filme: " + nomeFilme);
            return;
        }
        for (Sessao sessao : sessoes) {
            System.out.println("Sala: " + sessao.getSala().getNumeroSala());
            System.out.println("Data: " + sessao.getDataHora());
            System.out.println("-------------------------");
        }
    }
}
