package Entidades;
import java.util.ArrayList;
import Cartas.Carta;

public class Heroi extends Entidade {
    private int energia;
    
    public Heroi(String nome, int vida, int escudo) { //construtor
        super(nome, vida, escudo);
    }

    public static String escolherHeroi(java.util.Scanner inputs) {
        String escolhaheroi;
        System.out.println("Escolha o seu herói: "); //Escolha do heroi
        System.out.println("[1] 🏆 Alex Poatan\n[2] 🥋 Anderson Silva\n[3] 🥊 Fabrício Werdum");
        int escolha1 = inputs.nextInt();
        switch (escolha1) {
            case 1:
                escolhaheroi = "Alex Poatan";
                break;
            case 2:
                escolhaheroi = "Anderson Silva";
                break;
            case 3:
                escolhaheroi = "Fabrício Werdum";
                break;
            default:
                System.out.println("⚠️Escolha inválida. O lutador será Alex Poatan por padrão.");
                escolhaheroi = "Alex Poatan";
        }
        return escolhaheroi;
    }

    public static void printHeroiVenceu(Heroi heroi) {
        System.out.println("\u001B[48;5;193m" + "                                                        " + "\u001B[0m");
        System.out.println("\u001B[48;5;193m" + "🏆 VITÓRIA! " + heroi.getNome() + " Parabéns, você foi o campeão! 🏆" + "\u001B[48;5;193m");
        System.out.println("\u001B[48;5;193m" + "                                                        " + "\u001B[0m");
    }

    public boolean verificaMao (ArrayList<Carta> mao){
        int cartasInvalidas = 0;
        for (Carta i : mao){
            if (i.getCusto() > this.getEnergia()){
                cartasInvalidas++;
            }
        }
        if (cartasInvalidas == mao.size()){
            return false;
        } else {
            return true;
        }
    }

    //Getters e Setters
    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }   
}
