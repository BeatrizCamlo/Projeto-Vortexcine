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

    public void atualizarNome(Filme filme, String novoNome) {
        filme.setNome(novoNome);
    }

    public void atualizarDuracao(Filme filme, int duracao) {
        filme.setDuracaoEmMinutos(duracao);
    }

    public void atualizarGenero(Filme filme, GeneroFilme genero) {
        filme.setGenero(genero);
    }

    public List<Filme> obterTodos() {
        return new ArrayList<>(filmes);
    }

    public boolean contem(Filme filme) {
        return filmes.contains(filme);
    }
}
