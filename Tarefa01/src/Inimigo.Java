public class Inimigo {
    private String nome = "Jon Jones"; //#Goat
    private int vida = 20;
    private int escudo  = 0;
    private int forca = 0;
    
    public Inimigo(String nome, int vida, int escudo) { //construtor
        this.nome = nome;
        this.vida = vida;
        this.escudo = escudo;
    }

    //Métodos
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

    public void atacar(Heroi atacado) {
        this.forca = 3 + nextInt(5); //numero aleatorio para o ataque no range de 3 até 7
        alvo.receberDano(this.forca);
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
