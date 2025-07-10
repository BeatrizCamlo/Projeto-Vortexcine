package repositories;
import java.util.ArrayList;
import java.util.List;
import entities.Filme;
import entities.Funcionario;
import entities.GeneroFilme;
import exceptions.*;

public class RepositorioFilmes {
    private List<Filme> filmes;

    public RepositorioFilmes() {
        this.filmes = new ArrayList<>();
    }

    public void adicionarFilme(Funcionario funcionario, Filme filme) {
        if(!funcionario.ehGerente()){
            throw new CampoInvalido("Apenas gerentes podem adicionar filmes.");
        }
        if (filme == null) {
            throw new CampoInvalido("Filme não pode ser nulo.");
        }
        if(filmes.contains(filme)) {
            throw new CampoInvalido("Filme já cadastrado.");
        }
        if (obterFilmePorNome(filme.getNome()) != null) {
            throw new CampoInvalido("Já existe um filme cadastrado com este nome.");
        }
        System.out.println("Adicionando filme: " + filme.getNome());
        filmes.add(filme);
    }

    public Filme obterFilmePorNome(String nome) {
        for (Filme filme : filmes) {
            if (filme.getNome().equalsIgnoreCase(nome)) {
                return filme;
            }
        }
        return null;
    }

    public List<Filme> obterTodosFilmes() {
        return new ArrayList<>(filmes);
    }

    public void removerFilme(Funcionario funcionario, Filme filme) {
        if(!funcionario.ehGerente()){
            throw new CampoInvalido("Apenas gerentes podem remover filmes.");
        }
        if (filme == null) {
            throw new CampoInvalido("Filme não pode ser nulo.");
        }
        if (!filmes.contains(filme)) {
            throw new CampoInvalido("Filme não encontrado.");
        }
        System.out.println("Removendo filme: " + filme.getNome());
        filmes.remove(filme); 
    }

    public void modificarNomeFilme(Funcionario funcionario, Filme filme, String novoNome) {
        if(!funcionario.ehGerente()){
            throw new CampoInvalido("Apenas gerentes podem modificar filmes.");
        }
        if (filme == null || novoNome == null || novoNome.isEmpty()) {
            throw new CampoInvalido("Filme ou nome não podem ser nulos ou vazios.");
        }
        if (!filmes.contains(filme)) {
            throw new CampoInvalido("Filme não encontrado.");
        }
        if (obterFilmePorNome(novoNome) != null) {
            throw new CampoInvalido("Já existe um filme cadastrado com este nome.");
        }
        System.out.println("Modificando nome do filme: " + filme.getNome() + " para " + novoNome);
        filme.setNome(novoNome);
    }

    public void modificarDuracaoFilme(Funcionario funcionario, Filme filme, int novaDuracao) {
        if(!funcionario.ehGerente()){
            throw new CampoInvalido("Apenas gerentes podem modificar filmes.");
        }
        if (filme == null || novaDuracao <= 0) {
            throw new CampoInvalido("Filme não pode ser nulo e duração deve ser positiva.");
        }
        if (!filmes.contains(filme)) {
            throw new CampoInvalido("Filme não encontrado.");
        }
        filme.setDuracaoEmMinutos(novaDuracao);
    }

    public void modificarGeneroFilme(Funcionario funcionario, Filme filme, GeneroFilme novoGenero) {
        if(!funcionario.ehGerente()){
            throw new CampoInvalido("Apenas gerentes podem modificar filmes.");
        }
        if (filme == null || novoGenero == null || novoGenero.toString().isEmpty()) {
            throw new CampoInvalido("Filme ou gênero não podem ser nulos ou vazios.");
        }
        if (!filmes.contains(filme)) {
            throw new CampoInvalido("Filme não encontrado.");
        }
        filme.setGenero(novoGenero);
    }

    public void popularFilmesIniciais() {
        filmes.add(new Filme("Matrix", 136, GeneroFilme.ACAO));
        filmes.add(new Filme("O Poderoso Chefão", 175, GeneroFilme.DRAMA));
        filmes.add(new Filme("Toy Story", 81, GeneroFilme.ANIMACAO));
        filmes.add(new Filme("Titanic", 195, GeneroFilme.ROMANCE));
        filmes.add(new Filme("Vingadores: Ultimato", 181, GeneroFilme.ACAO));
    }
}
