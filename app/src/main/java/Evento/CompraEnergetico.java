package Evento;

import Entidades.Heroi;

public class CompraEnergetico implements CompraLoja {
    @Override
    public void aplicar(Heroi heroi) {
        heroi.setVida(heroi.getVida() + 10);
    }
}
