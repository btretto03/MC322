import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import Cartas.CartaDano;
import Entidades.Heroi;

public class CartasTest {
    private Heroi heroi;

    @BeforeEach
    void estadoInicial (){
        heroi = new Heroi("teste1", 0, 0, 0);
    }

    @Nested
    class CartaDanoTest {
        private CartaDano cartaDano;
        @BeforeEach
        void inicializacao (){
            cartaDano = new CartaDano("danoTeste", 0);
        }

        @Test
        void semEscudo (){
            heroi.setVida(10);
            cartaDano.setCusto(5);

            cartaDano.usar(heroi);

            assertEquals(0, heroi.getVida());
        }

        @Test
        void comEscudo (){
            heroi.setVida(10);
            heroi.setEscudo(5);
            cartaDano.setCusto(5);

            cartaDano.usar(heroi);

            assertEquals(5, heroi.getVida());
            assertEquals(0, heroi.getEscudo());
        }
    }
}
