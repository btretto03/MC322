import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import Cartas.CartaDano;
import Entidades.Heroi;

/**
 * Testes das cartas e seus efeitos sobre o heroi.
 */
public class CartasTest {
    private Heroi heroi;

    /**
     * Cria um heroi base para cada cenario.
     */
    @BeforeEach
    void estadoInicial (){
        heroi = new Heroi("teste1", 0, 0, 0);
    }

    /**
     * Agrupa cenarios de uso de CartaDano.
     */
    @Nested
    class CartaDanoTest {
        private CartaDano cartaDano;

        /**
         * Instancia uma carta de dano para os testes.
         */
        @BeforeEach
        void inicializacao (){
            cartaDano = new CartaDano("danoTeste", 0);
        }

        /**
         * Garante que o dano sem escudo reduz apenas vida.
         */
        @Test
        void semEscudo (){
            heroi.setVida(10);
            cartaDano.setCusto(5);

            cartaDano.usar(heroi);

            assertEquals(0, heroi.getVida());
        }

        /**
         * Garante que o escudo absorve dano antes da vida.
         */
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
