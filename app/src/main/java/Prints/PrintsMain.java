package Prints;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.io.InputStream;

import java.util.ArrayList;

import Cartas.Carta;
import Entidades.Heroi;
import Entidades.Inimigo;
import Jogo.Aux;
import Efeitos.Efeitos;
import Efeitos.Nocaute;

/**
 * Colecao central de mensagens e telas exibidas no terminal durante a partida.
 */
public class PrintsMain {
    private static  String roxo = "\u001B[35m";
    private static String reset = "\u001B[0m";
    private static String vermelho = "\u001B[31m";
    private static String amarelo = "\u001B[33m";
    private static String ciano = "\u001B[36m";
    private static String italico = "\u001B[3m";
    private static String fundoSalmao = "\u001B[48;5;210m";
    private static String fundoVerdeClaro = "\u001B[48;5;193m";
    private static String fundoVermelhoNegrito = "\u001B[41;1m";
    private static String vermelhoNegrito = "\u001B[31;1m";
    private static String branco = "\u001B[37m";
    private static String laranja = "\u001B[38;5;208m";

    
    /**
     * Exibe a abertura inicial do jogo com arte ASCII.
     */
    public static void printInicial2() {
        System.out.print("\033[H\033[2J"); // limpa tela
        System.out.println(fundoSalmao + "                                              " + reset);
        Jogo.Aux.esperar(200);
        System.out.println(fundoSalmao + " 🥊 ULTIMATE FIGHTING JAVA CHAMPIONSHIP 🥊    " + reset);
        Jogo.Aux.esperar(200);
        System.out.println(fundoSalmao + "                                              \n" + reset);
    
        Jogo.Aux.esperar(500);

        try {
            InputStream arquivo = PrintsMain.class.getClassLoader().getResourceAsStream("Printinicial.txt");
            if (arquivo == null){
                throw new FileNotFoundException();
            }
            Scanner leitor = new Scanner(arquivo);
            int linhaAtual = 1;

            while (leitor.hasNextLine()) {
                Jogo.Aux.esperar(200); 
                String linha = leitor.nextLine();
                
                if (linhaAtual <= 5) { //UFC
                    Jogo.Aux.esperar(200); 
                    System.out.println(vermelho + linha + reset);
                } else {
                    System.out.println(amarelo + linha + reset); //JAVA
                }
                linhaAtual++;
            }
            leitor.close();

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado;"); //Java nao compila sem isso
        }

        Jogo.Aux.esperar(200);
        System.out.println("\n🥊 BEM-VINDO AO OCTÓGONO JAVA  🥊\n");

        Jogo.Aux.esperar(500);
        System.out.println(ciano + italico + "   🕹️ Desenvolvido por: Bruno Antonio Tretto & João Felipe Denadai Madeira" + reset + "\n");
        Jogo.Aux.esperar(4000);
    }
    
    /**
     * Exibe mensagem de inicio da luta.
     */
    public static void printInicioluta() {
        System.out.println("🔥 A LUTA VAI COMEÇAR! 🔥\n");

    }

    /**
     * Exibe aviso de baralho vazio antes de reciclar descarte.
     */
    public static void printBaralhoVazio() {
        System.out.println("🔄 Baralho vazio! Voltando o descarte para a pilha");

    }

    /**
     * Imprime cabecalho visual do round atual.
     *
     * @param contadorRound numero do round
     */
    public static void printNovoRound(int contadorRound) {
        System.out.println(fundoSalmao + "                                        " + reset);
        System.out.println(fundoSalmao + "               ROUND " + contadorRound + "                  " + reset);
        System.out.println(fundoSalmao + "                                        " + reset);
    }

    /**
     * Exibe mensagem de fim do turno por falta de energia.
     */
    public static void printFimEnergia() {
        System.out.println("\n-------------------------------------------------");
        System.out.println("\n🪫 Energia insuficiente para continuar. Rodada finalizada.\n");
        System.out.println("-------------------------------------------------");
    }

