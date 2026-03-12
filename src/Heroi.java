public class Heroi extends Entidade {
    private int energia;
    
    public Heroi(String nome, int vida, int escudo) { //construtor
        super(nome, vida, escudo);
    }

    //Métodos
    public void ganharEscudo(int escudo) {
        this.escudo += escudo;
    }
    
    //Getters e Setters
    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }   
}
