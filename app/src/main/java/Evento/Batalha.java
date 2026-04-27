package Evento;

import java.util.ArrayList;
import java.util.Scanner;

import Cartas.*;
import Efeitos.*;
import Entidades.*;
import Jogo.Publisher;
import Jogo.Salvamento.EstadoTorneio;
import Jogo.Salvamento.VariaveisBatalha;
import Prints.PrintsMain;

public class Batalha extends Evento {
    private ArrayList<Inimigo> inimigos;
    private Heroi heroi;
    private Publisher juiz;
    private int energiaMaxima;
    private Scanner inputs;
    private boolean usouEfeito;
    private boolean jogadorSaiu; //save feito
    private int torneioNivelAtual;
    private ArrayList<Integer> torneioCaminho;
    private String torneioLutadorIgnorado;

    public Batalha(Heroi heroi, Publisher juiz, Scanner inputs, ArrayList<Inimigo> inimigos){
        this.heroi = heroi;
        this.juiz = juiz;
        this.inputs = inputs;
        this.inimigos = inimigos;
        this.jogadorSaiu = false;
        this.torneioNivelAtual = 1;
        this.torneioCaminho = new ArrayList<>();
        this.torneioLutadorIgnorado = "";
    }

    public int jogadorSaiu() {
        if (jogadorSaiu) {
            return 1;
        }
        return 0;
    }

    @Override
    public void iniciar(Heroi heroi, ArrayList<Carta> pilhaCompra, ArrayList<Carta> pilhaDescarte) {
        this.Luta(pilhaCompra, pilhaDescarte, 1);
        return;
    }

