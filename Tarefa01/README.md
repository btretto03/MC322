# Laboratório 01 - MC322

**Desenvolvido por:**
* Bruno Antonio Tretto - RA: 268060
* João Felipe Denadai Madeira - RA: 258477

## 📌 Sobre o Projeto
O objetivo deste laboratório foi desenvolver um sistema de batalhas via terminal, fortemente inspirado na logística do jogo "Slay the Spire". Para isso aplicamos os conceitos iniciais da disciplina de Programação Orientada a Objetos (POO), como a construção de classes, a definição de atributos e métodos.

Para esta implementação, adaptamos a dinâmica de combate para o universo do UFC. O usuário pode escolher o seu lutador dentre as opções disponíveis para enfrentar o oponente. A lógica principal foi mantida: o jogador precisa gerenciar sua energia a cada turno para atacar ou levantar a guarda (representado pelas cartas de escudo), buscando nocautear o adversário antes de ser derrotado.

## 🚀 Como compilar e executar

O projeto foi feito para ser compilado e executado através de comandos, conforme solicitado. Para isso deve-se ter instalado:
* Java Development Kit instalado.
* Terminal compatível.

Posteriormente, compilar-se-á o código:
Na raiz do repositório, execute o comando abaixo. Ele gerará os arquivos compilados

```bash
javac -d bin $(find src -name "*.java")
java -cp bin App
```