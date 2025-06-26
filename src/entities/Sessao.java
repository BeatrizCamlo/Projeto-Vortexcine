package entities;

import java.util.ArrayList;
import java.util.List;

public class Sessao {
    private Filme filme;
    private Sala sala;
    private String dataHora;
    private List<Cliente> publico;
    private List<Assento> assentosOcupados;

    public Sessao(Filme filme, Sala sala, String dataHora) {
        this.filme = filme;
        this.sala = sala;
        this.dataHora = dataHora;
        this.publico = new ArrayList<>();
        this.assentosOcupados = new ArrayList<>();
    }

    public Filme getFilme() {
        return filme;
    }

    public Sala getSala() {
        return sala;
    }

    public String getDataHora() {
        return dataHora;
    }

    public boolean assentoDisponivel(Coordenada coordenada) {
        for (int i = 0; i <= assentosOcupados.size(); i++) {
            if (assentosOcupados.get(i).getCoordenada().equals(coordenada)) {
                return false;
            }
        }
        return true;
    }

    public boolean ocuparAssento(Assento assento, Cliente cliente) {
        if (!assentoDisponivel(assento.getCoordenada())) {
            return false;
        }
        assento.ocupar();
        assentosOcupados.add(assento);
        if (!publico.contains(cliente)) {
            publico.add(cliente);
        }
        return true;
    }

    
    public void exibirInformacoesSessao() {
        System.out.println("=== Sessão ===");
        System.out.println("Filme: " + filme.getNome());
        System.out.println("Data/Hora: " + dataHora);
        System.out.println("Sala: " + sala.getNumeroSala());
        System.out.println("Assentos ocupados: " + assentosOcupados.size());
        System.out.println("Total de espectadores: " + publico.size());
    }
}
