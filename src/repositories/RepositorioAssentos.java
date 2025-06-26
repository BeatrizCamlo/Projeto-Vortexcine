package repositories;

import java.util.HashMap;

import entities.Assento;
import entities.Cliente;

public class RepositorioAssentos {
    private HashMap<Cliente, Assento> assentosOcupados;

    public RepositorioAssentos() {
        this.assentosOcupados = new HashMap<>();
    }

    public void ocuparAssento(Cliente cliente, Assento assento){
        if (cliente == null || assento == null) {
            throw new IllegalArgumentException("Cliente ou Assento não podem ser nulos.");
        }
        if (assentosOcupados.containsKey(cliente)) {
            throw new IllegalArgumentException("Assento já reservado para este cliente.");
        }
        assentosOcupados.put(cliente, assento);
    }

    public boolean isAssentoOcupado(Assento assento) {
        if (assento == null) {
            throw new IllegalArgumentException("Assento não pode ser nulo.");
        }
        return assentosOcupados.containsValue(assento);
    }

    public void desocuparAssento(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        if (!assentosOcupados.containsKey(cliente)) {
            throw new IllegalArgumentException("Assento não encontrado para este cliente.");
        }
    }

    public void limparAssentos() {
        assentosOcupados.clear();
    }

    public HashMap<Cliente, Assento> getAssentosOcupados() {
        return assentosOcupados;
    }
}
