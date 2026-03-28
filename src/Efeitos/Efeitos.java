package Efeitos;

import Entidades.Entidade;

public abstract class Efeitos {
    protected String nome;
    protected int acumulos;
    protected Entidade dono;

    public Efeitos(String nome, int acumulos, Entidade dono) {
        this.nome = nome;
        this.acumulos = acumulos;
        this.dono = dono;
    }
    public abstract void aplicarEfeito();
    public String getString() {
        return this.nome;
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
