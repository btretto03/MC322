public class CartaEscudo extends Carta {

    public CartaEscudo(String nome, int custo) {
        super(nome, custo);
    }
    
    public void usar(Heroi heroi, Inimigo inimigo) {
    heroi.ganharEscudo(3);
    System.out.println("✨ Defesa ativada: " + this.getNome());
    }

    public void printRodada (int indice){
        System.out.println( String.format("[%d] 🛡️ %s (Custo: %d)", indice, this.getNome(), this.getCusto()));
    }
}
