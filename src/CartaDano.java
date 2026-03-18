public class CartaDano extends Carta{
   
    public CartaDano(String nome, int custo) {
        super(nome, custo);
    }

    @Override
    public int usar(Entidade alvo) {
    int dano = (int) (Math.random() * 5) + 3;
    alvo.receberDano(dano);
    return dano;
    }

    public void printRodada (int indice){
        System.out.println( String.format("[%d] ⚔️ %s (Custo: %d)",indice, this.getNome(), this.getCusto()));
    }
}
