public class CartaDano extends Carta{
   
    public CartaDano(String nome, int custo) {
        super(nome, custo);
    }

    @Override
    public void usar(Entidade inimigo) {  
        int dano = (int) (Math.random() * 5) + 3; //Gera um numero aleatorio para o dano no range de 3 até 7
        inimigo.receberDano(dano);
    }
}
