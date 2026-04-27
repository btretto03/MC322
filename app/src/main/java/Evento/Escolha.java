package Evento;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import Cartas.Carta;
import Entidades.Heroi;
import Prints.PrintsMain;

public class Escolha extends Evento {
	@Override
	public void iniciar(Heroi heroi, ArrayList<Carta> pilhaCompra, ArrayList<Carta> pilhaDescarte) {
        Prints.PrintsMain.printDescobriuCaixa();
		Scanner input = new Scanner(System.in);
		int escolha = input.nextInt();

		while (true){
			if (escolha == 1){
				this.abrirCaixa(heroi, input);
				break;
			} else if (escolha == 2){
				input.close();
				return;
			} else {
				System.out.println("⚠️ Escolha inválida");
				continue;
			}
		}

		input.close();
	}

	public void abrirCaixa (Heroi heroi, Scanner input){
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
