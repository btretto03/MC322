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

    public abstract int usar(Entidade alvo);

    public abstract void printRodada (int indice);

    //getters e setters
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
