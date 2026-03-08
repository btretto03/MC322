public class CartaEscudo {
    private String nome = "Escudo(pensar em um nome melhor)";
    private int custo = 2; // dnv podemos mudar

    public CartaEscudo(String nome, int custo) {
        this.nome = nome;
        this.custo = custo;
    }
    public void usar() {

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
