package Jogo.Salvamento;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import java.util.ArrayList;

import Cartas.Carta;
import Cartas.CartaDano;
import Cartas.CartaEscudo;


public final class Salvamento {

    private static Gson gson() {
        return new Gson();
    }

    public static ArrayList<CartaSalva> salvarCartas(ArrayList<Carta> cartas) {
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

    public static ArrayList<Carta> carregarCartas(ArrayList<CartaSalva> cartas) {
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

        Gson json = gson();

        try (FileWriter writer = new FileWriter("save.json")) {
            json.toJson(batalha, writer);
            System.out.println("Partida salva");
        } catch (IOException e) {
            System.out.println("Erro no salvamento.");
        }
    }

    public static VariaveisBatalha carregarPartida() {
        Gson json = gson();

        try (FileReader reader = new FileReader("save.json")) {
            return json.fromJson(reader, VariaveisBatalha.class);
        } catch (IOException e) {
            System.out.println("Erro no carregamento.");
            return null;
        }
    }
}