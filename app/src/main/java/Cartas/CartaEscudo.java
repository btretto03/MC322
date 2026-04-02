package Cartas;
import Entidades.Entidade;

/**
 * Carta defensiva que adiciona escudo ao alvo.
 */
public class CartaEscudo extends Carta {

    public CartaEscudo(String nome, int custo) {
        super(nome, custo, "adicionando escudo");
    }
    
    /**
     * Aplica escudo proporcional ao custo da carta.
     *
     * @param alvo entidade que recebera escudo
     * @return quantidade de escudo adicionada
     */
    @Override
    public int usar(Entidade alvo) {
        int escudo = this.getCusto() * 2;
        alvo.ganharEscudo(escudo);
        return escudo;
    }

    /**
     * Imprime os dados da carta no menu de rodada.
     *
     * @param indice posicao da carta na mao
     */
    public void printRodada (int indice){
        System.out.println( String.format("[%d] 🛡️ %s (Custo: %d | Escudo: %d)", indice, this.getNome(), this.getCusto(), this.getCusto() * 2));
    }
}
