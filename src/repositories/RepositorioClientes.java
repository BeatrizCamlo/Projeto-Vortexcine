package repositories;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import entities.Cliente;
import interfaces.RepositorioInterface;

public class RepositorioClientes implements RepositorioInterface<Cliente> {

    private List<Cliente> clientes;
    private HashMap<String, Cliente> clientesPorEmail;

    public RepositorioClientes() {
        this.clientes = new ArrayList<>();
        this.clientesPorEmail = new HashMap<>();
    }

    public void cadastrar(Cliente cliente) {
        clientes.add(cliente);
        clientesPorEmail.put(cliente.getEmail(), cliente);
    }

    public void remover(Cliente cliente) {
        clientes.remove(cliente);
        clientesPorEmail.remove(cliente.getEmail());
    }

    public void editarNome(Cliente cliente, String novoNome) {
        cliente.setNome(novoNome);
    }

    public void editarEmail(Cliente cliente, String novoEmail) {
        clientesPorEmail.remove(cliente.getEmail());
        cliente.setEmail(novoEmail);
        clientesPorEmail.put(novoEmail, cliente);
    }

    public void editarSenha(Cliente cliente, String novaSenha) {
        cliente.setSenha(novaSenha);
    }

    public Cliente obterPorNome(String nome) {
        for (Cliente cliente : clientes) {
            if (cliente.getNome().equals(nome)) {
                return cliente;
            }
        }
        return null;
    }

    public Cliente obterPorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

    public Cliente obterPorEmail(String email) {
        return clientesPorEmail.get(email);
    }

    public List<Cliente> obterTodos() {
        return new ArrayList<>(clientes);
    }
}
