package Jogo;

import java.util.ArrayList;

import Cartas.Carta;
import Entidades.Heroi;
import Entidades.Inimigo;

public final class Salvamento {
    public static void salvarPartidaEmAndamento(Heroi heroi, ArrayList<Inimigo> inimigos, ArrayList<Carta> pilhaCompra, ArrayList<Carta> pilhaDescarte, int roundAtual) {
        System.out.println("\n💾 Salvando...");
        System.out.println("- Herói: " + heroi.getNome());
        System.out.println("- Round: " + roundAtual);
        System.out.println("- Inimigos: " + inimigos.size());
        System.out.println("- Pilha de compra: " + pilhaCompra.size());
        System.out.println("- Pilha de descarte: " + pilhaDescarte.size());
    }
}