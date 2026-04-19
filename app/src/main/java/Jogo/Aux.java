package Jogo;

import java.util.ArrayList;
import java.util.Collections;

import Cartas.Carta;
import Cartas.CartaDano;
import Cartas.CartaEscudo;
import Entidades.Heroi;
import Entidades.Inimigo;

/**
 * Funcoes utilitarias para apoio ao fluxo principal da partida.
 */
public class Aux {
    /**
     * Limpa a tela do terminal usando codigos ANSI.
     */
    public static void limparTela() {
        System.out.print("\033[H\033[2J\033[3J");
        System.out.flush();
    }
    
    /**
     * Pausa a execucao por um intervalo em milissegundos.
     *
     * @param milisegundos tempo de espera em milissegundos
     */
    public static void esperar(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Permite ao jogador escolher qual inimigo sera alvo de um ataque ou efeito.
     *
     * @param inimigos lista de inimigos disponiveis
     * @param inputs leitor de entrada do usuario
     * @return inimigo selecionado
     */
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

    /**
     * Verifica se existe ao menos um inimigo vivo.
     *
     * @param inimigos lista de inimigos
     * @return true se ao menos um inimigo estiver vivo
     */
    public static boolean inimigosVivos (ArrayList<Inimigo> inimigos){
        for (Inimigo inimigo : inimigos){
            if (inimigo.estaVivo()){
                return true;
            }
        }
        return false;
    }

    /**
     * Gera os inimigos da fase baseando-se no nível atual da árvore utilizando switch case.
     */
    public static ArrayList<Inimigo> prepararInimigos(int nivelAtual, String nomeOponente, String nomeIgnorado) {
        ArrayList<Inimigo> inimigos = new ArrayList<>();

        switch (nivelAtual) {
            case 2:
                inimigos.add(new Inimigo(nomeOponente, 20, 0));
                break;

            case 3:
                inimigos.add(new Inimigo(nomeOponente, 25, 0));
                break;

            case 4:
                inimigos.add(new Inimigo(nomeOponente, 30, 0)); 
                inimigos.add(new Inimigo(nomeIgnorado, 25, 0)); 
                break;
            case 5:
                inimigos.add(new Inimigo(nomeOponente, 50, 0));
                break;

            default:
                break;
        }

        return inimigos;
    }

    /**
     * Gera o baralho inicial do heroi.
     *
     * <p>Inclui cartas de dano e de escudo, com custo ciclico de 1 a 4.</p>
     *
     * @return lista com as cartas iniciais
     */
    public static ArrayList<Carta> gerarBaralhoInicial() {
        ArrayList<Carta> baralho = new ArrayList<>();
        String[] nomeCartas = {
            "Gancho de direita", "Jab", "Joelhada", "Cotovelada", "Chute alto", "Chute brasileiro", "Cruzado de direita", "Cruzado de esquerda", "Gancho de esquerda",
            "Direto", "Chute baixo", "Chute frontal", "Guilhotina", "Voadora", 
            "Esquivo para direita", "Bloqueio", "Esquivo para esquerda", "Esquivo para trás", 
            "Guarda alta", "Guarda baixa", "Correr", "Clinch"};

        int custo = 1;
        for (int i = 0; i < nomeCartas.length; i++) {
            if (i < 14) {
                baralho.add(new CartaDano(nomeCartas[i], custo));
            } else {
                baralho.add(new CartaEscudo(nomeCartas[i], custo));
            }
            custo++; 
            if (custo > 4) { // Limita o custo das cartas em 4
                custo = 1; 
            }
        }

        return baralho;
    }

    /**
     * Compra uma mao de ate 4 cartas a partir da pilha de compra.
     *
     * <p>Quando a pilha de compra esvazia, o descarte é embaralhado de volta virando a nova pilha de compra.</p>
     *
     * @param pilhaCompra pilha atual de compra
     * @param pilhaDescarte pilha de descarte
     * @return lista de cartas compradas para a mao
     */
    public static ArrayList<Carta> comprarMao(ArrayList<Carta> pilhaCompra, ArrayList<Carta> pilhaDescarte) {
        ArrayList<Carta> mao = new ArrayList<>();
        
        for (int i = 0; i < 4; i ++) {
            if (pilhaCompra.size() == 0) {
                Prints.PrintsMain.printBaralhoVazio();
                while (pilhaDescarte.size() > 0) {
                    Carta cartaAux = pilhaDescarte.remove(0);
                    pilhaCompra.add(cartaAux);
                }
                Collections.shuffle(pilhaCompra);
            }
            
            if (pilhaCompra.size() > 0) {
                int cartaAleatoria = (int) (Math.random() * pilhaCompra.size());
                mao.add(pilhaCompra.remove(cartaAleatoria));
            }
        }
        return mao;
    }
    
    /**
     * Avalia condicoes de fim de jogo e imprime o resultado correspondente.
     *
     * @param heroi heroi do jogador
     * @param inimigos lista de inimigos da partida
     * @return true se a partida terminou
     */
    public static boolean verificarFimDeJogo(Heroi heroi, ArrayList<Inimigo> inimigos) {
        boolean heroiVivo = heroi.estaVivo();
        boolean temInimigosVivos = inimigosVivos(inimigos); 

        if (heroiVivo && !temInimigosVivos) { 
            Prints.PrintsMain.printHeroiVenceu(heroi);
            return true;
        }
        
        if (!heroiVivo && temInimigosVivos) { 
            Prints.PrintsMain.printInimigoVenceu(inimigos);
            return true;
        }
        
        if (!heroiVivo && !temInimigosVivos) { 
            Prints.PrintsMain.printEmpate();
            return true;
        }

        return false; 
    }

}
