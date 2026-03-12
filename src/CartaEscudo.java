public class CartaEscudo extends Carta {

    public CartaEscudo(String nome, int custo) {
        super(nome, custo);
    }
    
    @Override
    public void usar(Entidade heroi) {
        heroi.setEscudo(heroi.getEscudo() + 3);
    }
}
