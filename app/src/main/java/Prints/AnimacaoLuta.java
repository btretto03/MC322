package Prints;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import Jogo.Aux;

public class AnimacaoLuta {
    
    public static void printLutadoresParados() {
        imprimirTXT("LutaInterativa/1vs1/Heroi/Soco/LutaInicial.txt"); 
    }

    public static void animarGolpeHeroi(String nomeCarta) {
        String pasta = mapearGolpeParaPasta(nomeCarta);

        String[] nomesArquivos = {"LutaInicial.txt", "LutaAproximacao.txt", "LutaSoco.txt"};
        
        for (int i = 0; i < nomesArquivos.length; i++) {
            if (i > 0) {
                System.out.print("\033[7A"); 
            } else {
                Aux.limparTela();
            }
            
            String caminho = "LutaInterativa/1vs1/Heroi/" + pasta + "/" + nomesArquivos[i];
            imprimirTXT(caminho);
            
            if (i == nomesArquivos.length - 1) {
                Aux.esperar(1500);
            } else {
                Aux.esperar(600);
            }
        }
    }

    public static void animarGolpeInimigo(String nomeInimigo) {
        String[] pastasAtaque = {"Soco", "Chute", "Voadora"};
        String pasta = pastasAtaque[(int) (Math.random() * pastasAtaque.length)];
        String[] nomesArquivos = {"LutaInicial.txt", "LutaAproximando.txt", "LutaSoco.txt"};

        for (int i = 0; i < nomesArquivos.length; i++) {
            if (i > 0) {
                System.out.print("\033[7A");
            } else {
                Aux.limparTela();
            }
            
            String caminho = "LutaInterativa/1vs1/Inimigo/" + pasta + "/" + nomesArquivos[i];
            imprimirTXT(caminho);
            
            if (i == nomesArquivos.length - 1) {
                Aux.esperar(1500);
            } else {
                Aux.esperar(600);
            }
        }
    }

    private static String mapearGolpeParaPasta(String nomeCarta) {
        String nome = nomeCarta.toLowerCase();
        
        if (nome.contains("chute")){ 
            return "Chute";
        }
        if (nome.contains("voadora")){
             return "Voadora";
        }
        if (nome.contains("bloqueio") || nome.contains("guarda") || nome.contains("esquiva")){
             return "Defesa";
        }
        return "Soco"; 
    }

    private static void imprimirTXT(String caminhoArquivo) {
        String caminhoCompleto = "src/main/java/Prints/" + caminhoArquivo;
        File arquivo = new File(caminhoCompleto);

        try {
            Scanner leitor = new Scanner(arquivo);
            while (leitor.hasNextLine()) {
                System.out.println(leitor.nextLine());
            }
            leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("⚠️ Arte não encontrada em:\n" + arquivo.getAbsolutePath());
        }
    }
}