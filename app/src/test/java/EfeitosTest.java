import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import Entidades.Heroi;
import Entidades.Inimigo;
import Efeitos.Adrenalina;
import Efeitos.Nocaute;
import Efeitos.Provocacao;
import Efeitos.Sangramento;
import Jogo.Publisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes unitarios dos efeitos aplicados em herois e inimigos.
 */
public class EfeitosTest {
    private Publisher juiz;
    private Heroi heroi;

    /**
     * Inicializa heroi e publicador para cada cenario.
     */
    @BeforeEach
    void estadoInicial (){
        juiz = new Publisher();
        heroi = new Heroi("teste1", 0, 0, 0);
    }

    /**
     * Agrupa cenarios do efeito Sangramento.
     */
    @Nested
    class SangramentoTest {
        private Sangramento sangramento;

        /**
         * Cria efeito de sangramento para cada teste.
         */
        @BeforeEach
        void inicializacao (){
            sangramento = new Sangramento("sangramentoTest", 5, heroi);
        }

        /**
         * Verifica aplicacao de dano e decremento de acumulos.
         */
        @Test
        void aplicacaoSimples (){
            heroi.setVida(10);
            heroi.adicionarEfeito(sangramento, juiz);
            juiz.inscrever(sangramento);
            juiz.notificarSubscribers();

            assertEquals(5, heroi.getVida());
            assertEquals(4, sangramento.getAcumulos());
        }

        /**
         * Garante que vida nao fica negativa ao aplicar sangramento.
         */
        @Test
        void matandoAlvo (){
            heroi.setVida(1);
            heroi.adicionarEfeito(sangramento, juiz);
            juiz.inscrever(sangramento);
            juiz.notificarSubscribers();

            assertEquals(0, heroi.getVida());
            assertEquals(4, sangramento.getAcumulos());
        }
    }

    /**
     * Agrupa cenarios do efeito Provocacao.
     */
    @Nested
    class ProvocacaoTest {
        private Provocacao provocacao;

        /**
         * Cria efeito de provocacao para cada cenario.
         */
        @BeforeEach
        void inicializacao (){
            provocacao = new Provocacao("provocacaoTest", 5, heroi);
        }

        /**
         * Verifica perda de escudo apos notificacao do efeito.
         */
        @Test
        void aplicacaoSimples (){
            heroi.setEscudo(1);
            heroi.adicionarEfeito(provocacao, juiz);
            juiz.inscrever(provocacao);
            
            juiz.notificarSubscribers();

            assertEquals(0, heroi.getEscudo());
        }

        /**
         * Garante que escudo nao assuma valor negativo.
         */
        @Test
        void escudoNegativo (){ //O escudo do herói não pode ficar negativo
            heroi.setEscudo(0);
            heroi.adicionarEfeito(provocacao, juiz);
            juiz.inscrever(provocacao);
            
            juiz.notificarSubscribers();

            assertEquals(0, heroi.getEscudo());
        }
    }

    /**
     * Agrupa cenarios do efeito Adrenalina.
     */
    @Nested
    class AdrenalinaTest {
        private Adrenalina adrenalina;

        /**
         * Cria efeito de adrenalina para os testes.
         */
        @BeforeEach
        void inicializacao (){
            adrenalina = new Adrenalina("AdrenalinaTest", 0, heroi);
        }

        /**
         * Com vida baixa, adrenalina deve recuperar vida.
         */
        @Test
        void vidaBaixa (){
            heroi.setVida(10);
            adrenalina.setAcumulos(10);
            heroi.adicionarEfeito(adrenalina, juiz);
            juiz.inscrever(adrenalina);

            juiz.notificarSubscribers();

            assertEquals(20, heroi.getVida());
        }

        /**
         * Com vida alta, adrenalina nao deve alterar vida.
         */
        @Test
        void vidaAlta (){
            heroi.setVida(30);
            adrenalina.setAcumulos(10);
            heroi.adicionarEfeito(adrenalina, juiz);
            juiz.inscrever(adrenalina);

            juiz.notificarSubscribers();

            assertEquals(30, heroi.getVida());
        }
    }

    /**
     * Agrupa cenarios do efeito Nocaute.
     */
    @Nested
    class NocauteTest {
        private Nocaute nocaute;
        private ArrayList<Inimigo> inimigos;

        /**
         * Inicializa heroi, lista de inimigos e efeito para os testes.
         */
        @BeforeEach
        void configuracao (){
            inimigos = new ArrayList<>();
            nocaute = new Nocaute("NocauteTeste", heroi, inimigos);
            heroi.setVida(10);
        }

        /**
         * Forca condicao de nocaute e valida derrota do heroi.
         */
        @Test
        void Acontece (){
            nocaute.setControleTeste(-1);
            nocaute.aplicarEfeito();

            assertFalse(heroi.estaVivo());
        }

        /**
         * Sem condicao de nocaute, o heroi deve seguir vivo.
         */
        @Test
        void naoAcontece (){
            nocaute.setControleTeste(0);
            nocaute.aplicarEfeito();

            assertTrue(heroi.estaVivo());
        }
    }
}


