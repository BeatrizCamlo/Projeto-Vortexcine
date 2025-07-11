package services;

import entities.Assento;
import entities.Cliente;
import exceptions.CampoInvalido;
import repositories.RepositorioAssentos;

public class AssentoService {
    private RepositorioAssentos repositorio;

    public AssentoService(RepositorioAssentos repositorio) {
        this.repositorio = repositorio;
    }

    public void reservarAssento(Cliente cliente, Assento assento) {
        if (cliente == null || assento == null) {
            throw new CampoInvalido("Cliente ou Assento não podem ser nulos.");
        }
        if (repositorio.existeReserva(cliente)) {
            throw new CampoInvalido("Cliente já reservou um assento.");
        }
        if (repositorio.assentoJaReservado(assento)) {
            throw new CampoInvalido("Assento já está ocupado.");
        }

        assento.setOcupado();
        repositorio.adicionar(cliente, assento);
    }

    public void cancelarReserva(Cliente cliente) {
        if (cliente == null) {
            throw new CampoInvalido("Cliente não pode ser nulo.");
        }
        if (!repositorio.existeReserva(cliente)) {
            throw new CampoInvalido("Cliente não possui reserva.");
        }

        Assento assento = repositorio.remover(cliente);
        assento.liberar();
    }

    public boolean verificarDisponibilidade(Assento assento) {
        if (assento == null) {
            throw new CampoInvalido("Assento não pode ser nulo.");
        }
        return !repositorio.assentoJaReservado(assento);
    }
}
