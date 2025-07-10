package repositories;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import entities.Cliente;
import interfaces.RepositorioInterface;
import exceptions.*;

public class RepositorioClientes implements RepositorioInterface<Cliente> {

    private List<Cliente> clientes;
    private HashMap<String, Cliente> clientesPorEmail;

    public RepositorioClientes() {
        this.clientes = new ArrayList<>();
        this.clientesPorEmail = new HashMap<>();
    }
    @Override
    public void cadastrar(Cliente cliente) {
        if (cliente == null) {
            throw new CampoInvalido("Cliente não pode ser nulo.");
        }
        if (clientes.contains(cliente)) {
            throw new CampoInvalido("Cliente já cadastrado.");
        }
        if (clientesPorEmail.containsKey(cliente.getEmail())){
            throw new CampoInvalido("Já existe um cliente cadastrado com este email.");
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

    @Override
    public void remover(Cliente cliente) {
        if (cliente == null) {
            throw new CampoInvalido("Cliente não pode ser nulo.");
        }
        if (!clientes.contains(cliente)) {
            throw new CampoInvalido("Cliente não encontrado.");
        }
        clientes.remove(cliente);
    }
    
    public void editarNome(Cliente cliente, String novoNome) {
        if (cliente == null || novoNome == null || novoNome.isEmpty()) {
            throw new CampoInvalido("Cliente ou nome não podem ser nulos ou vazios.");
        }
        if (!clientes.contains(cliente)) {
            throw new CampoInvalido("Cliente não encontrado.");
        }
        cliente.setNome(novoNome);
    }

    @Override
    public void editarEmail(Cliente cliente, String novoEmail) {
        if (cliente == null || novoEmail == null || novoEmail.isEmpty()) {
            throw new CampoInvalido("Cliente ou email não podem ser nulos ou vazios.");
        }
        if (!clientes.contains(cliente)) {
            throw new CampoInvalido("Cliente não encontrado.");
        }
        cliente.setEmail(novoEmail);
    }

    @Override
    public void editarSenha(Cliente cliente, String novaSenha) {
        if (cliente == null || novaSenha == null || novaSenha.isEmpty()) {
            throw new CampoInvalido("Cliente ou senha não podem ser nulos ou vazios.");
        }
        if (!clientes.contains(cliente)) {
            throw new CampoInvalido("Cliente não encontrado.");
        }
        cliente.setSenha(novaSenha);
    }


    public Cliente autenticarCliente(String email, String senha) {
        if (email == null || senha == null || email.isEmpty() || senha.isEmpty()) {
            throw new CampoInvalido("Email e senha não podem ser nulos ou vazios.");
        }
        Cliente cliente = clientesPorEmail.get(email);
        if (cliente != null && cliente.getSenha().equals(senha)) {
            return cliente;
        }
            return null;
        }


    @Override
    public Cliente obterPorNome(String nome) {
        for (Cliente cliente : clientes) {
            if (cliente.getNome().equals(nome)) {
                return cliente;
            }
        }
        return null;
    }
    
    @Override
    public Cliente obterPorEmail(String email){
        for (Cliente cliente : clientes) {
            if (cliente.getEmail().equals(email)) {
                return cliente;
            }

        }
        return null;
    }

    @Override
    public Cliente obterPorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }
}
