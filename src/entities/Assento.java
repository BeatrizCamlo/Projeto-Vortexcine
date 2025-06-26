package entities;

public class Assento {
    private Coordenada coordenada;
    private boolean ocupado;

    public Assento(Coordenada coordenada) {
        this.coordenada = coordenada;
        this.ocupado = false;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void ocupar() {
        ocupado = true;
    }

    public void liberar() {
        ocupado = false;
    }

    public void exibirInformacoesAssento() {
        System.out.println("Assento " + coordenada + " - " + (ocupado ? "Ocupado" : "Livre") );
    }
}
