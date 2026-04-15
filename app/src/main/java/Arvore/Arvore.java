package Arvore;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.InputStream;
import java.util.Scanner;

public class Arvore {
    String vermelho = "\u001B[31m";
    String reset = "\u001B[0m";

    protected DefaultMutableTreeNode raiz;
    
    public Arvore (String nome) {
        this.raiz = new DefaultMutableTreeNode(nome);
    }

    public void adicionarFilho(String nome) {
        DefaultMutableTreeNode novoFilho = new DefaultMutableTreeNode(nome);
        this.raiz.add(novoFilho);
    }

public void gerarFilhos() {
        /** Trabalharemos com a seguinte árvore 
         * O        O      (cinturao)
         * \     /
         * O     O         (Final)
         * / \  / \
         * O  O O  O       (Batalha intermediária)
         * \  /  \  /
         * O      O        (Batalha inicial)
         * \    /
         *    O            (inicio)
         */ 
        DefaultMutableTreeNode n1 = new DefaultMutableTreeNode("Renato Moicano");
        DefaultMutableTreeNode n2 = new DefaultMutableTreeNode("José Aldo");
        this.raiz.add(n1);
        this.raiz.add(n2);
        DefaultMutableTreeNode n3 = new DefaultMutableTreeNode("C. Mcgregor");
        DefaultMutableTreeNode n4 = new DefaultMutableTreeNode("C. Sonnen");
        DefaultMutableTreeNode n5 = new DefaultMutableTreeNode("W. Silva");
        DefaultMutableTreeNode n6 = new DefaultMutableTreeNode("V. Belfort");
        
        n1.add(n3);
        n1.add(n4);
        n2.add(n5);
        n2.add(n6);

        DefaultMutableTreeNode n7_caminhoConor = new DefaultMutableTreeNode("Jon Jones");
        DefaultMutableTreeNode n7_caminhoSonnen = new DefaultMutableTreeNode("Jon Jones");
        DefaultMutableTreeNode n8_caminhoWanderlei = new DefaultMutableTreeNode("Bruce Lee");
        DefaultMutableTreeNode n8_caminhoVitor = new DefaultMutableTreeNode("Bruce Lee");

        n3.add(n7_caminhoConor);
        n4.add(n7_caminhoSonnen); 
        n5.add(n8_caminhoWanderlei);
        n6.add(n8_caminhoVitor);

        DefaultMutableTreeNode c1_caminhoConor = new DefaultMutableTreeNode("Myke Tyson");
        DefaultMutableTreeNode c1_caminhoSonnen = new DefaultMutableTreeNode("Myke Tyson");
        DefaultMutableTreeNode c2_caminhoWanderlei = new DefaultMutableTreeNode("Chuck Norris");
        DefaultMutableTreeNode c2_caminhoVitor = new DefaultMutableTreeNode("Chuck Norris");

        n7_caminhoConor.add(c1_caminhoConor);
        n7_caminhoSonnen.add(c1_caminhoSonnen);
        n8_caminhoWanderlei.add(c2_caminhoWanderlei);
        n8_caminhoVitor.add(c2_caminhoVitor);
    }

    public void imprimirArvoreProgresso(String nomeOponente) {
        try {
            InputStream arquivo = Arvore.class.getClassLoader().getResourceAsStream("Arvore.txt");

            Scanner leitor = new Scanner(arquivo);

            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();
                if (linha.contains(nomeOponente)) {
                String linhaColorida = linha.replace(nomeOponente, vermelho + nomeOponente + reset);
                System.out.println(linhaColorida);
            } else {
                System.out.println(linha);
            }
            }
            
            leitor.close();
            Jogo.Aux.esperar(1500);

        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao imprimir a árvore: " + e.getMessage());
        }
    }

    public DefaultMutableTreeNode getRaiz() {
        return raiz;
    }

    public void setRaiz(DefaultMutableTreeNode raiz) {
        this.raiz = raiz;
    }
}
