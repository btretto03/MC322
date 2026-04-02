package Prints;

public class PrintsEntidades {
private static String reset = "\u001B[0m";
    //private static String amarelo = "\u001B[33m";
    private static String ciano = "\u001B[36m";
    private static String branco = "\u001B[37m";

    public static void menuEscolhaHeroi() {
        System.out.println("        🔥 SELECIONE SEU LUTADOR 🔥      ");
  
        
        System.out.println("\nCOMO JOGAR:" + reset);
        System.out.println(ciano + " • Use cartas de Dano para atacar e Escudo para se defender.");
        System.out.println(" • Gerencie sua Energia, cada round você terá 6 de energia.");
        System.out.println(" • Use golpes de Dano para carregar sua barra de FÚRIA.");
        System.out.println(" • Com 3 de Fúria, você libera efeitos especiais potentes." + reset);
        System.out.println("\n MODOS DISPONIVEIS:");
        System.out.println(ciano + " • 1v1 - Herói e inimigo com 50 de vida cada.");
        System.out.println(" • 1v2 - Herói com 100 de vida e inimigos com 25 cada." + reset);
        
        System.out.println("\n----------------------------------------" + reset);
        System.out.println("ESCOLHA SEU HERÓI:" + reset);
        System.out.println(" [1] 🏆 Alex Poatan (O 'Mãos de Pedra')" + reset);
        System.out.println( " [2] 🥋 Anderson Silva( O 'Spider')" + reset);
        System.out.println(" [3] 🥊 Fabrício Werdum ( O 'Vai Cavalo')" + reset);
        System.out.println(branco +  " [4] ⌨️ Digite seu próprio nome");
        System.out.println("----------------------------------------" + reset);
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

}