    /**
     * Imprime vida atual do heroi e dos inimigos.
     *
     * @param heroi objeto heroi utilizado pelo jogador
     * @param inimigos lista de inimigos da partida
     */
    public static void printStatus(Heroi heroi, ArrayList<Inimigo> inimigos) {
        System.out.println("🟩 " + heroi.getNome() + ": ❤️ " + heroi.getVida() + " VIDA");

        inimigos.stream().filter(inimigo -> inimigo.estaVivo()).forEach(i -> System.out.println("🟥 " + i.getNome() + ": ❤️ " + i.getVida() + " VIDA"));
        inimigos.stream().filter(inimigo -> !inimigo.estaVivo()).forEach(i -> System.out.println("🟥 " + i.getNome() + ": 💀 FOI NOCAUTEADO"));
        System.out.println();
        
        boolean temEfeitoHeroi = !heroi.getListaEfeitos().isEmpty();
        boolean temEfeitoInimigo = false;
        for (Inimigo inimigo : inimigos) {
            if (!inimigo.getListaEfeitos().isEmpty()) {
                temEfeitoInimigo = true;
                break;
            }
        }

        if (temEfeitoHeroi || temEfeitoInimigo) {
            AnimacaoLuta.printLutadoresEfeito(heroi, inimigos);
        } else {
            AnimacaoLuta.printLutadoresParados(heroi, inimigos);
        }
    }

    /**
     * Imprime energia, barra de furia e cartas disponiveis na mao do jogador.
     *
     * @param energia energia atual do heroi
     * @param energiaMaxima energia máxima do heroi
     * @param mao cartas atuais na mao
     * @param furia nivel de furia atual
     */
    public static void printEnergiaEMenu(int energia, int energiaMaxima, ArrayList<Carta> mao, int furia) {
        System.out.println("\n---------------------------------------");
        System.out.println("🔋 Energia: " + energia + "/" + energiaMaxima + "   🔥 Fúria: " + getBarraFuria(furia));
        
        System.out.println("\nSuas opções:");
        for (int i = 0; i < mao.size(); i ++) {
            mao.get(i).printRodada(i);
        }
        
        if (furia >= 3) {
            System.out.println("\n" + fundoVermelhoNegrito + " [99] ⚡ Liberar efeito especial " + reset);
        }
        
        System.out.print("\nEscolha uma carta, -1 para passar, ou 5 para salvar e sair: ");
    }

    public static void printErroOpcao() {
        System.out.println("⚠️ Opção inválida! Digite -1 para voltar a jogada");
    }

    /**
     * Imprime as acoes do jogador no round e dano total causado.
     *
     * @param acoes lista textual de acoes executadas
     * @param inimigos lista de inimigos
     * @param vidaInimigosInicio vida de cada inimigo no inicio do round
     */
    public static void printAcoesDoRound(ArrayList<String> acoes, ArrayList<Inimigo> inimigos, int[] vidaInimigosInicio) {
        System.out.println(fundoSalmao + "\n 🥊SUAS AÇÕES NESSE ROUND🥊 \n" + reset);
        for (String acao : acoes) System.out.println(acao);
        for (int i = 0; i < inimigos.size(); i++){
            System.out.println("\n 🩸 Vida removida do " + inimigos.get(i).getNome() + " nesse round: " + (vidaInimigosInicio[i] - inimigos.get(i).getVida()));
        }
        System.out.println("----------------------------------------");
    }

    /**
     * Resume as acoes executadas por um inimigo no round.
     *
     * @param nome nome do inimigo
     * @param dano dano preparado/aplicado
     * @param escudo escudo ganho no round
     * @param vidaRemovida vida removida do heroi
     * @param escudoRemovido escudo removido do heroi
     */
    public static void printAcoesInimigo(String nome, int dano, int escudo, int vidaRemovida, int escudoRemovido) {
        System.out.println(fundoSalmao + "\n 🥊AÇÕES DO INIMIGO NESSE ROUND🥊\n" + reset);
    
        if (escudo != 0) {
            System.out.println("🛡️ " + nome + " ganhou " + escudo + " de escudo!");
        }
        if (dano != 0) {
            if (escudoRemovido > 0 && vidaRemovida > 0) {
                System.out.println("💥 O ataque de " + nome + " com " + dano + " de dano derrubou a defesa (absorveu " + escudoRemovido + ") e atingiu " + vidaRemovida + " de vida.");
            } else if (escudoRemovido > 0 && vidaRemovida == 0) {
                System.out.println("🛡️ O inimigo atacou com " + dano + ", mas a defesa absorveu tudo!");
            } else {
                System.out.println("💥 " + nome + " acertou em cheio, tirando " + vidaRemovida + " de vida!");
            }
        }
        System.out.println("----------------------------------------");
    }

    public static void printErroEnergia(String nome) {
        System.out.println("🪫 Infelizmente " + nome + " não tem energia suficiente! Digite -1 para voltar a jogada");
    }

