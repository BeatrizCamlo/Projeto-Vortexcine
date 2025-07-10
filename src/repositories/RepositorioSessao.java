package repositories;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import entities.Filme;
import entities.Sala;
import entities.Sessao;
import exceptions.*;

public class RepositorioSessao {
    private List<Sessao> sessoesDisponiveis;
    private HashMap<String, Sessao> sessoesPorFilme;

    public RepositorioSessao() {
        this.sessoesDisponiveis = new ArrayList<>();
        this.sessoesPorFilme = new HashMap<>();
    }

    public void adicionarSessao(Sessao sessao) {
        if (sessao == null) {
            throw new CampoInvalido("Sessão não pode ser nula.");
        }
        if (sessoesDisponiveis.contains(sessao)) {
            throw new CampoInvalido("Sessão já cadastrada.");
        }
        if (sessoesPorFilme.containsKey(sessao.getFilme().getNome())) {
            throw new CampoInvalido("Já existe uma sessão cadastrada para este filme.");
        }
        sessoesDisponiveis.add(sessao);
        sessoesPorFilme.put(sessao.getFilme().getNome(), sessao);
    }

    public void modificarSalaSessao(Sessao sessao, Sala novaSala) {
        if (sessao == null || novaSala == null) {
            throw new CampoInvalido("Sessão ou sala não podem ser nulos ou vazios.");
        }
        if (!sessoesDisponiveis.contains(sessao)) {
            throw new CampoInvalido("Sessão não encontrada.");
        }
        sessao.setSala(novaSala);
    }

    public void modificarFilmeSessao(Sessao sessao, Filme novoFilme) {
        if (sessao == null || novoFilme == null) {
            throw new CampoInvalido("Sessão ou filme não podem ser nulos ou vazios.");
        }
        if (!sessoesDisponiveis.contains(sessao)) {
            throw new CampoInvalido("Sessão não encontrada.");
        }
        sessao.setFilme(novoFilme);
    }
    
    public void modificarDataSessao(Sessao sessao, String novaData) {
        if (sessao == null || novaData == null || novaData.isEmpty()) {
            throw new CampoInvalido("Sessão ou data não podem ser nulos ou vazios.");
        }
        if (!sessoesDisponiveis.contains(sessao)) {
            throw new CampoInvalido("Sessão não encontrada.");
        }
        sessao.setData(novaData);
    }

    public void exibirSessoes(){
        System.out.println("==== Filmes em Cartaz ====");
        for(Sessao sessao : sessoesDisponiveis) {
            System.out.println("Filme: " + sessao.getFilme().getNome());
            System.out.println("Sala: " + sessao.getSala().getNumeroSala());
            System.out.println("Data: " + sessao.getDataHora());
            System.out.println("-------------------------");
        }
    }
}
