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
                Coordenada coordenada = new Coordenada(linha, coluna);
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

    public void adicionarSessao(Sessao sessao) {
        sessoes.add(sessao);
    }

    public void exibirAssentos() {
        for (int i = 0; i < assentos.size(); i++) {
            assentos.get(i).exibirInformacoesAssento();
        }
    }

    public void exibirInformacoes() {
        System.out.println("=== Sala " + numeroSala + " ===");
        System.out.println("Assentos totais: " + assentos.size());
        System.out.println("Sessões nesta sala: " + sessoes.size());
    }

    public void exibirMapaAssentos() {
        System.out.println("Sala " + numeroSala + " - Mapa de Assentos\n");

        int ocupados = 0;
        int colunas = 10;
        String[] fileiras = {"A", "B", "C", "D", "E"};

        System.out.print("    ");
        for (int col = 1; col <= colunas; col++) {
            System.out.printf("%-4d", col);
        }
        System.out.println();

        for (String linha : fileiras) {
            System.out.print(linha + ": ");
            for (int col = 1; col <= colunas; col++) {
                Assento assento = buscarAssento(linha, col);
                boolean ocupado = assento != null && assento.isOcupado();
                System.out.print(ocupado ? "[X] " : "[ ] ");
                if (ocupado) {
                    ocupados++;
                }
            }
            System.out.println();
        }

        int total = assentos.size();
        System.out.println("\n [ ] = livre | [X] = ocupado");
        System.out.printf("Capacidade total: %d | Ocupados: %d | Livres: %d\n\n", total, ocupados, total - ocupados);
    }

    public Assento buscarAssento(String linha, int coluna) {
        for (Assento a : assentos) {
            if (a.getCoordenada().getLinha().equalsIgnoreCase(linha) && a.getCoordenada().getColuna() == coluna) {
                return a;
            }
        }
        return null;
    }
}
