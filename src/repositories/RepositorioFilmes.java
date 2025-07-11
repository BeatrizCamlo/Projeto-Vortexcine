package repositories;
import java.util.ArrayList;
import java.util.List;
import entities.Filme;
import entities.GeneroFilme;

public class RepositorioFilmes {
    private List<Filme> filmes;

    public RepositorioFilmes() {
        this.filmes = new ArrayList<>();
    }

    public void adicionar(Filme filme) {
        filmes.add(filme);
    }

    public void remover(Filme filme) {
        filmes.remove(filme);
    }

    public Filme obterPorNome(String nome) {
        for (Filme filme : filmes) {
            if (filme.getNome().equalsIgnoreCase(nome)) {
                return filme;
            }
        }
        return null;
    }

    public List<Filme> obterTodos() {
        return new ArrayList<>(filmes);
    }

    public boolean contem(Filme filme) {
        return filmes.contains(filme);
    }

    public void popularFilmesIniciais() {
        adicionar(new Filme("Matrix", 136, GeneroFilme.ACAO));
        adicionar(new Filme("O Poderoso Chefão", 175, GeneroFilme.DRAMA));
        adicionar(new Filme("Toy Story", 81, GeneroFilme.ANIMACAO));
        adicionar(new Filme("Titanic", 195, GeneroFilme.ROMANCE));
        adicionar(new Filme("Vingadores: Ultimato", 181, GeneroFilme.ACAO));
    }
}
