package repositories;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import entities.Funcionario;
import interfaces.RepositorioInterface;
import exceptions.*;

public class RepositorioFuncionarios implements RepositorioInterface<Funcionario> {

    private List<Funcionario> funcionariosAtivos;
    private HashMap<String, Funcionario> funcionariosPorEmail;

    public RepositorioFuncionarios() {
        this.funcionariosAtivos = new ArrayList<>();
        this.funcionariosPorEmail = new HashMap<>();
    }

    @Override
    public void cadastrar(Funcionario funcionario) {
        if (funcionario == null) {
            throw new CampoInvalido("Funcionário não pode ser nulo.");
        }
        if (funcionariosAtivos.contains(funcionario)) {
            throw new CampoInvalido("Funcionário já cadastrado.");
        }
        if (funcionariosPorEmail.containsKey(funcionario.getEmail())) {
            throw new CampoInvalido("Já existe um funcionário com este email.");
        }
        funcionariosAtivos.add(funcionario);
        funcionariosPorEmail.put(funcionario.getEmail(), funcionario);
    }

    @Override
    public void remover(Funcionario funcionario) {
        if (funcionario == null || !funcionariosAtivos.contains(funcionario)) {
            throw new CampoInvalido("Funcionário não encontrado.");
        }
        funcionariosAtivos.remove(funcionario);
        funcionariosPorEmail.remove(funcionario.getEmail());
    }

    @Override
    public void editarNome(Funcionario funcionario, String novoNome) {
        if (funcionario == null || novoNome == null || novoNome.isEmpty()) {
            throw new CampoInvalido("Funcionário ou nome inválidos.");
        }
        if (!funcionariosAtivos.contains(funcionario)) {
            throw new CampoInvalido("Funcionário não encontrado.");
        }
        funcionario.setNome(novoNome);
    }

    @Override
    public void editarEmail(Funcionario funcionario, String novoEmail) {
        if (funcionario == null || novoEmail == null || novoEmail.isEmpty()) {
            throw new CampoInvalido("Funcionário ou email inválidos.");
        }
        if (!funcionariosAtivos.contains(funcionario)) {
            throw new CampoInvalido("Funcionário não encontrado.");
        }
        if (funcionariosPorEmail.containsKey(novoEmail)) {
            throw new CampoInvalido("Já existe um funcionário com este email.");
        }

        funcionariosPorEmail.remove(funcionario.getEmail());
        funcionario.setEmail(novoEmail);
        funcionariosPorEmail.put(novoEmail, funcionario);
    }

    @Override
    public void editarSenha(Funcionario funcionario, String novaSenha) {
        if (funcionario == null || novaSenha == null || novaSenha.isEmpty()) {
            throw new CampoInvalido("Funcionário ou senha inválidos.");
        }
        if (!funcionariosAtivos.contains(funcionario)) {
            throw new CampoInvalido("Funcionário não encontrado.");
        }
        funcionario.setSenha(novaSenha);
    }

    @Override
    public Funcionario obterPorNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new CampoInvalido("Nome inválido.");
        }
        for (Funcionario funcionario : funcionariosAtivos) {
            if (funcionario.getNome().equalsIgnoreCase(nome)) {
                return funcionario;
            }
        }
        return null;
    }

    @Override
    public Funcionario obterPorId(int id) {
        for (Funcionario funcionario : funcionariosAtivos) {
            if (funcionario.getId() == id) {
                return funcionario;
            }
        }
        return null;
    }

    @Override
    public Funcionario obterPorEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new CampoInvalido("Email inválido.");
        }
        return funcionariosPorEmail.get(email);
    }

    // Método útil adicional
    public List<Funcionario> obterTodosFuncionarios() {
        return new ArrayList<>(funcionariosAtivos);
    }

    public Funcionario autenticarFuncionario(String email, String senha) {
        if (email == null || senha == null || email.isEmpty() || senha.isEmpty()) {
            throw new CampoInvalido("Email e senha não podem ser vazios.");
        }
        Funcionario funcionario = funcionariosPorEmail.get(email);
        if (funcionario == null || !funcionario.getSenha().equals(senha)) {
            throw new CampoInvalido("Email ou senha inválidos.");
        }
        return funcionario;
    }

    public void modificarStatusGerente(Funcionario funcionario, boolean novoStatus) {
        if (!funcionariosAtivos.contains(funcionario)) {
            throw new CampoInvalido("Funcionário não encontrado.");
        }
        funcionario.setEhGerente(novoStatus);
    }

    public void inicializarGerentePadrao() {
        Funcionario gerentePadrao = new Funcionario("adm", "adm", "adm", true);
        try {
            cadastrar(gerentePadrao);
        } catch (CampoInvalido e) {
            System.out.println("Gerente padrão já existente.");
        }
    }

    public Funcionario buscarPorEmail(String email) {
        return funcionariosPorEmail.get(email);
    }
}
