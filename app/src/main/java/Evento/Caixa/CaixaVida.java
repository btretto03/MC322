package Evento.Caixa;

import Entidades.Heroi;

public class CaixaVida implements AplicarCaixa {
    public void aplicar (Heroi heroi){
        heroi.setVida(heroi.getVida() + 15);
    }
}
