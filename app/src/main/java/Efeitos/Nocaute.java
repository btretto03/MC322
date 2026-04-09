package Efeitos;

import Prints.PrintsMain;
import java.util.ArrayList;

import Entidades.Inimigo;

public class Nocaute extends Efeitos{
    private boolean nocauteado = false;
    private ArrayList <Inimigo> inimigos;

    public Nocaute(String nome, Entidades.Entidade dono, ArrayList <Inimigo> inimigos){
        super(nome, 0, dono);
        this.inimigos = inimigos;
    }

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
            setNocauteado(false);
        }
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
