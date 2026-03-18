public class Inimigo extends Entidade {


    public Inimigo(String nome, int vida, int escudo) { //construtor
    super(nome, vida, escudo);

}
    
    public static String escolherInimigo() {
    String escolhainimigo;
    App.limparTela();
    System.out.println("Escolha o seu inimigo: "); //Escolha do inimigo
        System.out.println("[1] 👻 Vitor Belfort\n[2] 🥊 Popó\n[3] 🦴 Jon Jones");
        int escolha2 = App.inputs.nextInt();
        switch (escolha2) {
            case 1:
                escolhainimigo = "Vitor Belfort";
                break;
            case 2:
                escolhainimigo = "Popó";
                break;
            case 3:
                escolhainimigo = "Jon Jones";
                break;
            default:
                System.out.println("⚠️Escolha inválida. O lutador será Popó por padrão.");
                escolhainimigo = "Popó";
        }
        return escolhainimigo;
    }

    public static void printInimigoVenceu(Inimigo inimigo) {
        System.out.println("\u001B[48;5;210m" + "                                                     " + "\u001B[0m");
        System.out.println("\u001B[48;5;210m" + "💀 DERROTA... " + inimigo.getNome() + " venceu. Tente novamente. 💀" + "\u001B[0m");
        System.out.println("\u001B[48;5;210m" + "                                                     " + "\u001B[0m");
    }

    public void anuncio(Heroi heroi) {
    System.out.println();
    System.out.println(" 🥊AÇÕES DO INIMIGO NESSE ROUND🥊");
    int acao = (int) (Math.random() * 2);

    if (acao == 0) {
        int dano = (int) (Math.random() * 5) + 5;
        System.out.println(this.getNome() + " pretende atacar causando " + dano + " de dano!");
        int vidaAnterior = heroi.getVida();
        heroi.receberDano((int) (Math.random() * 5) + 5);
        int vidaAtual = heroi.getVida();

        int vidaRemovida = vidaAnterior - vidaAtual;

        System.out.println("💥 " + this.getNome() + " conseguiu atacar, tirando " + vidaRemovida + " de vida do herói!");

    } else {
        int escudoGanho = (int) (Math.random() * 3) + 3;
        System.out.println(this.getNome() + " pretende se defender!");
        System.out.println("🛡️ " + this.getNome() + " ganhou " + escudoGanho + " de escudo!");
    }
}
  
}
