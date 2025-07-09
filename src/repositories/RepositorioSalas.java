package repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.Sala;
import entities.Sessao;

public class RepositorioSalas {
    private List<Sala> salasTotais;
    private HashMap<Sessao, Sala> salasPorSessao;


    public RepositorioSalas() {
        salasTotais = new ArrayList<>();
        salasPorSessao = new HashMap<>();
    }

    public void adicionarSala(Sala sala) {
        if (sala == null) {
            throw new IllegalArgumentException("Sala não pode ser nula.");
        }
        salasTotais.add(sala);
    }

    public List<Sala> getTodasAsSalas() {
        return salasTotais;
    }

    public void ocuparSalaComSessao(Sala sala, Sessao sessao) {
        if (sala == null || sessao == null) {
            throw new IllegalArgumentException("Sala ou sessão não podem ser nulos.");
        }
        if (!salasTotais.contains(sala)) {
            throw new IllegalArgumentException("Sala não encontrada.");
        }
        if (!salasPorSessao.containsKey(sessao)) {
            salasPorSessao.put(sessao, sala);
        } else {
           throw new IllegalArgumentException("Sessão já está ocupada em outra sala.");
        }
    }

    public Sala buscarSalaPorSessao(Sessao sessao) {
        if (sessao == null) {
            throw new IllegalArgumentException("Sessão não pode ser nula.");
        }
        return  salasPorSessao.get(sessao);
    }

    public Sala buscarPorNumero(int numero) {
        for (Sala sala : salasTotais) {
            if (sala.getNumeroSala() == numero) {
                return sala;
            }
        }
        return null;
    }
    
}
