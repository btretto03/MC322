import java.util.ArrayList;
import java.util.Scanner;

import Cartas.*; // '.*'importa todas as classes do pacote 
import Entidades.*;
import Prints.*;
import Efeitos.*;
import Jogo.Batalha;
import Jogo.Publisher;

/**
 * Classe principal da aplicacao e ponto de entrada do jogo.
 *
 * <p>Este arquivo coordena o ciclo completo de uma partida: selecao do heroi,
 * configuracao dos inimigos, gerenciamento de baralho/mao e execucao dos rounds
 * ate que uma condicao de fim de jogo seja atendida.</p>
 */
public class App {
    /**
     * Scanner compartilhado para leitura de entradas do usuario no terminal.
     */
    public static Scanner inputs = new Scanner(System.in);

    /**
     * Inicia a aplicacao e executa o loop principal de combate.
     *
     * <p>Fluxo principal:</p>
     * <ol>
     *   <li>Instancia os objetos-base da partida (juiz, heroi e modo de jogo).</li>
     *   <li>Prepara baralho, pilha de compra e pilha de descarte.</li>
     *   <li>Executa rounds com escolhas do jogador, ataques inimigos e efeitos ativos.</li>
     *   <li>Encerra quando heroi ou inimigos atingem condicao final.</li>
     * </ol>
     *
     * @param args argumentos de linha de comando (nao utilizados)
     */
    public static void main(String[] args)  {
        Prints.PrintsMain.printInicial2();
        Jogo.Aux.limparTela();
//----------------------------------INSTANCIAMENTO------------------------------------------
        Publisher juiz = new Publisher();
        String escolhaheroi = Heroi.escolherHeroi(inputs);
        Heroi heroi = new Heroi(escolhaheroi, 50, 0, 0);
        
        Jogo.Aux.limparTela();
        System.out.println("\n🏆 APRESENTANDO SEU LUTADOR! 🏆\n");
        Prints.PrintsEntidades.printArteHeroi();

//----------------------------------ESCOLHA DO MODO------------------------------------------
        ArrayList<Inimigo> inimigos = Jogo.Aux.escolherModoDeJogo(inputs, heroi);
        
        Jogo.Aux.limparTela();
        System.out.println("\n Seus oponentes entraram no octógono! \n");
        for (int i = 0; i < inimigos.size(); i++) {
            System.out.println("⚔️ " + inimigos.get(i).getNome().toUpperCase() + "\n");
            if (i == 0) {
                Prints.PrintsEntidades.printArteInimigo();
            } else if (i == 1) {
                Prints.PrintsEntidades.printArteInimigo2();
            }
        }

//----------------------------------BARALHO E PILHAS------------------------------------------
        ArrayList<Carta> baralho = Jogo.Aux.gerarBaralhoInicial();
        Prints.PrintsMain.printInicioluta();

        ArrayList<Carta> pilhaCompra = new ArrayList<>(baralho);
        ArrayList<Carta> pilhaDescarte = new ArrayList<>();

//----------------------------------LUTA------------------------------------------
        int furia = 0; //Variável para o usuario usar um efeito
        int contadorRound = 1;

        
        Jogo.Batalha ufcjava = new Batalha(inimigos, heroi, juiz, inputs);

        ufcjava.Luta(pilhaCompra, pilhaDescarte);
        
        inputs.close(); 
    }  
}