package Prints;
import java.util.ArrayList;

import Cartas.Carta;
import Entidades.Heroi;
import Entidades.Inimigo;

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
    public static void printStatus(String heroi, int vidaheroi, String inimigo, int vidaInimigo) {
        System.out.println(String.format("🟩 %s: ❤️  %d VIDA", heroi, vidaheroi));
        System.out.println(String.format("🟥 %s: ❤️  %d VIDA\n", inimigo, vidaInimigo));
    }

    public static void printEnergiaEMenu(int energia, ArrayList<Carta> mao) {
        System.out.println("\n \n🔋 Energia disponível: " + energia + "/6");
        System.out.println("---------------------------------------");
        System.out.println("Suas opções de ação:");
        for (int i = 0; i < mao.size(); i ++) {
            mao.get(i).printRodada(i);
        }
        System.out.println("Escolha o número da carta ou -1 para passar a vez");
    }

    public static void printEmpate() {
        System.out.println("\u001B[48;5;210m" + "                                                     " + "\u001B[0m");
        System.out.println("\u001B[48;5;210m" + "                       EMPATE!                       " + "\u001B[0m");
        System.out.println("\u001B[48;5;210m" + "                                                     " + "\u001B[0m");
    }

    public static void printErroOpcao() {
        System.out.println("⚠️ Opção inválida! Digite -1 para voltar a jogada");
    }

    public static void printAcoesDoRound(ArrayList<String> acoes, int dano) {
        System.out.println("\n 🥊SUAS AÇÕES NESSE ROUND🥊");
        for (String acao : acoes) System.out.println(acao);
        System.out.println("\n Vida removida do inimigo nesse round: " + dano);
    }

    public static void printErroEnergia(String nome) {
        System.out.println("🪫 Infelizmente " + nome + " não tem energia suficiente! Digite -1 para voltar a jogada");
    }

    public static void printInimigoVenceu(Inimigo inimigo) {
        System.out.println("\u001B[48;5;210m" + "                                                     " + "\u001B[0m");
        System.out.println("\u001B[48;5;210m" + "💀 DERROTA... " + inimigo.getNome() + " venceu. Tente novamente. 💀" + "\u001B[0m");
        System.out.println("\u001B[48;5;210m" + "                                                     " + "\u001B[0m");
    }
    
    public static void printHeroiVenceu(Heroi heroi) {
        System.out.println("\u001B[48;5;193m" + "                                                        " + "\u001B[0m");
        System.out.println("\u001B[48;5;193m" + "🏆 VITÓRIA! " + heroi.getNome() + " Parabéns, você foi o campeão! 🏆" + "\u001B[48;5;193m");
        System.out.println("\u001B[48;5;193m" + "                                                        " + "\u001B[0m");
    }
}

