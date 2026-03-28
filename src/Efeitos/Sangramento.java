package Efeitos;

import Entidades.Entidade;

public class Sangramento extends Efeitos {
    
    public Sangramento(String nome, int acumulos, Entidade dono) {
        super(nome, acumulos, dono);
    }

    @Override
    public void aplicarEfeito() {
        int vidaAtual = dono.getVida();
        int novaVida = vidaAtual - this.acumulos;
        if (novaVida < 0) {
            dono.setVida(0);
        }
        dono.setVida(novaVida);
        this.acumulos--;
    }
    
}
