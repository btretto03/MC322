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
        System.out.print("\033[H\033[2J\033[3J");
        System.out.flush();
    }
    public static Scanner inputs = new Scanner(System.in);

    public static Inimigo escolherAlvo (ArrayList<Inimigo> inimigos){
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
    public static void main(String[] args)  {
        Publisher juiz = new Publisher();
        Prints.PrintsMain.printInicial();
    

//----------------------------------INSTANCIAMENTO------------------------------------------
        String escolhaheroi = Heroi.escolherHeroi(inputs);
        limparTela();
        String escolhainimigo = Inimigo.escolherInimigo(inputs);
        
        Heroi heroi = new Heroi(escolhaheroi, 50, 0); //definindo as classes
        ArrayList<Inimigo> inimigos = new ArrayList<>();
        inimigos.add(new Inimigo(escolhainimigo, 50, 0));

//----------------------------------DOIS INIMIGOS------------------------------------------
        int inimigoSecundario = (int) (Math.random() * 7) + 1;

        if (inimigoSecundario <= 3){ //se o valor aleatório for entre 0 e 3, cria um novo inimigo
            Scanner pseudoInput = new Scanner(String.valueOf(inimigoSecundario));
            String nomeSecundario = Inimigo.escolherInimigo(pseudoInput);

            while (nomeSecundario == escolhainimigo) {
                inimigoSecundario = (int) (Math.random() * 3) + 1;
                pseudoInput = new Scanner(String.valueOf(inimigoSecundario));
                nomeSecundario = Inimigo.escolherInimigo(pseudoInput);
            }

            inimigos.add(new Inimigo(nomeSecundario, 25, 0));
            inimigos.get(0).setVida(25);
        }

//----------------------------------BARALHO E PILHAS------------------------------------------
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

//----------------------------------LUTA------------------------------------------
        int furia = 0; //Variável para o usuario usar um efeito
        int contadorRound = 1;

        while(true) { //Loop da luta
            heroi.setEnergia(6); //energia do heroi é resetada a cada Round
            
            //resetando escudo a 0 em cada round
            heroi.setEscudo(0); 
            inimigos.forEach(inimigo -> inimigo.setEscudo(0));
            
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
                    int cartaAleatoria = (int) (Math.random() * pilhaCompra.size());
                    mao.add(pilhaCompra.remove(cartaAleatoria)); //Compra do topo da pilha já embaralhada
                }
            }

            if (heroi.estaVivo() == true && inimigosVivos(inimigos)) { //Os dois vivos
                ArrayList<String> acoesDoRoundHeroi = new ArrayList<>();
                int[] vidaInimigosInicio = new int[2];

                for(int i = 0; i < inimigos.size(); i++){
                    vidaInimigosInicio[i] = inimigos.get(i).getVida();
                }

                int usouEfeito = 0; //variavel que guarda se o heroi usou efeito

                limparTela();
                Prints.PrintsMain.printNovoRound(contadorRound);
                inimigos.stream().filter(inimigo -> inimigo.estaVivo()).forEach(i -> i.anuncio(heroi));
                Prints.PrintsMain.digiteParaContinuar(inputs, 0);

//----------------------------------ESCOLHAS USUÁRIO------------------------------------------
                while (heroi.getEnergia() > 0 && mao.size() > 0) {
                    limparTela();
                    
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

//----------------------------------ESCOLHA EFEITOS------------------------------------------
                   if (num == 99 && furia >= 3) {
                        limparTela();
                        
                        Prints.PrintsMain.menuEfeito();
                        
                        int escolha = inputs.nextInt();
                        if (escolha == 0) {
                            limparTela();
                            System.out.println("❌ Efeito especial cancelado.");
                            continue;
                        }

                        Efeitos efeito = null;
                        Entidade alvoEfeito = heroi;

                        switch (escolha) {
                            case 1:
                                alvoEfeito = escolherAlvo(inimigos);
                                efeito = new Sangramento("Sangramento", 3, alvoEfeito);
                                break;
                            case 2:
                                alvoEfeito = escolherAlvo(inimigos);
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
                        
                        limparTela();
                        continue;
                    }

//----------------------------------ESCOLHA INVÁLDA------------------------------------------
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

//----------------------------------ESCOLHA VÁLIDA------------------------------------------
                    Carta cartaEscolhida = mao.remove(num);
                    pilhaDescarte.add(cartaEscolhida);

                    if (cartaEscolhida instanceof CartaEscudo){
                        int escudoAdicionado = cartaEscolhida.usar(heroi);
                        heroi.setEnergia(heroi.getEnergia() - cartaEscolhida.getCusto());
                        
                        acoesDoRoundHeroi.add("✨ " + cartaEscolhida.getNome() + ": " + cartaEscolhida.getDescricao() + " de " + escudoAdicionado + ".");
                        
                    } else { 
                        Entidade alvoCarta = escolherAlvo(inimigos);
                        limparTela();
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

                limparTela();
                PrintsMain.printAcoesDoRound(acoesDoRoundHeroi, inimigos, vidaInimigosInicio);

                while (mao.size() > 0) { //oq sobrou na mao
                    pilhaDescarte.add(mao.remove(0));
                }

                if (!inimigosVivos(inimigos)) { //Inimigos morreram antes de atacar
                    limparTela();
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

            if (heroi.estaVivo() == true && inimigosVivos(inimigos) == false) { //Inimigos morreram
                 Prints.PrintsMain.printHeroiVenceu(heroi);
                break;
            }
            if (heroi.estaVivo() == false && inimigosVivos(inimigos) == true){ //Heroi morreu
                Prints.PrintsMain.printInimigoVenceu(inimigos);
                break;
            }
            if (heroi.estaVivo() == false && inimigosVivos(inimigos) == false){ //empate
                Prints.PrintsMain.printEmpate();
                break;
            }
        }
        inputs.close(); 
    }  
}