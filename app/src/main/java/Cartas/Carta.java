package Cartas;
import Entidades.Entidade;

/**
 * Classe base para todas as cartas utilizadas durante o combate.
 */
public abstract class Carta {
    protected String nome ;
    protected int custo; 
    /** Texto resumido da acao da carta. */
    protected String descricao;

    /**
     * Construtor da classe Carta.
     *
     * @param nome nome da carta
     * @param custo custo de energia para usar a carta
     * @param descricao descricao da acao da carta
     */
    public Carta(String nome, int custo, String descricao) {
        this.nome = nome;
        this.custo = custo;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Usa a carta no alvo especificado.
     *
     * @param alvo a entidade alvo da carta
     * @return o valor do efeito aplicado
     */
    public abstract int usar(Entidade alvo);

    /**
     * Imprime os dados da carta no menu de rodada.
     *
     * @param indice posicao da carta na mao
     */
    public abstract void printRodada (int indice);

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCusto() {
        return custo;
    }

    public void setCusto(int custo) {
        this.custo = custo;
    }
}
