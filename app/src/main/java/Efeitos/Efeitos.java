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

    public String getNome() { 
        return nome; 
    }

    public int getAcumulos() { 
        return acumulos;
     }

    public void setAcumulos(int acumulos) { 
        this.acumulos = acumulos; 
    }
}
