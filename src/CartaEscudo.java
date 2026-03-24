public class CartaEscudo extends Carta {

    public CartaEscudo(String nome, int custo) {
        super(nome, custo, "adicionando escudo");
    }
    
    @Override
    public int usar(Entidade alvo) {
        int escudo = this.getCusto() * 2;
        alvo.ganharEscudo(escudo);
        return escudo;
    }

    public void printRodada (int indice){
        System.out.println( String.format("[%d] 🛡️ %s (Custo: %d | Escudo: %d)", indice, this.getNome(), this.getCusto(), this.getCusto() * 2));
    }
}
