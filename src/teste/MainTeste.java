package teste;

import java.time.LocalDateTime;

import entities.*;

public class MainTeste {
    public static void main(String[] args) {
        Filme filme = new Filme("interestelar", 192, GeneroFilme.ACAO);

        Sala sala = new Sala(1);

        LocalDateTime datahora = LocalDateTime.of(2025, 11, 21, 06, 40);
        Sessao sessao = new Sessao(filme, sala, datahora);
        Cliente cliente = new Cliente("Moises", "moises@gmail.com", "1234");
        Assento assento = sala.buscarAssento("B", 5);
        
        if (assento == null) {
            System.out.println("Assento não encontrado.");
            return;
        }
        if (!assento.isOcupado()) {
            assento.ocupar();

            TipoIngresso tipo = TipoIngresso.MEIA_ENTRADA;
            double preco = 15.00; 

            Ingresso ingresso = new Ingresso(sessao, assento, cliente, tipo, preco);

            ingresso.exibirInformacoes();

            sala.exibirMapaAssentos();
        } else {
            System.out.println("Assento já está ocupado.");
        }
    }
}
