public class Inimigo extends Entidade {
    private int forca = 0;
    
    public Inimigo(String nome, int vida, int escudo) { //construtor
        super(nome, vida, escudo);
    }
    
    public static String escolherInimigo() {
    String escolhainimigo;
    System.out.println("Escolha o seu inimigo: "); //Escolha do inimigo
        System.out.println("[1] 👻 Vitor Belfort\n[2] 🥊 Popó\n[3] 🦴 Jon Jones");
        int escolha2 = App.inputs.nextInt();
        App.limparTela();
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

    //Métodos
    public void atacar(Heroi atacado) {
        this.forca = (int) (Math.random() * 5) + 6; //numero aleatorio para o ataque no range de 6 até 10
        atacado.receberDano(this.forca);
    }    
}
