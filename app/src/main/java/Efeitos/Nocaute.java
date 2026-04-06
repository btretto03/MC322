package Efeitos;

import Prints.PrintsMain;

public class Nocaute extends Efeitos{
    private boolean nocauteado = false;

    public Nocaute(String nome, Entidades.Entidade dono){
        super(nome, 0, dono);
    }

    @Override
    public void aplicarEfeito(){
        int tentativa = (int) Math.random() * 11;

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

    public boolean getNocauteado(){
        return nocauteado;
    }

}
