package Jogo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import Cartas.Carta;
import Cartas.CartaDano;
import Cartas.CartaEscudo;
import Entidades.Heroi;
import Entidades.Inimigo;

public class Aux {
    public static void limparTela() {
        System.out.print("\033[H\033[2J\033[3J");
        System.out.flush();
    }
    
    public static void esperar(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
    }
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
    public static ArrayList<Inimigo> escolherModoDeJogo(Scanner inputs, Heroi heroi) {
        Prints.PrintsMain.printEscolhaModo();
        int modo = inputs.nextInt();
        ArrayList<Inimigo> inimigos = new ArrayList<>();

        switch (modo) {
            case 1:
                limparTela();
                System.out.println("🥊 MODO 1 VS 1 SELECIONADO 🥊\n");
                String inimigo = Inimigo.escolherInimigo(inputs);
                inimigos.add(new Inimigo(inimigo, 2, 0));
                break;
            case 2:
                Inimigo.escolherInimigoDuplo(inputs, 1, inimigos);
                break;
            case 3:
                Inimigo.escolherInimigoDuplo(inputs, 2, inimigos);
                break;
            default:
                limparTela();
                System.out.println("⚠️ Opção inválida! Modo 1 VS 1 selecionado por padrão.\n");
                String inimigoDefault = Inimigo.escolherInimigo(inputs);
                inimigos.add(new Inimigo(inimigoDefault, 50, 0));
                break;
        }

        if (inimigos.size() > 1) { 
            heroi.setVida(heroi.getVida() * 2);
        }
        return inimigos;
    }

    public static ArrayList<Carta> gerarBaralhoInicial() {
        ArrayList<Carta> baralho = new ArrayList<>();
        String[] nomeCartas = {
            "Cruzado de direita", "Gancho de direita", "Gancho de esquerda",  "Cruzado de esquerda", 
            "Jab", "Direto", "Chute baixo", "Chute frontal", "Guilhotina", "Voadora", 
            "Esquivo para direita", "Bloqueio", "Esquivo para esquerda", "Esquivo para trás", 
            "Guarda alta", "Guarda baixa", "Correr"
        };
        
        int custo = 1;
        for (int i = 0; i < nomeCartas.length; i++) {
            if (i < 10) {
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
