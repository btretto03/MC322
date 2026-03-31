package Efeitos;

public class Adrenalina extends Efeitos {
    
    public Adrenalina(String nome, int acumulos, Entidades.Entidade dono) {
        super(nome, acumulos, dono);
    }

    @Override
    public void aplicarEfeito() {
        int vidaAtual = dono.getVida();
        int novaVida = vidaAtual + this.acumulos;
        if (novaVida > 30) {
            novaVida = 30;
        }
        dono.setVida(novaVida);
        this.acumulos--;
    }
    
}
