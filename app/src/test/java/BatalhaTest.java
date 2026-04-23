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
import Evento.Batalha;
import Jogo.Publisher;

/**
 * Testes unitarios dos fluxos principais da classe Batalha.
 */
public class BatalhaTest {
    private Batalha batalahaTest;
    private Heroi heroi;
    private Publisher juiz;
    private ArrayList<Inimigo> inimigos;

    /**
     * Prepara estado base da batalha para cada teste.
     */
    @BeforeEach
    void inicializacao () {
        heroi = new Heroi("Teste", 0, 0, 0);
        juiz = new Jogo.Publisher();
        inimigos = new ArrayList<>();
        batalahaTest = new Batalha(heroi, juiz, null, inimigos);
    }


    /**
     * Agrupa cenarios de inicializacao de round.
     */
    @Nested
    class inicioRoundTest {

        /**
         * Garante energia e escudo em estado inicial conhecido.
         */
        @BeforeEach
        void configuracao (){
            heroi.setEnergia(0);
            heroi.setEscudo(10);
        }

        /**
         * Valida calculo de inicio de round com um inimigo.
         */
        @Test
        void umInimigo (){
            Inimigo inimigo1 = new Inimigo("teste2", 1, 0);
            inimigos.add(inimigo1);
            
            batalahaTest.inicioRound();

            assertEquals(6, heroi.getEnergia());
            assertEquals(0, heroi.getEscudo());
        }

        /**
         * Valida calculo de inicio de round com dois inimigos.
         */
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
   

    /**
     * Agrupa cenarios de escolha do modo de jogo.
     */
    @Nested
    class modoDeJogoTest {
        
        /**
         * Escolha direta do modo padrao.
         */
        @Test
        void caso1 (){
            String inputSimulado = "1" + "\n";
            ByteArrayInputStream in = new ByteArrayInputStream(inputSimulado.getBytes());

            Scanner inputs = new Scanner(in);

            assertEquals(0, Evento.Batalha.modoDeJogo(inputs));
        }

        /**
         * Fluxo 2->1 deve selecionar modo alternativo.
         */
        @Test
        void caso21 (){
            String inputSimulado = "2\n" + "1\n";
            ByteArrayInputStream in = new ByteArrayInputStream(inputSimulado.getBytes());

            Scanner inputs = new Scanner(in);

            assertEquals(1, Evento.Batalha.modoDeJogo(inputs));
        }

        /**
         * Fluxo 2->2 retorna ao modo padrao.
         */
        @Test
        void caso22 (){
            String inputSimulado = "2\n" + "2\n";
            ByteArrayInputStream in = new ByteArrayInputStream(inputSimulado.getBytes());

            Scanner inputs = new Scanner(in);

            assertEquals(0, Evento.Batalha.modoDeJogo(inputs));
        }

        /**
         * Entrada invalida deve cair no comportamento padrao.
         */
        @Test
        void casoInvalido (){
            String inputSimulado = "X\n";
            ByteArrayInputStream in = new ByteArrayInputStream(inputSimulado.getBytes());

            Scanner inputs = new Scanner(in);

            assertEquals(0, Evento.Batalha.modoDeJogo(inputs));
        }
    }

    /**
     * Agrupa cenarios de verificacao de efeitos ativos.
     */
    @Nested
    class verficaEfeitosTest {
        private Sangramento efeitoTest;

        /**
         * Configura batalha com um inimigo e efeito de sangramento.
         */
        @BeforeEach
        void configuracao (){
            Inimigo inimigo = new Inimigo("Teste2", 10, 0);
            inimigos.add(inimigo);
            efeitoTest = new Sangramento("sangramento", 1, heroi);
        }

        /**
         * Deve detectar efeito quando o heroi possui status ativo.
         */
        @Test
        void heroiComEfeito (){
            heroi.adicionarEfeito(efeitoTest, juiz);
            assertTrue(batalahaTest.verficaEfeitos());
        }

        /**
         * Deve detectar efeito quando um inimigo possui status ativo.
         */
        @Test
        void inimigoComEfeito (){
            inimigos.get(0).adicionarEfeito(efeitoTest, juiz);
            assertTrue(batalahaTest.verficaEfeitos());
        }

        /**
         * Sem efeitos ativos, a verificacao deve retornar falso.
         */
        @Test
        void nenhumEfeito (){
            assertFalse(batalahaTest.verficaEfeitos());
        }
    }

    /**
     * Agrupa cenarios de acoes executadas pelos inimigos.
     */
    @Nested
    class acoesInimigoTest {
     
        /**
         * Cria inimigo base para os cenarios de ataque e efeito.
         */
        @BeforeEach
        void configuracao (){
            Inimigo inimigo = new Inimigo("Teste2", 10, 0);
            inimigos.add(inimigo);
        }

        /**
         * Com usouEfeito falso, inimigo deve apenas atacar.
         */
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

        /**
         * Com usouEfeito verdadeiro, deve ocorrer aplicacao de efeito.
         */
        @Test
        void efeitoSemAtaque (){
            batalahaTest.setUsouEfeito(true);
            batalahaTest.acoesInimigo();
            
            assertTrue(heroi.getListaEfeitos().size() > 0 || inimigos.get(0).getListaEfeitos().size() > 0);
        }

        /**
         * Com ataque e efeito ativos, ambos resultados devem ocorrer.
         */
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