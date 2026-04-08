package Prints;

import java.io.File;
import java.util.Scanner;
import Jogo.Aux;

public class AnimacaoLuta {

    public static void printLutadoresParados() {
        imprimirCenaFixo("1heroisoco.txt");
    }

    public static void animarGolpeHeroi(String nomeCarta) {
        String nome = nomeCarta.toLowerCase().trim(); //pega o nome das cartas e padroniza para minusculo e sem espacos
        String arquivoAlvo = "1heroisoco.txt"; 

        if (nome.contains("chute")) {
            arquivoAlvo = "1heroichute.txt";
        } else if (nome.contains("voadora")) {
            arquivoAlvo = "1heroivoadora.txt";
        } else if (nome.contains("bloqueio")|| nome.contains("esquivo") || nome.contains("guarda")  || nome.contains("correr")) {
            arquivoAlvo = "1heroidefesa.txt";
        } else {
            arquivoAlvo = "1heroisoco.txt";
        }
        executarAnimacao(arquivoAlvo);
    }

    public static void animarGolpeInimigo(String nomeInimigo) {
        int acaoinimigo = (int)(Math.random() * 2);
        if (acaoinimigo == 0) {
            executarAnimacao("1inimigosoco.txt");
        } else {
            executarAnimacao("1inimigochute.txt");
        }
    }

    public static void animarDefesaInimigo() {
        executarAnimacao("1inimigodefesa.txt");
    }

    private static void executarAnimacao(String nomeArquivo) {

        String caminho = "src/main/java/Prints/LutaInterativa/1vs1/" + nomeArquivo;

        try {
            File f = new File(caminho);

            if (!f.exists()) {
                System.out.println("Arquivo não encontrado: " + nomeArquivo);
                return;
            }

            Scanner leitor = new Scanner(f);
            int contador = 0;

            Aux.limparTela();

            while (leitor.hasNextLine()) {
                System.out.println(leitor.nextLine());
                contador++;

                if (contador == 7) {
                    Aux.esperar(500);
                    Aux.limparTela();
                    contador = 0;
                }
            }
            Aux.esperar(150);
            leitor.close();
            //Aux.limparTela();
        } catch (Exception e) {
            System.out.println("Erro ao abrir: " + nomeArquivo);
        }
    }

    private static void imprimirCenaFixo(String nomeArquivo) {
        String caminho = "src/main/java/Prints/LutaInterativa/1vs1/" + nomeArquivo;

        try {
            Scanner leitor = new Scanner(new File(caminho));

            int linhas = 0;

            while (leitor.hasNextLine() && linhas < 7) {
                System.out.println(leitor.nextLine());
                linhas ++;
            }

            leitor.close();

        } catch (Exception e) {
            System.out.println("Erro ao mostrar Cena parada");
        }
    }
}