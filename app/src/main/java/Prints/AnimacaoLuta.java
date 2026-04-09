package Prints;
import Entidades.Heroi;
import Entidades.Inimigo;
import Jogo.Aux;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class AnimacaoLuta {

    public static void printLutadoresParados(ArrayList<Inimigo> inimigos) {
        int qtdInimigos = inimigos.size();
        String caminho;

        if (qtdInimigos == 2) {
            caminho = "src/main/java/Prints/LutaInterativa/1vs2/2heroisoco1.txt";
        } else {
            caminho = "src/main/java/Prints/LutaInterativa/1vs1/1heroisoco.txt";
        }

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

                if (!heroi.getListaEfeitos().isEmpty()) { //efeito heroi
                    linha = aplicarEfeitoEmoji(linha, "😬", heroi.getListaEfeitos().get(0).getNome());
                }

                if (!inimigos.isEmpty() && inimigos.get(0).estaVivo() && !inimigos.get(0).getListaEfeitos().isEmpty()) { //efeito inimigo1
                    linha = aplicarEfeitoEmoji(linha, "😠", inimigos.get(0).getListaEfeitos().get(0).getNome());
                }

                if (qntInimigos == 2 && inimigos.get(1).estaVivo() && !inimigos.get(1).getListaEfeitos().isEmpty()) { //efeito inimigo2
                    linha = aplicarEfeitoEmoji(linha, "😡", inimigos.get(1).getListaEfeitos().get(0).getNome());
                }


                System.out.println(linha);
                linhas ++;
            }
        } catch (Exception e) {
            System.out.println("Erro ao mostrar Cena parada com efeitos");
        }
    }

    private static String aplicarEfeitoEmoji(String linha, String emojiBase, String nomeEfeito) {
            switch (nomeEfeito) {
                case "Sangramento": 
                    return linha.replace(emojiBase, emojiBase + "🩸"); 
                case "Adrenalina":  
                    return linha.replace(emojiBase, emojiBase + "💉"); 
                case "Provocacao":  
                    return linha.replace(emojiBase, emojiBase + "🤬"); 
                case "Nocaute":     
                    return linha.replace(emojiBase, emojiBase + "💫"); 
                default:            
                    return linha;
            }
        }

    public static void animarGolpeHeroi(String nomeCarta, int qtdInimigos, int alvo) {
        String nome = nomeCarta.toLowerCase().trim(); //pega o nome das cartas e padroniza para minusculo e sem espacos
        String prefixo, arquivo, sufixo, pasta;
                
        if (qtdInimigos == 2) {
            prefixo = "2";
        } else {
            prefixo = "1";
        }
        if (qtdInimigos == 2) {
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
        if (qtdInimigos == 2) {
            pasta = "1vs2/";
        } else {
            pasta = "1vs1/";
        }
        executarAnimacao(pasta + arquivo);
    }

    public static void animarGolpeInimigo(int qtdInimigos, int inimigoIndex) {
        int acaoinimigo = (int)(Math.random() * 2);
        String prefixo, nomeInim, golpe, pasta;

        if (qtdInimigos == 2) {
            prefixo = "2";
        } else {
            prefixo = "1";
        }

        if (qtdInimigos == 2) {
            nomeInim = "inimigo" + (inimigoIndex + 1);
        } else {
            nomeInim = "inimigo";
        }
        if (acaoinimigo == 0) {
            golpe = "soco.txt";
        } else {
            golpe = "chute.txt";
        }
        if (qtdInimigos == 2) {
            pasta = "1vs2/";
        } else {
            pasta = "1vs1/";
        }
        executarAnimacao(pasta + prefixo + nomeInim + golpe);
    }

    public static void animarDefesaInimigo(int qtdInimigos, int inimigoIndex) {
        String prefixo, nomeInim, pasta;
        if (qtdInimigos == 2) {
            prefixo = "2";
        } else {
            prefixo = "1";
        }
        if (qtdInimigos == 2) {
            nomeInim = "inimigo" + (inimigoIndex + 1);
        } else {
            nomeInim = "inimigo";
        }
        if (qtdInimigos == 2) {
            pasta = "1vs2/";
        } else {
            pasta = "1vs1/";
        }
        executarAnimacao(pasta + prefixo + nomeInim + "defesa.txt");
    }

   private static void executarAnimacao(String arquivo) {
        String caminho = "src/main/java/Prints/LutaInterativa/" + arquivo;

        try {
            File f = new File(caminho);
            Scanner leitor = new Scanner(f);
            int contador = 0;
            Aux.limparTela();

            while (leitor.hasNextLine()) {
                System.out.println(leitor.nextLine());
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