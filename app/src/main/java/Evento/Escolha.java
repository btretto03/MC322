package Evento;

import java.util.ArrayList;
import java.util.Scanner;

import Cartas.Carta;
import Entidades.Heroi;

public class Escolha extends Evento {
	private Scanner input;

	public Escolha (Scanner input){
		this.input = input;
	}

	@Override
	public void iniciar(Heroi heroi, ArrayList<Carta> pilhaCompra, ArrayList<Carta> pilhaDescarte) {
        Prints.PrintsMain.printDescobriuCaixa();
		
		while (true){
			int escolha = input.nextInt();
			if (escolha == 1){
				this.abrirCaixa(heroi);
				break;
			} else if (escolha == 2){
				return;
			} else {
				System.out.println("⚠️ Escolha inválida");
				continue;
			}
		}
	}

	public void abrirCaixa (Heroi heroi){
		int resultado = (int) (Math.random() * 5) + 1;

		Jogo.Aux.limparTela();
		Prints.PrintsMain.printResultadoCaixa(resultado);

		switch (resultado) {
			case 1:
				heroi.setVida(heroi.getVida() + 15);
				break;
			case 2:
				heroi.setVida(heroi.getVida() + 15);
				break;
			case 3:
				heroi.setVida(heroi.getVida() - 15);
				break;
			case 4:
				heroi.setVida(heroi.getVida() - 15);
				break;
			case 5:
				heroi.setVida(heroi.getVida() - 15);
				break;
			default:
				break;
		}

		while (input.nextInt() != 0) {
			continue;
		}
	}

}
