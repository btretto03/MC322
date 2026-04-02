package Efeitos;

/**
 * Contrato para objetos que reagem ao final do round.
 */
public interface Subscriber {
    void serNotificado(); //avisa quando o round acaou
}