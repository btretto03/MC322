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

public class EfeitosTest {
    private Publisher juiz;
    private Heroi heroi;

    @BeforeEach
    void estadoInicial (){
        juiz = new Publisher();
        heroi = new Heroi("teste1", 0, 0, 0);
    }

    @Nested
    class SangramentoTest {
        private Sangramento sangramento;
        @BeforeEach
        void inicializacao (){
            sangramento = new Sangramento("sangramentoTest", 5, heroi);
        }

        @Test
        void aplicacaoSimples (){
            heroi.setVida(10);
            heroi.adicionarEfeito(sangramento, juiz);
            juiz.inscrever(sangramento);
            juiz.notificarSubscribers();

            assertEquals(5, heroi.getVida());
            assertEquals(4, sangramento.getAcumulos());
        }

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

    @Nested
    class ProvocacaoTest {
        private Provocacao provocacao;

        @BeforeEach
        void inicializacao (){
            provocacao = new Provocacao("provocacaoTest", 5, heroi);
        }

        @Test
        void aplicacaoSimples (){
            heroi.setEscudo(1);
            heroi.adicionarEfeito(provocacao, juiz);
            juiz.inscrever(provocacao);
            
            juiz.notificarSubscribers();

            assertEquals(0, heroi.getEscudo());
        }

        @Test
        void escudoNegativo (){ //O escudo do herói não pode ficar negativo
            heroi.setEscudo(0);
            heroi.adicionarEfeito(provocacao, juiz);
            juiz.inscrever(provocacao);
            
            juiz.notificarSubscribers();

            assertEquals(0, heroi.getEscudo());
        }
    }

    @Nested
    class AdrenalinaTest {
        private Adrenalina adrenalina;

        @BeforeEach
        void inicializacao (){
            adrenalina = new Adrenalina("AdrenalinaTest", 0, heroi);
        }

        @Test
        void vidaBaixa (){
            heroi.setVida(10);
            adrenalina.setAcumulos(10);
            heroi.adicionarEfeito(adrenalina, juiz);
            juiz.inscrever(adrenalina);

            juiz.notificarSubscribers();

            assertEquals(20, heroi.getVida());
        }

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

    @Nested
    class NocauteTest {
        private Nocaute nocaute;
        private ArrayList<Inimigo> inimigos;

        @BeforeEach
        void configuracao (){
            inimigos = new ArrayList<>();
            nocaute = new Nocaute("NocauteTeste", heroi, inimigos);
            heroi.setVida(10);
        }

        @Test
        void Acontece (){
            nocaute.setControleTeste(-1);
            nocaute.aplicarEfeito();

            assertFalse(heroi.estaVivo());
        }

        @Test
        void naoAcontece (){
            nocaute.setControleTeste(0);
            nocaute.aplicarEfeito();

            assertTrue(heroi.estaVivo());
        }
    }
}


