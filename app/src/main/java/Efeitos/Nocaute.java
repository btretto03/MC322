package Efeitos;

import Prints.PrintsMain;
import java.util.ArrayList;

import Entidades.Inimigo;

/**
 * Efeito especial que tenta nocautear (eliminar) o alvo ao final do round.
 *
 * <p>Quando notificado pelo {@link Jogo.Publisher}, o efeito sorteia uma chance
 * de nocaute e, se bem-sucedido, zera a vida do dono do efeito. A probabilidade
 * depende do modo (1v1 ou 1v2) e o resultado e exibido no terminal.</p>
 */
public class Nocaute extends Efeitos{
    private boolean nocauteado = false;
    private ArrayList <Inimigo> inimigos;

    /**
     * Cria o efeito de nocaute.
     *
     * @param nome nome do efeito (ex.: "Nocaute")
     * @param dono entidade alvo do efeito
     * @param inimigos lista de inimigos da luta (define o modo 1v1/1v2)
     */
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

        int tentativa = (int) Math.random() * probabilidade;

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

    public boolean getNocauteado(){
        return nocauteado;
    }

}
