package Evento;

import java.util.ArrayList;
import java.util.Scanner;

import Cartas.Carta;
import Entidades.Heroi;
import Jogo.Aux;
import Prints.PrintsMain;

public class Loja extends Evento {
    private Heroi heroi;
    private Scanner inputs;

    public Loja(Heroi heroi, Scanner inputs) {
        this.heroi = heroi;
        this.inputs = inputs;
    }

    @Override
    public void iniciar(Heroi heroi, ArrayList<Carta> pilhaCompra, ArrayList<Carta> pilhaDescarte) {
        while (true) {
            Aux.limparTela();
            PrintsMain.printLoja(this.heroi.getOuro());

            int escolha = inputs.nextInt();

            if (escolha == 0) {
                return;
            }

            if (escolha == 1) { // energetico
                if (this.heroi.getOuro() >= 50) {
                    this.heroi.setOuro(this.heroi.getOuro() - 50);
                    this.heroi.setVida(this.heroi.getVida() + 10);
                    System.out.println("\n✅ Você comprou um Energético! (+10 Vida)");
                } else {
                    System.out.println("\n❌ Ouro insuficiente!");
                }
            } else if (escolha == 2) { // bandagem
                if (this.heroi.getOuro() >= 80) {
                    this.heroi.setOuro(this.heroi.getOuro() - 80);
                    this.heroi.setBonusDanoProximaLuta(3);
                    System.out.println("\n✅ Você comprou uma Bandagem! (+3 dano em todos ataques na próxima luta)");
                } else {
                    System.out.println("\n❌ Ouro insuficiente!");
                }
            } else if (escolha == 3) { // protetor
                if (this.heroi.getOuro() >= 60) {
                    this.heroi.setOuro(this.heroi.getOuro() - 60);
                    this.heroi.setEscudoInicioProximaLuta(15);
                    System.out.println("\n✅ Você comprou um Protetor! (+15 escudo no 1º round da próxima luta)");
                } else {
                    System.out.println("\n❌ Ouro insuficiente!");
                }
            } else if (escolha == 4) { // kit + vida
                if (this.heroi.getOuro() >= 120) {
                    this.heroi.setOuro(this.heroi.getOuro() - 120);
                    this.heroi.setVida(this.heroi.getVida() + 20);
                    System.out.println("\n✅ Você comprou um Kit Premium! (+20 Vida)");
                } else {
                    System.out.println("\n❌ Ouro insuficiente!");
                }
            }

            PrintsMain.digiteParaContinuar(inputs, 0);
        }
    }
}
