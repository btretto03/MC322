package Entidades;

import Prints.*;

import java.util.ArrayList;

import Efeitos.*;
import Jogo.Publisher;

public class Inimigo extends Entidade {
    private int dano = 0;

    public Inimigo(String nome, int vida, int escudo) { //construtor
        super(nome, vida, escudo);
    }

    public static String escolherInimigo(java.util.Scanner inputs) {
        String escolhainimigo;

        PrintsEntidades.menuEscolhaInimigo();
        int escolha2 = inputs.nextInt();
        switch (escolha2) {
            case 1:
                escolhainimigo = "Vitor Belfort";
                break;
            case 2:
                escolhainimigo = "Popó";
                break;
            case 3:
                escolhainimigo = "Jon Jones";
                break;
            case 4:
                
                System.out.print("\nDigite o nome do inimigo:\n> ");
                inputs.nextLine();
                escolhainimigo = inputs.nextLine();
                break;
            default:
                System.out.println("\n⚠️ Escolha inválida. O lutador será Popó por padrão.");
                escolhainimigo = "Popó";
        }
        return escolhainimigo;
    }

    public static void escolherInimigoDuplo(java.util.Scanner inputs, int caso, ArrayList<Inimigo> inimigos) {
        switch(caso) {
            case 1: //escolher dois inimigos
                Jogo.Aux.limparTela();
                System.out.println("🥊 MODO 1 VS 2 SELECIONADO 🥊\n");
                System.out.println("➡️ Escolha o PRIMEIRO oponente:");
                String inimigo1 = Inimigo.escolherInimigo(inputs);
                Jogo.Aux.limparTela();
                System.out.println("🥊 MODO 1 VS 2 SELECIONADO 🥊\n");
                System.out.println("➡️ Escolha o SEGUNDO oponente:");
                String inimigo2 = Inimigo.escolherInimigo(inputs);

                while (inimigo1.equals(inimigo2)) {
                    Jogo.Aux.limparTela();
                    System.out.println("\n⚠️ O lutador " + inimigo1 + " já foi escolhido, tente novamente!");
                    System.out.println("➡️ Escolha o SEGUNDO oponente:");
                    inimigo2 = Inimigo.escolherInimigo(inputs);
                }

                inimigos.add(new Inimigo(inimigo1, 25, 0));
                inimigos.add(new Inimigo(inimigo2, 25, 0));
                break; 

            case 2: //alatorio 
                Jogo.Aux.limparTela();

                System.out.println("🎲 MODO SORTE SELECIONADO 🎲\n");
                System.out.println("➡️ Escolha seu oponente principal:");
                
                String inimigoSorte = Inimigo.escolherInimigo(inputs);
                inimigos.add(new Inimigo(inimigoSorte, 50, 0));

                int inimigoSecundario = (int) (Math.random() * 7) + 1;
                if (inimigoSecundario <= 3) { // 2 inimigos
                    java.util.Scanner pseudoInput = new java.util.Scanner(String.valueOf(inimigoSecundario));
                    String nomeSecundario = Inimigo.escolherInimigo(pseudoInput);

                    while (nomeSecundario.equals(inimigoSorte)) {
                        inimigoSecundario = (int) (Math.random() * 3) + 1;
                        pseudoInput = new java.util.Scanner(String.valueOf(inimigoSecundario));
                        nomeSecundario = Inimigo.escolherInimigo(pseudoInput);
                    }

                    inimigos.add(new Inimigo(nomeSecundario, 25, 0));
                    inimigos.get(0).setVida(25);
                    
                    Jogo.Aux.limparTela();
                    System.out.println("🎲 Você deu azar! Um segundo lutador entrou no octógono: " + nomeSecundario + "!");
                    Prints.PrintsMain.digiteParaContinuar(inputs, 0);
                } else {
                    Jogo.Aux.limparTela();
                    System.out.println("🎲 Você deu sorte! Apenas o " + inimigoSorte + " entrou no octógono!");
                    Prints.PrintsMain.digiteParaContinuar(inputs, 0);
                }
                break;
        }
    }
    

    public void anuncio(Heroi alvo) {
        if (this.dano == 0 && this.escudo == 0) {
            int acao;
            for (int i = 0; i < 2; i++){   
                if (alvo.getVida() <= 10 || alvo.getVida() > 20){ //prioriza ataque se o herói tiver pouca vida
                    acao = 0;
                } else if (this.getVida() <= 5) {  //prioriza defesa se o inimigo tiver pouca vida
                    acao = 1;
                } else {
                    acao = (int) (Math.random() * 2);
                }

                if (acao == 0) { //ataque
                    this.dano += (int) (Math.random() * 4) + 4;
                } else {
                    this.escudo += (int) (Math.random() * 4) + 3;
                }
            }
        }
        PrintsEntidades.printPretensoesInimigo(this.getNome(), this.dano, this.escudo);
    }

    public void atacar (Heroi alvo){
        int vidaAnterior = alvo.getVida();
        int escudoAnterior = alvo.getEscudo();

        alvo.receberDano(dano);

        int vidaRemovida = vidaAnterior - alvo.getVida();
        int escudoRemovido = escudoAnterior - alvo.getEscudo();
        
        PrintsMain.printAcoesInimigo(this.getNome(), this.dano, this.escudo, vidaRemovida, escudoRemovido);
        this.dano = 0;
        this.escudo = 0;
    }

    public void usarEfeito(Heroi heroi, Publisher juiz) {
        int tipoEfeito = (int) (Math.random() * 3) + 1;
        PrintsMain.printEfeitoInimigo(this.getNome(), tipoEfeito);
        
        switch (tipoEfeito) {
            case 1:
                Efeitos sangramento = new Sangramento("Corte Profundo", 3, heroi);
                heroi.adicionarEfeito(sangramento, juiz);
                juiz.inscrever(sangramento);
                break;
            case 2:
                Efeitos provocacao = new Provocacao("Humilhação", 2, heroi);
                heroi.adicionarEfeito(provocacao, juiz);
                juiz.inscrever(provocacao);
                break;
            case 3:
                Efeitos adrenalina = new Adrenalina("Doping", 3, this); 
                this.adicionarEfeito(adrenalina, juiz);
                juiz.inscrever(adrenalina);
                break;
        }
    }
}