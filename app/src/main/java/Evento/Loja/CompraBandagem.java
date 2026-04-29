package Evento.Loja;

import Entidades.Heroi;

public class CompraBandagem implements CompraLoja {
    @Override
    public void aplicar(Heroi heroi) {
        heroi.setBonusDanoProximaLuta(3);
    }
}