    public void Luta(ArrayList<Carta> pilhaCompra, ArrayList<Carta> pilhaDescarte, int contadorRound){
        while(true){

            if (jogadorSaiu) {
                return;
            }

            boolean heroiVenceu = heroi.estaVivo() && !Jogo.Aux.inimigosVivos(inimigos);
            if (Jogo.Aux.verificarFimDeJogo(heroi, inimigos)) {
                if (heroiVenceu) {
                    heroi.adicionarOuro(40);
                }
                Prints.PrintsMain.digiteParaContinuar(inputs, 0);
                break;
            }


            if (heroi.estaVivo() == true && Jogo.Aux.inimigosVivos(inimigos)){
                inicioRound();

                if (contadorRound == 1) {
                    if (heroi.getEscudoInicioProximaLuta() > 0) {
                        heroi.ganharEscudo(heroi.getEscudoInicioProximaLuta());
                        heroi.setEscudoInicioProximaLuta(0);
                    }
                }

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

                if (jogadorSaiu) {
                    return;
                }

                Jogo.Aux.limparTela();
                PrintsMain.printAcoesDoRound(acoesDoRoundHeroi, inimigos, vidaInimigosInicio);
                Prints.PrintsMain.digiteParaContinuar(inputs, 0);

                limparMao(mao, pilhaDescarte);

                if (!Jogo.Aux.inimigosVivos(inimigos)) { //Inimigos morreram antes de atacar
                    heroi.adicionarOuro(40);
                    Jogo.Aux.limparTela();
                    Prints.PrintsMain.printHeroiVenceu(heroi);
                    Prints.PrintsMain.digiteParaContinuar(inputs, 0);
                    return;
                }

                acoesInimigo();

                if (Jogo.Aux.verificarFimDeJogo(heroi, inimigos)) {
                    boolean heroiVenceuAposInimigo = heroi.estaVivo() && !Jogo.Aux.inimigosVivos(inimigos);
                    if (heroiVenceuAposInimigo) {
                        heroi.adicionarOuro(40);
                    }
                    Prints.PrintsMain.digiteParaContinuar(inputs, 0);
                    break;
                }

                Prints.PrintsMain.digiteParaContinuar(inputs, 0);

                if (verficaEfeitos()){
                    aplicaEfeitos();

                    if (Jogo.Aux.verificarFimDeJogo(heroi, inimigos)) {
                        boolean heroiVenceuAposEfeitos = heroi.estaVivo() && !Jogo.Aux.inimigosVivos(inimigos);
                        if (heroiVenceuAposEfeitos) {
                            heroi.adicionarOuro(40);
                        }
                        Prints.PrintsMain.digiteParaContinuar(inputs, 0);
                        break;
                    }
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
            if (!Jogo.Aux.inimigosVivos(inimigos)) {
            return;
            }
            Jogo.Aux.limparTela();
            Prints.PrintsMain.printNovoRound(contadorRound);
            PrintsMain.printStatus(heroi, inimigos);

            if (heroi.getBonusDanoProximaLuta() > 0) {
                System.out.println("Dano extra ativo: +" + heroi.getBonusDanoProximaLuta() + " dano em todos os ataques nesta luta");
            }

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
            if (num == 5) {
                jogadorSaiu = true;
                VariaveisBatalha batalha = new VariaveisBatalha(); //salvando o estado atual da partida
                batalha.heroi = heroi;
                batalha.inimigos = inimigos;
                batalha.pilhaCompra = Jogo.Salvamento.Salvamento.salvarCartas(pilhaCompra);
                batalha.pilhaDescarte = Jogo.Salvamento.Salvamento.salvarCartas(pilhaDescarte);
                batalha.roundAtual = contadorRound;
                Jogo.Salvamento.Salvamento.salvarPartida(batalha, null);

                EstadoTorneio torneio = new EstadoTorneio();
                torneio.nivelAtual = torneioNivelAtual;
                torneio.caminho = new ArrayList<>(torneioCaminho);
                torneio.lutadorIgnorado = torneioLutadorIgnorado;
                Jogo.Salvamento.Salvamento.salvarTorneio(torneio, null);

                System.out.println("\n👋 A partida foi salva, saindo do jogo!");
                return;
            }
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
            if (cartaEscolhida instanceof CartaDano) {
                if (heroi.getBonusDanoProximaLuta() > 0) {
                    alvoCarta.receberDano(heroi.getBonusDanoProximaLuta());
                    valor += heroi.getBonusDanoProximaLuta();
                }
            }
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
        if (!heroi.estaVivo() || !Jogo.Aux.inimigosVivos(inimigos)) {
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

    public void iniciarTorneio(ArrayList<Carta> pilhaCompra, ArrayList<Carta> pilhaDescarte) {
        iniciar(pilhaCompra, pilhaDescarte, null);
    }

    public void iniciar(ArrayList<Carta> pilhaCompra, ArrayList<Carta> pilhaDescarte, EstadoTorneio estado) {
        Arvore.Arvore mapa = new Arvore.Arvore("Início");
        mapa.gerarFilhos();
        javax.swing.tree.DefaultMutableTreeNode noAtual = mapa.getRaiz();
        String lutadorIgnorado = "";

        torneioCaminho.clear();
        torneioNivelAtual = 1;
        torneioLutadorIgnorado = "";

        int nivelAtual = 1;
        if (estado != null) { //se o torneio ainda nao comecou
            for (int indice : estado.caminho) {
                noAtual = (javax.swing.tree.DefaultMutableTreeNode) noAtual.getChildAt(indice);
                torneioCaminho.add(indice);
            }
            lutadorIgnorado = estado.lutadorIgnorado;
            nivelAtual = estado.nivelAtual;
            torneioNivelAtual = nivelAtual;
            torneioLutadorIgnorado = lutadorIgnorado;
        }
        while (heroi.estaVivo() && nivelAtual < 5) {
            if (jogadorSaiu) {
                return;
            }
            nivelAtual ++;
            Jogo.Aux.limparTela();
            if (nivelAtual > 1) {
                mapa.imprimirArvoreProgresso(noAtual.toString());
            }

            System.out.println("\n💰 Ouro atual: " + heroi.getOuro());

            System.out.println("\nEscolha seu próximo desafio ou digite [6] para ir a loja:");
            int totalFilhos = noAtual.getChildCount();
            for (int i = 0; i < totalFilhos; i ++) {
                javax.swing.tree.DefaultMutableTreeNode filho = (javax.swing.tree.DefaultMutableTreeNode) noAtual.getChildAt(i);
                String textoOpcao = filho.getUserObject().toString();
                if (textoOpcao.contains("BOX -> ")) {
                    textoOpcao = "📦BOX SURPRESA -> " + textoOpcao.replace("BOX -> ", "").trim();
                }
                System.out.println("[" + (i + 1) + "] " + textoOpcao);
            }
            int escolhaMenu = inputs.nextInt();
            if (escolhaMenu == 6) {
                new Loja(heroi, inputs).iniciar(heroi, pilhaCompra, pilhaDescarte);
                nivelAtual--; 
                continue;
            }

            int escolha = escolhaMenu - 1;
            javax.swing.tree.DefaultMutableTreeNode proximoNo = (javax.swing.tree.DefaultMutableTreeNode) noAtual.getChildAt(escolha);
            String nomeOponente = proximoNo.toString();

            if (nomeOponente.contains("BOX -> ")) {
                Escolha boxSurpresa = new Escolha(inputs);
                Jogo.Aux.limparTela();
                boxSurpresa.iniciar(heroi, pilhaCompra, pilhaDescarte);

                nomeOponente = nomeOponente.replace("BOX -> ", "").trim();
            }

            torneioCaminho.add(escolha);

            if (nivelAtual == 3) {
                int direcao;
                if (escolha == 0) { //esquerda
                    direcao = 1;
                }  else {  //direita
                    direcao = 0;
                }
                lutadorIgnorado = noAtual.getChildAt(direcao).toString().replace("BOX -> ", "").trim();
            }

            torneioNivelAtual = nivelAtual;
            torneioLutadorIgnorado = lutadorIgnorado;
             
            if (nivelAtual == 4) {
                Prints.PrintsMain.printInvasaoOctogono(nomeOponente, lutadorIgnorado);
            }
            if (nivelAtual == 5) {
                Prints.PrintsMain.printLutaPeloCinturao(nomeOponente);
            }

            this.inimigos = Jogo.Aux.prepararInimigos(nivelAtual, nomeOponente, lutadorIgnorado);
            this.Luta(pilhaCompra, pilhaDescarte, 1);

            if (jogadorSaiu) {
                return;
            }

            if (!heroi.estaVivo()) {
                break;
            }

            heroi.limparFimLuta();
            noAtual = proximoNo;
        }
    }

    public static int modoDeJogo(Scanner inputs) {
        System.out.println("🎮 Modo de jogo:");
        System.out.println("[1] Começar um novo jogo");
        System.out.println("[2] Carregar um jogo salvo");
        System.out.print("Escolha uma opção: ");

        String escolha = inputs.nextLine();

        switch (escolha) {
            case "1":
                System.out.println("✅ Você escolheu: Novo jogo");
                return 0;
                
            case "2":
                System.out.println("✅ Você escolheu: Carregar jogo salvo");
                System.out.println("\nDeseja carregar o último jogo que você não concluiu?");
                System.out.println("[1] Sim");
                System.out.println("[2] Não (começar novo jogo)");
                System.out.print("Escolha uma opção: ");

                String confirmar = inputs.nextLine();

                switch (confirmar) {
                    case "1":
                        return 1;
                    case "2":
                        System.out.println("✅ Ok! Iniciando um novo jogo.");
                        return 0;
                }
                return 0;
        }

        return 0;
    }

    public void setUsouEfeito (boolean valor){
        this.usouEfeito = valor;
    }
}
