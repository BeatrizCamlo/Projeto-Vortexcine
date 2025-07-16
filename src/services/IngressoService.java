package services;

import entities.Assento;
import entities.Cliente;
import entities.Ingresso;
import entities.Sessao;
import entities.TipoIngresso;
import exceptions.CampoInvalido;
import repositories.RepositorioIngressos;

import java.util.List;

public class IngressoService {
    private final RepositorioIngressos repositorio;

    public IngressoService(RepositorioIngressos repositorio) {
        this.repositorio = repositorio;
    }

    public Ingresso comprarIngresso(Cliente cliente, Sessao sessao, Assento assento, TipoIngresso tipo, double preco) {
    if (cliente == null || sessao == null || assento == null || tipo == null) {
        throw new CampoInvalido("Parâmetros inválidos para compra de ingresso.");
    }
    if (assento.isOcupado()) {
        throw new CampoInvalido("Assento já está ocupado.");
    }
    if (preco < 0) {
        throw new CampoInvalido("Preço inválido.");
    }

    assento.setOcupado();

    Ingresso ingresso = new Ingresso(sessao, assento, cliente, tipo, preco);
    repositorio.adicionar(ingresso);

    return ingresso;
}


    public List<Ingresso> listarIngressosPorCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        return repositorio.buscarPorCliente(cliente);
    }
}
