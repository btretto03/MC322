package Efeitos;

import Prints.PrintsMain;
import java.util.ArrayList;
import java.util.Random;

import Entidades.Inimigo;

/**
 * Efeito especial que tenta nocautear (eliminar) o alvo ao final do round.
 *
 * <p>Quando notificado pelo {@link Jogo.Publisher}, o efeito sorteia uma chance
 * de nocaute e, se bem-sucedido, zera a vida do dono do efeito.</p>
 */
public class Nocaute extends Efeitos{
    private boolean nocauteado = false;
    private Random geradorAleatorio = new Random();
    private ArrayList <Inimigo> inimigos;

    public Nocaute(String nome, Entidades.Entidade dono, ArrayList <Inimigo> inimigos){
        super(nome, 1, dono);
        this.inimigos = inimigos;
    }

    /**
     * Tenta executar o nocaute e consome o efeito (1 acumulado).
     */
    @Override
    public void aplicarEfeito(){
        int probabilidade;

        if (inimigos.size() == 2){
            probabilidade = 6;
        } else {
            probabilidade = 11;
        }

        int tentativa = geradorAleatorio.nextInt(probabilidade);

        if (tentativa == 1){
            dono.setVida(0);
            setNocauteado(true);
            PrintsMain.printInimigoNocauteado(dono.getNome(), getNocauteado()); 
        } else{
            PrintsMain.printInimigoNocauteado(dono.getNome(), getNocauteado()); 
            setNocauteado(false);
        }

        this.setAcumulos(getAcumulos() - 1);
    }

    public void setNocauteado (boolean valor){
        this.nocauteado = valor;
    }

    public void setInimigos (ArrayList<Inimigo> inimigos){
        this.inimigos = inimigos;
    }

    public void setGeradorAleatorio (Random geradorAleatorio){
        this.geradorAleatorio = geradorAleatorio;
    }

    public boolean getNocauteado(){
        return nocauteado;
    }

}
