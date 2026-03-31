package Prints;
import java.util.ArrayList;

import Cartas.Carta;
import Entidades.Heroi;
import Entidades.Inimigo;
import Efeitos.Efeitos;

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

    public static void printNovoRound(int contadorRound) {
        System.out.println("\u001B[48;5;210m" + "                                        " + "\u001B[0m");
        System.out.println("\u001B[48;5;210m" + "               ROUND " + contadorRound + "                  " + "\u001B[0m");
        System.out.println("\u001B[48;5;210m" + "                                        " + "\u001B[0m");
    }

    public static void printFimEnergia() {
        System.out.println("\n-------------------------------------------------");
        System.out.println("\n🪫 Energia insuficiente para continuar. Rodada finalizada.\n");
        System.out.println("-------------------------------------------------");
    }
    public static void printStatus(String heroi, int vidaheroi, ArrayList<Inimigo> inimigos) {
        System.out.println(String.format("🟩 %s: ❤️  %d VIDA", heroi, vidaheroi));
        inimigos.stream().filter(inimigo -> inimigo.estaVivo()).forEach(i -> System.out.println(String.format("🟥 %s: ❤️  %d VIDA", i.getNome(), i.getVida())));
        inimigos.stream().filter(inimigo -> !inimigo.estaVivo()).forEach(i -> System.out.println(String.format("🟥 %s: 💀  FOI NOCAUTEADO", i.getNome())));

    }

    public static void printEnergiaEMenu(int energia, ArrayList<Carta> mao, int furia) {
        System.out.println("\n---------------------------------------");
        System.out.println("🔋 Energia: " + energia + "/6   🔥 Fúria: " + getBarraFuria(furia));
        
        System.out.println("\nSuas opções:");
        for (int i = 0; i < mao.size(); i ++) {
            mao.get(i).printRodada(i);
        }
        
        if (furia >= 3) {
            System.out.println("\n\u001B[41;1m [99] ⚡ Liberar efeito especial \u001B[0m");
        }
        
        System.out.print("\nEscolha uma carta ou -1 para passar: ");
    }

    public static void printErroOpcao() {
        System.out.println("⚠️ Opção inválida! Digite -1 para voltar a jogada");
    }

    public static void printAcoesDoRound(ArrayList<String> acoes, ArrayList<Inimigo> inimigos, int[] vidaInimigosInicio) {
        System.out.println("\n 🥊SUAS AÇÕES NESSE ROUND🥊 \n");
        for (String acao : acoes) System.out.println(acao);
        for (int i = 0; i < inimigos.size(); i++){
            System.out.println("\n 🩸 Vida removida do " + inimigos.get(i).getNome() + " nesse round: " + (vidaInimigosInicio[i] - inimigos.get(i).getVida()));
        }
        System.out.println("----------------------------------------");
    }

    public static void printErroEnergia(String nome) {
        System.out.println("🪫 Infelizmente " + nome + " não tem energia suficiente! Digite -1 para voltar a jogada");
    }

    public static void menuEfeito() {
        System.out.println("\n\u001B[31;1m" + "----------------------------------------" + "\u001B[0m");
        System.out.println("         ⚡ \u001B[31;1mEfeito especial\u001B[0m ⚡");
        System.out.println("\u001B[31;1m" + "----------------------------------------" + "\u001B[0m");
        System.out.println(" [1] 🩸 Sangramento (Dano contínuo no inimigo)");
        System.out.println(" [2] 🗣️ Provocação  (Reduz escudo do inimigo)");
        System.out.println(" [3] 💉 Adrenalina  (Recupera sua vida)");
        System.out.println(" [0] ❌ Cancelar");
        System.out.println("\u001B[31;1m" + "----------------------------------------" + "\u001B[0m");
        System.out.print("Sua escolha: \n");
    }

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
            System.out.print("[" + efeitosInimigo.get(i).getNome() + " " + efeitosInimigo.get(i).getAcumulos() + "x] ");
        }
        System.out.println("\n-------------------------------------------------");
    }

    public static String getBarraFuria(int furia) {
        String barra = "";
        int blocos = Math.min(furia, 3); 

        if (blocos == 0) {
            barra = "\u001B[37m[ ░░░░░░░░░░░░ ]\u001B[0m"; 
        } else if (blocos == 1) {
             barra = "\u001B[33m[ ████░░░░░░░░ ]\u001B[0m"; 
        } else if (blocos == 2) {
            barra = "\u001B[38;5;208m[ ████████░░░░ ]\u001B[0m"; 
        } else {
            barra = "\u001B[31m[ ████████████ ] MAX!\u001B[0m";
        }
        return barra;
    }

    public static void printEfeitoAgindo(String nomeLutador, String nomeEfeito, int acumulos) {
        System.out.println("⚡ " + nomeLutador + " está sob efeito de " + nomeEfeito + " (" + acumulos + "x)!");
    }

    public static void printEmpate() {
        System.out.println("\u001B[48;5;210m" + "                                                     " + "\u001B[0m");
        System.out.println("\u001B[48;5;210m" + "                       EMPATE!                       " + "\u001B[0m");
        System.out.println("\u001B[48;5;210m" + "                                                     " + "\u001B[0m");
    }

    public static void printInimigoVenceu(ArrayList<Inimigo> inimigos) {
        String nome;
        
        if (inimigos.size() > 1){
            nome = String.format("%s e %s", inimigos.get(0).getNome(), inimigos.get(1).getNome());
        } else{
            nome = inimigos.get(0).getNome();
        }

        int tamanho = 44 + nome.length(); 
        
        String espacos = " ".repeat(tamanho); 
        String mensagem = "  💀 DERROTA... " + nome + " venceu. Tente novamente. 💀  ";

        System.out.println("\u001B[48;5;210m" + espacos + "\u001B[0m");
        System.out.println("\u001B[48;5;210m" + mensagem + "\u001B[0m");
        System.out.println("\u001B[48;5;210m" + espacos + "\u001B[0m");
    }

    public static void printHeroiVenceu(Heroi heroi) {
        String nome = heroi.getNome();
        int tamanho = 49 + nome.length(); 
        
        String espacos = " ".repeat(tamanho); 
        String mensagem = "  🏆 VITÓRIA! " + nome + " Parabéns, você foi o campeão! 🏆  ";

        System.out.println("\u001B[48;5;193m" + espacos + "\u001B[0m");
        System.out.println("\u001B[48;5;193m" + mensagem + "\u001B[0m");
        System.out.println("\u001B[48;5;193m" + espacos + "\u001B[0m");
    }

    public static void printEscolhaAlvo (ArrayList<Inimigo> inimigos){
        System.out.println("\n\u001B[31;1m" + "----------------------------------------" + "\u001B[0m");
        System.out.println("          \u001B[31;1mEscolha seu alvo\u001B[0m ");
        System.out.println("\u001B[31;1m" + "----------------------------------------" + "\u001B[0m");

        for(int i = 0; i < inimigos.size(); i++){
            if (inimigos.get(i).estaVivo()){
                System.out.println("[" + (i+1) + "]" + " " + inimigos.get(i).getNome() + "\n");
            }
        
        }

        System.out.println("\u001B[31;1m" + "----------------------------------------" + "\u001B[0m");
        System.out.print("Sua escolha: \n");
    }

    public static void printNovoInimigo(){
        System.out.println("\u001B[48;5;210m" + "                                                     " + "\u001B[0m");
        System.out.println("\u001B[48;5;210m" + "                  NOVO INIMIGO                       " + "\u001B[0m");
        System.out.println("\u001B[48;5;210m" + "                                                     " + "\u001B[0m");
    }
}

