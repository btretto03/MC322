package Efeitos;

import Entidades.Entidade;

/**
 * Efeito que desgasta o escudo do alvo a cada round.
 */
public class Provocacao extends Efeitos {
    
    public Provocacao(String nome, int acumulos, Entidade dono) {
        super(nome, acumulos, dono);
    }

    /**
     * Remove 1 ponto de escudo do alvo por round enquanto houver acumulacoes.
     */
    @Override
    public void aplicarEfeito() { //A provocacao vai distrair o adversario e reduzir seu escudo em 1 a cada turno
        int escudoAtual = dono.getEscudo();
        if (escudoAtual > 0) {
            dono.ganharEscudo(-1);
        }
        this.acumulos--;
    }
        
}
