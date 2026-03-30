import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import Cartas.*; // '.*'importa todas as classes do pacote 
import Entidades.*;
import Prints.*;
import Efeitos.*;
import Jogo.Publisher;

public class App {
    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        
    }
    public static Scanner inputs = new Scanner(System.in);
    public static void main(String[] args)  {
        Publisher juiz = new Publisher();
        Prints.PrintsMain.printInicial();
    
        String escolhaheroi = Heroi.escolherHeroi(inputs);
        String escolhainimigo = Inimigo.escolherInimigo(inputs);
        Heroi heroi = new Heroi(escolhaheroi, 50, 0); //definindo as classes
        Inimigo inimigo = new Inimigo(escolhainimigo, 50, 0);

        ArrayList<Carta> Baralho = new ArrayList<>();
        String[] nomeCartas = {"Cruzado de direita", "Gancho de direita", "Gancho de esquerda",  "Cruzado de esquerda", "Jab", "Direto", "Chute baixo", "Chute frontal", "Guilhotina", "Voadora", "Esquivo para direita", "Bloqueio", "Esquivo para esquerda", "Esquivo para trás", "Guarda alta", "Guarda baixa", "Correr",};
        
        int custo = 1;
        for (int i = 0; i < nomeCartas.length; i++) {
            if (i < 10) {
                Baralho.add(new CartaDano(nomeCartas[i], custo));
            } else {
                Baralho.add(new CartaEscudo(nomeCartas[i], custo));
            }
            custo ++; 
            if (custo > 4) { //limita o custo das cartas em 4
                custo = 1; 
            }
        }

        
        Prints.PrintsMain.printInicioluta();

        ArrayList <Carta> pilhaCompra = new ArrayList<>(Baralho);
        ArrayList <Carta> pilhaDescarte = new ArrayList<>();

        int furia = 0; //Variável para o usuario usar um efeito
        int contadorRound = 1;
        while(true) { //Loop da luta
            heroi.setEnergia(6); //energia do heroi é resetada a cada Round
            heroi.setEscudo(0); //resetando escudo a 0 em cada round
            inimigo.setEscudo(0); //resetando o escudo do inimigo
            
            ArrayList <Carta> mao = new ArrayList<>();

            for (int i = 0; i < 4; i ++) {
                
                if (pilhaCompra.size() == 0) {
                    Prints.PrintsMain.printBaralhoVazio();
                    while (pilhaDescarte.size() > 0) {
                        Carta cartaAux = pilhaDescarte.remove(0);
                        pilhaCompra.add(cartaAux);
                    }
                    Collections.shuffle(pilhaCompra); //Adicionando o embaralhamento
                }
                
                if (pilhaCompra.size() > 0) {
                    mao.add(pilhaCompra.remove(0)); //Compra do topo da pilha já embaralhada
                }
            }

            if (heroi.estaVivo() == true && inimigo.estaVivo() == true) { //Os dois vivos
                ArrayList<String> acoesDoRoundHeroi = new ArrayList<>();
                int vidaInimigoInicio = inimigo.getVida();
                int usouEfeito = 0; //variavel que guarda se o heroi usou efeito

                limparTela();

                Prints.PrintsMain.printNovoRound(contadorRound);

                inimigo.anuncio(heroi);

                Prints.PrintsMain.digiteParaContinuar(inputs, 0);

                while (heroi.getEnergia() > 0 && mao.size() > 0) { //Loop de escolha das cartas para o usuário
                    
                    limparTela();
                    
                    Prints.PrintsMain.printNovoRound(contadorRound);
                    Prints.PrintsMain.printStatus(escolhaheroi, heroi.getVida(), escolhainimigo, inimigo.getVida());

                    if (heroi.getListaEfeitos().size() > 0 || inimigo.getListaEfeitos().size() > 0) {
                        PrintsMain.printEfeitosLutadores(heroi.getNome(), heroi.getListaEfeitos(), inimigo.getNome(), inimigo.getListaEfeitos());
                    }

                    if (acoesDoRoundHeroi.size() > 0) {
                        System.out.println("\n🥊 Suas ações neste round:");
                        for (String acao : acoesDoRoundHeroi) {
                            System.out.println("  " + acao);
                        }
                    }
                    PrintsMain.printEnergiaEMenu(heroi.getEnergia(), mao, furia);
                    
                    if (!heroi.verificaMao(mao)){
                        limparTela();
                        
                        Prints.PrintsMain.printFimEnergia();
                        Prints.PrintsMain.digiteParaContinuar(inputs, 0);
                        
                        while (mao.size() > 0) {
                            pilhaDescarte.add(mao.remove(0));
                        }
                        break;
                    }

                    int num = inputs.nextInt();
                    if (num == -1){
                        break;
                    }

                   if (num == 99 && furia >= 3) {
                        limparTela();
                        
                        Prints.PrintsMain.menuEfeito();
                        
                        int escolha = inputs.nextInt();
                        if (escolha == 0) {
                            limparTela();
                            System.out.println("❌ Efeito especial cancelado.");
                            continue;
                        }

                        limparTela();
                        System.out.println("\n🔥 Efeito especial selecionado! Em qual carta da sua mão você quer usá-lo?");
                        System.out.println("Energia disponível: " + heroi.getEnergia() + "/6\n");
                        
                        for (int j = 0; j < mao.size(); j++) {
                            mao.get(j).printRodada(j);
                        }
                        
                        System.out.print("\nDigite o número da carta ou -1 para cancelar: ");
                        int numCarta = inputs.nextInt();

                        if (numCarta == -1) {
                            limparTela();
                            System.out.println("❌ Especial cancelado.");
                            continue;
                        }

                        if (numCarta < 0 || numCarta >= mao.size() || mao.get(numCarta).getCusto() > heroi.getEnergia()) {
                            limparTela();
                            System.out.println("⚠️ Carta inválida ou energia insuficiente! Especial cancelado.");
                            Prints.PrintsMain.digiteParaContinuar(inputs, 0);
                            continue; 
                        }

                        Carta cartaEscolhida = mao.remove(numCarta);
                        pilhaDescarte.add(cartaEscolhida);

                        if (cartaEscolhida instanceof CartaEscudo) {
                            int escudoAdicionado = cartaEscolhida.usar(heroi);
                            acoesDoRoundHeroi.add("✨ " + cartaEscolhida.getNome() + " (Potencializada): " + escudoAdicionado + " de escudo.");
                        } else {
                            int valor = cartaEscolhida.usar(inimigo);
                            acoesDoRoundHeroi.add("💥 " + cartaEscolhida.getNome() + " (Potencializada): " + valor + " de dano.");
                        }

                        heroi.setEnergia(heroi.getEnergia() - cartaEscolhida.getCusto());

                        Efeitos efeito = null;
                        Entidade alvoEfeito = inimigo;

                        switch (escolha) {
                            case 1:
                                efeito = new Sangramento("Sangramento", 3, inimigo);
                                break;
                            case 2:
                                efeito = new Provocacao("Provocacao", 3, inimigo);
                                break;
                            case 3:
                                efeito = new Adrenalina("Adrenalina", 3, heroi);
                                alvoEfeito = heroi;
                                break;
                        }

                        if (efeito != null) {
                            alvoEfeito.adicionarEfeito(efeito, juiz);
                            juiz.inscrever(efeito);
                            furia -= 3; // Gasta a fúria
                            usouEfeito = 1; // Avisa o inimigo para retaliar
                            acoesDoRoundHeroi.add("⚡ O golpe aplicou " + efeito.getNome() + " (3x) no alvo!");
                        }
                        
                        limparTela();
                        continue;
                    }

                    if (num >= mao.size() || mao.get(num).getCusto() > heroi.getEnergia()) {
                        limparTela();
                        if(num >= mao.size()){
                            System.out.println("⚠️ Opção inválida!");
                        } else {
                            System.out.println("🪫 Infelizmente " + heroi.getNome() + " não tem energia suficiente!");
                        }
                        Prints.PrintsMain.digiteParaContinuar(inputs, -1);
                        continue;
                    }

                    Carta cartaEscolhida = mao.remove(num);
                    pilhaDescarte.add(cartaEscolhida);

                    if (cartaEscolhida instanceof CartaEscudo){
                        int escudoAdicionado = cartaEscolhida.usar(heroi);
                        heroi.setEnergia(heroi.getEnergia() - cartaEscolhida.getCusto());
                        
                        acoesDoRoundHeroi.add("✨ " + cartaEscolhida.getNome() + ": " + cartaEscolhida.getDescricao() + " de " + escudoAdicionado + ".");
                        
                    } else { 
                        Entidade alvoCarta = inimigo;
                        if (cartaEscolhida instanceof CartaEfeito && cartaEscolhida.getNome().equals("Adrenalina")) {
                            alvoCarta = heroi;
                        }
                        int valor = cartaEscolhida.usar(alvoCarta);
                        heroi.setEnergia(heroi.getEnergia() - cartaEscolhida.getCusto());
                        acoesDoRoundHeroi.add("💥 " + cartaEscolhida.getNome() + ": " + cartaEscolhida.getDescricao() + " de " + valor + ".");
                    }
                    if (cartaEscolhida instanceof CartaDano) {
                        if (furia < 3) { //toda vez que o heroi usa uma carta de dano ele ganha um ponto de furia (limite em 3)
                            furia ++;
                        }
                    }
                }

                limparTela();
                PrintsMain.printAcoesDoRound(acoesDoRoundHeroi, vidaInimigoInicio - inimigo.getVida());

                while (mao.size() > 0) { //oq sobrou na mao
                    pilhaDescarte.add(mao.remove(0));
                }

                if (inimigo.estaVivo() == false) { //Inimigo morreu antes de atacar
                    limparTela();
                    Prints.PrintsMain.printHeroiVenceu(heroi);
                    return;
                }

               
                if (usouEfeito == 1) { // // Se o heroi usou efeito o inimigo tambem vai
                    inimigo.usarEfeito(heroi, juiz);
                    usouEfeito = 0;
                    }
                inimigo.atacar(heroi);
    

                if (heroi.getListaEfeitos().size() > 0 || inimigo.getListaEfeitos().size() > 0) {
                    System.out.println("🧪 Os efeitos estão agindo...");
                    juiz.notificarSubscribers();

                    for (int i = heroi.getListaEfeitos().size() - 1; i >= 0;  i--) { //garante que o efeito seja removido da lista do heroi caso acabe a duração
                        Efeitos efeito = heroi.getListaEfeitos().get(i);
                        PrintsMain.printEfeitoAgindo(heroi.getNome(), efeito.getNome(), efeito.getAcumulos());
                    if (efeito.getAcumulos() == 0) {
                        juiz.desinscrever(efeito);
                        heroi.getListaEfeitos().remove(i);
                        }
                    }
                    for (int i = heroi.getListaEfeitos().size() - 1; i >= 0; i--) { //efeito no heroi
                        Efeitos efeito = heroi.getListaEfeitos().get(i);
                        PrintsMain.printEfeitoAgindo(heroi.getNome(), efeito.getNome(), efeito.getAcumulos());
                        
                        if (efeito.getAcumulos() <= 0) {
                            juiz.desinscrever(efeito);
                            heroi.getListaEfeitos().remove(i);
                        }
                    }
                    for (int i = inimigo.getListaEfeitos().size() - 1; i >= 0; i--) { // efeito no inimigo
                        Efeitos efeito = inimigo.getListaEfeitos().get(i);
                        PrintsMain.printEfeitoAgindo(inimigo.getNome(), efeito.getNome(), efeito.getAcumulos());
                        if (efeito.getAcumulos() <= 0) {
                            juiz.desinscrever(efeito);
                            inimigo.getListaEfeitos().remove(i);
                        }
                    }
                } 
                System.out.println();
                Prints.PrintsMain.digiteParaContinuar(inputs, 0);
                contadorRound++;
            }

            if (heroi.estaVivo() == true && inimigo.estaVivo() == false) { //Inimigo morreu
                 Prints.PrintsMain.printHeroiVenceu(heroi);
                break;
            }
            if (heroi.estaVivo() == false && inimigo.estaVivo() == true){ //Heroi morreu
                Prints.PrintsMain.printInimigoVenceu(inimigo);
                break;
            }
            if (heroi.estaVivo() == false && inimigo.estaVivo() == false){ //empate
                Prints.PrintsMain.printEmpate();
                break;
            }
        }
        inputs.close(); 
    }  
}