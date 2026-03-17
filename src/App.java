import java.util.ArrayList;
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
        String[] nomeCartas = {"Cruzado de direita", "Gancho de direita", "Gancho de esquerda",  "Cruzado de esquerda", "Jab", "Direto", "Chute baixo", "Chute frontal", "Guilhotina", "Voadora", "Esquivo para direita", "Bloqueio", "Esquivo para esquerda", "Esquivo para trás", "Guarda alta", "Guarda baixa", "Correr",};
        int custo = 1;

        for (int i = 0; i < nomeCartas.length; i++) {
            if (i < 10) {
                Baralho.add(new CartaDano(nomeCartas[i], custo));
            } else {
                Baralho.add(new CartaEscudo(nomeCartas[i], custo));
            }
            custo ++; 
            if (custo > 4) { //limita o custo das cartas em 4
                custo = 1; 
            }
        }

        System.out.println("🔥 A LUTA VAI COMEÇAR! 🔥\n");
        ArrayList <Carta> pilhaCompra = new ArrayList<>(Baralho);
        ArrayList <Carta> pilhaDescarte = new ArrayList<>();

        while(true) { //Loop da luta
            heroi.setEnergia(5); //energia do heroi é resetada a cada Round
            heroi.setEscudo(0); //resetando escudo a 0 em cada round

            ArrayList <Carta> mao = new ArrayList<>();
            for (int i = 0;i < 4; i ++) {
                if (pilhaCompra.size() ==0) {
                    System.out.println("🔄 Baralho vazio! Voltando o descarte para a pilha");
                    while (pilhaDescarte.size() > 0) {
                        Carta cartaAux = pilhaDescarte.remove(0);
                        pilhaCompra.add(cartaAux);
                    }
                }
                if (pilhaCompra.size() > 0) {
                    int cartaaleatoria = (int) (Math.random() * pilhaCompra.size());
                    mao.add(pilhaCompra.remove(cartaaleatoria));
                }
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

                while (heroi.getEnergia() > 0 && mao.size() > 0) {
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

                    System.out.println("Caso queira passar a vez digite -1"); //Tem que implementar isso aqui dps ver como fazer

                    int num = inputs.nextInt();

                    limparTela();


                    if (num == -1){
                        break;
                    }

                    Carta cartaEscolhida = mao.remove(num);
                    pilhaDescarte.add(cartaEscolhida);

                    if (cartaEscolhida instanceof CartaEscudo){
                        cartaEscolhida.usar(heroi);
                        heroi.setEnergia(heroi.getEnergia() - cartaEscolhida.getCusto());
                        
                        acoesDoRound.add("✨ Defesa ativada: " + cartaEscolhida.getNome());
                        System.out.println(); 
                        
                    } else if (cartaEscolhida instanceof CartaDano){
                        cartaEscolhida.usar(inimigo);
                        heroi.setEnergia(heroi.getEnergia() - cartaEscolhida.getCusto());
                        
                        acoesDoRound.add("💥 Ataque desferido: " + cartaEscolhida.getNome());
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
                while (mao.size() > 0) { //oq sobrou na mao
                    pilhaDescarte.add(mao.remove(0));
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