public class Inimigo {
    private String nome = "Jon Jones"; //#Goat
    private int vida = 20;
    private int escudo  = 0;
    
    public Inimigo(String nome, int vida, int escudo) { //construtor
        this.nome = nome;
        this.vida = vida;
        this.escudo = escudo;
    }

    //Métodos
    public void receberDano (int dano) {

    }
    
    public void atacar() {

    }

    public boolean estaVivo() {
        if(this.getVida() > 0) {
            return true;
        } else {
            return false;
        }
    }

    //getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getEscudo() {
        return escudo;
    }

    public void setEscudo(int escudo) {
        this.escudo = escudo;
    }
    
}
