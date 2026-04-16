import org.junit.jupiter.api.Test;

import Entidades.Heroi;
import Cartas.Carta;
import Cartas.CartaDano;
import Entidades.Inimigo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class AuxTest {
    
    @Test
    void escolherAlvo_2vivos_Test(){
        ArrayList<Inimigo> inimigos = new ArrayList<>();
        Inimigo inimigoTeste1 = new Inimigo("um", 1, 0);
        Inimigo inimigoTeste2 = new Inimigo("dois", 1, 0);

        inimigos.add(inimigoTeste1);
        inimigos.add(inimigoTeste2);

        for (int i = 1; i < 3; i++){
            String inputSimulado = "" + i + "\n";
            ByteArrayInputStream in = new ByteArrayInputStream(inputSimulado.getBytes());

            Scanner inputs = new Scanner(in);

            if (i == 1){
                assertEquals(inimigoTeste1, Jogo.Aux.escolherAlvo(inimigos, inputs));
            } else if (i == 2){
                assertEquals(inimigoTeste2, Jogo.Aux.escolherAlvo(inimigos, inputs));
            }

            inputs.close();
        }
    }

    @Test
    void escolherAlvo_1vivo_Test(){
        ArrayList<Inimigo> inimigos = new ArrayList<>();
        Inimigo inimigoTeste1 = new Inimigo("um", 1, 0);

        inimigos.add(inimigoTeste1);

        for (int i = 1; i < 3; i++){
            String inputSimulado = "" + i + "\n";
            ByteArrayInputStream in = new ByteArrayInputStream(inputSimulado.getBytes());

            Scanner inputs = new Scanner(in);

            if (i == 1){
                assertEquals(inimigoTeste1, Jogo.Aux.escolherAlvo(inimigos, inputs));
            } else if (i == 2){
                assertEquals(inimigoTeste1, Jogo.Aux.escolherAlvo(inimigos, inputs));
            }

            inputs.close();
        }
    } 

    @Test
    void inimigosVivos_2vivos_Test (){
        ArrayList<Inimigo> inimigos = new ArrayList<>();
        Inimigo inimigoTeste1 = new Inimigo("um", 1, 0);
        Inimigo inimigoTeste2 = new Inimigo("dois", 1, 0);

        inimigos.add(inimigoTeste1);
        inimigos.add(inimigoTeste2);

        assertTrue(Jogo.Aux.inimigosVivos(inimigos));
    }

    @Test
    void inimigosVivos_1vivo_Test (){
        ArrayList<Inimigo> inimigos = new ArrayList<>();
        Inimigo inimigoTeste1 = new Inimigo("um", 1, 0);
        Inimigo inimigoTeste2 = new Inimigo("dois", 0, 0);

        inimigos.add(inimigoTeste1);
        inimigos.add(inimigoTeste2);

        assertTrue(Jogo.Aux.inimigosVivos(inimigos));
    }

    @Test
    void inimigosVivos_0vivos_Test (){
        ArrayList<Inimigo> inimigos = new ArrayList<>();
        Inimigo inimigoTeste1 = new Inimigo("um", 0, 0);
        Inimigo inimigoTeste2 = new Inimigo("dois", 0, 0);

        inimigos.add(inimigoTeste1);
        inimigos.add(inimigoTeste2);

        assertFalse(Jogo.Aux.inimigosVivos(inimigos));
    }

    @Test
    void prepararInimigos_nivel2_Test (){        
        ArrayList<Inimigo> resultadoObtido = Jogo.Aux.prepararInimigos(2, "teste1", "teste2"); 

        assertEquals(1, resultadoObtido.size());
    }

    @Test
    void prepararInimigos_nivel3_Test (){        
        ArrayList<Inimigo> resultadoObtido = Jogo.Aux.prepararInimigos(3, "teste1", "teste2"); 

        assertEquals(1, resultadoObtido.size());
    }

    @Test
    void prepararInimigos_nivel4_Test (){        
        ArrayList<Inimigo> resultadoObtido = Jogo.Aux.prepararInimigos(4, "teste1", "teste2"); 

        assertEquals(2, resultadoObtido.size());
    }
}
