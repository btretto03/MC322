package Cartas;

import Entidades.Entidade;

public class CartaCruzado extends CartaDano{
    static private String descricao = "Ataque pesado de grande impacto.";
    static private int dano =  7;


    public CartaCruzado(){
        super(tipo(), 3, descricao);
    }

    private static String tipo (){
        String[] tipo = {"Cruzado de esquerda", "Cruzado de direita"};
        int indice = (int) Math.random() * 2;

        return tipo[indice];
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
