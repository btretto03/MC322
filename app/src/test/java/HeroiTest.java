import org.junit.jupiter.api.Test;

import Entidades.Heroi;
import Cartas.Carta;
import Cartas.CartaDano;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Testes unitarios de comportamentos da entidade Heroi.
 */
public class HeroiTest {
    /**
     * Valida o mapeamento da entrada para escolha de heroi.
     */
    @Test
    void escolherHeroiTest (){
        for (int i = 1; i < 4; i++){
            String inputSimulado = "" + i + "\n";
            ByteArrayInputStream in = new ByteArrayInputStream(inputSimulado.getBytes());

            Scanner inputs = new Scanner(in);

            if (i == 1){
                assertEquals("Alex Poatan", Heroi.escolherHeroi(inputs));
            } else if (i == 2){
                assertEquals("Anderson Silva", Heroi.escolherHeroi(inputs));
            } else if (i == 3){
                assertEquals("Fabrício Werdum", Heroi.escolherHeroi(inputs));
            } 

            inputs.close();
        }

    }

    /**
     * Verifica que mao com energia suficiente e valida.
     */
    @Test
    void verificaMao_valida_Test (){
        Heroi heroi = new Heroi("Teste", 1, 1, 1);
        heroi.setEnergia(10);
        ArrayList<Carta> mao = new ArrayList<>();

        Carta carta = new CartaDano("carta teste", 1);
        mao.add(carta);

        assertTrue(heroi.verificaMao(mao));
    }

    /**
     * Verifica que mao sem energia suficiente e invalida.
     */
    @Test
    void verificaMao_invalida_Test (){
        Heroi heroi = new Heroi("Teste", 1, 1, 1);
        heroi.setEnergia(0);
        ArrayList<Carta> mao = new ArrayList<>();

        Carta carta = new CartaDano("carta teste", 1);
        mao.add(carta);

        assertFalse(heroi.verificaMao(mao));
    }
}
