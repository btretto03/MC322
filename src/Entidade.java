public class Entidade {
    protected String nome; 
    protected int vida;
    protected int escudo;

    public Entidade (String nome, int vida, int escudo){
        this.nome = nome;
        this.vida = vida;
        this.escudo = escudo;
    }

    //Getters e setters
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

    public void receberDano (int dano) {
        int escudoAtual = this.getEscudo();
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

    public boolean estaVivo() {
        if(this.getVida() > 0) {
            return true;
        } else {
            return false;
        }
    }
}