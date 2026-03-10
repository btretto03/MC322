import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args)  {
        Scanner inputs = new Scanner(System.in); //leitura de dados do usuário

        String[] nomeAtaques = {"Cruzado de direita", "Gancho de direita", "Gancho de esquerda", "Uppercut", "Cruzado de esquerda"}; //pensei nisso pra quando formos printar que ele usou um ataque fazer um loop nessa lista, só pra ficar uns nomes diferentes... mesma coisa na de baixo
        List<CartaDano> Ataques = new ArrayList<>();
        for(int i = 0; i < nomeAtaques.length; i++){
            int custo = (int) (Math.random() * 3) + 1;
            Ataques.add(new CartaDano(nomeAtaques[i], custo));
        }
        
        String[] nomeEscudos = {"Esquivo para direita", "Bloqueio", "Esquivo para esquerda", "Esquivo para trás"};
        List<CartaEscudo> Escudos = new ArrayList<>();
        for(int i = 0; i < nomeEscudos.length; i++){
            int custo = (int) (Math.random() * 3) + 1;
            Escudos.add(new CartaEscudo(nomeEscudos[i], custo));
        }

        Heroi alex = new Heroi();
        Inimigo jon = new Inimigo();

        while(true) {
            alex.setEnergia(3); //energia do poatan é resetada a cada turno

            if (alex.estaVivo() == true && jon.estaVivo() == true) { //Os dois vivos
                String vida = String.format("Alex está com %d de vida", alex.getVida());
                System.out.println(vida);

                while (alex.getEnergia() > 0) {
                    System.out.println("Energia disponpivel: " + alex.getEnergia());

                    int i = 0;
                    for(; i < Ataques.size(); i++){ 
                        CartaDano ataqueAtual = Ataques.get(i);
                        if (ataqueAtual.getCusto() <= alex.getEnergia()){
                            String ataqueDisponivel = String.format("%d - %s - Custo: %d", i, ataqueAtual.getNome(), ataqueAtual.getCusto());
                            System.out.println(ataqueDisponivel);
                        }
                    }
                        
                    for(int j = 0; j < Escudos.size(); j++){ 
                        CartaEscudo escudoAtual = Escudos.get(j);
                        if (escudoAtual.getCusto() <= alex.getEnergia()){
                            String ataqueDisponivel = String.format("%d - %s - Custo: %d", j + i, escudoAtual.getNome(), escudoAtual.getCusto());
                            System.out.println(ataqueDisponivel);
                        }
                    }

                    int num = inputs.nextInt();

                    if (num >= i){
                        CartaEscudo escudoEscolhido = Escudos.get(num - i);
                        escudoEscolhido.usar(alex);
                        alex.setEnergia(alex.getEnergia() - escudoEscolhido.getCusto());
                        Escudos.remove(num - i);
                    } else if (num >= 0 || num <= i){
                        CartaDano ataqueEscolhido = Ataques.get(num);
                        ataqueEscolhido.usar(jon);
                        alex.setEnergia(alex.getEnergia() - ataqueEscolhido.getCusto());
                        Ataques.remove(num);
                    } else{
                        System.out.println("Selecione um valor válido.");
                        continue;
                    }
                    
                    // break;
                }

            break;

            } else if (alex.estaVivo() == true && jon.estaVivo() == false) { //Jon morreu
                break;
            } else if (alex.estaVivo() == false && jon.estaVivo() == true){ //Alex morreu
                break;
            }
            

        }
        inputs.close(); 
    }  
}
