package Jogo;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Scanner;

import Cartas.Carta;
import Cartas.CartaDano;
import Cartas.CartaEscudo;
import Efeitos.Adrenalina;
import Efeitos.Efeitos;
import Efeitos.Nocaute;
import Efeitos.Provocacao;
import Efeitos.Sangramento;
import Entidades.Entidade;
import Entidades.Heroi;
import Entidades.Inimigo;
import Prints.PrintsMain;

public class Batalha {
    private ArrayList<Inimigo> inimigos;
    private Heroi heroi;
    private Publisher juiz;
    private int energiaMaxima;


    public Batalha(Heroi heroi, ArrayList<Inimigo> inimigos){
        this.inimigos = inimigos;
        this.heroi = heroi;
    }

    public ArrayList<Entidade> Luta (ArrayList<Carta> pilhaCompra, ArrayList<Carta> pilhaDescarte, Scanner inputs){
        int contadorRound = 1;
        while(true){

            if (Jogo.Aux.verificarFimDeJogo(heroi, inimigos)) {
                break;
            }


            if (heroi.estaVivo() == true && Jogo.Aux.inimigosVivos(inimigos)){
                inicioRound();
                ArrayList<Carta> mao = Jogo.Aux.comprarMao(pilhaCompra, pilhaDescarte);
                ArrayList<String> acoesDoRoundHeroi = new ArrayList<>();
                int[] vidaInimigosInicio = new int[2];

                for(int i = 0; i < inimigos.size(); i++){
                    vidaInimigosInicio[i] = inimigos.get(i).getVida();
                }
                
                Jogo.Aux.limparTela();
                Prints.PrintsMain.printNovoRound(contadorRound);
                inimigos.stream().filter(inimigo -> inimigo.estaVivo()).forEach(i -> i.anuncio(heroi));
                Prints.PrintsMain.digiteParaContinuar(inputs, 0);
            }
        }

    }

    public void inicioRound (){
        heroi.setEnergia(6); //energia do heroi é resetada a cada Round
            
        // Aumenta energia se tiver 2 inimigos vivos
        if (inimigos.stream().filter(inimigo -> inimigo.estaVivo()).count() >= 2) {
            heroi.setEnergia(8);
        }
        
        //resetando escudo a 0 em cada round
        heroi.setEscudo(0); 
        inimigos.forEach(inimigo -> inimigo.setEscudo(0));
    }

    public void round (ArrayList<Carta> mao, ArrayList<String> acoesDoRoundHeroi, int contadorRound, Scanner inputs){
        while (heroi.getEnergia() > 0 && mao.size() > 0){
            int usouEfeito = 0; //variavel que guarda se o heroi usou efeito
            Jogo.Aux.limparTela();
            Prints.PrintsMain.printNovoRound(contadorRound);
            PrintsMain.printStatus(heroi, inimigos);

            mostrarEfeitos();

            mostrarAcoesJogador(acoesDoRoundHeroi);

            setEnergiaMax();
            PrintsMain.printEnergiaEMenu(heroi.getEnergia(), energiaMaxima, mao, heroi.getFuria());

            if (!heroi.verificaMao(mao)){
                Jogo.Aux.limparTela();
                Prints.PrintsMain.printFimEnergia();
                Prints.PrintsMain.digiteParaContinuar(inputs, 0);
            }

            int num = inputs.nextInt();
            if (num == -1){
                break;
            }

            if (num == 99 && heroi.getFuria() >= 3){
                escolhaEfeitos(inputs, usouEfeito, acoesDoRoundHeroi);
                continue;
            }

            if (num >= mao.size() || num < -1 || mao.get(num).getCusto() > heroi.getEnergia()){
                escolhaInvalida(num, mao.size(), inputs);
                continue;
            }

        }
    }

    public void mostrarEfeitos (){
        for (Inimigo inimigo : inimigos){
            if (!inimigo.estaVivo()){
                continue;
            }
            if (heroi.getListaEfeitos().size() > 0 || inimigo.getListaEfeitos().size() > 0) {
                PrintsMain.printEfeitosLutadores(heroi.getNome(), heroi.getListaEfeitos(), inimigo.getNome(), inimigo.getListaEfeitos());
            }
        }
    }

    public void mostrarAcoesJogador (ArrayList<String> acoesDoRoundHeroi){
        if (acoesDoRoundHeroi.size() > 0) {
            System.out.println("\n🥊 Suas ações neste round:");
            for (String acao : acoesDoRoundHeroi) {
                System.out.println("    " + acao);
            }
        }
    }

    public void setEnergiaMax (){
        if (inimigos.stream().filter(inimigo -> inimigo.estaVivo()).count() >= 2) {
            energiaMaxima = 8;
        } else {
            energiaMaxima = 6;
        }
    }

    public void escolhaEfeitos (Scanner inputs, int usouEfeito, ArrayList<String> acoesDoRoundHeroi){
            Jogo.Aux.limparTela();
            Prints.PrintsMain.menuEfeito();
            
            int escolha = inputs.nextInt();
            if (escolha == 0) {
                Jogo.Aux.limparTela();
                System.out.println("❌ Efeito especial cancelado.");
                return;
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
                case 4:
                    alvoEfeito = Jogo.Aux.escolherAlvo(inimigos, inputs);
                    efeito = new Nocaute("Nocaute", alvoEfeito, inimigos);
                    break;
            }

            if (efeito != null) {
                alvoEfeito.adicionarEfeito(efeito, juiz);
                juiz.inscrever(efeito);
                // furia -= 3; // Gasta a fúria
                heroi.setFuria(heroi.getFuria() - 3);
                usouEfeito = 1; // Avisa o inimigo para retaliar
                if (efeito.getNome() != "Nocaute"){
                    acoesDoRoundHeroi.add("⚡ O golpe aplicou " + efeito.getNome() + " (3X) no " +alvoEfeito.getNome() + "!");
                } else {
                    acoesDoRoundHeroi.add("🎯 Tentativa de K.O no " +alvoEfeito.getNome() + "!🎯");
                }
            }
            
            Jogo.Aux.limparTela();
    }

    public void escolhaInvalida (int num, int qntCartas, Scanner inputs){
        Jogo.Aux.limparTela();
        if(num >= qntCartas || num < -1){
            System.out.println("⚠️ Opção inválida!");
        } else {
            System.out.println("🪫 Infelizmente " + heroi.getNome() + " não tem energia suficiente!");
        }
        Prints.PrintsMain.digiteParaContinuar(inputs, -1);
    }

    public int inimigosVivos (){
        return (int) inimigos.stream().filter(inimigo -> inimigo.estaVivo()).count();
    }

    public ArrayList<Inimigo> getInimigos (){
        return this.inimigos;
    }
}
