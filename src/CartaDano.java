public class CartaDano extends Carta{
   
    public CartaDano(String nome, int custo) {
        super(nome, custo);
    }

    public void usar(Heroi heroi, Inimigo inimigo) {
    int dano = (int) (Math.random() * 5) + 3;
    inimigo.receberDano(dano);
    System.out.println("💥 Ataque desferido: " + this.getNome());
    }

    public void printRodada (int indice){
        System.out.println( String.format("[%d] ⚔️ %s (Custo: %d)",indice, this.getNome(), this.getCusto()));
    }
}
