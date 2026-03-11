public class Heroi {
    private String nome; 
    private int vida;
    private int escudo;
    private int energia;
    
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
    
    public boolean estaVivo() {
        if(this.getVida() > 0) {
            return true;
        } else {
            return false;
        }
    }

    //getters e setters
    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getVida() {
        return this.vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getEscudo() {
        return this.escudo;
    }

    public void setEscudo(int escudo) {
        this.escudo = escudo;
    }

    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }
    
}
