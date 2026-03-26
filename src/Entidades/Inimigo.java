package Entidades;
import Prints.PrintsEntidades;

public class Inimigo extends Entidade {
    private int dano = 0;

    public Inimigo(String nome, int vida, int escudo) { //construtor
    super(nome, vida, escudo);
    }
    
    public static String escolherInimigo(java.util.Scanner inputs) {
        String escolhainimigo;

        System.out.print("\033[H\033[2J"); //limpar tela
        System.out.flush();

        PrintsEntidades.menuEscolhaInimigo();
        int escolha2 = inputs.nextInt();
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
        PrintsEntidades.printPretensoesInimigo(this.getNome(), this.dano, this.escudo);
    }

    public void atacar (Heroi alvo){
        int vidaAnterior = alvo.getVida();
        int escudoAnterior = alvo.getEscudo();

        alvo.receberDano(dano);

        int vidaRemovida = vidaAnterior - alvo.getVida();
        int escudoRemovido = escudoAnterior - alvo.getEscudo();
        
        PrintsEntidades.printAcoesInimigo(this.getNome(), this.dano, this.escudo, vidaRemovida, escudoRemovido);
        this.dano = 0;
        this.escudo = 0;
    }
  
}