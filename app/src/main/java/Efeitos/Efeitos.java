package Efeitos;

import Entidades.Entidade;

/**
 * Classe base para efeitos aplicados em entidades.
 */
public abstract class Efeitos implements Subscriber {
    protected String nome;
    protected int acumulos;
    protected Entidade dono;

    /**
     * Construtor da classe Efeitos.
     *
     * @param nome nome do efeito
     * @param acumulos numero de acumulos do efeito
     * @param dono entidade dona do efeito
     */
    public Efeitos(String nome, int acumulos, Entidade dono) {
        this.nome = nome;
        this.acumulos = acumulos;
        this.dono = dono;
    }

    /**
     * Aplica o efeito na entidade.
     */
    public abstract void aplicarEfeito();
    
    /**
     * Retorna uma representacao em string do efeito.
     *
     * @return string representando o efeito
     */
    public String getString() {
        return this.nome + " " + this.acumulos + "x";
    }

    /**
     * Recebe notificacao do round e aplica o efeito.
     */
    @Override
    public void serNotificado() {
        aplicarEfeito();
    }    
    
    /**
     * Retorna o nome do efeito.
     *
     * @return o nome do efeito
     */
    public String getNome() { 
        return nome; 
    }

    /**
     * Retorna o numero de acumulos do efeito.
     *
     * @return o numero de acumulos
     */
    public int getAcumulos() { 
        return acumulos;
     }

    /**
     * Define o numero de acumulos do efeito.
     *
     * @param acumulos o novo numero de acumulos
     */
    public void setAcumulos(int acumulos) { 
        this.acumulos = acumulos; 
    }

}
