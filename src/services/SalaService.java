package services;

import entities.Sala;
import entities.Sessao;
import exceptions.CampoInvalido;
import repositories.RepositorioSalas;

public class SalaService {
    private RepositorioSalas repositorio;

    public SalaService(RepositorioSalas repositorio) {
        this.repositorio = repositorio;
    }

    public void cadastrarSala(Sala sala) {
        if (sala == null) {
            throw new CampoInvalido("Sala não pode ser nula.");
        }
        if (repositorio.contem(sala)) {
            throw new CampoInvalido("Sala já cadastrada.");
        }
        repositorio.adicionar(sala);
    }

    public void associarSessaoASala(Sala sala, Sessao sessao) {
        if (sala == null || sessao == null) {
            throw new CampoInvalido("Sala ou sessão não podem ser nulas.");
        }

        if (!repositorio.contem(sala)) {
            throw new CampoInvalido("Sala não encontrada.");
        }

        if (repositorio.sessaoJaAssociada(sessao)) {
            throw new CampoInvalido("Sessão já está associada a uma sala.");
        }

        // Verificação de conflito de horário com outras sessões da mesma sala
        for (Sessao existente : sala.getSessoes()) {
            if (conflita(sessao, existente)) {
                throw new CampoInvalido("Já existe uma sessão em andamento nesse horário na sala.");
            }
        }

        // Se não houver conflito, associar normalmente
        sala.adicionarSessao(sessao);
        repositorio.associarSessao(sala, sessao);
    }

    public Sala buscarSalaPorSessao(Sessao sessao) {
        if (sessao == null) {
            throw new CampoInvalido("Sessão não pode ser nula.");
        }
        return repositorio.buscarPorSessao(sessao);
    }

    public Sala buscarSalaPorNumero(int numero) {
        Sala sala = repositorio.buscarPorNumero(numero);
        if (sala == null) {
            throw new CampoInvalido("Sala não encontrada.");
        }
        return sala;
    }

    private boolean conflita(Sessao nova, Sessao existente) {
        return !(nova.getHorarioFim().isBefore(existente.getHorarioInicio()) ||
                 nova.getHorarioInicio().isAfter(existente.getHorarioFim()));
    }
}
