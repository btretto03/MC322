public class Carta {
    protected String nome ;
    protected int custo; 
    
    public Carta(String nome, int custo) {
        this.nome = nome;
        this.custo = custo;
    }

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
