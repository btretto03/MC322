import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import Cartas.*; // '.*'importa todas as classes do pacote 
import Entidades.*;
import Prints.*;
import Efeitos.*;
import Jogo.Manager;

public class App {
    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        
    }
    public static Scanner inputs = new Scanner(System.in);
    public static void main(String[] args)  {
        Manager juiz = new Manager();
        Prints.PrintsMain.printInicial();
    
        String escolhaheroi = Heroi.escolherHeroi(inputs);
        String escolhainimigo = Inimigo.escolherInimigo(inputs);
        Heroi heroi = new Heroi(escolhaheroi, 30, 0); //definindo as classes
        Inimigo inimigo = new Inimigo(escolhainimigo, 30, 0);

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
        Baralho.add(new CartaEfeito("Corte de Cotovelo", 2, "Sangramento", juiz));
        Baralho.add(new CartaEfeito("Provocação", 1, "Provocacao", juiz));
        Baralho.add(new CartaEfeito("Adrenalina Pura", 3, "Adrenalina", juiz));

        Prints.PrintsMain.printInicioluta();
        ArrayList <Carta> pilhaCompra = new ArrayList<>(Baralho);
        ArrayList <Carta> pilhaDescarte = new ArrayList<>();

        while(true) { //Loop da luta
            heroi.setEnergia(6); //energia do heroi é resetada a cada Round
            heroi.setEscudo(0); //resetando escudo a 0 em cada round
            inimigo.setEscudo(0); //resetando o escudo do inimigo
            
            ArrayList <Carta> mao = new ArrayList<>();
            for (int i = 0;i < 4; i ++) {
                if (pilhaCompra.size() == 0) {
                    Prints.PrintsMain.printBaralhoVazio();
                    while (pilhaDescarte.size() > 0) {
                        Carta cartaAux = pilhaDescarte.remove(0);
                        pilhaCompra.add(cartaAux);
                    }
                    Collections.shuffle(pilhaCompra); //Adicionando o embaralhamento
                }
                if (pilhaCompra.size() > 0) {
                    int cartaaleatoria = (int) (Math.random() * pilhaCompra.size()); 
                    mao.add(pilhaCompra.remove(cartaaleatoria));
                }
            }

            if (heroi.estaVivo() == true && inimigo.estaVivo() == true) { //Os dois vivos
                ArrayList<String> acoesDoRoundHeroi = new ArrayList<>();
                int vidaInimigoInicio = inimigo.getVida();

                limparTela();
                Prints.PrintsMain.printNovoRound();
                Prints.PrintsMain.printStatus(escolhaheroi, heroi.getVida(), escolhainimigo, inimigo.getVida());
                PrintsMain.printEfeitosLutadores(heroi.getNome(), heroi.getListaEfeitos(), inimigo.getNome(), inimigo.getListaEfeitos());

                inimigo.anuncio(heroi);                

                while (heroi.getEnergia() > 0 && mao.size() > 0) {
                    PrintsMain.printEnergiaEMenu(heroi.getEnergia(), mao);

                    if (!heroi.verificaMao(mao)){
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
                System.out.println("\n---------------------------------------");
                inimigo.atacar(heroi);
                System.out.println("---------------------------------------\n");

                if (heroi.getListaEfeitos().size() > 0 || inimigo.getListaEfeitos().size() > 0) {
                    System.out.println("🧪 Os efeitos estão agindo...");
                    juiz.notificarSubscribers();

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
                    Prints.PrintsMain.digiteParaContinuar(inputs, 0);
                } 
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