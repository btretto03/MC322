import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import Entidades.Heroi;
import Cartas.Carta;
import Cartas.CartaDano;
import Efeitos.Adrenalina;
import Efeitos.Provocacao;
import Efeitos.Sangramento;
import Entidades.Inimigo;
import Jogo.Publisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Scanner;

public class EfeitosTest {
    private Publisher juiz;
    private Heroi heroi;
    private Inimigo inimigo;

    @BeforeEach
    void estadoInicial (){
        juiz = new Publisher();
        heroi = new Heroi("teste1", 0, 0, 0);
        inimigo = new Inimigo("teste2", 0, 0);
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
}


