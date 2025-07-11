package services;

import java.util.List;

import entities.Cliente;
import exceptions.CampoInvalido;
import repositories.RepositorioClientes;

public class ClienteService {
    private RepositorioClientes repositorio;

    public ClienteService(RepositorioClientes repositorio) {
        this.repositorio = repositorio;
    }

    public void cadastrarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new CampoInvalido("Cliente não pode ser nulo.");
        }
        if (repositorio.obterPorEmail(cliente.getEmail()) != null) {
            throw new CampoInvalido("Já existe um cliente cadastrado com este email.");
        }
        repositorio.cadastrar(cliente);
    }

    public void removerCliente(Cliente cliente) {
        if (cliente == null) {
            throw new CampoInvalido("Cliente não pode ser nulo.");
        }
        if (repositorio.obterPorId(cliente.getId()) == null) {
            throw new CampoInvalido("Cliente não encontrado.");
        }
        repositorio.remover(cliente);
    }

    public void editarNome(Cliente cliente, String novoNome) {
        if (cliente == null || novoNome == null || novoNome.isEmpty()) {
            throw new CampoInvalido("Cliente ou nome inválido.");
        }
        Cliente cExistente = repositorio.obterPorId(cliente.getId());
        if (cExistente == null) {
            throw new CampoInvalido("Cliente não encontrado.");
        }
        cExistente.setNome(novoNome);
    }

    public void editarEmail(Cliente cliente, String novoEmail) {
        if (cliente == null || novoEmail == null || novoEmail.isEmpty()) {
            throw new CampoInvalido("Cliente ou email inválido.");
        }
        if (repositorio.obterPorEmail(novoEmail) != null) {
            throw new CampoInvalido("Email já cadastrado para outro cliente.");
        }
        Cliente cExistente = repositorio.obterPorId(cliente.getId());
        if (cExistente == null) {
            throw new CampoInvalido("Cliente não encontrado.");
        }
        repositorio.editarEmail(cExistente, novoEmail);
    }

    public void editarSenha(Cliente cliente, String novaSenha) {
        if (cliente == null || novaSenha == null || novaSenha.isEmpty()) {
            throw new CampoInvalido("Cliente ou senha inválido.");
        }
        Cliente cExistente = repositorio.obterPorId(cliente.getId());
        if (cExistente == null) {
            throw new CampoInvalido("Cliente não encontrado.");
        }
        cExistente.setSenha(novaSenha);
    }

    public Cliente autenticar(String email, String senha) {
        if (email == null || senha == null || email.isEmpty() || senha.isEmpty()) {
            throw new CampoInvalido("Email e senha não podem ser nulos ou vazios.");
        }
        Cliente cliente = repositorio.obterPorEmail(email);
        if (cliente != null && cliente.getSenha().equals(senha)) {
            return cliente;
        }
        return null;
    }

    public Cliente buscarPorEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new CampoInvalido("Email não pode ser vazio.");
        }

        Cliente cliente = repositorio.obterPorEmail(email);
        if (cliente == null) {
            throw new CampoInvalido("Cliente não encontrado.");
        }

        return cliente;
    }

    public List<Cliente> obterTodosClientes() {
        List<Cliente> clientes = repositorio.obterTodos();
        if (clientes == null || clientes.isEmpty()) {
            throw new CampoInvalido("Nenhum cliente cadastrado.");
        }
        return clientes;
    }
}
