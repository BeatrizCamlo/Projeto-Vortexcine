package entities;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Sessao {
    private Filme filme;
    private Sala sala;
    private LocalDateTime  dataHora;
    private List<Cliente> publico;
    private List<Assento> assentosOcupados;

    public Sessao(Filme filme, Sala sala, LocalDateTime dataHora) {
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

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public LocalDateTime getDataHora() {
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

    public void setFilme(Filme novoFilme) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setFilme'");
    }

    public void setData(String novaData) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setData'");
    }
}
