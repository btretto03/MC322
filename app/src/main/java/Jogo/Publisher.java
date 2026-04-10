package Jogo;

import java.util.ArrayList;
import Efeitos.Nocaute;
import Efeitos.Subscriber;

/**
 * Publicador simples para notificar subscribers de efeitos por round.
 */
public class Publisher {
    private ArrayList<Subscriber> inscritos = new ArrayList<>();

    /**
     * Inscreve um subscriber para receber notificacao ao final de cada round.
     *
     * @param subs subscriber a ser inscrito
     */
    public void inscrever(Subscriber subs) {
        if (!inscritos.contains(subs)) {
            inscritos.add(subs);
        }
    }

    /**
     * Remove um subscriber da lista de notificacao.
     *
     * @param subs subscriber a ser removido
     */
    public void desinscrever(Subscriber subs) {
        inscritos.remove(subs);
    }
    
    /**
     * Notifica todos os inscritos em ordem reversa de cadastro.
     */
    public void notificarSubscribers() {
        for (int i = inscritos.size() - 1; i >= 0; i--) {
            Subscriber inscrito = inscritos.get(i); 
            if (inscrito instanceof Nocaute nocaute){
                inscrito.serNotificado();
                if (nocaute.getNocauteado()){
                    break;
                } else{
                    desinscrever(nocaute);
                }
            } else{
                inscrito.serNotificado();
            }
        }
    }
}
