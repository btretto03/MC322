public class CartaEscudo extends Carta {

    public CartaEscudo(String nome, int custo) {
        super(nome, custo);
    }
    
    @Override
    public void usar(Entidade heroi) {
        heroi.setEscudo(heroi.getEscudo() + 3);
    }

    public void printRodada (int indice){
        System.out.println( String.format("[%d] 🛡️ %s (Custo: %d)", indice, this.getNome(), this.getCusto()));
    }
}
