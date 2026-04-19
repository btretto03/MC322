package Efeitos;

import Prints.PrintsMain;
import java.util.ArrayList;

import Entidades.Inimigo;

/**
 * Efeito especial que tenta nocautear (eliminar) o alvo ao final do round.
 *
 * <p>Quando notificado pelo {@link Jogo.Publisher}, o efeito sorteia uma chance
 * de nocaute e, se bem-sucedido, zera a vida do dono do efeito.</p>
 */
public class Nocaute extends Efeitos{
    private boolean nocauteado = false;
    private ArrayList <Inimigo> inimigos;
    private int controleTeste = 1; //Variável criada para poder realizar teste na aplicação do efeito (0: Não aplica o efeito; -1: Aplica o efeito)

    public Nocaute(String nome, Entidades.Entidade dono, ArrayList <Inimigo> inimigos){
        super(nome, 1, dono);
        this.inimigos = inimigos;
    }

    /**
     * Tenta executar o nocaute e consome o efeito (1 acumulado).
     */
    @Override
    public void aplicarEfeito(){
        if (controleTeste == 0){
            return;
        }

        int probabilidade;

        if (inimigos.size() == 2){
            probabilidade = 6;
        } else {
            probabilidade = 11;
        }

        int tentativa = (int)(Math.random() * probabilidade);

        if (tentativa == 1 || controleTeste == -1){
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

    public void setControleTeste (int valor){
        this.controleTeste = valor;
    }

    public boolean getNocauteado(){
        return nocauteado;
    }

}
