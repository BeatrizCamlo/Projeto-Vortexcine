package entities;

import java.util.List;
import java.util.ArrayList;

public class Cliente extends Pessoa {
    private List<Ingresso> ingressos;

    public Cliente(String nome, String email, String senha){
        super(nome, email, senha);
        this.ingressos = new ArrayList<>();
    }

    public List<Ingresso> getIngressos() {
        return ingressos;
    }

    public void adicionarIngresso(Ingresso ingresso) {
        if (ingresso != null) {
            ingressos.add(ingresso);
        }
    }

    public void listarIngressos() {
        if (ingressos.isEmpty()) {
            System.out.println("Nenhum ingresso encontrado.");
            return;
        }
        for (int i = 0; i < ingressos.size(); i++) {
            System.out.println("- " + ingressos.get(i));
        }
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        // Se quiser, pode adicionar mais informações aqui, por exemplo:
        System.out.println("Número de ingressos comprados: " + ingressos.size());
    }
}
