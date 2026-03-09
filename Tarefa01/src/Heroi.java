public class Heroi {
    private String nome = "Alex Poatan"; //#Chama
    private int vida = 20;
    private int escudo  = 0;
    
    public Heroi(String nome, int vida, int escudo) { //construtor
        this.nome = nome;
        this.vida = vida;
        this.escudo = escudo;
    }

    //Métodos
    public void receberDano (int dano) {
        int escudoAtual  = this.getEscudo();
        if(escudoAtual > 0) {
            int danoEfetivo = dano - escudoAtual;
            if (dano < escudoAtual){
                this.escudo -= dano;
            } else {
                this.vida -= danoEfetivo;
                this.escudo = 0;
            }
        } else {
            this.vida -= dano;
        }
    }

    public void ganharEscudo(int escudo) {
        this.escudo += escudo;
    }
    
    public boolean estaVivo() { //Filosófico
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
