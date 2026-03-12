public class Inimigo extends Entidade {
    private int forca = 0;
    
    public Inimigo(String nome, int vida, int escudo) { //construtor
        super(nome, vida, escudo);
    }
    
    //Métodos
    public void atacar(Heroi atacado) {
        this.forca = (int) (Math.random() * 5) + 6; //numero aleatorio para o ataque no range de 6 até 10
        atacado.receberDano(this.forca);
    }    
}
