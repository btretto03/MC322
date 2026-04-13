package Jogo;

import java.util.ArrayList;

import Cartas.Carta;
import Cartas.CartaDano;
import Cartas.CartaEscudo;
import Efeitos.Adrenalina;
import Efeitos.Efeitos;
import Efeitos.Nocaute;
import Efeitos.Provocacao;
import Efeitos.Sangramento;
import Entidades.Entidade;
import Entidades.Inimigo;
import Prints.PrintsMain;

public class Batalha {
    private ArrayList<Inimigo> inimigos;

    public Batalha(ArrayList<Inimigo> inimigos){
        this.inimigos = inimigos;
    }

    public int inimigosVivos (){
        return (int) inimigos.stream().filter(inimigo -> inimigo.estaVivo()).count();
    }

    public ArrayList<Inimigo> getInimigos (){
        return this.inimigos;
    }
}
