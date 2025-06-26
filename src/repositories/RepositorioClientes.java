package repositories;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import entities.Cliente;

public class RepositorioClientes {

    private List<Cliente> clientes;
    private HashMap<String, Cliente> clientesPorEmail;

    public RepositorioClientes() {
        this.clientes = new ArrayList<>();
        this.clientesPorEmail = new HashMap<>();
    }

    public void adicionarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        if (clientes.contains(cliente)) {
            throw new IllegalArgumentException("Cliente já cadastrado.");
        }
        if (clientesPorEmail.containsKey(cliente.getEmail()){
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este email.");
        }
        clientes.add(cliente);
    }

    public Cliente obterClientePorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId().equals(id)) {
                return cliente;
            }
        }
        return null;
    }

    public List<Cliente> obterTodosClientes() {
        return new ArrayList<>(clientes);
    }

    public void removerCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        if (!clientes.contains(cliente)) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        clientes.remove(cliente);
    }
    
    public void modificarNomeCliente(Cliente cliente, String novoNome) {
        if (cliente == null || novoNome == null || novoNome.isEmpty()) {
            throw new IllegalArgumentException("Cliente ou nome não podem ser nulos ou vazios.");
        }
        if (!clientes.contains(cliente)) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        cliente.setNome(novoNome);
    }

    public void modificarEmailCliente(Cliente cliente, String novoEmail) {
        if (cliente == null || novoEmail == null || novoEmail.isEmpty()) {
            throw new IllegalArgumentException("Cliente ou email não podem ser nulos ou vazios.");
        }
        if (!clientes.contains(cliente)) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        cliente.setEmail(novoEmail);
    }

    public void modificarSenhaCliente(Cliente cliente, String novaSenha) {
        if (cliente == null || novaSenha == null || novaSenha.isEmpty()) {
            throw new IllegalArgumentException("Cliente ou senha não podem ser nulos ou vazios.");
        }
        if (!clientes.contains(cliente)) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        cliente.setSenha(novaSenha);
    }
}
