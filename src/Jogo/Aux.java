package Jogo;

import java.util.ArrayList;

import Entidades.Inimigo;

public class Aux {
    public static void limparTela() {
        System.out.print("\033[H\033[2J\033[3J");
        System.out.flush();
    }
    public static Inimigo escolherAlvo (ArrayList<Inimigo> inimigos, java.util.Scanner inputs ){
        while (true) {
            if(inimigos.size() > 1){
                Prints.PrintsMain.printEscolhaAlvo(inimigos);
                int inimigoEscolhido = inputs.nextInt();

                if (inimigoEscolhido != 1 && inimigoEscolhido != 2){
                    System.out.println("⚠️ Opção inválida!");
                    continue;
                }

                return inimigos.get(inimigoEscolhido - 1);
            }
            return inimigos.get(0);
        }
        
    }

    public static boolean inimigosVivos (ArrayList<Inimigo> inimigos){
        for (Inimigo inimigo : inimigos){
            if (inimigo.estaVivo()){
                return true;
            }
        }
        return false;
    }
}
