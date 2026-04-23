package Jogo.Salvamento;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;

import Cartas.Carta;
import Cartas.CartaDano;
import Cartas.CartaEscudo;

/**
 * Utilitario responsavel por serializar e desserializar estados do jogo.
 */
public class Salvamento {

    /**
     * Converte cartas de jogo para a representacao persistivel.
     *
     * @param cartas lista de cartas em memoria
     * @return lista de cartas prontas para serializacao
     */
    public static ArrayList<CartaSalva> salvarCartas(ArrayList<Carta> cartas) { //salva as cartas
        ArrayList<CartaSalva> novaListaCartas = new ArrayList<>();
        for (Carta c : cartas) {
            CartaSalva cs = new CartaSalva();
            cs.nome = c.getNome();
            cs.custo = c.getCusto();
            cs.descricao = c.getDescricao();
            novaListaCartas.add(cs);
        }
        return novaListaCartas;
    }

    /**
     * Reconstroi cartas de jogo a partir da estrutura salva.
     *
     * @param cartas lista de cartas vindas do arquivo salvo
     * @return lista de cartas tipadas para uso em jogo
     */
    public static ArrayList<Carta> carregarCartas(ArrayList<CartaSalva> cartas) { //carrega as cartas de novo classificando elas por tipo novamente
        ArrayList<Carta> novaListaCartas = new ArrayList<>();
        for (CartaSalva cs : cartas) {
            String descricao = cs.descricao;
            if (cs.descricao == null) {
                descricao = "";
            } else {
                descricao = cs.descricao.toLowerCase();
            }

            if (descricao.contains("escudo")) {
                novaListaCartas.add(new CartaEscudo(cs.nome, cs.custo));
            } else {
                novaListaCartas.add(new CartaDano(cs.nome, cs.custo));
            }
        }

        return novaListaCartas;
    }

    /**
     * Salva o estado da batalha em formato JSON.
     *
     * @param batalha estado da batalha a ser salvo
     * @param caminhoArquivo caminho de destino; se nulo usa save.json
     */
    public static void salvarPartida(VariaveisBatalha batalha, String caminhoArquivo) {
        if (caminhoArquivo == null){
            caminhoArquivo = "save.json";
        }

        System.out.println("\n💾 Salvando...");
        Gson json = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(caminhoArquivo)) {
            json.toJson(batalha, writer);
            System.out.println("Partida salva");
        } catch (IOException e) {
            System.out.println("Erro no salvamento.");
        }
    }

    /**
     * Carrega o estado da batalha salvo em save.json.
     *
     * @return estado carregado ou null em caso de erro
     */
    public static VariaveisBatalha carregarPartida() {
        Gson json = new Gson();
        try (FileReader reader = new FileReader("save.json")) {
            return json.fromJson(reader, VariaveisBatalha.class);
        } catch (IOException e) {
            System.out.println("Erro no carregamento.");
            return null;
        }
    }

    /**
     * Salva o estado do torneio em formato JSON.
     *
     * @param torneio estado do torneio
     * @param caminhoArquivo caminho de destino; se nulo usa saveTorneio.json
     */
    public static void salvarTorneio(EstadoTorneio torneio, String caminhoArquivo) { //salva os dados da arvore do torneio
        if (caminhoArquivo == null){
            caminhoArquivo = "saveTorneio.json";
        }
        
        Gson json = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(caminhoArquivo)) {
            json.toJson(torneio, writer);
        } catch (IOException e) {
            System.out.println("Erro no salvamento do torneio.");
        }
    }

    /**
     * Carrega o estado do torneio a partir de um arquivo JSON.
     *
     * @param caminhoArquivo caminho do arquivo; se nulo usa saveTorneio.json
     * @return estado carregado ou null em caso de erro
     */
    public static EstadoTorneio carregarTorneio(String caminhoArquivo) { //carrega o json do torneio
        
        if (caminhoArquivo == null){
            caminhoArquivo = "saveTorneio.json";
        }
        
        Gson json = new Gson();
        try (FileReader reader = new FileReader(caminhoArquivo)) {
            return json.fromJson(reader, EstadoTorneio.class);
        } catch (IOException e) {
            System.out.println("Erro no carregamento do torneio.");
            return null;
        }
    }
}