package repositories;

import entities.Cliente;
import entities.Ingresso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioIngressos {
    private final List<Ingresso> ingressos;

    public RepositorioIngressos() {
        this.ingressos = new ArrayList<>();
    }

    public void adicionar(Ingresso ingresso) {
        if (ingresso == null) {
            throw new IllegalArgumentException("Ingresso não pode ser nulo.");
        }
        ingressos.add(ingresso);
    }

    public List<Ingresso> buscarPorCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        return ingressos.stream()
                .filter(i -> i.getCliente().equals(cliente))
                .collect(Collectors.toList());
    }

    public List<Ingresso> listarTodos() {
        return new ArrayList<>(ingressos);
    }
}
