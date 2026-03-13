import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
        public static void limparTela() {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
        public static Scanner inputs = new Scanner(System.in); //leitura de dados do usuário(definido como static para ser acessível em outros métodos e classes)
        public static void main(String[] args)  {

        System.out.println("\u001B[48;5;210m" + "                                                  " + "\u001B[0m"); //Print inicial
        System.out.println("\u001B[48;5;210m" + "   🥊 ULTIMATE FIGHTING JAVA CHAMPIONSHIP 🥊      " + "\u001B[0m");
        System.out.println("\u001B[48;5;210m" + "                                                  " + "\u001B[0m");
       
        String escolhaheroi = Heroi.escolherHeroi();
        String escolhainimigo = Inimigo.escolherInimigo();
        
        Heroi heroi = new Heroi(escolhaheroi, 30, 0); //definindo as classes
        Inimigo inimigo = new Inimigo(escolhainimigo, 30, 0);

        ArrayList<Carta> Baralho = new ArrayList<>();
        String[] nomeAtaques = {"Cruzado de direita", "Gancho de direita", "Gancho de esquerda",  "Cruzado de esquerda", "Jab", "Direto", "Chute baixo", "Chute frontal", "Guilhotina", "Voadora"};
        for (int i = 0; i < nomeAtaques.length; i ++){
            int custo = i + 1;
            Baralho.add(new CartaDano(nomeAtaques[i], custo));
        }
        
        String[] nomeEscudos = {"Esquivo para direita", "Bloqueio", "Esquivo para esquerda", "Esquivo para trás", "Guarda alta", "Guarda baixa", "Correr",};
        for (int i = 0; i < nomeEscudos.length; i ++){
            int custo = i + 1;
            Baralho.add(new CartaEscudo(nomeEscudos[i], custo));
        }
        System.out.println();
        System.out.println("🔥 A LUTA VAI COMEÇAR! 🔥\n");


        ArrayList <Carta> pilhaCompra = new ArrayList<>();
        ArrayList <Carta> pilhaDescarte = new ArrayList<>();
        pilhaCompra = Baralho;

        while(true) { //Loop da luta
            heroi.setEnergia(100); //energia do heroi é resetada a cada Round
            heroi.setEscudo(0); //resetando escudo a 0 em cada round

            ArrayList <Carta> mao = new ArrayList<>();
            for (int i = 0;i < 4; i ++) {
                int cartaaleatoria = (int) (Math.random() * Baralho.size());
                mao.add(pilhaCompra.remove(cartaaleatoria));
            }

            if (heroi.estaVivo() == true && inimigo.estaVivo() == true) { //Os dois vivos
                
                System.out.println("\u001B[48;5;210m" + "                                        " + "\u001B[0m");
                System.out.println("\u001B[48;5;210m" + "               NOVO ROUND               " + "\u001B[0m");
                System.out.println("\u001B[48;5;210m" + "                                        " + "\u001B[0m");
                
                String vidaHeroi = String.format("🟩 %s: ❤️  %d VIDA", escolhaheroi, heroi.getVida());
                String vidaInimigo = String.format("🟥 %s: ❤️  %d VIDA", escolhainimigo, inimigo.getVida());
                System.out.println(vidaHeroi);
                System.out.println(vidaInimigo + "\n");

                ArrayList<String> acoesDoRound = new ArrayList<>();

                while (heroi.getEnergia() > 0) {
                    System.out.println("🔋 Energia disponível: " + heroi.getEnergia() + "/5");
                    System.out.println("---------------------------------------");
                    System.out.println("Suas opções de ação:");
                    int i = 0;
                    for(; i < mao.size(); i ++){ 
                        Carta cartaAtual = mao.get(i);
                        cartaAtual.printRodada(i);
                    }
                        
                    System.out.println("---------------------------------------");
                    System.out.print("Escolha o número da carta: ");

                    //System.out.println("Caso queira passar a vez digite passar"); Tem que implementar isso aqui dps ver como fazer

                    int num = inputs.nextInt();
                    limparTela();

                    if (num >= i){
                        CartaEscudo escudoEscolhido = Escudos.get(num - i);
                        escudoEscolhido.usar(heroi);
                        heroi.setEnergia(heroi.getEnergia() - escudoEscolhido.getCusto());
                        
                        acoesDoRound.add("✨ Defesa ativada: " + escudoEscolhido.getNome());
                        System.out.println(); 
                        
                    } else if (num >= 0 || num <= i){
                        CartaDano ataqueEscolhido = Ataques.get(num);
                        ataqueEscolhido.usar(inimigo);
                        heroi.setEnergia(heroi.getEnergia() - ataqueEscolhido.getCusto());
                        
                        acoesDoRound.add("💥 Ataque desferido: " + ataqueEscolhido.getNome());
                        System.out.println(); 
                        
                    } else{
                        System.out.println("\nPor favor, Selecione um valor válido.\n");
                        continue;
                    }
                    
                }

                System.out.println(" 🥊SUAS AÇÕES NESSE ROUND🥊");
                for (int i = 0; i < acoesDoRound.size(); i++) {
                    System.out.println(acoesDoRound.get(i));
                }

                inimigo.atacar(heroi);
                System.out.println();
                System.out.println("Digite 0 para continuar a luta...");
                int continuar = inputs.nextInt();
                while (continuar != 0) { //Forçar o usuario digitar 0 para continuar para que mostre seus ataques
                    System.out.print("Valor inválido! Digite 0 para continuar: ");
                    continuar = inputs.nextInt();
                }
                limparTela();
            } 
            if (heroi.estaVivo() == true && inimigo.estaVivo() == false) { //Inimigo morreu
                Heroi.printHeroiVenceu(heroi);
                break;
            }
            if (heroi.estaVivo() == false && inimigo.estaVivo() == true){ //Heroi morreu
                Inimigo.printInimigoVenceu(inimigo);
                break;
            }
            

        }
        inputs.close(); 
    }  
}