package repositories;
import java.util.HashMap;
import entities.Assento;
import entities.Cliente;
import exceptions.*;

public class RepositorioAssentos {
    private HashMap<Cliente, Assento> assentosOcupados;

    public RepositorioAssentos() {
        this.assentosOcupados = new HashMap<>();
    }

    public void ocuparAssento(Cliente cliente, Assento assento){
        if (cliente == null || assento == null) {
            throw new CampoInvalido("Cliente ou Assento não podem ser nulos.");
        }
        if (assentosOcupados.containsKey(cliente)) {
            throw new CampoInvalido("Assento já reservado para este cliente.");
        }
        assentosOcupados.put(cliente, assento);
    }

    public boolean isAssentoOcupado(Assento assento) {
        if (assento == null) {
            throw new CampoInvalido("Assento não pode ser nulo.");
        }
        return assentosOcupados.containsValue(assento);
    }

    public void desocuparAssento(Cliente cliente) {
        if (cliente == null) {
            throw new CampoInvalido("Cliente não pode ser nulo.");
        }
        if (!assentosOcupados.containsKey(cliente)) {
            throw new CampoInvalido("Assento não encontrado para este cliente.");
        }
    }

    public void limparAssentos() {
        assentosOcupados.clear();
    }

    public HashMap<Cliente, Assento> getAssentosOcupados() {
        return assentosOcupados;
    }
}
