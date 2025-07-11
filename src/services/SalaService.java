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
}
