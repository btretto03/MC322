package Prints;

public class PrintsMain {
    public static void printInicial() {
        System.out.println("\u001B[48;5;210m" + "                                                  " + "\u001B[0m");
        System.out.println("\u001B[48;5;210m" + "   🥊 ULTIMATE FIGHTING JAVA CHAMPIONSHIP 🥊      " + "\u001B[0m");
        System.out.println("\u001B[48;5;210m" + "                                                  " + "\u001B[0m");

    }
    public static void printInicioluta() {
        System.out.println("🔥 A LUTA VAI COMEÇAR! 🔥\n");

    }
    public static void printBaralhoVazio() {
        System.out.println("🔄 Baralho vazio! Voltando o descarte para a pilha");

    }
    public static void printNovoRound() {
        System.out.println("\u001B[48;5;210m" + "                                        " + "\u001B[0m");
        System.out.println("\u001B[48;5;210m" + "               NOVO ROUND               " + "\u001B[0m");
        System.out.println("\u001B[48;5;210m" + "                                        " + "\u001B[0m");
    }
    public static void printFimEnergia() {
        System.out.println("-------------------------------------------------");
        System.out.println("\n🪫 Energia insuficiente para continuar. Rodada finalizada.\n");
        System.out.println("-------------------------------------------------");
        System.out.println("⚠️ Digite 0 para continuar \n \n"); 
    }
    public static void printEmpate() {
        System.out.println("\u001B[48;5;210m" + "                                                     " + "\u001B[0m");
        System.out.println("\u001B[48;5;210m" + "                       EMPATE!                       " + "\u001B[0m");
        System.out.println("\u001B[48;5;210m" + "                                                     " + "\u001B[0m");
    }
    public static void printInimigoVenceu(Inimigo inimigo) {
        System.out.println("\u001B[48;5;210m" + "                                                     " + "\u001B[0m");
        System.out.println("\u001B[48;5;210m" + "💀 DERROTA... " + inimigo.getNome() + " venceu. Tente novamente. 💀" + "\u001B[0m");
        System.out.println("\u001B[48;5;210m" + "                                                     " + "\u001B[0m");
    }
}
