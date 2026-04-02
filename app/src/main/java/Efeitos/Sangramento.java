package Efeitos;

import Entidades.Entidade;

/**
 * Efeito de dano continuo (a cada round) que reduz vida do alvo por acumulacao.
 */
public class Sangramento extends Efeitos {

    public Sangramento(String nome, int acumulos, Entidade dono) {
        super(nome, acumulos, dono);
    }

    /**
     * Aplica dano periodico e reduz acumulacao do efeito.
     */
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
