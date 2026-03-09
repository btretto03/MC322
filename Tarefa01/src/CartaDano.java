public class CartaDano {
    private String nome = "Golpe";
    private int custo = 2; //podemos mudar esses valores, só to colocando e depois com ele pronto testamos como ficar melhor da pra ver pra ser aleatorio tambem(achei legal como fiz no ataque do inimigo)

    /*public CartaDano(String nome, int custo) {
        this.nome = nome;
        this.custo = custo;
    }*/

    public void usar(Inimigo atacado) {  
        int dano = (int) (Math.random() * 5) + 3; //Gera um numero aleatorio para o dano no range de 3 até 7
        atacado.receberDano(dano);
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
