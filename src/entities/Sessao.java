package entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Sessao {
    private Filme filme;
    private Sala sala;
    private LocalDateTime horarioInicio;
    private List<Cliente> publico;
    private List<Assento> assentosOcupados;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Sessao(Filme filme, Sala sala, LocalDateTime dataHora) {
        this.filme = filme;
        this.sala = sala;
        this.horarioInicio = (dataHora);
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

    public LocalDateTime getHorarioInicio() {
        return horarioInicio;
    }

    public LocalDateTime getHorarioFim() {
        return horarioInicio.plusMinutes(filme.getDuracaoEmMinutos());
    }

    public boolean assentoDisponivel(Coordenada coordenada) {
        for (Assento assento : assentosOcupados) {
            if (assento.getCoordenada().equals(coordenada)) {
                return false;
            }
        }
        return true;
    }

    public boolean ocuparAssento(Assento assento, Cliente cliente) {
        if (!assentoDisponivel(assento.getCoordenada())) {
            return false;
        }
        assento.setOcupado();
        assentosOcupados.add(assento);
        if (!publico.contains(cliente)) {
            publico.add(cliente);
        }
        return true;
    }

    public void exibirInformacoesSessao() {
        System.out.println("=== Sessão ===");
        System.out.println("Filme: " + filme.getNome());
        System.out.println("Data/Hora de início: " + horarioInicio.format(FORMATTER));
        System.out.println("Horário de término: " + getHorarioFim().format(FORMATTER));
        System.out.println("Sala: " + sala.getNumeroSala());
        System.out.println("Assentos ocupados: " + assentosOcupados.size());
        System.out.println("Total de espectadores: " + publico.size());
    }

    public void setFilme(Filme novoFilme) {
        this.filme = novoFilme;
    }

    public void setHorarioInicio(String novaDataHora) {
        this.horarioInicio = LocalDateTime.parse(novaDataHora, FORMATTER);
    }
    
    public String getDataHora() {
        return horarioInicio.format(FORMATTER);
    }

    public void setData(String novaData) {
        this.horarioInicio = LocalDateTime.parse(novaData + " " + horarioInicio.toLocalTime().format(FORMATTER), FORMATTER);
    }
}
