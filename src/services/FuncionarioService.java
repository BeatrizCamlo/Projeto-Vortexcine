package services;

import java.util.List;

import entities.Funcionario;
import exceptions.CampoInvalido;
import repositories.RepositorioFuncionarios;

public class FuncionarioService {
    private RepositorioFuncionarios repositorio;
    private static final String dominio_aceito = "vortexcine.com";

    public FuncionarioService(RepositorioFuncionarios repositorio) {
        this.repositorio = repositorio;
    }

    private void validarEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new CampoInvalido("Email inválido.");
        }

        String dominio = email.substring(email.indexOf("@") + 1).toLowerCase();

        if (!dominio_aceito.contains(dominio)) {
            throw new CampoInvalido("Domínio de e-mail não aceito: " + dominio);
        }
    }

    public void cadastrarFuncionario(Funcionario funcionario) {
        if (funcionario == null) {
            throw new CampoInvalido("Funcionário não pode ser nulo.");
        }
        validarEmail(funcionario.getEmail());
        if (repositorio.obterPorEmail(funcionario.getEmail()) != null) {
            throw new CampoInvalido("Email já cadastrado.");
        }
        repositorio.cadastrar(funcionario);
    }

    public Funcionario autenticar(String email, String senha) {
        if (email == null || senha == null || email.isEmpty() || senha.isEmpty()) {
            throw new CampoInvalido("Email e senha não podem ser vazios.");
        }
        Funcionario funcionario = repositorio.obterPorEmail(email);
        if (funcionario == null || !funcionario.getSenha().equals(senha)) {
            throw new CampoInvalido("Email ou senha inválidos.");
        }
        return funcionario;
    }

    public void editarNome(Funcionario funcionario, String novoNome) {
        if (funcionario == null || novoNome == null || novoNome.isEmpty()) {
            throw new CampoInvalido("Nome inválido.");
        }
        repositorio.editarNome(funcionario, novoNome);
    }

    public void editarEmail(Funcionario funcionario, String novoEmail) {
        if (funcionario == null || novoEmail == null || novoEmail.isEmpty()) {
            throw new CampoInvalido("Email inválido.");
        }
        if (repositorio.obterPorEmail(novoEmail) != null) {
            throw new CampoInvalido("Email já está em uso.");
        }
        repositorio.editarEmail(funcionario, novoEmail);
    }

    public void editarSenha(Funcionario funcionario, String novaSenha) {
        if (funcionario == null || novaSenha == null || novaSenha.isEmpty()) {
            throw new CampoInvalido("Senha inválida.");
        }
        repositorio.editarSenha(funcionario, novaSenha);
    }

    public void promoverOuRebaixarGerente(Funcionario funcionario, boolean novoStatus) {
        if (funcionario == null) {
            throw new CampoInvalido("Funcionário inválido.");
        }
        repositorio.modificarStatusGerente(funcionario, novoStatus);
    }

    public void removerFuncionario(Funcionario funcionario) {
        if (funcionario == null) {
            throw new CampoInvalido("Funcionário inválido.");
        }
        if (repositorio.obterPorId(funcionario.getId()) == null) {
            throw new CampoInvalido("Funcionário não encontrado.");
        }
        repositorio.remover(funcionario);
    }

    public Funcionario buscarPorEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new CampoInvalido("Email não pode ser vazio.");
        }

        Funcionario funcionario = repositorio.obterPorEmail(email);
        if (funcionario == null) {
            throw new CampoInvalido("Cliente não encontrado.");
        }

        return funcionario;
    }

    public List<Funcionario> obterTodosClientes() {
        List<Funcionario> funcionario = repositorio.obterTodosFuncionarios();
        if (funcionario == null || funcionario.isEmpty()) {
            throw new CampoInvalido("Nenhum cliente cadastrado.");
        }
        return funcionario;
    }
}
