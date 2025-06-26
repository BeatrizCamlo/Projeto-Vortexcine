package repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.Funcionario;

public class RepositorioFuncionarios {
    private List<Funcionario> funcionariosAtivos;
    private HashMap<String, Funcionario> funcionariosPorEmail;

    public RepositorioFuncionarios() {
        this.funcionariosAtivos = new ArrayList<>();
        this.funcionariosPorEmail = new HashMap<>();
    }

    public void adicionarFuncionario(Funcionario funcionario) {
        if (!funcionario.ehGerente()) {
            throw new IllegalArgumentException("Apenas gerentes podem adicionar funcionários.");
        }
        if (funcionariosAtivos.contains(funcionario)) {
            throw new IllegalArgumentException("Funcionário já cadastrado.");
        }
        if (funcionariosPorEmail.containsKey(funcionario.getEmail())) {
            throw new IllegalArgumentException("Já existe um funcionário cadastrado com este email.");
        }
        funcionariosAtivos.add(funcionario);
        funcionariosPorEmail.put(funcionario.getEmail(), funcionario);
    }

    public Funcionario buscarFuncionarioPorId(int id) {
        for (Funcionario funcionario : funcionariosAtivos) {
            if (funcionario.getId() == id) {
                return funcionario;
            }
        }
        return null;
    }

    public void modificarNomeFuncionario(Funcionario funcionario, String novoNome) {
        if(funcionario.ehGerente()){
            throw new IllegalArgumentException("Apenas gerentes podem modificar nomes de funcionários.");
        }
        if (funcionario == null || novoNome == null || novoNome.isEmpty()) {
            throw new IllegalArgumentException("Funcionário ou nome não podem ser nulos ou vazios.");
        }
        if (!funcionariosAtivos.contains(funcionario)) {
            throw new IllegalArgumentException("Funcionário não encontrado.");
        }
        funcionario.setNome(novoNome);
    }

    public void modificarEmailFuncionario(Funcionario funcionario, String email) {
        if(funcionario.ehGerente()){
            throw new IllegalArgumentException("Apenas gerentes podem obter funcionários por email.");
        }
        if(email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio.");
        }
        if(!funcionariosPorEmail.containsKey(email)) {
            throw new IllegalArgumentException("Funcionário não encontrado com este email.");
        }
    }

    public void modificarSenhaFuncionario(Funcionario funcionario, String novaSenha) {
        if(funcionario.ehGerente()){
            throw new IllegalArgumentException("Apenas gerentes podem modificar senhas de funcionários.");
        }
        if (funcionario == null || novaSenha == null || novaSenha.isEmpty()) {
            throw new IllegalArgumentException("Funcionário ou senha não podem ser nulos ou vazios.");
        }
        if (!funcionariosAtivos.contains(funcionario)) {
            throw new IllegalArgumentException("Funcionário não encontrado.");
        }
        funcionario.setSenha(novaSenha);
    }
    
    public List<Funcionario> obterTodosFuncionarios() {
        return new ArrayList<>(funcionariosAtivos);
    }

    public void removerFuncionario(Funcionario funcionario) {
        if(!funcionario.ehGerente()){
            throw new IllegalArgumentException("Apenas gerentes podem remover funcionários.");
        }
        if (!funcionariosAtivos.contains(funcionario)) {
            throw new IllegalArgumentException("Funcionário não encontrado.");
        }
        funcionariosAtivos.remove(funcionario);
        funcionariosPorEmail.remove(funcionario.getEmail());
    }
}
