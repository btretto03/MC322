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


public final class Salvamento {

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

    public static void salvarPartida(VariaveisBatalha batalha) {
        System.out.println("\n💾 Salvando...");
        Gson json = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter("save.json")) {
            json.toJson(batalha, writer);
            System.out.println("Partida salva");
        } catch (IOException e) {
            System.out.println("Erro no salvamento.");
        }
    }

    public static VariaveisBatalha carregarPartida() {
        Gson json = new Gson();
        try (FileReader reader = new FileReader("save.json")) {
            return json.fromJson(reader, VariaveisBatalha.class);
        } catch (IOException e) {
            System.out.println("Erro no carregamento.");
            return null;
        }
    }

    public static void salvarTorneio(EstadoTorneio torneio) { //salva os dados da arvore do torneio
        Gson json = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("saveTorneio.json")) {
            json.toJson(torneio, writer);
        } catch (IOException e) {
            System.out.println("Erro no salvamento do torneio.");
        }
    }

    public static EstadoTorneio carregarTorneio() { //carrega o json do torneio
        Gson json = new Gson();
        try (FileReader reader = new FileReader("saveTorneio.json")) {
            return json.fromJson(reader, EstadoTorneio.class);
        } catch (IOException e) {
            System.out.println("Erro no carregamento do torneio.");
            return null;
        }
    }
}