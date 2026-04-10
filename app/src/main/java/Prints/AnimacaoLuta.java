package Prints;
import Entidades.*;
import Jogo.Aux;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class AnimacaoLuta {

    public static void printLutadoresParados(Heroi heroi, ArrayList<Inimigo> inimigos) {
        int qntInimigos = inimigos.size();
        String caminho;

        if (qntInimigos == 2) {
            caminho = "src/main/java/Prints/LutaInterativa/1vs2/2heroisoco1.txt";
        } else {
            caminho = "src/main/java/Prints/LutaInterativa/1vs1/1heroisoco.txt";
        }

        try {
            Scanner leitor = new Scanner(new File(caminho));
            int linhas = 0;
            while (leitor.hasNextLine() && linhas < 7) {
                String linha = leitor.nextLine();
                linha = printMorteHeroi(linha, heroi);
                linha = printMorte(linha, inimigos);
                System.out.println(linha);
                linhas ++;
            }
            leitor.close();
        } catch (Exception e) {
            System.out.println("Erro ao mostrar Cena parada");
        }
    }

    public static String printMorte(String linha, ArrayList<Inimigo> inimigos) {
        for (int i = 0; i < inimigos.size(); i++) {
            String cabeca;
            if (i == 0) {
                cabeca = "😠";
            } else if (i == 1) {
                cabeca = "😡";
            } else {
                continue;
            }

            if (!inimigos.get(i).estaVivo()) {
                linha = linha.replace(cabeca, "💀");
            }
        }
        return linha;
    }

    public static String printMorteHeroi(String linha, Heroi heroi) {
        if (heroi != null && !heroi.estaVivo()) {
            linha = linha.replace("😬", "💀");
        }
        return linha;
    }

    public static void printLutadoresEfeito(Heroi heroi, ArrayList<Inimigo> inimigos) {
        int qntInimigos = inimigos.size();
        String caminho;
        if (qntInimigos == 2) {
            caminho = "src/main/java/Prints/LutaInterativa/1vs2/2heroisoco1.txt";
        } else {
            caminho = "src/main/java/Prints/LutaInterativa/1vs1/1heroisoco.txt";
        }
        
        try (Scanner leitor = new Scanner(new File(caminho))) {
            int linhas = 0;
            while (leitor.hasNextLine() && linhas < 7) {
                String linha = leitor.nextLine();

                linha = printMorteHeroi(linha, heroi);

                if (!heroi.getListaEfeitos().isEmpty()) { //efeito heroi
                    linha = aplicarEfeitoEmoji(linha, "😬", heroi.getListaEfeitos().get(0).getNome());
                }

                if (!inimigos.isEmpty() && inimigos.get(0).estaVivo() && !inimigos.get(0).getListaEfeitos().isEmpty()) { //efeito inimigo1
                    linha = aplicarEfeitoEmoji(linha, "😠", inimigos.get(0).getListaEfeitos().get(0).getNome());
                }

                if (qntInimigos == 2 && inimigos.get(1).estaVivo() && !inimigos.get(1).getListaEfeitos().isEmpty()) { //efeito inimigo2
                    linha = aplicarEfeitoEmoji(linha, "😡", inimigos.get(1).getListaEfeitos().get(0).getNome());
                }

                linha = printMorte(linha, inimigos);


                System.out.println(linha);
                linhas ++;
            }
        } catch (Exception e) {
            System.out.println("Erro ao mostrar Cena parada com efeitos");
        }
    }

    private static String aplicarEfeitoEmoji(String linha, String cabeca, String nomeEfeito) {
            switch (nomeEfeito) {
                case "Sangramento": 
                    return linha.replace("  " + cabeca , cabeca + "🩸"); 
                case "Adrenalina":  
                    return linha.replace("  " + cabeca, cabeca + "💉"); 
                case "Provocacao":  
                    return linha.replace("  " + cabeca,"📢🫨"); 
                case "Nocaute":     
                    return linha.replace("  " + cabeca, cabeca + "💫"); 
                default:            
                    return linha;
            }
        }

    public static void animarGolpeHeroi(Heroi heroi, ArrayList<Inimigo> inimigos, String nomeCarta, int alvo) {
        int qntInimigos = inimigos.size();
        String nome = nomeCarta.toLowerCase().trim(); //pega o nome das cartas e padroniza para minusculo e sem espacos
        String prefixo, arquivo, sufixo, pasta;
                
        if (qntInimigos == 2) {
            prefixo = "2";
        } else {
            prefixo = "1";
        }
        if (qntInimigos == 2) {
            sufixo = String.valueOf(alvo + 1);
        } else {
            sufixo = "";
        }

        if (nome.contains("chute")) {
            arquivo = prefixo + "heroichute" + sufixo + ".txt";
        } else if (nome.contains("voadora")) {
            arquivo = prefixo + "heroivoadora" + sufixo + ".txt";
        } else if (nome.contains("bloqueio") || nome.contains("esquivo") || nome.contains("guarda") || nome.contains("correr")) {
            arquivo = prefixo + "heroidefesa.txt";
        } else {
            arquivo = prefixo + "heroisoco" + sufixo + ".txt";
        }
        if (qntInimigos == 2) {
            pasta = "1vs2/";
        } else {
            pasta = "1vs1/";
        }
        executarAnimacao(pasta + arquivo, heroi, inimigos);
    }

    public static void animarGolpeInimigo(Heroi heroi, ArrayList<Inimigo> inimigos, int inimigoIndex) {
        int qntInimigos = inimigos.size();
        int acaoinimigo = (int)(Math.random() * 2);
        String prefixo, nomeInim, golpe, pasta;

        if (qntInimigos == 2) {
            prefixo = "2";
        } else {
            prefixo = "1";
        }

        if (qntInimigos == 2) {
            nomeInim = "inimigo" + (inimigoIndex + 1);
        } else {
            nomeInim = "inimigo";
        }
        if (acaoinimigo == 0) {
            golpe = "soco.txt";
        } else {
            golpe = "chute.txt";
        }
        if (qntInimigos == 2) {
            pasta = "1vs2/";
        } else {
            pasta = "1vs1/";
        }
        executarAnimacao(pasta + prefixo + nomeInim + golpe, heroi, inimigos);
    }

    public static void animarDefesaInimigo(Heroi heroi, ArrayList<Inimigo> inimigos, int inimigoIndex) {
        int qntInimigos = inimigos.size();
        String prefixo, nomeInim, pasta;
        if (qntInimigos == 2) {
            prefixo = "2";
        } else {
            prefixo = "1";
        }
        if (qntInimigos == 2) {
            nomeInim = "inimigo" + (inimigoIndex + 1);
        } else {
            nomeInim = "inimigo";
        }
        if (qntInimigos == 2) {
            pasta = "1vs2/";
        } else {
            pasta = "1vs1/";
        }
        executarAnimacao(pasta + prefixo + nomeInim + "defesa.txt", heroi, inimigos);
    }

   private static void executarAnimacao(String arquivo, Heroi heroi, ArrayList<Inimigo> inimigos) {
        String caminho = "src/main/java/Prints/LutaInterativa/" + arquivo;

        try {
            File f = new File(caminho);
            Scanner leitor = new Scanner(f);
            int contador = 0;
            Aux.limparTela();

            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();
                linha = printMorteHeroi(linha, heroi);
                if (inimigos != null) {
                    linha = printMorte(linha, inimigos);
                }
                System.out.println(linha);
                contador ++;

                if (contador == 7) {
                    Aux.esperar(500);
                    Aux.limparTela();
                    contador = 0;
                }
            }
            Aux.esperar(150);
            leitor.close();
        } catch (Exception e) {
            System.out.println("Erro ao abrir: " + arquivo);
        }
    }
}