package repositories;

import java.util.HashMap;
import java.util.List;

import entities.Funcionario;
import entities.Sala;
import entities.Sessao;

public class RepositorioSalas {
    private List<Sala> salasTotais;
    private HashMap<Sessao, Sala> salasPorSessao;

    public RepositorioSalas(List<Sala> salasTotais, HashMap<Sessao, Sala> salasPorSessao) {
        this.salasTotais = salasTotais;
        this.salasPorSessao = salasPorSessao;
    }

    public void ocuparSalaComSessao(Sala sala, Sessao sessao) {
        if (sala == null || sessao == null) {
            throw new IllegalArgumentException("Sala ou sessão não podem ser nulos.");
        }
        if (!salasTotais.contains(sala)) {
            throw new IllegalArgumentException("Sala não encontrada.");
        }
        if (!salasPorSessao.containsKey(sessao.getHorarios())) {
            salasPorSessao.put(sessao.getHorarios(), List.of(sala));
        } else {
           throw new IllegalArgumentException("Sessão já está ocupada em outra sala.");
        }
    }

    public Sala buscarSalaPorSessao(Sessao sessao) {
        if (sessao == null) {
            throw new IllegalArgumentException("Sessão não pode ser nula.");
        }
        return salasPorSessao.get(sessao.getFilme.getNome());
    }
    
}
