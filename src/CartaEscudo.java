public class CartaEscudo extends Carta {

    public CartaEscudo(String nome, int custo) {
        super(nome, custo);
    }
    
    @Override
    public int usar(Entidade alvo) {
    alvo.ganharEscudo(3);
    return 3;
    }

    public void printRodada (int indice){
        System.out.println( String.format("[%d] 🛡️ %s (Custo: %d)", indice, this.getNome(), this.getCusto()));
    }
}
