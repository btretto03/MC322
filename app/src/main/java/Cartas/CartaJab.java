package Cartas;

import Entidades.Entidade;

public class CartaJab extends CartaDano {
    static private String descricao = "Ataque leve e rápido.";
    static private int dano =  2;

    public CartaJab(){
        super("Jab", 1, descricao);
    }

    @Override
    public int usar(Entidade alvo){
        return dano;
    }

    @Override
    public void printRodada (int indice){
        System.out.println(String.format("[%d] ⚔️ %s (Custo: %d | Dano: %d)", indice, this.getNome(), this.getCusto(), dano));
    }
}