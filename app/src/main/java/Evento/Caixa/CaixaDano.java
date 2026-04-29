package Evento.Caixa;

import Entidades.Heroi;

public class CaixaDano implements AplicarCaixa {
    public void aplicar (Heroi heroi){
        heroi.setVida(heroi.getVida() - 15);
    }
}
