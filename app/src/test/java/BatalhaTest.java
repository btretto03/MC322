import java.lang.reflect.Array;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import Cartas.*;
import Entidades.Heroi;

public class BatalhaTest {
    private Heroi heroi;

    @BeforeEach
    void estadoInicial (){
        heroi = new Heroi("teste1", 0, 0, 0);
    }
    
    @Nested
    class aplicarCartaTest {

        @Test
        void cartaDano (){
            CartaDano carta = new CartaDano(null, 1);
            heroi.setVida(10);
            ArrayList<String> vetorFalso = new ArrayList<>();
            int vidaInicial = heroi.getVida();

        }
    }
}
