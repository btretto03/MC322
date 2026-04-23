# 🥋 Manual do Jogo: Ultimate Fighting Java Championship

Bem-vindo ao Octógono JAVA! Este é um manual que contém todas as informações necessárias para você entender as mecânicas, gerenciar seu lutador e vencer a luta.

## 🥊 Modos de Jogo
Logo ao iniciar a execução, você poderá optar entre:
* **Novo jogo:** começa uma nova campanha.
* **Carregar jogo salvo:** carrega o último estado salvo (luta + torneio).

## 🏆 Torneio (árvore de desafios)
Ao começar uma campanha, você avança por uma árvore de desafios escolhendo o próximo oponente.
O jogo usa suas escolhas para montar a próxima luta (e em certas fases existe um “lutador ignorado”, mostrado em eventos específicos).

## 💾 Salvamento e carregament
Quando você escolhe carrega um jogo salvo, o jogo:
1. Restaura a luta do ponto exato do save.
2. Após terminar a luta carregada, continua o torneio do mesmo ponto.

## ⚙️ Dinâmica de Turnos e Baralho
O combate é inspirado em jogos de construção de deck (como *Slay the Spire*). 

1. **Mão do Jogador:** A cada rodada, você saca uma quantidade específica de cartas da sua **Pilha de Compra**.
2. **Energia:** Toda carta tem um custo. Você precisa gerenciar sua estamina/energia no turno para decidir se vai focar em atacar, defender ou se curar.
3. **Fim do Turno:** Ao encerrar sua jogada, todas as cartas que sobraram na sua mão (usadas ou não) vão para a **Pilha de Descarte**.
4. **Descarte** Quando a Pilha de Compra esvazia, a Pilha de Descarte é reembaralhada para formar uma nova Pilha de Compra.

O inimigo possui uma inteligência própria: ele analisa o cenário da luta. Se a sua vida estiver muito alta ou muito baixa, ele será agressivo e priorizará ataques. Se a vida dele estiver em risco, ele priorizará cartas de defesa.

## 🃏 Cartas de Combate
O arsenal do jogo conta com um total de 22 cartas, divididas entre ataques e defesas:
**Cartas de Dano (Ataque)**
* Gancho de direita
* Gancho de esquerda
* Jab
* Direto
* Cruzado de direita
* Cruzado de esquerda
* Joelhada
* Cotovelada
* Chute alto
* Chute baixo
* Chute frontal
* Chute brasileiro
* Guilhotina
* Voadora

**Cartas de Escudo (Defesa)**
* Bloqueio
* Guarda alta
* Guarda baixa
* Esquivo para direita
* Esquivo para esquerda
* Esquivo para trás
* Correr
* Clinch
---

## 🔥 Sistema de Fúria e Efeitos Especiais
A cada ataque bem-sucedido (uso de carta de dano), o seu lutador acumula **1 ponto de Fúria**.

* **Carga Máxima:** 3 pontos de Fúria.
* **Uso:** Ao atingir o máximo, você pode gastar essa Fúria para realizar um **Efeito Especial** no próximo golpe, alterando o rumo da luta por até **3 turnos consecutivos**.

### Lista de Efeitos
| Ícone | Efeito | O que faz |
| :---: | :--- | :--- |
| 🩸 | **Sangramento** | Aplica dano contínuo. O alvo perde uma quantia fixa de HP ao final de cada rodada. |
| 🗣️ | **Provocação** | Quebra a guarda. Reduz a eficiência dos escudos gerados pelo oponente. |
| 💉 | **Adrenalina** | Aplicado no próprio herói. Garante cura e recuperação de pontos de vida a cada rodada. |
| ☠️ | **Nocaute** | Golpe de sorte! Tem **10% de chance** de eliminar o inimigo instantaneamente no final do round (válido tanto no 1v1 quanto no 1v2, onde a chance de nocaute vai para 20%). |
---

🔙 **[Voltar para a página principal (README)](README.md)**