    public static void printInvasaoOctogono(String nomeBoss, String nomeIgnorado) {
        Aux.limparTela();
        System.out.println(fundoVermelhoNegrito + "                                                     " + reset);
        System.out.println(fundoVermelhoNegrito + "              ALERTA DE INVASÃO! 🚨                  " + reset);
        System.out.println(fundoVermelhoNegrito + "                                                     " + reset);
        System.out.println("\n" + vermelhoNegrito + "O octógono foi invadido!" + reset);
        Jogo.Aux.esperar(500);
        System.out.println( nomeIgnorado + " não gostou de ficar de fora e apareceu");
        Jogo.Aux.esperar(500);
        System.out.println("💥 " + nomeIgnorado + " pulou a grade e se uniu a " + nomeBoss + "!");
        System.out.println(amarelo + "⚠️ PREPARE-SE PARA UMA LUTA 2 VS 1! ⚠️" + reset);
        Jogo.Aux.esperar(3000);
    }
    public static void printLutaPeloCinturao(String campeao) {
        Aux.limparTela();
        Aux.esperar(200);
        System.out.println("\n" + amarelo + "==========================================================" + reset);
        Aux.esperar(200);
        System.out.println(amarelo + "          🏆  A GRANDE FINAL DO UFC JAVA  🏆" + reset);
        Aux.esperar(200);
        System.out.println(amarelo + "==========================================================" + reset);
        Aux.esperar(200);
        System.out.println("\n" + vermelhoNegrito + "  ATENÇÃO: " + reset + "Você chegou à última luta!");
        Aux.esperar(200);
        System.out.println("  O cinturão peso-pesado do Java está em jogo.");
        Aux.esperar(200);
        System.out.println("\n  No octógono te espera o grande campeão: " + roxo + campeao + reset);
        Aux.esperar(200);
        System.out.println(amarelo + "==========================================================\n" + reset);
        Aux.esperar(7000);
    }
    /**
     * Exibe menu de escolha de efeito.
     */
    public static void menuEfeito() {
        System.out.println("\n" + vermelhoNegrito + "----------------------------------------" + reset);
        System.out.println("         ⚡ " + vermelhoNegrito + "Efeito especial" + reset + " ⚡");
        System.out.println(vermelhoNegrito + "----------------------------------------" + reset);
        System.out.println(" [1] 🩸 Sangramento (Dano contínuo no inimigo)");
        System.out.println(" [2] 🗣️ Provocação  (Reduz escudo do inimigo)");
        System.out.println(" [3] 💉 Adrenalina  (Recupera sua vida)");
        System.out.println(" [4] 😵 Nocaute  (10% de chance de derrubar o inimigo (1v1))");
        System.out.println("                 (20% de chance de derrubar um inimigo (1v2))");
        System.out.println(" [0] ❌ Cancelar");
        System.out.println(vermelhoNegrito + "----------------------------------------" + reset);
        System.out.print("Sua escolha: \n");
    }

    /**
     * Exibe mensagem anunciando o efeito especial aplicado pelo inimigo.
     *
     * @param nomeInimigo nome do inimigo
     * @param tipo codigo do efeito aplicado: <br>
     * 1 - Sangramento <br>
     * 2 - Provocação <br>
     * 3 - Adrenalina
     */
    public static void printEfeitoInimigo(String nomeInimigo, int tipo) {
        System.out.println("\n 🥊Efeito especial de retaliação do inimigo🥊");
        System.out.println("\n💢 " + nomeInimigo.toUpperCase() + " ficou furioso porque você usou um Efeito Especial nele!\n");
        
        switch (tipo) {
            case 1:
                System.out.println("💀 " + nomeInimigo + " revidou com um golpe baixo! [Você recebeu Sangramento]");
                break;
            case 2:
                System.out.println("🎤 " + nomeInimigo + " começou a te xingar! [Sua defesa caiu - Efeito: Provocação]");
                break;
            case 3:
                System.out.println("💉 " + nomeInimigo + " usou uma substância suspeita! [Ele ganhou Adrenalina]");
                break;
        }
        System.out.println("\n----------------------------------------");
    }

    /**
     * Pausa o fluxo ate o usuario digitar o valor esperado.
     *
     * @param inputs leitor de entrada do usuario
     * @param caso 0 exige entrada 0, qualquer outro valor exige -1
     */
    public static void digiteParaContinuar(java.util.Scanner inputs, int caso) {
        if (caso == 0) {
            System.out.println("Digite 0 para continuar a luta");
            int continuar = inputs.nextInt();
            while (continuar != 0) {
            System.out.print("⚠️ Valor inválido! Digite 0 para continuar: ");
            continuar = inputs.nextInt();
        }
        } else {
            System.out.println("Digite -1 para continuar a luta");
            int continuar = inputs.nextInt();
            while (continuar != -1) {
                System.out.print("⚠️ Valor inválido! Digite -1 para voltar: ");
                continuar = inputs.nextInt();
            }
         }
    }
    
