public class CartaDano extends Carta{
   
    public CartaDano(String nome, int custo) {
        super(nome, custo);
    }

    @Override
    public int usar(Entidade alvo) {
        int dano = this.getCusto() * 2;
        alvo.receberDano(dano);
        return dano;
    }

    public void printRodada (int indice){
        System.out.println(String.format("[%d] ⚔️ %s (Custo: %d | Dano: %d)", indice, this.getNome(), this.getCusto(), this.getCusto() * 2));
    }
}
