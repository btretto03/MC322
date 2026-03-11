import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args)  {
        Scanner inputs = new Scanner(System.in); //leitura de dados do usuário
        String escolhaheroi, escolhainimigo;

        System.out.println("==================================================");
        System.out.println("         🥊 BEM-VINDO AO CLUBE DA LUTA 🥊");
        System.out.println("==================================================");


        System.out.println();
        System.out.println("Escolha o seu lutador: "); //Escolha do heroi
        System.out.println("[1] 🗿 Alex Poatan \n[2] 🕷️  Anderson Silva\n[3] 🥋 Fabrício Werdun");
        int escolha = inputs.nextInt();
        switch (escolha) {
            case 1:
                escolhaheroi = "Alex Poatan";
                break;
            case 2:
                escolhaheroi = "Anderson Silva";
                break;
            case 3:
                escolhaheroi = "Fabrício Werdun";
                break;
            default:
                System.out.println("⚠️Escolha inválida. O lutador será Alex Poatan por padrão.");
                escolhaheroi = "Alex Poatan";
        }
         
        System.out.println("Escolha o seu inimigo: "); //Escolha do inimigo
        System.out.println("[1] 👻 Vitor Belfort\n[2] 🥊 Popó\n[3] 🦴 Jon Jones");
        int escolha2 = inputs.nextInt();
        switch (escolha2) {
            case 1:
                escolhainimigo = "Chael Sonnen";
                break;
            case 2:
                escolhainimigo = "Popó";
                break;
            case 3:
                escolhainimigo = "Jon Jones";
                break;
            default:
                System.out.println("⚠️Escolha inválida. O lutador será Popó por padrão.");
                escolhainimigo = "Popó";
        }

        Heroi heroi = new Heroi(escolhaheroi, 30, 0); //definindo as classes
        Inimigo inimigo = new Inimigo(escolhainimigo, 30, 0);

        String[] nomeAtaques = {"Cruzado de direita", "Gancho de direita", "Gancho de esquerda",  "Cruzado de esquerda"};
        List<CartaDano> Ataques = new ArrayList<>();
        for (int i = 0; i < nomeAtaques.length; i ++){
            int custo = i + 1;
            Ataques.add(new CartaDano(nomeAtaques[i], custo));
        }
        
        String[] nomeEscudos = {"Esquivo para direita", "Bloqueio", "Esquivo para esquerda", "Esquivo para trás"};
        List<CartaEscudo> Escudos = new ArrayList<>();
        for (int i = 0; i < nomeEscudos.length; i ++){
            int custo = i + 1;
            Escudos.add(new CartaEscudo(nomeEscudos[i], custo));
        }
        System.out.println("\n🔥 A LUTA VAI COMEÇAR! 🔥\n");
        while(true) {
            heroi.setEnergia(5); //energia do poatan é resetada a cada turno


            if (heroi.estaVivo() == true && inimigo.estaVivo() == true) { //Os dois vivos
                String vidaHeroi = String.format("Heroi está com %d de vida", heroi.getVida());
                String vidaInimigo = String.format("Inimigo está com %d de vida", inimigo.getVida());
                System.out.println(vidaHeroi + "\n");
                System.out.println(vidaInimigo + "\n");

                while (heroi.getEnergia() > 0) {
                    System.out.println("Energia disponpivel: " + heroi.getEnergia());

                    int i = 0;
                    for(; i < Ataques.size(); i++){ 
                        CartaDano ataqueAtual = Ataques.get(i);
                        if (ataqueAtual.getCusto() <= heroi.getEnergia()){
                            String ataqueDisponivel = String.format("%d - %s - Custo: %d", i, ataqueAtual.getNome(), ataqueAtual.getCusto());
                            System.out.println(ataqueDisponivel);
                        }
                    }
                        
                    for(int j = 0; j < Escudos.size(); j++){ 
                        CartaEscudo escudoAtual = Escudos.get(j);
                        if (escudoAtual.getCusto() <= heroi.getEnergia()){
                            String ataqueDisponivel = String.format("%d - %s - Custo: %d", j + i, escudoAtual.getNome(), escudoAtual.getCusto());
                            System.out.println(ataqueDisponivel);
                        }
                    }

                    int num = inputs.nextInt();

                    if (num >= i){
                        CartaEscudo escudoEscolhido = Escudos.get(num - i);
                        escudoEscolhido.usar(heroi);
                        heroi.setEnergia(heroi.getEnergia() - escudoEscolhido.getCusto());
                        
                    } else if (num >= 0 || num <= i){
                        CartaDano ataqueEscolhido = Ataques.get(num);
                        ataqueEscolhido.usar(inimigo);
                        heroi.setEnergia(heroi.getEnergia() - ataqueEscolhido.getCusto());
                        
                    } else{
                        System.out.println("Selecione um valor válido.");
                        continue;
                    }
                    
                }

                inimigo.atacar(heroi);

            } 
            if (heroi.estaVivo() == true && inimigo.estaVivo() == false) { //Inimigo morreu
                System.out.println("Heroi ganhou!!");
                break;
            }
            if (heroi.estaVivo() == false && inimigo.estaVivo() == true){ //Heroi morreu
                System.out.println("Inimigo ganhou!!");
                break;
            }
            

        }
        inputs.close(); 
    }  
}
