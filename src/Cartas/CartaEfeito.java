package Cartas;

import Entidades.Entidade;
import Efeitos.*;
import Jogo.Manager;

public class CartaEfeito extends Carta {
    private String tipoEfeito;
    private Manager gerenciador;

    public CartaEfeito(String nome, int custo, String tipoEfeito, Manager gc) {
        super(nome, custo, "Aplicando efeito");
        this.tipoEfeito = tipoEfeito;
        this.gerenciador = gc;
    }

    @Override
   public int usar(Entidade alvo) {
        Efeitos novoEfeito;
        if (this.tipoEfeito.equals("Sangramento")) {
            novoEfeito = new Sangramento("Sangramento", 2, alvo);
        } 
        else if (this.tipoEfeito.equals("Provocacao")) {
            novoEfeito = new Provocacao("Provocacao", 2, alvo);
        } 
        else if (this.tipoEfeito.equals("Adrenalina")) {
            novoEfeito = new Adrenalina("Adrenalina", 3, alvo);
        } else {
            novoEfeito = null; //sem esse else o java nao deixava compilar, mas na pratica isso n acontece pq os nomes estão pré estabelecidos
        }
        alvo.adicionarEfeito(novoEfeito);
        gerenciador.inscrever(novoEfeito);
        
        return novoEfeito.getAcumulos();
    }

    @Override
    public void printRodada(int indice) {
        System.out.println(String.format("[%d] %s (Custo: %d | Efeito: %s)", 
            indice, this.getNome(), this.getCusto(), this.tipoEfeito));
    }
}