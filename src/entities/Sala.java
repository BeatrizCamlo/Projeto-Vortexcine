package entities;

import java.util.ArrayList;
import java.util.List;

public class Sala {
    private int numeroSala;
    private List<Assento> assentos;
    private List<Sessao> sessoes;

    public Sala(int numeroSala) {
        this.numeroSala = numeroSala;
        this.assentos = new ArrayList<>();
        this.sessoes = new ArrayList<>();
        gerarAssentos();
    }

    private void gerarAssentos() {
    String[] fileiras = {"A", "B", "C", "D", "E"};
    int colunas = 10;

        for (String linha : fileiras) {
            for (int coluna = 1; coluna <= colunas; coluna++) {
                String cadeira = linha + coluna;  
                Coordenada coordenada = new Coordenada(cadeira);
                Assento assento = new Assento(coordenada);
                assentos.add(assento);
            }
        }
    }

    public int getNumeroSala() {
        return numeroSala;
    }

    public List<Assento> getAssentos() {
        return assentos;
    }

    public List<Sessao> getSessoes() {
        return sessoes;
    }

    public Assento buscarAssento(String cadeira) {
        for (Assento assento : assentos) {
            if (assento.getCoordenada().getCadeira().equalsIgnoreCase(cadeira)) {
                return assento;
            }
        }
        return null;
    }


    public void exibirAssentos() {
        for (int i = 0; i < assentos.size(); i++) {
            assentos.get(i).exibirInformacoesAssento();
        }
    }

    public void adicionarSessao(Sessao sessao) {
    sessoes.add(sessao);
    }

    public void exibirInformacoes() {
        System.out.println("=== Sala " + numeroSala + " ===");
        System.out.println("Assentos totais: " + assentos.size());
        System.out.println("Sessões nesta sala: " + sessoes.size());
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Sala other = (Sala) obj;
        return this.numeroSala == other.numeroSala;
    }

    public int hashCode() {
        return Integer.hashCode(numeroSala);
    }

}
