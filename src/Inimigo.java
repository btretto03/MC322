import java.util.ArrayList;

public class Inimigo extends Entidade {

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

    public void anuncio(ArrayList<String> acoesRound){
        System.out.println(" 🥊AÇÕES DO INIMIGO NESSE ROUND🥊");
        for(int i = 0; i < acoesRound.size(); i++){
            System.out.println(acoesRound.get(i));
        }
        acoesRound.clear();
    }
  
}
