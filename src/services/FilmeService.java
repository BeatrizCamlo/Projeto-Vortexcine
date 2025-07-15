package services;

import java.util.List;

import entities.Filme;
import entities.Funcionario;
import entities.GeneroFilme;
import exceptions.CampoInvalido;
import repositories.RepositorioFilmes;

public class FilmeService {
    private RepositorioFilmes repositorio;

    public FilmeService(RepositorioFilmes repositorio) {
        this.repositorio = repositorio;
    }

    public void adicionarFilme(Funcionario funcionario, Filme filme) {
        verificarGerente(funcionario);
        if (filme == null) {
            throw new CampoInvalido("Filme não pode ser nulo.");
        }
        if (repositorio.contem(filme)) {
            throw new CampoInvalido("Filme já cadastrado.");
        }
        if (repositorio.obterPorNome(filme.getNome()) != null) {
            throw new CampoInvalido("Já existe um filme com este nome.");
        }
        repositorio.adicionar(filme);
    }

    public void removerFilme(Funcionario funcionario, Filme filme) {
        verificarGerente(funcionario);
        if (filme == null) {
            throw new CampoInvalido("Filme não pode ser nulo.");
        }
        if (!repositorio.contem(filme)) {
            throw new CampoInvalido("Filme não encontrado.");
        }
        repositorio.remover(filme);
    }

    public void modificarNome(Funcionario funcionario, Filme filme, String novoNome) {
        verificarGerente(funcionario);
        if (filme == null || novoNome == null || novoNome.isEmpty()) {
            throw new CampoInvalido("Filme ou nome inválido.");
        }
        if (!repositorio.contem(filme)) {
            throw new CampoInvalido("Filme não encontrado.");
        }
        if (repositorio.obterPorNome(novoNome) != null) {
            throw new CampoInvalido("Nome de filme já existe.");
        }
        filme.setNome(novoNome);
    }

    public void modificarDuracao(Funcionario funcionario, Filme filme, int duracao) {
        verificarGerente(funcionario);
        if (filme == null || duracao <= 0) {
            throw new CampoInvalido("Filme não pode ser nulo e duração deve ser positiva.");
        }
        if (!repositorio.contem(filme)) {
            throw new CampoInvalido("Filme não encontrado.");
        }
        filme.setDuracaoEmMinutos(duracao);
    }

    public void modificarGenero(Funcionario funcionario, Filme filme, GeneroFilme genero) {
        verificarGerente(funcionario);
        if (filme == null || genero == null) {
            throw new CampoInvalido("Filme ou gênero inválido.");
        }
        if (!repositorio.contem(filme)) {
            throw new CampoInvalido("Filme não encontrado.");
        }
        filme.setGenero(genero);
    }

    private void verificarGerente(Funcionario funcionario) {
        if (funcionario == null || !funcionario.ehGerente()) {
            throw new CampoInvalido("Apenas gerentes podem executar esta ação.");
        }
    }

    public List<Filme> obterTodosFilmes(){
        return repositorio.obterTodos();
    }

    public Filme obterPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new CampoInvalido("Nome inválido.");
        }
        return repositorio.obterPorNome(nome);
    }   

    public void adicionarFilmeaoBanco(Filme filme) {
        if (filme == null) {
            throw new CampoInvalido("Filme não pode ser nulo.");
        }
        if (repositorio.contem(filme)) {
            throw new CampoInvalido("Filme já cadastrado.");
        }
        if (repositorio.obterPorNome(filme.getNome()) != null) {
            throw new CampoInvalido("Já existe um filme com este nome.");
        }
        repositorio.adicionar(filme);
    }

}
