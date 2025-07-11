package repositories;
import java.util.HashMap;
import entities.Assento;
import entities.Cliente;

public class RepositorioAssentos {
    private HashMap<Cliente, Assento> assentosOcupados = new HashMap<>();

    public void adicionar(Cliente cliente, Assento assento) {
        assentosOcupados.put(cliente, assento);
    }

    public boolean existeReserva(Cliente cliente) {
        return assentosOcupados.containsKey(cliente);
    }

    public boolean assentoJaReservado(Assento assento) {
        return assentosOcupados.containsValue(assento);
    }

    public Assento remover(Cliente cliente) {
        return assentosOcupados.remove(cliente);
    }

    public Assento obterAssentoPorCliente(Cliente cliente) {
        return assentosOcupados.get(cliente);
    }

    public HashMap<Cliente, Assento> listarReservas() {
        return new HashMap<>(assentosOcupados);
    }

    public void limparTudo() {
        assentosOcupados.clear();
    }
}
