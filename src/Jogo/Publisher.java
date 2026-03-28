package Jogo;

import java.util.ArrayList;
import Efeitos.Subscriber;

public class Publisher {
    private ArrayList<Subscriber> inscritos = new ArrayList<>();

    public void inscrever(Subscriber subs) {
        if (!inscritos.contains(subs)) {
            inscritos.add(subs);
        }
    }
    public void desinscrever(Subscriber subs) {
        inscritos.remove(subs);
    }
    
    public void notificarSubscribers() {
        for (int i = inscritos.size() - 1; i >= 0; i--) {
            inscritos.get(i).serNotificado();
        }
    }
}
