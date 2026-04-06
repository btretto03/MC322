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

    /**
     * Retorna a descricao da carta.
     *
     * @return a descricao da carta
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descricao da carta.
     *
     * @param descricao a nova descricao da carta
     */
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

    /**
     * Retorna o nome da carta.
     *
     * @return o nome da carta
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da carta.
     *
     * @param nome o novo nome da carta
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o custo da carta.
     *
     * @return o custo da carta
     */
    public int getCusto() {
        return custo;
    }

    /**
     * Define o custo da carta.
     *
     * @param custo o novo custo da carta
     */
    public void setCusto(int custo) {
        this.custo = custo;
    }
}
