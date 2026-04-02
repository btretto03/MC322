package Entidades;

import java.util.ArrayList;
import Efeitos.Efeitos;
import Jogo.Publisher;

/**
 * Classe abstrata de molde para qualquer participante de combate (heroi ou inimigo).
 */
public abstract class Entidade {
    protected String nome; 
    protected int vida;
    protected int escudo = 0;
    protected ArrayList<Efeitos> listaEfeitos = new ArrayList<>();

    public Entidade (String nome, int vida, int escudo){
        this.nome = nome;
        this.vida = vida;
        this.escudo = escudo;
    }

    //Métodos
    /**
     * Aplica dano considerando absorcao de escudo antes da vida.
     *
     * @param dano dano bruto recebido
     */
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

    public void ganharEscudo(int escudo) {
        this.escudo += escudo;
    }   

    /**
     * Adiciona um efeito na entidade ou acumula em um efeito existente.
     *
     * @param efeito efeito a adicionar
     * @param juiz publicador de notificacoes dos efeitos
     */
    public void adicionarEfeito(Efeitos efeito, Publisher juiz) {
        for (int i = 0; i < this.listaEfeitos.size(); i ++) {
            Efeitos efeitoLista = listaEfeitos.get(i);
            if (efeitoLista.getNome().equals(efeito.getNome())) {
                int soma = efeitoLista.getAcumulos() + efeito.getAcumulos();
                efeitoLista.setAcumulos(soma);
                return;
            }
        }
        this.listaEfeitos.add(efeito);
        juiz.inscrever(efeito);
    }

    //Getters e setters
    public String getNome() {
        return this.nome;
    }   

    public ArrayList<Efeitos> getListaEfeitos() {
        return listaEfeitos;
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
}
