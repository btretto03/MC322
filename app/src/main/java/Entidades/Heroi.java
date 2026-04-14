package Entidades;
import java.util.ArrayList;
import Cartas.Carta;
import Prints.PrintsEntidades;

/**
 * Entidade controlada pelo jogador.
 */
public class Heroi extends Entidade {
    private int energia;
    private int furia;
    
    /**
     * Construtor da classe Heroi.
     *
     * @param nome nome do heroi
     * @param vida vida inicial do heroi
     * @param escudo escudo inicial do heroi
     */
    public Heroi(String nome, int vida, int escudo) { //construtor
        super(nome, vida, escudo);
        this.energia = 6;
    }

    /**
     * Exibe menu e retorna o nome do heroi escolhido.
     *
     * @param inputs leitor de entrada do usuario
     * @return nome do heroi selecionado
     */
    public static String escolherHeroi(java.util.Scanner inputs) {
        String escolhaheroi;
        PrintsEntidades.menuEscolhaHeroi();
        int escolha1 = inputs.nextInt();
        switch (escolha1) {
            case 1:
                escolhaheroi = "Alex Poatan";
                break;
            case 2:
                escolhaheroi = "Anderson Silva";
                break;
            case 3:
                escolhaheroi = "Fabrício Werdum";
                break;
                case 4:
                System.out.print("Digite o nome do herói:\n");
                inputs.nextLine();
                escolhaheroi = inputs.nextLine();
                break;
            default:
                System.out.println("⚠️Escolha inválida. O lutador será Alex Poatan por padrão.");
                escolhaheroi = "Alex Poatan";
        }
        return escolhaheroi;
    }

    /**
     * Verifica se existe ao menos uma carta jogavel com a energia atual.
     *
     * @param mao cartas disponiveis na mao
     * @return true quando ha alguma carta com custo compativel
     */
    public boolean verificaMao (ArrayList<Carta> mao){
        int cartasInvalidas = 0;
        for (Carta i : mao){
            if (i.getCusto() > this.getEnergia()){
                cartasInvalidas++;
            }
        }
        if (cartasInvalidas == mao.size()){
            return false;
        } else {
            return true;
        }
    }

    //Getters e Setters
    public int getEnergia() {
        return energia;
    }
    
    public void setEnergia(int energia) {
        this.energia = energia;
    }   

    public void setFuria (int furia){
        if (furia > 3){
            this.furia = 3;
        } else{
            this.furia = furia;
        }

        if (this.furia < 0){
            this.furia = 0;
        }
    }

    public int getFuria(){
        return this.furia;
    }
}
