# Minha Universidade — Versão CLI (Java puro)

Aplicação interativa de terminal escrita em Java puro, sem framework e sem banco de dados. Todos os dados vivem em memória durante a execução.

Implementa as quatro questões da atividade. Ao iniciar, o estado é populado com o cenário da Questão 4 (3 disciplinas, 1 professor, 3 alunos, 2 visitantes e 3 turmas), e um menu interativo permite manipular as entidades.

## Requisitos

- Java 21+

## Como rodar

A partir desta pasta:

```bash
javac *.java
java Main
```

## Menu

```
 1) Adicionar visitante
 2) Adicionar aluno regular
 3) Adicionar aluno bolsista
 4) Adicionar professor
 5) Adicionar disciplina
 6) Criar turma
 7) Matricular aluno em turma
 8) Remover aluno de turma
 9) Listar visitantes
10) Listar alunos
11) Listar professores
12) Listar disciplinas
13) Listar turmas (com detalhes)
14) Calcular mensalidade de um aluno
15) Professor dar aula
16) Incrementar idade (fazer aniversário)
 0) Sair
```

Operações que envolvem associação (criar turma, matricular aluno, calcular mensalidade etc.) apresentam uma listagem numerada e o usuário escolhe pelo índice. `0` cancela a seleção.

## Estrutura

```
universidade-cli/
├── Pessoa.java          (Q1, Q2) abstrata + fazerAniversario()
├── Visitante.java       (Q3) extends Pessoa
├── Aluno.java           (Q3) abstrata, extends Pessoa
├── Bolsista.java        (Q3) extends Aluno, override pagarMensalidade()
├── Regular.java         (Q3) extends Aluno, override pagarMensalidade()
├── Professor.java       (Q3) extends Pessoa, com darAula()
├── Disciplina.java      (Q1) classe simples
├── Turma.java           (Q4) composição com List<Aluno>
└── Main.java            menu interativo + seed do cenário Q4
```

## Mapeamento das questões

| Questão | Conceito | Onde está |
|---|---|---|
| 1 | Classes e Objetos | `Pessoa` (abstrata), `Disciplina` |
| 2 | Encapsulamento | atributos `private` + `fazerAniversario()` em `Pessoa` |
| 3 | Herança e Polimorfismo | `Visitante`, `Aluno`, `Professor` extends `Pessoa`; `Bolsista`, `Regular` extends `Aluno` com override de `pagarMensalidade()` |
| 4 | Associação e Composição | `Turma` agrega `Disciplina`, `Professor` e `List<Aluno>` com `adicionarAluno`, `removerAluno`, `listarAlunos`; `Main` instancia o cenário e oferece o menu |

## Diferença para a versão API

| | CLI (esta pasta) | API REST (raiz do repo) |
|---|---|---|
| Persistência | RAM (some ao fechar) | PostgreSQL (volume Docker) |
| Interface | Menu de terminal | HTTP REST (JSON) |
| Framework | Nenhum (Java puro) | Spring Boot + JPA |
| Build | `javac` | Maven |
| Deploy | Local apenas | VPS via GitHub Actions + Traefik |

Para a versão completa em REST + Postgres + Docker, veja o [README principal](../README.md).
