package repositories;

import java.util.ArrayList;
import java.util.List;

import entities.Filme;
import entities.Funcionario;
import entities.GeneroFilme;

public class RepositorioFilmes {
    private List<Filme> filmes;

    public RepositorioFilmes() {
        this.filmes = new ArrayList<>();
    }

    public void adicionarFilme(Funcionario funcionario, Filme filme) {
        if(!funcionario.ehGerente()){
            throw new IllegalArgumentException("Apenas gerentes podem adicionar filmes.");
        }
        if (filme == null) {
            throw new IllegalArgumentException("Filme não pode ser nulo.");
        }
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
            throw new IllegalArgumentException("Apenas gerentes podem remover filmes.");
        }
        if (filme == null) {
            throw new IllegalArgumentException("Filme não pode ser nulo.");
        }
        if (!filmes.contains(filme)) {
            throw new IllegalArgumentException("Filme não encontrado.");
        }
        filmes.remove(filme); 
    }

    public void modificarNomeFilme(Funcionario funcionario, Filme filme, String novoNome) {
        if(!funcionario.ehGerente()){
            throw new IllegalArgumentException("Apenas gerentes podem modificar filmes.");
        }
        if (filme == null || novoNome == null || novoNome.isEmpty()) {
            throw new IllegalArgumentException("Filme ou nome não podem ser nulos ou vazios.");
        }
        if (!filmes.contains(filme)) {
            throw new IllegalArgumentException("Filme não encontrado.");
        }
        filme.setNome(novoNome);
    }

    public void modificarDuracaoFilme(Funcionario funcionario, Filme filme, int novaDuracao) {
        if(!funcionario.ehGerente()){
            throw new IllegalArgumentException("Apenas gerentes podem modificar filmes.");
        }
        if (filme == null || novaDuracao <= 0) {
            throw new IllegalArgumentException("Filme não pode ser nulo e duração deve ser positiva.");
        }
        if (!filmes.contains(filme)) {
            throw new IllegalArgumentException("Filme não encontrado.");
        }
        filme.setDuracaoEmMinutos(novaDuracao);
    }

    public void modificarGeneroFilme(Funcionario funcionario, Filme filme, GeneroFilme novoGenero) {
        if(!funcionario.ehGerente()){
            throw new IllegalArgumentException("Apenas gerentes podem modificar filmes.");
        }
        if (filme == null || novoGenero == null || novoGenero.toString().isEmpty()) {
            throw new IllegalArgumentException("Filme ou gênero não podem ser nulos ou vazios.");
        }
        if (!filmes.contains(filme)) {
            throw new IllegalArgumentException("Filme não encontrado.");
        }
        filme.setGenero(novoGenero);
    }
}
