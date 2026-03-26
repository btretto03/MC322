import Entidades.Entidade;

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

public void anuncio(Heroi alvo) {
        System.out.println();
        System.out.println(" 🥊PRETENÇÕES DO INIMIGO NESSE ROUND🥊");
        System.out.println();
        int acao;
        for (int i = 0; i < 2; i++){   
            if (alvo.getVida() <= 10 || alvo.getVida() > 20){ //prioriza ataque se o herói tiver pouca vida
                acao = 0;
            } else if (this.getVida() <= 5) {  //prioriza defesa se o inimigo tiver pouca vida
                acao = 1;
            } else {
                acao = (int) (Math.random() * 2);
            }

            if (acao == 0) { //ataque
                this.dano += (int) (Math.random() * 4) + 4;
            } else {
                this.escudo += (int) (Math.random() * 4) + 3;
            }
        }

        if (this.dano > 0) {
            String intensidade;
            if (this.dano <= 4){
                intensidade = "um ataque leve";
            } else if (this.dano <= 9){
                intensidade = "um ataque forte";
            } else {
                intensidade = "um ataque devastador";
            }
            System.out.println("⚠️ " + this.getNome() + " prepara " + intensidade + " causando " + this.dano + " de dano!");
        }

        if (this.escudo > 0) {
            String intensidade;
            if(this.escudo <= 5){
                intensidade = "uma guarda simples";
            } else if (this.escudo <= 8){
                intensidade = "uma boa defesa";
            } else {
                intensidade = "uma guarda impenetrável";
            }
            System.out.println("🛡️ " + this.getNome() + " levanta " + intensidade + " com " + this.escudo + " de escudo!");
        }

        System.out.println();
    }

    public void atacar (Heroi alvo){
        System.out.println();
        System.out.println(" 🥊AÇÕES DO INIMIGO NESSE ROUND🥊");
        System.out.println();
        
        if (this.escudo != 0){
            System.out.println("🛡️ " + this.getNome() + " ganhou " + this.escudo + " de escudo!");
        }
        if (this.dano != 0){
            int vidaAnterior = alvo.getVida();
            int escudoAnterior = alvo.getEscudo();
            alvo.receberDano(dano);
            int vidaAtual = alvo.getVida();
            int escudoAtual = alvo.getEscudo();
            int vidaRemovida = vidaAnterior - vidaAtual;
            int escudoRemovido = escudoAnterior - escudoAtual;

            if (escudoRemovido > 0 && vidaRemovida > 0) {
                System.out.println("💥 O ataque de " + this.dano + " derrubou a defesa do herói (absorveu " + escudoRemovido + ") e atingiu " + vidaRemovida + " de dano para a vida.");
            } else if (escudoRemovido > 0 && vidaRemovida == 0) {
                System.out.println("🛡️ O inimigo atacou causando " + this.dano + " de dano, mas a defesa absorveu tudo!");
            } else {
                System.out.println("💥 " + this.getNome() + " acertou em cheio, tirando " + vidaRemovida + " de vida do herói!");
            }
        }

        this.dano = 0;
        this.escudo = 0;
    }
  
}