    /**
     * Exibe os efeitos ativos no heroi e nos inimigos.
     *
     * @param nomeHeroi nome do heroi
     * @param efeitosHeroi efeitos ativos no heroi
     * @param nomeInimigo nome do inimigo
     * @param efeitosInimigo efeitos ativos no inimigo
     */
    public static void printEfeitosLutadores(String nomeHeroi, ArrayList<Efeitos> efeitosHeroi, String nomeInimigo, ArrayList<Efeitos> efeitosInimigo) {
        System.out.println("-------------------------------------------------");
        System.out.print("🩸 Efeitos agindo em " + nomeHeroi + ": ");

        if (efeitosHeroi.isEmpty()) {  
            System.out.print("Sem ação de efeito! ");
        }
        for (int i = 0; i < efeitosHeroi.size(); i++) {
            System.out.print("[" + efeitosHeroi.get(i).getNome() + " " + efeitosHeroi.get(i).getAcumulos() + "x] ");
        }
    
        System.out.print("\n🩸 Efeitos agindo em " + nomeInimigo + ": ");
        if (efeitosInimigo.isEmpty()) {
             System.out.print("Sem ação de efeito! ");
        }
        for (int i = 0; i < efeitosInimigo.size(); i++) {
            Efeitos efeito = efeitosInimigo.get(i);

            if (efeito instanceof Nocaute nocaute){
                if (nocaute.getNocauteado()){
                    System.out.println("FOI NOCAUTEADO!!!");
                } else {
                    System.out.println("A tentativa de nocaute em " + nomeInimigo + " falhou 😔");
                }
            } else{
                System.out.print("[" + efeitosInimigo.get(i).getNome() + " " + efeitosInimigo.get(i).getAcumulos() + "x] ");
            }
        }
        System.out.println("\n-------------------------------------------------");
    }

    public static String getBarraFuria(int furia) {
        String barra = "";
        int blocos = Math.min(furia, 3); 

        if (blocos == 0) {
            barra = branco + "[ ░░░░░░░░░░░░ ]" + reset; 
        } else if (blocos == 1) {
             barra = amarelo + "[ ████░░░░░░░░ ]" + reset; 
        } else if (blocos == 2) {
            barra = laranja + "[ ████████░░░░ ]" + reset; 
        } else {
            barra = vermelho + "[ ████████████ ] MÁXIMO!" + reset;
        }
        return barra;
    }

    public static void printEfeitoAgindo(String nomeLutador, String nomeEfeito, int acumulos) {
        if (nomeEfeito != "Nocaute"){
            System.out.println("⚡ " + nomeLutador + " está sob efeito de " + nomeEfeito + " (" + acumulos + "x)!");
        }
    }

    public static void printInimigoNocauteado (String nomeIimigo, boolean check){
        if (check){
            System.out.println(fundoVermelhoNegrito);
            System.out.println();
            System.out.println("        😵"+ nomeIimigo + " FOI NOCAUTEADO!😵            ");
            System.out.println();
            System.out.println(reset);
            Jogo.Aux.esperar(1500);
        } else{
            System.out.println();
            System.out.println();
            System.out.println(fundoVermelhoNegrito + "        TENTATIVA DE NOCAUTE EM "+ nomeIimigo + " FALHOU!😢​            " + reset);
            System.out.println();
            System.out.println();
            Jogo.Aux.esperar(1500);
        }
    }

    public static void printEmpate() {
        System.out.println(fundoSalmao + "                                                     " + reset);
        System.out.println(fundoSalmao + "                       EMPATE!                       " + reset);
        System.out.println(fundoSalmao + "                                                     " + reset);
    }

    /**
     * Exibe arte ASCII de derrota com cor e espera.
     */
    public static void printDerrota() {
        try {
            InputStream arquivo = PrintsMain.class.getClassLoader().getResourceAsStream("Derrota.txt");
            if (arquivo == null) {
                throw new FileNotFoundException();
            }
            Scanner leitor = new Scanner(arquivo);
            while (leitor.hasNextLine()) {
                System.out.println(vermelhoNegrito + leitor.nextLine() + reset);
                Jogo.Aux.esperar(120);
            }
            leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("[Arquivo não encontrado");
        }
        Jogo.Aux.esperar(800);
    }

