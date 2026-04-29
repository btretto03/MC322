package Evento.Loja;

import java.util.ArrayList;
import java.util.Scanner;

import Cartas.Carta;
import Entidades.Heroi;
import Evento.Evento;
import Jogo.Aux;
import Prints.PrintsMain;

public class Loja extends Evento { //utilizarei o padrao strateegy
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

            CompraLoja efeito;
            int preco;
            String mensagem;
            if (escolha == 1) { // energetico
                efeito = new CompraEnergetico();
                preco = 50;
                mensagem = "\n✅ Você comprou um Energético! (+10 Vida)";
            } else if (escolha == 2) { // bandagem
                efeito = new CompraBandagem();
                preco = 80;
                mensagem = "\n✅ Você comprou uma Bandagem! (+3 dano em todos ataques na próxima luta)";
            } else if (escolha == 3) { // protetor
                efeito = new CompraProtetor();
                preco = 60;
                mensagem = "\n✅ Você comprou um Protetor! (+15 escudo no 1º round da próxima luta)";
            } else if (escolha == 4) { // kit + vida
                efeito = new CompraKitPremium();
                preco = 90;
                mensagem = "\n✅ Você comprou um Kit Premium! (+20 Vida)";
            } else {
                System.out.println("⚠️ Escolha inválida");
                PrintsMain.digiteParaContinuar(inputs, 0);
                continue;
            }

            if (this.heroi.getOuro() >= preco) {
                this.heroi.setOuro(this.heroi.getOuro() - preco);
                efeito.aplicar(this.heroi);
                System.out.println(mensagem);
            } else {
                System.out.println("\n❌ Ouro insuficiente!");
            }

            PrintsMain.digiteParaContinuar(inputs, 0);
        }
    }
}
