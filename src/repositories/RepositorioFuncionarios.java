package repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.Funcionario;
import interfaces.RepositorioInterface;

public class RepositorioFuncionarios implements RepositorioInterface<Funcionario> {

    private List<Funcionario> funcionariosAtivos;
    private HashMap<String, Funcionario> funcionariosPorEmail;

    public RepositorioFuncionarios() {
        this.funcionariosAtivos = new ArrayList<>();
        this.funcionariosPorEmail = new HashMap<>();
    }

    public void cadastrar(Funcionario funcionario) {
        funcionariosAtivos.add(funcionario);
        funcionariosPorEmail.put(funcionario.getEmail(), funcionario);
    }

    public void remover(Funcionario funcionario) {
        funcionariosAtivos.remove(funcionario);
        funcionariosPorEmail.remove(funcionario.getEmail());
    }

    public void editarNome(Funcionario funcionario, String novoNome) {
        funcionario.setNome(novoNome);
    }

    public void editarEmail(Funcionario funcionario, String novoEmail) {
        funcionariosPorEmail.remove(funcionario.getEmail());
        funcionario.setEmail(novoEmail);
        funcionariosPorEmail.put(novoEmail, funcionario);
    }

    public void editarSenha(Funcionario funcionario, String novaSenha) {
        funcionario.setSenha(novaSenha);
    }

    public Funcionario obterPorNome(String nome) {
        for (Funcionario f : funcionariosAtivos) {
            if (f.getNome().equalsIgnoreCase(nome)) {
                return f;
            }
        }
        return null;
    }

    public Funcionario obterPorId(int id) {
        for (Funcionario f : funcionariosAtivos) {
            if (f.getId() == id) {
                return f;
            }
        }
        return null;
    }

    public Funcionario obterPorEmail(String email) {
        return funcionariosPorEmail.get(email);
    }

    public List<Funcionario> obterTodosFuncionarios() {
        return new ArrayList<>(funcionariosAtivos);
    }

    public void modificarStatusGerente(Funcionario funcionario, boolean novoStatus) {
        funcionario.setEhGerente(novoStatus);
    }
}