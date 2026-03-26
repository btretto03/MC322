package Prints;

public class PrintsEntidades {

    public static void menuEscolhaHeroi() {
        System.out.println("Escolha o seu herói: ");
        System.out.println("[1] 🏆 Alex Poatan\n[2] 🥋 Anderson Silva\n[3] 🥊 Fabrício Werdum");
    }

    public static void menuEscolhaInimigo() {
        System.out.println("Escolha o seu inimigo: ");
        System.out.println("[1] 👻 Vitor Belfort\n[2] 🥊 Popó\n[3] 🦴 Jon Jones");
    }

    public static void printPretensoesInimigo(String nome, int dano, int escudo) {
    System.out.println("\n 🥊PRETENÇÕES DO INIMIGO NESSE ROUND🥊\n");
    
    if (dano > 0) {
        String intensidade;
        if (dano <= 4) {
            intensidade = "um ataque leve";
        } else if (dano <= 9) {
            intensidade = "um ataque forte";
        } else {
            intensidade = "um ataque devastador";
        }
        System.out.println("⚠️ " + nome + " prepara " + intensidade + " causando " + dano + " de dano!");
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

    public static void printAcoesInimigo(String nome, int dano, int escudo, int vidaRemovida, int escudoRemovido) {
        System.out.println("\n 🥊AÇÕES DO INIMIGO NESSE ROUND🥊\n");
    
        if (escudo != 0) {
            System.out.println("🛡️ " + nome + " ganhou " + escudo + " de escudo!");
        }
        if (dano != 0) {
            if (escudoRemovido > 0 && vidaRemovida > 0) {
                System.out.println("💥 O ataque de " + dano + " derrubou a defesa (absorveu " + escudoRemovido + ") e atingiu " + vidaRemovida + " de vida.");
            } else if (escudoRemovido > 0 && vidaRemovida == 0) {
                System.out.println("🛡️ O inimigo atacou com " + dano + ", mas a defesa absorveu tudo!");
            } else {
                System.out.println("💥 " + nome + " acertou em cheio, tirando " + vidaRemovida + " de vida!");
            }
        }
    }
}