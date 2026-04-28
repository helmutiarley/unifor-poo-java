# Minha Universidade API

API REST em Java para gerenciar pessoas (visitantes, alunos, professores), disciplinas e turmas. Desenvolvida como atividade final da disciplina N685 — Programação Orientada a Objetos (Unifor).

Produção: https://unifor.helmutiarley.cloud

## Sumário

- [Stack](#stack)
- [Requisitos](#requisitos)
- [Executando localmente](#executando-localmente)
- [Configuração](#configuração)
- [Endpoints](#endpoints)
- [Estrutura](#estrutura)
- [Deploy](#deploy)
- [Versão CLI](#versão-cli)

## Stack

- Java 21
- Spring Boot 3.3 (Web + Data JPA)
- PostgreSQL 16
- Maven
- Docker + Docker Compose
- Traefik v3 (reverse proxy + Let's Encrypt em produção)
- GitHub Actions (CI/CD)

## Requisitos

Para desenvolvimento local, basta ter Docker e Docker Compose instalados. Para rodar a aplicação fora de container, é necessário Java 21 e Maven.

## Executando localmente

Subir a stack completa (API + banco) em containers:

```bash
docker compose up --build
```

A API fica disponível em `http://localhost:8080`.

Alternativamente, com o banco em container e a aplicação no IDE ou via Maven:

```bash
docker compose up -d postgres
mvn spring-boot:run
```

Na primeira inicialização, a aplicação executa o `DataSeeder`, que popula o banco com o cenário base da atividade: três disciplinas, um professor, três alunos (dois regulares e um bolsista), dois visitantes e três turmas.

## Configuração

As variáveis de ambiente são opcionais em desenvolvimento. Em produção, ao menos `POSTGRES_PASSWORD` deve ser definida.

| Variável | Padrão | Descrição |
|---|---|---|
| `POSTGRES_PASSWORD` | `postgres` | Senha do PostgreSQL |
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://localhost:5432/universidade` | URL JDBC do banco |
| `SPRING_DATASOURCE_USERNAME` | `postgres` | Usuário do banco |
| `SPRING_DATASOURCE_PASSWORD` | `postgres` | Senha (use `POSTGRES_PASSWORD` quando rodar via compose) |

Um arquivo `.env.example` está disponível como ponto de partida:

```bash
cp .env.example .env
```

## Endpoints

Todos os recursos são expostos sob o prefixo `/api` e respondem JSON, incluindo erros. As subclasses de `Pessoa` (`Visitante`, `Professor`, `Bolsista`, `Regular`) são serializadas com um campo discriminador `tipo`.

### Formato de erro

Todos os erros seguem o mesmo formato:

```json
{
  "timestamp": "2026-04-28T21:00:00Z",
  "status": 404,
  "error": "Não encontrado",
  "message": "Aluno com id 99 não encontrado."
}
```

Status retornados: `400` para corpo inválido, `404` para recurso ou rota inexistente, `500` para falhas internas.

### Visitantes

```
POST   /api/visitantes            cria visitante
GET    /api/visitantes            lista visitantes
GET    /api/visitantes/{id}       busca por id
```

Request `POST /api/visitantes`:

```json
{
  "cpf": "555.555.555-55",
  "nome": "Pedro Visitante",
  "idade": 30
}
```

Response (recurso completo):

```json
{
  "tipo": "VISITANTE",
  "id": 4,
  "cpf": "555.555.555-55",
  "nome": "Pedro Visitante",
  "idade": 30
}
```

### Alunos

```
POST   /api/alunos/regular            cria aluno regular
POST   /api/alunos/bolsista           cria aluno bolsista
GET    /api/alunos                    lista todos
GET    /api/alunos/{id}               busca por id
GET    /api/alunos/{id}/mensalidade   retorna o valor da mensalidade
```

Request `POST /api/alunos/regular` ou `POST /api/alunos/bolsista`:

```json
{
  "cpf": "222.222.222-22",
  "nome": "Ana Lima",
  "idade": 20,
  "matricula": "2026A001"
}
```

Response (recurso completo, `tipo` reflete a classe concreta):

```json
{
  "tipo": "REGULAR",
  "id": 1,
  "cpf": "222.222.222-22",
  "nome": "Ana Lima",
  "idade": 20,
  "matricula": "2026A001"
}
```

Response `GET /api/alunos/{id}/mensalidade`:

```json
{
  "id": 1,
  "nome": "Ana Lima",
  "tipo": "Regular",
  "valor": 1500.0
}
```

### Professores

```
POST   /api/professores               cria professor
GET    /api/professores               lista professores
GET    /api/professores/{id}          busca por id
GET    /api/professores/{id}/dar-aula invoca darAula()
```

Request `POST /api/professores`:

```json
{
  "cpf": "111.111.111-11",
  "nome": "Carlos Souza",
  "idade": 45,
  "centro": "CCT"
}
```

Response (recurso completo):

```json
{
  "tipo": "PROFESSOR",
  "id": 1,
  "cpf": "111.111.111-11",
  "nome": "Carlos Souza",
  "idade": 45,
  "centro": "CCT"
}
```

Response `GET /api/professores/{id}/dar-aula`:

```json
{
  "mensagem": "Professor Carlos Souza está dando aula no centro CCT."
}
```

### Disciplinas

```
POST   /api/disciplinas           cria disciplina
GET    /api/disciplinas           lista disciplinas
GET    /api/disciplinas/{id}      busca por id
```

Request `POST /api/disciplinas`:

```json
{
  "codigo": "N685",
  "nome": "Programação Orientada a Objetos",
  "semestre": 3
}
```

Response:

```json
{
  "id": 1,
  "codigo": "N685",
  "nome": "Programação Orientada a Objetos",
  "semestre": 3
}
```

### Turmas

```
POST   /api/turmas                          cria turma
GET    /api/turmas                          lista turmas
GET    /api/turmas/{id}                     busca por id
GET    /api/turmas/{id}/alunos              lista alunos da turma
POST   /api/turmas/{id}/alunos/{alunoId}    matricula aluno
DELETE /api/turmas/{id}/alunos/{alunoId}    desmatricula aluno
```

Request `POST /api/turmas` (referencia disciplina e professor por id):

```json
{
  "codigo": "T-POO-2026.1",
  "disciplinaId": 1,
  "professorId": 1
}
```

Response (recurso completo, com objetos aninhados):

```json
{
  "id": 1,
  "codigo": "T-POO-2026.1",
  "disciplina": {
    "id": 1,
    "codigo": "N685",
    "nome": "Programação Orientada a Objetos",
    "semestre": 3
  },
  "professor": {
    "tipo": "PROFESSOR",
    "id": 1,
    "cpf": "111.111.111-11",
    "nome": "Carlos Souza",
    "idade": 45,
    "centro": "CCT"
  },
  "alunos": [
    {
      "tipo": "REGULAR",
      "id": 1,
      "cpf": "222.222.222-22",
      "nome": "Ana Lima",
      "idade": 20,
      "matricula": "2026A001"
    },
    {
      "tipo": "BOLSISTA",
      "id": 3,
      "cpf": "444.444.444-44",
      "nome": "Maria Costa",
      "idade": 19,
      "matricula": "2026B001"
    }
  ]
}
```

`POST` e `DELETE` em `/{id}/alunos/{alunoId}` retornam a turma completa atualizada.

### Pessoas

```
PATCH  /api/pessoas/{id}/aniversario   incrementa a idade de qualquer pessoa
```

Não há corpo de requisição. A resposta é o recurso da pessoa atualizado, com a `idade` incrementada e o campo `tipo` indicando a subclasse concreta:

```json
{
  "tipo": "REGULAR",
  "id": 1,
  "cpf": "222.222.222-22",
  "nome": "Ana Lima",
  "idade": 21,
  "matricula": "2026A001"
}
```

### Exemplos com curl

Criar um aluno regular:

```bash
curl -X POST http://localhost:8080/api/alunos/regular \
  -H "Content-Type: application/json" \
  -d '{"cpf":"123.456.789-00","nome":"Lucas","idade":21,"matricula":"2026A100"}'
```

Criar uma turma associando disciplina e professor existentes:

```bash
curl -X POST http://localhost:8080/api/turmas \
  -H "Content-Type: application/json" \
  -d '{"codigo":"T-ES-2026.1","disciplinaId":4,"professorId":1}'
```

Matricular um aluno em uma turma:

```bash
curl -X POST http://localhost:8080/api/turmas/4/alunos/4
```

## Estrutura

```
src/main/java/com/unifor/universidade/
├── MinhaUniversidadeApplication.java
├── model/
│   ├── Pessoa.java         classe abstrata, base da hierarquia
│   ├── Visitante.java
│   ├── Aluno.java          abstrata
│   ├── Bolsista.java
│   ├── Regular.java
│   ├── Professor.java
│   ├── Disciplina.java
│   └── Turma.java
├── repository/             interfaces JpaRepository
├── service/                regras de negócio
├── controller/             controllers REST
├── exception/              NotFoundException e GlobalExceptionHandler
├── dto/                    objetos de requisição
└── config/
    └── DataSeeder.java     popula o banco no primeiro start
```

A hierarquia de pessoas é mapeada via `@Inheritance(SINGLE_TABLE)` com coluna discriminadora. `Aluno` é uma classe abstrata cuja implementação concreta é fornecida por `Bolsista` e `Regular`, que sobrescrevem `pagarMensalidade()`. `Turma` mantém uma associação `@ManyToMany` com `Aluno`.

## Deploy

O deploy em produção é automatizado via GitHub Actions. A cada push para `main`, o workflow se conecta ao VPS via SSH, atualiza o repositório, regenera o arquivo `.env` a partir dos secrets do GitHub e recria os containers via Docker Compose. O Traefik gerencia o roteamento HTTPS e a emissão automática do certificado pelo Let's Encrypt.

Secrets requeridos no repositório:

| Nome | Descrição |
|---|---|
| `SSH_KEY` | Chave privada SSH com acesso ao VPS |
| `POSTGRES_PASSWORD` | Senha do banco em produção |

## Versão CLI

Uma implementação alternativa em Java puro (sem framework, sem banco) está disponível em [`universidade-cli/`](./universidade-cli/). Atende ao escopo estrito da atividade: as quatro questões implementadas e a classe `Main` reproduzindo o cenário da Questão 4 com saída em terminal.
