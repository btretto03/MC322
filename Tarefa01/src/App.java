import java.util.Scanner;

public class App {
    public static void main(String[] args)  {
        Scanner inputs = new Scanner(System.in); //leitura de dados do usuário
        String[] Ataques = {"Cruzado de direita", "Gancho de direita", "Gancho de esquerda", "Uppercut", "Cruzado de esquerda"}; //pensei nisso pra quando formos printar que ele usou um ataque fazer um loop nessa lista, só pra ficar uns nomes diferentes... mesma coisa na de baixo
        String[] Escudos = {"Esquivo para direita", "Bloqueio", "Esquivo para esquerda", "Esquivo para trás"};
        Heroi alex = new Heroi();
        Inimigo jon = new Inimigo();
        CartaDano cartadano = new CartaDano();
        CartaEscudo cartaescudo = new CartaEscudo();

        while(true) {
            alex.setEnergia(3); //energia do poatan é resetada a cada turno

            if (alex.estaVivo() == true && jon.estaVivo() == true) { //Os dois vivos
                
            } else if (alex.estaVivo() == true && jon.estaVivo() == false) { //Jon morreu

            } else if (alex.estaVivo() == false && jon.estaVivo() == true){ //Alex morreu
                    
            }
            

        }
        inputs.close(); 
    }  
}
