import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import Efeitos.Sangramento;
import Entidades.Heroi;
import Entidades.Inimigo;
import Jogo.Batalha;
import Jogo.Publisher;

public class BatalhaTest {
    private Batalha batalahaTest;
    private Heroi heroi;
    private Publisher juiz;
    private ArrayList<Inimigo> inimigos;

    @BeforeEach
    void inicializacao () {
        heroi = new Heroi("Teste", 0, 0, 0);
        juiz = new Jogo.Publisher();
        inimigos = new ArrayList<>();
        batalahaTest = new Batalha(heroi, juiz, null, inimigos);
    }


    @Nested
    class inicioRoundTest {

        @BeforeEach
        void configuracao (){
            heroi.setEnergia(0);
            heroi.setEscudo(10);
        }

        @Test
        void umInimigo (){
            Inimigo inimigo1 = new Inimigo("teste2", 1, 0);
            inimigos.add(inimigo1);
            
            batalahaTest.inicioRound();

            assertEquals(6, heroi.getEnergia());
            assertEquals(0, heroi.getEscudo());
        }

        @Test
        void doisInimigos (){
            Inimigo inimigo1 = new Inimigo("teste2", 1, 0);
            Inimigo inimigo2 = new Inimigo("teste3", 1, 0);
            inimigos.add(inimigo1);
            inimigos.add(inimigo2);

            batalahaTest.inicioRound();

            assertEquals(8, heroi.getEnergia());
            assertEquals(0, heroi.getEscudo());            
        }
    } 
   

    @Nested
    class modoDeJogoTest {
        
        @Test
        void caso1 (){
            String inputSimulado = "1" + "\n";
            ByteArrayInputStream in = new ByteArrayInputStream(inputSimulado.getBytes());

            Scanner inputs = new Scanner(in);

            assertEquals(0, Jogo.Batalha.modoDeJogo(inputs));
        }

        @Test
        void caso21 (){
            String inputSimulado = "2\n" + "1\n";
            ByteArrayInputStream in = new ByteArrayInputStream(inputSimulado.getBytes());

            Scanner inputs = new Scanner(in);

            assertEquals(1, Jogo.Batalha.modoDeJogo(inputs));
        }

        @Test
        void caso22 (){
            String inputSimulado = "2\n" + "2\n";
            ByteArrayInputStream in = new ByteArrayInputStream(inputSimulado.getBytes());

            Scanner inputs = new Scanner(in);

            assertEquals(0, Jogo.Batalha.modoDeJogo(inputs));
        }

        @Test
        void casoInvalido (){
            String inputSimulado = "X\n";
            ByteArrayInputStream in = new ByteArrayInputStream(inputSimulado.getBytes());

            Scanner inputs = new Scanner(in);

            assertEquals(0, Jogo.Batalha.modoDeJogo(inputs));
        }
    }

    @Nested
    class verficaEfeitosTest {
        private Sangramento efeitoTest;

        @BeforeEach
        void configuracao (){
            Inimigo inimigo = new Inimigo("Teste2", 10, 0);
            inimigos.add(inimigo);
            efeitoTest = new Sangramento("sangramento", 1, heroi);
        }

        @Test
        void heroiComEfeito (){
            heroi.adicionarEfeito(efeitoTest, juiz);
            assertTrue(batalahaTest.verficaEfeitos());
        }

        @Test
        void inimigoComEfeito (){
            inimigos.get(0).adicionarEfeito(efeitoTest, juiz);
            assertTrue(batalahaTest.verficaEfeitos());
        }

        @Test
        void nenhumEfeito (){
            assertFalse(batalahaTest.verficaEfeitos());
        }
    }

    @Nested
    class acoesInimigoTest {
     
        @BeforeEach
        void configuracao (){
            Inimigo inimigo = new Inimigo("Teste2", 10, 0);
            inimigos.add(inimigo);
        }

        @Test
        void ataqueSemEfeito (){
            heroi.setVida(10);
            int vidaHeroiAntes = heroi.getVida();

            batalahaTest.setUsouEfeito(false);
            inimigos.get(0).anuncio(heroi);
            batalahaTest.acoesInimigo();

            int vidaHeroiDepois = heroi.getVida();

            assertTrue(vidaHeroiAntes > vidaHeroiDepois);
        }

        @Test
        void efeitoSemAtaque (){
            batalahaTest.setUsouEfeito(true);
            batalahaTest.acoesInimigo();
            
            assertTrue(heroi.getListaEfeitos().size() > 0 || inimigos.get(0).getListaEfeitos().size() > 0);
        }

        @Test 
        void AtaqueMaisEfeito (){
            heroi.setVida(10);
            int vidaHeroiAntes = heroi.getVida();

            batalahaTest.setUsouEfeito(true);
            inimigos.get(0).anuncio(heroi);
            batalahaTest.acoesInimigo();

            int vidaHeroiDepois = heroi.getVida();

            assertTrue(vidaHeroiAntes > vidaHeroiDepois);
            assertTrue(heroi.getListaEfeitos().size() > 0 || inimigos.get(0).getListaEfeitos().size() > 0);
        }
    }
}