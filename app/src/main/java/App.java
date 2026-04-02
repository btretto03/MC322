import java.util.ArrayList;
import java.util.Scanner;

import Cartas.*; // '.*'importa todas as classes do pacote 
import Entidades.*;
import Prints.*;
import Efeitos.*;
import Jogo.Publisher;

/**
 * Classe principal da aplicacao e ponto de entrada do jogo.
 *
 * <p>Este arquivo coordena o ciclo completo de uma partida: selecao do heroi,
 * configuracao dos inimigos, gerenciamento de baralho/mao e execucao dos rounds
 * ate que uma condicao de fim de jogo seja atendida.</p>
 */
public class App {
    /**
     * Scanner compartilhado para leitura de entradas do usuario no terminal.
     */
    public static Scanner inputs = new Scanner(System.in);

    /**
     * Inicia a aplicacao e executa o loop principal de combate.
     *
     * <p>Fluxo principal:</p>
     * <ol>
     *   <li>Instancia os objetos-base da partida (juiz, heroi e modo de jogo).</li>
     *   <li>Prepara baralho, pilha de compra e pilha de descarte.</li>
     *   <li>Executa rounds com escolhas do jogador, ataques inimigos e efeitos ativos.</li>
     *   <li>Encerra quando heroi ou inimigos atingem condicao final.</li>
     * </ol>
     *
     * @param args argumentos de linha de comando (nao utilizados)
     */
    public static void main(String[] args)  {
        Prints.PrintsMain.printInicial2();
//----------------------------------INSTANCIAMENTO------------------------------------------
        Publisher juiz = new Publisher();
        String escolhaheroi = Heroi.escolherHeroi(inputs);
        Heroi heroi = new Heroi(escolhaheroi, 50, 0);
        Jogo.Aux.limparTela();

//----------------------------------ESCOLHA DO MODO------------------------------------------
        ArrayList<Inimigo> inimigos = Jogo.Aux.escolherModoDeJogo(inputs, heroi);

//----------------------------------BARALHO E PILHAS------------------------------------------
        ArrayList<Carta> baralho = Jogo.Aux.gerarBaralhoInicial();
        Prints.PrintsMain.printInicioluta();

        ArrayList<Carta> pilhaCompra = new ArrayList<>(baralho);
        ArrayList<Carta> pilhaDescarte = new ArrayList<>();

//----------------------------------LUTA------------------------------------------
        int furia = 0; //Variável para o usuario usar um efeito
        int contadorRound = 1;

        while(true) { //Loop da luta
            heroi.setEnergia(6); //energia do heroi é resetada a cada Round
            
            //resetando escudo a 0 em cada round
            heroi.setEscudo(0); 
            inimigos.forEach(inimigo -> inimigo.setEscudo(0));
            
            ArrayList<Carta> mao = Jogo.Aux.comprarMao(pilhaCompra, pilhaDescarte);

            if (heroi.estaVivo() == true && Jogo.Aux.inimigosVivos
            (inimigos)) { //Os dois vivos
                ArrayList<String> acoesDoRoundHeroi = new ArrayList<>();
                int[] vidaInimigosInicio = new int[2];

                for(int i = 0; i < inimigos.size(); i++){
                    vidaInimigosInicio[i] = inimigos.get(i).getVida();
                }
                int usouEfeito = 0; //variavel que guarda se o heroi usou efeito
                
                Jogo.Aux.limparTela();
                Prints.PrintsMain.printNovoRound(contadorRound);
                inimigos.stream().filter(inimigo -> inimigo.estaVivo()).forEach(i -> i.anuncio(heroi));
                Prints.PrintsMain.digiteParaContinuar(inputs, 0);

//----------------------------------ESCOLHAS USUÁRIO------------------------------------------
                while (heroi.getEnergia() > 0 && mao.size() > 0) {
                    Jogo.Aux.limparTela();
                    
                    Prints.PrintsMain.printNovoRound(contadorRound);
                    Prints.PrintsMain.printStatus(escolhaheroi, heroi.getVida(), inimigos);

                    for (Inimigo inimigo : inimigos){
                        if (heroi.getListaEfeitos().size() > 0 || inimigo.getListaEfeitos().size() > 0) {
                            PrintsMain.printEfeitosLutadores(heroi.getNome(), heroi.getListaEfeitos(), inimigo.getNome(), inimigo.getListaEfeitos());
                        }
                    }

                    if (acoesDoRoundHeroi.size() > 0) {
                        System.out.println("\n🥊 Suas ações neste round:");
                        for (String acao : acoesDoRoundHeroi) {
                            System.out.println("    " + acao);
                        }
                    }
                    PrintsMain.printEnergiaEMenu(heroi.getEnergia(), mao, furia);
                    
                    if (!heroi.verificaMao(mao)){
                        Jogo.Aux.limparTela();
                        
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

//----------------------------------ESCOLHA EFEITOS------------------------------------------
                   if (num == 99 && furia >= 3) {
                        Jogo.Aux.limparTela();
                        
                        Prints.PrintsMain.menuEfeito();
                        
                        int escolha = inputs.nextInt();
                        if (escolha == 0) {
                            Jogo.Aux.limparTela();
                            System.out.println("❌ Efeito especial cancelado.");
                            continue;
                        }

                        Efeitos efeito = null;
                        Entidade alvoEfeito = heroi;

                        switch (escolha) {
                            case 1:
                                alvoEfeito = Jogo.Aux.escolherAlvo(inimigos, inputs);
                                efeito = new Sangramento("Sangramento", 3, alvoEfeito);
                                break;
                            case 2:
                                alvoEfeito = Jogo.Aux.escolherAlvo(inimigos, inputs);
                                efeito = new Provocacao("Provocacao", 3, alvoEfeito);
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
                            acoesDoRoundHeroi.add("⚡ O golpe aplicou " + efeito.getNome() + " (3X) no " +alvoEfeito.getNome() + "!");
                        }
                        
                        Jogo.Aux.limparTela();
                        continue;
                    }

//----------------------------------ESCOLHA INVÁLDA------------------------------------------
                    if (num >= mao.size() || num < -1 || mao.get(num).getCusto() > heroi.getEnergia()) {
                        Jogo.Aux.limparTela();
                        if(num >= mao.size() || num < -1){
                            System.out.println("⚠️ Opção inválida!");
                        } else {
                            System.out.println("🪫 Infelizmente " + heroi.getNome() + " não tem energia suficiente!");
                        }
                        Prints.PrintsMain.digiteParaContinuar(inputs, -1);
                        continue;
                    }

//----------------------------------ESCOLHA VÁLIDA------------------------------------------
                    Carta cartaEscolhida = mao.remove(num);
                    pilhaDescarte.add(cartaEscolhida);

                    if (cartaEscolhida instanceof CartaEscudo){
                        int escudoAdicionado = cartaEscolhida.usar(heroi);
                        heroi.setEnergia(heroi.getEnergia() - cartaEscolhida.getCusto());
                        
                        acoesDoRoundHeroi.add("✨ " + cartaEscolhida.getNome() + ": " + cartaEscolhida.getDescricao() + " de " + escudoAdicionado + ".");
                        
                    } else { 
                        Entidade alvoCarta = Jogo.Aux.escolherAlvo(inimigos, inputs);
                        Jogo.Aux.limparTela();
                       /*  if (cartaEscolhida instanceof CartaEfeito && cartaEscolhida.getNome().equals("Adrenalina")) {
                            alvoCarta = heroi;
                        } */
                        int valor = cartaEscolhida.usar(alvoCarta);
                        heroi.setEnergia(heroi.getEnergia() - cartaEscolhida.getCusto());
                        acoesDoRoundHeroi.add("💥 " + cartaEscolhida.getNome() + ": " + cartaEscolhida.getDescricao() + " de " + valor + " em " + alvoCarta.getNome() + ".");
                    }
                    if (cartaEscolhida instanceof CartaDano) {
                        if (furia < 3) { //toda vez que o heroi usa uma carta de dano ele ganha um ponto de furia (limite em 3)
                            furia ++;
                        }
                    }
                }

                Jogo.Aux.limparTela();
                PrintsMain.printAcoesDoRound(acoesDoRoundHeroi, inimigos, vidaInimigosInicio);

                while (mao.size() > 0) { //oq sobrou na mao
                    pilhaDescarte.add(mao.remove(0));
                }

                if (!Jogo.Aux.inimigosVivos
                    (inimigos)) { //Inimigos morreram antes de atacar
                    Jogo.Aux.limparTela();
                    Prints.PrintsMain.printHeroiVenceu(heroi);
                    return;
                }

               
                if (usouEfeito == 1) { // // Se o heroi usou efeito o inimigo tambem vai
                    inimigos.stream().filter(inimigo -> inimigo.estaVivo()).forEach(i -> i.usarEfeito(heroi, juiz));
                    usouEfeito = 0;
                    }
                

                inimigos.stream().filter(inimigo -> inimigo.estaVivo()).forEach(i -> i.atacar(heroi));    

                
                boolean haEfeitos = heroi.getListaEfeitos().size() > 0;
                if (!haEfeitos) {
                    for (Inimigo inimigo : inimigos) {
                        if (inimigo.getListaEfeitos().size() > 0) {
                            haEfeitos = true;
                            break;
                        }
                    }
                }

                if (haEfeitos) {
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

                    for (Inimigo inimigo : inimigos) {
                        for (int i = inimigo.getListaEfeitos().size() - 1; i >= 0; i--) { // efeito no inimigo
                            Efeitos efeito = inimigo.getListaEfeitos().get(i);
                            PrintsMain.printEfeitoAgindo(inimigo.getNome(), efeito.getNome(), efeito.getAcumulos());
                            if (efeito.getAcumulos() <= 0) {
                                juiz.desinscrever(efeito);
                                inimigo.getListaEfeitos().remove(i);
                            }
                        }
                    }
                }
            
            
                System.out.println();
                Prints.PrintsMain.digiteParaContinuar(inputs, 0);
                contadorRound++;
            }

            if (Jogo.Aux.verificarFimDeJogo(heroi, inimigos)) {
                break;
            }
        }
        inputs.close(); 
    }  
}