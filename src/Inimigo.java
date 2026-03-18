public class Inimigo extends Entidade {
    private int dano = 0;

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

    public void anuncio() {
        System.out.println();
        System.out.println(" 🥊PRETENÇÕES DO INIMIGO NESSE ROUND🥊");
        System.out.println();
        for (int i = 0; i < 2; i++){   
            int acao = (int) (Math.random() * 2);

            if (acao == 0) {
                this.dano += (int) (Math.random() * 4) + 2;
                System.out.println(this.getNome() + " pretende atacar causando " + this.dano + " de dano!");
                
            } else {
                this.escudo += (int) (Math.random() * 3) + 2;
                System.out.println(this.getNome() + " pretende se defender com " + this.escudo + " de escudo!"); 
            }
        }

        System.out.println();
    }

    public void atacar (Heroi alvo){
        System.out.println();
        System.out.println(" 🥊AÇÕES DO INIMIGO NESSE ROUND🥊");
        System.out.println();
        
        if (this.dano != 0){
            int vidaAnterior = alvo.getVida();
            alvo.receberDano(dano);
            int vidaAtual = alvo.getVida();

            int vidaRemovida = vidaAnterior - vidaAtual;

            System.out.println("💥 " + this.getNome() + " conseguiu atacar, tirando " + vidaRemovida + " de vida do herói!");
        }

        if (this.escudo != 0){
            System.out.println("🛡️ " + this.getNome() + " ganhou " + this.escudo + " de escudo!");
        }

        this.dano = 0;
        this.escudo = 0;
    }
  
}
