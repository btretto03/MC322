package Prints;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import Jogo.Aux;

/**
 * Funcoes de impressao relacionadas a escolha e status de entidades.
 */
public class PrintsEntidades {
private static String reset = "\u001B[0m";
    private static String amarelo = "\u001B[33m";
    private static String ciano = "\u001B[36m";
    private static String branco = "\u001B[37m";
    private static String laranja = "\u001B[38;5;208m";

    public static void menuEscolhaHeroi() {
        System.out.println("\nCOMO JOGAR:" + reset);
        System.out.println(ciano + " • Use cartas de Dano para atacar e Escudo para se defender.");
        Aux.esperar(200);
        System.out.println(" • Gerencie sua Energia, cada round você terá 6 de energia.");
        Aux.esperar(200);
        System.out.println(" • Use golpes de Dano para carregar sua barra de FÚRIA.");
        Aux.esperar(200);
        System.out.println(" • Com 3 de Fúria, você libera efeitos especiais potentes." + reset);
        Aux.esperar(200);
        System.out.println("\n========================================");
        System.out.println("      🏆  REGRAS DO TORNEIO 🏆          ");
        System.out.println("========================================");
        Aux.esperar(200);
        System.out.println("   Você avançará pelos caminhos da árvore de um torneio.");
        Aux.esperar(200);
        System.out.println("   Sua vida e o seu baralho são mantidos entre as lutas!");
        Aux.esperar(200);
        System.out.println("   Efeitos, Energia e Fúria são resetados a cada novo oponente.");
        Aux.esperar(200);
        System.out.println("----------------------------------------\n");
        Aux.esperar(5000);
        Aux.limparTela();

        System.out.println("--------🔥 SELECIONE SEU LUTADOR 🔥--------");
        System.out.println(" [1] 🏆 Alex Poatan (O 'Mãos de Pedra')" );
        System.out.println( " [2] 🥋 Anderson Silva( O 'Spider')");
        System.out.println(" [3] 🥊 Fabrício Werdum ( O 'Vai Cavalo')");
        System.out.println(branco +  " [4] ⌨️ Digite seu próprio nome");
        System.out.println("----------------------------------------");
        System.out.print("Sua escolha: ");
    }

    public static void menuEscolhaInimigo() {
        System.out.println("\nEscolha o seu inimigo: ");
        System.out.println("[1] 👻 Vitor Belfort\n[2] 🥊 Popó\n[3] 🦴 Jon Jones\n[4] ⌨️ Quero digitar o nome");
    }

    public static void printPretensoesInimigo(String nome, int dano, int escudo) {
    System.out.println("\n 🥊PRETENÇÕES DO " + nome + " NESSE ROUND🥊\n");
    
    if (dano > 0) {
        String intensidade;
        if (dano <= 4) {
            intensidade = "um ataque leve";
        } else if (dano <= 9) {
            intensidade = "um ataque forte";
        } else {
            intensidade = "um ataque devastador";
        }
        System.out.println("⚠️ " + nome + " prepara " + intensidade + " causando " + dano + " de dano!\n");
    }
    
    if (escudo > 0) {
        String intensidade;
        if (escudo <= 5) {
            intensidade = "uma guarda simples";
        } else if (escudo <= 8) {
            intensidade = "uma boa defesa";
        } else {
            intensidade = "uma guarda impenetrável";
        }
        System.out.println("🛡️ " + nome + " levanta " + intensidade + " com " + escudo + " de escudo!\n");
        }
    }

    /**
     * Imprime a arte ASCII do herói.
     */
    public static void printArteHeroi() {
        try {
            InputStream arquivo = PrintsEntidades.class.getClassLoader().getResourceAsStream("Heroi.txt");
            if (arquivo == null) {
                throw new FileNotFoundException();
            }
            Scanner leitor = new Scanner(arquivo);
            
            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();
                System.out.println(amarelo + linha + reset);
                Jogo.Aux.esperar(150);
            }
            leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo Heroi.txt não encontrado");
        }
        Jogo.Aux.esperar(1500);
    }

    /**
     * Imprime a arte ASCII do inimigo.
     */
    public static void printArteInimigo() {
        try {
            InputStream arquivo = PrintsEntidades.class.getClassLoader().getResourceAsStream("Inimigo.txt");
            if (arquivo == null) {
                throw new FileNotFoundException();
            }
            Scanner leitor = new Scanner(arquivo);
            
            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();
                System.out.println(laranja + linha + reset);
                Jogo.Aux.esperar(150);
            }
            leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo Inimigo.txt não encontrado");
        }
        Jogo.Aux.esperar(1500);
    }

    /**
     * Imprime a arte ASCII do segundo inimigo.
     */
    public static void printArteInimigo2() {
        try {
            InputStream arquivo = PrintsEntidades.class.getClassLoader().getResourceAsStream("Inimigo2.txt");
            if (arquivo == null) {
                throw new FileNotFoundException();
            }
            Scanner leitor = new Scanner(arquivo);
            
            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();
                System.out.println(laranja + linha + reset);
                Jogo.Aux.esperar(150);
            }
            leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo Inimigo2.txt não encontrado");
        }
        Jogo.Aux.esperar(1500);
    }

}

