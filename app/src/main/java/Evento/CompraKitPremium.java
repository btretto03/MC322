package Evento;

import Entidades.Heroi;

public class CompraKitPremium implements CompraLoja {
    @Override
    public void aplicar(Heroi heroi) {
        heroi.setVida(heroi.getVida() + 20);
    }
}
