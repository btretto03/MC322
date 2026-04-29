package Evento.Loja;

import Entidades.Heroi;

public class CompraProtetor implements CompraLoja {
    @Override
    public void aplicar(Heroi heroi) {
        heroi.setEscudoInicioProximaLuta(15);
    }
}
