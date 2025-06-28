package repositories;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import entities.Cliente;
import entities.Funcionario;

public class RepositorioClientes {

    private List<Cliente> clientes;
    private HashMap<String, Cliente> clientesPorEmail;

    public RepositorioClientes() {
        this.clientes = new ArrayList<>();
        this.clientesPorEmail = new HashMap<>();
    }

    public void adicionarCliente(Cliente cliente, Funcionario funcionario) {
        if (funcionario == null || !funcionario.ehGerente()) {
            throw new IllegalArgumentException("Apenas gerentes podem adicionar clientes.");
        }
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        if (clientes.contains(cliente)) {
            throw new IllegalArgumentException("Cliente já cadastrado.");
        }
        if (clientesPorEmail.containsKey(cliente.getEmail())){
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este email.");
        }
        clientes.add(cliente);
        clientesPorEmail.put(cliente.getEmail(), cliente);
    }

    public Cliente obterClientePorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

    public List<Cliente> obterTodosClientes() {
        return new ArrayList<>(clientes);
    }

    public void removerCliente(Cliente cliente, Funcionario funcionario) {
        if (funcionario == null || !funcionario.ehGerente()) {
            throw new IllegalArgumentException("Apenas gerentes podem remover clientes.");
        }
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        if (!clientes.contains(cliente)) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        clientes.remove(cliente);
    }
    
    public void modificarNomeCliente(Cliente cliente, String novoNome, Funcionario funcionario) {
        if (funcionario == null || !funcionario.ehGerente()) {
            throw new IllegalArgumentException("Apenas gerentes podem modificar clientes.");
        }
        if (cliente == null || novoNome == null || novoNome.isEmpty()) {
            throw new IllegalArgumentException("Cliente ou nome não podem ser nulos ou vazios.");
        }
        if (!clientes.contains(cliente)) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        cliente.setNome(novoNome);
    }

    public void modificarEmailCliente(Cliente cliente, String novoEmail, Funcionario funcionario) {
        if (funcionario == null || !funcionario.ehGerente()) {
            throw new IllegalArgumentException("Apenas gerentes podem modificar clientes.");
        }
        if (cliente == null || novoEmail == null || novoEmail.isEmpty()) {
            throw new IllegalArgumentException("Cliente ou email não podem ser nulos ou vazios.");
        }
        if (!clientes.contains(cliente)) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        cliente.setEmail(novoEmail);
    }

    public void modificarSenhaCliente(Cliente cliente, String novaSenha, Funcionario funcionario) {
        if (funcionario == null || !funcionario.ehGerente()) {
            throw new IllegalArgumentException("Apenas gerentes podem modificar clientes.");
        }
        if (cliente == null || novaSenha == null || novaSenha.isEmpty()) {
            throw new IllegalArgumentException("Cliente ou senha não podem ser nulos ou vazios.");
        }
        if (!clientes.contains(cliente)) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        cliente.setSenha(novaSenha);
    }


    public Cliente autenticarCliente(String email, String senha) {
        if (email == null || senha == null || email.isEmpty() || senha.isEmpty()) {
            throw new IllegalArgumentException("Email e senha não podem ser nulos ou vazios.");
        }
        Cliente cliente = clientesPorEmail.get(email);
        if (cliente != null && cliente.getSenha().equals(senha)) {
            return cliente;
        }
            return null;
        }

    public Cliente buscarporEmail(String email){
        for (Cliente cliente : clientes) {
            if (cliente.getEmail().equals(email)) {
                return cliente;
            }

        }
        return null;
    }
}