    /**
     * Exibe arte ASCII de vitória com cor e espera.
     */
    public static void printVitoria() {
        try {
            InputStream arquivo = PrintsMain.class.getClassLoader().getResourceAsStream("Vitoria.txt");
            if (arquivo == null) {
                throw new FileNotFoundException();
            }
            Scanner leitor = new Scanner(arquivo);
            while (leitor.hasNextLine()) {
                System.out.println(amarelo + leitor.nextLine() + reset);
                Jogo.Aux.esperar(120);
            }
            leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de arte não encontrado");
        }
        Jogo.Aux.esperar(800);
    }

    public static void printInimigoVenceu(ArrayList<Inimigo> inimigos) {
        Jogo.Aux.limparTela();
        printDerrota();
        Jogo.Aux.esperar(2000);
        
        String nome;
        
        if (inimigos.size() > 1){
            nome = String.format("%s e %s", inimigos.get(0).getNome(), inimigos.get(1).getNome());
        } else{
            nome = inimigos.get(0).getNome();
        }

        int tamanho = 44 + nome.length(); 
        
        String espacos = " ".repeat(tamanho); 
        String mensagem = "  💀 DERROTA... " + nome + " venceu. Tente novamente. 💀  ";

        System.out.println(fundoSalmao + espacos + reset);
        System.out.println(fundoSalmao + mensagem + reset);
        System.out.println(fundoSalmao + espacos + reset);
    }

    public static void printHeroiVenceu(Heroi heroi) {
       //printVitoria();
        String nome = heroi.getNome();
        int tamanho = 49 + nome.length(); 
        
        String espacos = " ".repeat(tamanho);  
        String mensagem = "VITÓRIA! " + nome + " Parabéns, você derrotou o adversário!";

        System.out.println(fundoVerdeClaro + espacos + reset);
        System.out.println(fundoVerdeClaro + mensagem + reset);
        System.out.println(fundoVerdeClaro + espacos + reset);
    }

    public static void printLoja(int ouro) {
        try {
            InputStream arquivo = PrintsMain.class.getClassLoader().getResourceAsStream("Loja.txt");
            if (arquivo == null){
                throw new FileNotFoundException();
            }

            Scanner leitor = new Scanner(arquivo);
            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();
                if (linha.contains("XX")) {
                    linha = linha.replace("XX", "" + ouro);
                }
                System.out.println(linha);
            }
            leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de arte não encontrado");
        }
    }

    public static void printDescobriuCaixa (){
        try{
            InputStream arquivo = PrintsMain.class.getClassLoader().getResourceAsStream("Caixa.txt");
            if (arquivo == null){
                throw new FileNotFoundException();
            }
            Scanner leitor = new Scanner(arquivo);
            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();
                System.out.println(linha);
            }
            leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de arte não encontrado");
        }
    }

    public static void printResultadoCaixa (int resultado){
        try{
            String textoResultado = String.format("CaixaResultado%d.txt", resultado);
            InputStream arquivo = PrintsMain.class.getClassLoader().getResourceAsStream(textoResultado);
            if (arquivo == null){
                throw new FileNotFoundException();
            }
            Scanner leitor = new Scanner(arquivo);
            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();
                System.out.println(linha);
            }
            leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de arte não encontrado");
        }
    }

    /**
     * Exibe menu para selecao de alvo entre inimigos vivos.
     *
     * @param inimigos lista de inimigos disponiveis
     */
    public static void printEscolhaAlvo (ArrayList<Inimigo> inimigos){
        System.out.println("\n" + vermelhoNegrito + "----------------------------------------" + reset);
        System.out.println("          " + vermelhoNegrito + "Escolha seu alvo" + reset + " ");
        System.out.println(vermelhoNegrito + "----------------------------------------" + reset);

        for(int i = 0; i < inimigos.size(); i++){
            if (inimigos.get(i).estaVivo()){
                System.out.println("[" + (i+1) + "]" + " " + inimigos.get(i).getNome() + "\n");
            }
        
        }

        System.out.println(vermelhoNegrito + "----------------------------------------" + reset);
        System.out.print("Sua escolha: \n");
    }

    /**
     * Exibe banner de chegada de novo inimigo.
     */
    public static void printNovoInimigo(){
        System.out.println(fundoSalmao + "                                                     " + reset);
        System.out.println(fundoSalmao + "                  NOVO INIMIGO                       " + reset);
        System.out.println(fundoSalmao + "                                                     " + reset);
    }
}

