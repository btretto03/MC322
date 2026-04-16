import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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

    @Nested
    class inimigosVivosTest{
        private ArrayList<Inimigo> inimigos;

        @BeforeEach
        void criarInimigosTeste (){
            inimigos = new ArrayList<>();
            Inimigo inimigoTeste1 = new Inimigo("um", 1, 0);
            Inimigo inimigoTeste2 = new Inimigo("dois", 1, 0);

            inimigos.add(inimigoTeste1);
            inimigos.add(inimigoTeste2);
        }

        @Test
        void doisVivos (){
            assertTrue(Jogo.Aux.inimigosVivos(inimigos));
        }

        @Test
        void umVivo (){
            inimigos.get(1).setVida(0);
            assertTrue(Jogo.Aux.inimigosVivos(inimigos));
        }

        @Test
        void zeroVivos (){
            inimigos.get(0).setVida(0);
            inimigos.get(1).setVida(0);
            assertFalse(Jogo.Aux.inimigosVivos(inimigos));
        }
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


    @Nested
    class verificarFimDeJogoTest {
        private ArrayList<Inimigo> inimigos;
        private Heroi heroi;

        @BeforeEach
        void criarInimigosTeste(){
            inimigos = new ArrayList<>();
            inimigos.add(new Inimigo("teste2", 0, 0));
            heroi = new Heroi("teste", 0, 0, 0);
        }

        @Test
        void Vitoria (){
            heroi.setVida(1);
            inimigos.get(0).setVida(0);
            assertTrue(Jogo.Aux.verificarFimDeJogo(heroi, inimigos));
        }

        @Test
        void Empate (){
            heroi.setVida(0);
            inimigos.get(0).setVida(0);
            assertTrue(Jogo.Aux.verificarFimDeJogo(heroi, inimigos));
        }

        @Test
        void Derrota (){
            heroi.setVida(0);
            inimigos.get(0).setVida(1);
            assertTrue(Jogo.Aux.verificarFimDeJogo(heroi, inimigos));
        }

        @Test
        void jogoContinua (){
            heroi.setVida(1);
            inimigos.get(0).setVida(1);
            assertFalse(Jogo.Aux.verificarFimDeJogo(heroi, inimigos));
        }
    }
    
    @Nested
    class comprarMaoTest {
        private ArrayList<Carta> pilhaCompra;
        private ArrayList<Carta> pilhaDescarte;
        private ArrayList<Carta> mao;

        @BeforeEach
        void configurarPilhas (){
            pilhaCompra = Jogo.Aux.gerarBaralhoInicial();
            pilhaDescarte = new ArrayList<>();
            mao = new ArrayList<>();
        }

        //A pilha de compras inicia com 22 items
        @Test
        void comprarCincoMaos (){
            for(int i = 0; i < 5; i++){
                mao = Jogo.Aux.comprarMao(pilhaCompra, pilhaDescarte);
                for (Carta carta : mao){
                    pilhaCompra.remove(carta);
                    pilhaDescarte.add(carta);
                }
            }

            assertEquals(2, pilhaCompra.size());
            assertEquals(20, pilhaDescarte.size());
        }

        @Test
        //Ao comprar a sexta mao, a pilha de compras deve receber todas as cartas da pilhas de descarte
        void comprarSextaMao (){
            assertEquals(22, pilhaCompra.size());
            assertEquals(0, mao.size());
            assertEquals(0, pilhaDescarte.size());

            for(int i = 0; i < 6; i++){
                mao = Jogo.Aux.comprarMao(pilhaCompra, pilhaDescarte);
                if (i == 5){
                    continue; //Condição para simular o caso imediatamente antes das cartas serem utilizadas
                }
                for (Carta carta : mao){
                    pilhaDescarte.add(carta);
                }
            }

            assertEquals(18, pilhaCompra.size());
            assertEquals(4, mao.size());
            assertEquals(0, pilhaDescarte.size());
        }
    }


}
