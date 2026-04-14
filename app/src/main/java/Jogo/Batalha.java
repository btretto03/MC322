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
    private Scanner inputs;
    private boolean usouEfeito;


    public Batalha(ArrayList<Inimigo> inimigos, Heroi heroi, Publisher juiz, Scanner inputs){
        this.inimigos = inimigos;
        this.heroi = heroi;
        this.juiz = juiz;
        this.inputs = inputs;
    }

    public void Luta (ArrayList<Carta> pilhaCompra, ArrayList<Carta> pilhaDescarte){
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

                usouEfeito = false; 

                interacaoUsuario(mao, acoesDoRoundHeroi, contadorRound, pilhaCompra, pilhaDescarte);

                Jogo.Aux.limparTela();
                PrintsMain.printAcoesDoRound(acoesDoRoundHeroi, inimigos, vidaInimigosInicio);
                Prints.PrintsMain.digiteParaContinuar(inputs, 0);

                limparMao(mao, pilhaDescarte);

                if (!Jogo.Aux.inimigosVivos(inimigos)) { //Inimigos morreram antes de atacar
                    Jogo.Aux.limparTela();
                    Prints.PrintsMain.printHeroiVenceu(heroi);
                    return;
                }

                acoesInimigo();

                if (verficaEfeitos()){
                    aplicaEfeitos();
                }

                System.out.println();
                Jogo.Aux.esperar(300);
                contadorRound++;
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

    public void interacaoUsuario (ArrayList<Carta> mao, ArrayList<String> acoesDoRoundHeroi, int contadorRound,ArrayList<Carta> pilhaCompra, ArrayList<Carta> pilhaDescarte){
        while (heroi.getEnergia() > 0 && mao.size() > 0){
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
                return;
            }

            int num = inputs.nextInt();
            if (num == -1){
                return;
            }

            if (num == 99 && heroi.getFuria() >= 3){
                escolhaEfeitos(acoesDoRoundHeroi);
                continue;
            }

            if (num >= mao.size() || num < -1 || mao.get(num).getCusto() > heroi.getEnergia()){
                escolhaInvalida(num, mao.size());
                continue;
            }

            Carta cartaEscolhida = mao.remove(num);
            pilhaDescarte.add(cartaEscolhida);
            aplicarCarta(cartaEscolhida, acoesDoRoundHeroi);
        }
    }

    public void acoesInimigo(){
        if (usouEfeito == true) { // // Se o heroi usou efeito o inimigo tambem vai
            inimigos.stream().filter(inimigo -> inimigo.estaVivo()).forEach(i -> i.usarEfeito(heroi, juiz));
            usouEfeito = false;
        }
        
        for (int i = 0; i < inimigos.size(); i ++) {
            if (inimigos.get(i).estaVivo()) {
                inimigos.get(i).atacar(heroi, inimigos, i);
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

    public void escolhaEfeitos (ArrayList<String> acoesDoRoundHeroi){
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
                usouEfeito = true; // Avisa o inimigo para retaliar
                if (efeito.getNome() != "Nocaute"){
                    acoesDoRoundHeroi.add("⚡ O golpe aplicou " + efeito.getNome() + " (3X) no " +alvoEfeito.getNome() + "!");
                } else {
                    acoesDoRoundHeroi.add("🎯 Tentativa de K.O no " +alvoEfeito.getNome() + "!🎯");
                }
            }
            
            Jogo.Aux.limparTela();
        }

    public void escolhaInvalida (int num, int qntCartas){
        Jogo.Aux.limparTela();
        if(num >= qntCartas || num < -1){
            System.out.println("⚠️ Opção inválida!");
        } else {
            System.out.println("🪫 Infelizmente " + heroi.getNome() + " não tem energia suficiente!");
        }
        Prints.PrintsMain.digiteParaContinuar(inputs, -1);
    }

    public void aplicarCarta (Carta cartaEscolhida, ArrayList<String> acoesDoRoundHeroi){
        if (cartaEscolhida instanceof CartaEscudo){
            //Jogo.Aux.limparTela();
            Prints.AnimacaoLuta.animarGolpeHeroi(heroi, inimigos, cartaEscolhida.getNome(), 0);
            int escudoAdicionado = cartaEscolhida.usar(heroi);
            heroi.setEnergia(heroi.getEnergia() - cartaEscolhida.getCusto());
            
            acoesDoRoundHeroi.add("✨ " + cartaEscolhida.getNome() + ": " + cartaEscolhida.getDescricao() + " de " + escudoAdicionado + ".");
            Jogo.Aux.esperar(1000);
        } else { 
            Entidade alvoCarta = Jogo.Aux.escolherAlvo(inimigos, inputs);
            int alvo = inimigos.indexOf(alvoCarta);
            //Jogo.Aux.limparTela();
            Prints.AnimacaoLuta.animarGolpeHeroi(heroi, inimigos, cartaEscolhida.getNome(), alvo);
            //Jogo.Aux.limparTela();

            int valor = cartaEscolhida.usar(alvoCarta);
            heroi.setEnergia(heroi.getEnergia() - cartaEscolhida.getCusto());
            acoesDoRoundHeroi.add("💥 " + cartaEscolhida.getNome() + ": " + cartaEscolhida.getDescricao() + " de " + valor + " em " + alvoCarta.getNome() + ".");
            Jogo.Aux.esperar(1000);
        }
        if (cartaEscolhida instanceof CartaDano) {
            if (heroi.getFuria() < 3) { //toda vez que o heroi usa uma carta de dano ele ganha um ponto de furia (limite em 3)
                heroi.setFuria(heroi.getFuria() + 1);;
            }
        }
    }

    public boolean verficaEfeitos(){
        boolean haEfeitos = heroi.getListaEfeitos().size() > 0;
        
        if (!haEfeitos) {
            for (Inimigo inimigo : inimigos) {
                if (inimigo.getListaEfeitos().size() > 0) {
                    haEfeitos = true;
                    break;
                }
            }
        }

        return haEfeitos;
    }

    public void aplicaEfeitos (){
        System.out.println("🧪 Os efeitos estão agindo...");
        Jogo.Aux.esperar(300);
        juiz.notificarSubscribers();
        if (!Jogo.Aux.inimigosVivos(inimigos)) {
            Jogo.Aux.limparTela();
            Prints.PrintsMain.printHeroiVenceu(heroi);
            return;
        }

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
        PrintsMain.digiteParaContinuar(inputs, 0);
    }

    public void limparMao(ArrayList<Carta> mao, ArrayList<Carta> pilhaDescarte){
        while (mao.size() > 0) { //oq sobrou na mao
            pilhaDescarte.add(mao.remove(0));
        }
    }

    public int inimigosVivos (){
        return (int) inimigos.stream().filter(inimigo -> inimigo.estaVivo()).count();
    }

    public ArrayList<Inimigo> getInimigos (){
        return this.inimigos;
    }
}
