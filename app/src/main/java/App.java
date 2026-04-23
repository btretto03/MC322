import java.util.ArrayList;
import java.util.Scanner;

import Cartas.*; // '.*'importa todas as classes do pacote 
import Entidades.*;
import Evento.Batalha;
import Jogo.Aux;
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
        Aux.limparTela();
        Prints.PrintsMain.printInicial2();
        Aux.limparTela();
        int modo = Batalha.modoDeJogo(inputs);
        Jogo.Aux.limparTela();

        if (modo == 1) { //se carregou o save
            Jogo.Salvamento.VariaveisBatalha estado = Jogo.Salvamento.Salvamento.carregarPartida();
            Jogo.Salvamento.EstadoTorneio torneioEstado = Jogo.Salvamento.Salvamento.carregarTorneio(null);
            if (estado != null && estado.heroi != null && estado.inimigos != null) {
                Publisher juiz = new Publisher();
                Batalha batalha = new Batalha(estado.heroi, juiz, inputs, estado.inimigos);
                ArrayList<Carta> pilhaCompra = Jogo.Salvamento.Salvamento.carregarCartas(estado.pilhaCompra);
                ArrayList<Carta> pilhaDescarte = Jogo.Salvamento.Salvamento.carregarCartas(estado.pilhaDescarte);
                batalha.Luta(pilhaCompra, pilhaDescarte, estado.roundAtual);

                if (batalha.jogadorSaiu() == 0 && estado.heroi.estaVivo()) {
                    estado.heroi.limparFimLuta();
                    batalha.iniciar(pilhaCompra, pilhaDescarte, torneioEstado);
                    if (batalha.jogadorSaiu() == 0 && estado.heroi.estaVivo()) {
                        Aux.limparTela();
                        System.out.println("\n🏆 PARABÉNS! VOCÊ É O NOVO CAMPEÃO DO UFC JAVA!");
                        Prints.PrintsMain.printVitoria();
                    }
                }

                inputs.close();
                return;
            }
        }

//----------------------------------INSTANCIAMENTO------------------------------------------
        Publisher juiz = new Publisher();
        String escolhaheroi = Heroi.escolherHeroi(inputs);
        Heroi heroi = new Heroi(escolhaheroi, 130, 0, 0);
        Batalha torneio = new Batalha(heroi, juiz, inputs, null);
        

//----------------------------------BARALHO E PILHAS------------------------------------------
        ArrayList<Carta> baralho = Jogo.Aux.gerarBaralhoInicial();
        ArrayList<Carta> pilhaCompra = new ArrayList<>(baralho);
        ArrayList<Carta> pilhaDescarte = new ArrayList<>();
        
//----------------------------------TORNEIO------------------------------------------        
        torneio.iniciarTorneio(pilhaCompra, pilhaDescarte);

        if (torneio.jogadorSaiu() == 0 && heroi.estaVivo()) {
            Aux.limparTela();
            System.out.println("\n🏆 PARABÉNS! VOCÊ É O NOVO CAMPEÃO DO UFC JAVA!");
            Prints.PrintsMain.printVitoria();
        }

        inputs.close();
    }
}