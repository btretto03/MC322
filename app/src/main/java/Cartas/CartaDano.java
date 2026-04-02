package Cartas;
import Entidades.Entidade;

/**
 * Carta ofensiva que causa dano direto ao alvo proporcional ao custo.
 */
public class CartaDano extends Carta{

    public CartaDano(String nome, int custo) {
        super(nome, custo, "Causando dano");
    }

    @Override
    public int usar(Entidade alvo) {
        int dano = this.getCusto() * 2;
        alvo.receberDano(dano);
        return dano;
    }

    /**
     * Imprime os dados da carta no menu de rodada.
     *
     * @param indice posicao da carta na mao
     */
    public void printRodada (int indice){
        System.out.println(String.format("[%d] ⚔️ %s (Custo: %d | Dano: %d)", indice, this.getNome(), this.getCusto(), this.getCusto() * 2));
    }
}
