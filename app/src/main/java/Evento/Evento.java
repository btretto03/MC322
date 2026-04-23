package Evento;

import java.util.ArrayList;

import Cartas.Carta;
import Entidades.Heroi;

public abstract class Evento {

	public abstract void iniciar(Heroi heroi, ArrayList<Carta> pilhaCompra, ArrayList<Carta> pilhaDescarte);
}
