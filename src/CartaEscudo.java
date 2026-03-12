public class CartaEscudo extends Carta {

    public CartaEscudo(String nome, int custo) {
        super(nome, custo);
    }
    
    public void usar(Heroi heroi) {
        heroi.setEscudo(heroi.getEscudo() + 3);
    }
}
