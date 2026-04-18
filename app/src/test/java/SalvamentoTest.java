import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;

import Cartas.Carta;
import Cartas.CartaDano;
import Cartas.CartaEscudo;
import Jogo.Salvamento.CartaSalva;
import Jogo.Salvamento.EstadoTorneio;
import Jogo.Salvamento.VariaveisBatalha;

public class SalvamentoTest {
    
    @TempDir
    Path diretorioTemporario;
    @Nested
    class CartasTest {
        private ArrayList<Carta> cartasJogo;
        private ArrayList<CartaSalva> cartasSalvas;

        @BeforeEach
        void inicializacao (){
            cartasJogo = new ArrayList<>();
            CartaDano cartaJogoTeste1 = new CartaDano("Teste1", 1);
            CartaDano cartaJogoTeste2 = new CartaDano("Teste2", 2);
            CartaDano cartaJogoTeste3 = new CartaDano("Teste3", 3);

            cartasJogo.add(cartaJogoTeste1);
            cartasJogo.add(cartaJogoTeste2);
            cartasJogo.add(cartaJogoTeste3);

            cartasSalvas = new ArrayList<>();

            CartaSalva cartaSalvaTeste1 = new CartaSalva();
            cartaSalvaTeste1.nome = "Teste1";
            cartaSalvaTeste1.descricao = "Causando dano";
            cartaSalvaTeste1.custo = 1;
            
            CartaSalva cartaSalvaTeste2 = new CartaSalva();
            cartaSalvaTeste2.nome = "Teste2";
            cartaSalvaTeste2.descricao = "Causando dano";
            cartaSalvaTeste2.custo = 2;

            CartaSalva cartaSalvaTeste3 = new CartaSalva();
            cartaSalvaTeste3.nome = "Teste3";
            cartaSalvaTeste3.descricao = "adicionando escudo";
            cartaSalvaTeste2.custo = 3;

            cartasSalvas.add(cartaSalvaTeste1);
            cartasSalvas.add(cartaSalvaTeste2);
            cartasSalvas.add(cartaSalvaTeste3);
        }

        @Test
        void salvarCartasTest (){
            ArrayList<CartaSalva> cartasSalvas = Jogo.Salvamento.Salvamento.salvarCartas(cartasJogo);

            assertEquals(cartasJogo.size(), cartasSalvas.size());

            for (int i = 0; i < cartasJogo.size(); i++){
                assertEquals(cartasJogo.get(i).getNome(), cartasSalvas.get(i).nome);
                assertEquals(cartasJogo.get(i).getDescricao(), cartasSalvas.get(i).descricao);
                assertEquals(cartasJogo.get(i).getCusto(), cartasSalvas.get(i).custo);
            }
        }

        @Test
        void carregarCartasTest (){
            ArrayList<Carta> resultado = Jogo.Salvamento.Salvamento.carregarCartas(cartasSalvas);

            assertEquals(resultado.size(), cartasSalvas.size());

            for (int i = 0; i < resultado.size(); i++){
                assertEquals(resultado.get(i).getNome(), cartasSalvas.get(i).nome);
                assertEquals(resultado.get(i).getDescricao(), cartasSalvas.get(i).descricao);
                assertEquals(resultado.get(i).getCusto(), cartasSalvas.get(i).custo);
            }

            assertTrue(resultado.get(2) instanceof CartaEscudo);
        }
    
    }

    @Test
    void salvarPartidaTest (){
        VariaveisBatalha variaveisTeste = new VariaveisBatalha();
        
        Path arquivoTeste = diretorioTemporario.resolve("teste_save.json");

        Jogo.Salvamento.Salvamento.salvarPartida(variaveisTeste, arquivoTeste.toString());
        
        assertTrue(Files.exists(arquivoTeste));
    }

    @Test
    void salvarTorneioTest () throws Exception{
        EstadoTorneio torneio = new EstadoTorneio();
        torneio.lutadorIgnorado = "nome de teste";

        Path caminhoArquivo = diretorioTemporario.resolve("teste_torneio.json");

        Jogo.Salvamento.Salvamento.salvarTorneio(torneio, caminhoArquivo.toString());

        assertTrue(Files.exists(caminhoArquivo));

        String conteudoArquivo = Files.readString(caminhoArquivo);

        assertTrue(conteudoArquivo.contains("nome de teste"));
    }

    @Test
    void carregarTorneioTest (){
        EstadoTorneio torneio = new EstadoTorneio();
        torneio.lutadorIgnorado = "nome de teste";

        Path caminhoArquivo = diretorioTemporario.resolve("teste_torneio.json");

        Jogo.Salvamento.Salvamento.salvarTorneio(torneio, caminhoArquivo.toString());

        EstadoTorneio resultado = Jogo.Salvamento.Salvamento.carregarTorneio(caminhoArquivo.toString());

        assertEquals(torneio.lutadorIgnorado, resultado.lutadorIgnorado);
    }
}