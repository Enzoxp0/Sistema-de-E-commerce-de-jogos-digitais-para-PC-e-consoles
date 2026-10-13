# E-Commerce de Jogos Digitais (PC e Consoles) - API Administrativa

Sistema de E-commerce de jogos digitais para PC e consoles desenvolvido com **Quarkus**, **Hibernate ORM com Panache**, **PostgreSQL** e **Jakarta REST**.

---

## 📋 Atendimento aos Requisitos da Atividade

### 1. CRUDs Administrativos (Mínimo de 5 Implementados)
Todas as operações de **Cadastro (POST)**, **Consulta (GET por ID, Listagem e Filtros)**, **Edição (PUT)** e **Exclusão (DELETE)** foram implementadas com validação via Bean Validation e tratamento padronizado de erros (RFC 7807 Problem Details):

1. **`Cliente` (`/clientes`)**: Gestão de clientes gamers, nicknames, contatos e validação de documentos únicos.
2. **`Funcionario` (`/funcionarios`)**: Gestão de funcionários da loja, matrículas, cargos e salários.
3. **`Plataforma` (`/plataformas`)**: Cadastro e gestão das plataformas de jogos (PC, PlayStation 5, Xbox Series X/S, Nintendo Switch, etc.).
4. **`Desenvolvedora` (`/desenvolvedoras`)**: Cadastro e gestão de estúdios e produtoras de jogos (CD Projekt Red, Santa Monica, Capcom, etc.).
5. **`Jogo` (`/jogos`)**: Gestão completa do catálogo de jogos, valores, gênero, desenvolvedora associada, plataformas agregadas e especificações de hardware.

---

### 2. Tipos de Relacionamento de POO

| Tipo de Relacionamento | Implementação no Projeto | Descrição e Justificativa no Domínio |
| :--- | :--- | :--- |
| **Herança** | `Pessoa` ➔ `Cliente` e `Funcionario` | Utiliza a estratégia `@Inheritance(strategy = InheritanceType.JOINED)`. A superclasse `Pessoa` centraliza dados cadastrais universais (nome, CPF, email), enquanto `Cliente` e `Funcionario` especializam comportamentos e atributos específicos. |
| **Associação** | `Jogo` ➔ `Desenvolvedora` | Um jogo está associado a uma desenvolvedora (`@ManyToOne`). Ambos possuem ciclos de vida independentes, onde a desenvolvedora existe por si só e produz múltiplos títulos. |
| **Agregação** | `Jogo` ⟷ `Plataforma` | Um jogo agrega múltiplas plataformas onde pode ser executado (`@ManyToMany`). A exclusão de um jogo não exclui as plataformas existentes e vice-versa. |
| **Composição** | `Jogo` ➔ `RequisitoSistema` | Cada jogo possui sua especificação de requisitos de hardware (`@OneToOne` com `CascadeType.ALL` e `orphanRemoval = true`). O requisito de sistema não possui ciclo de vida independente; se o jogo for excluído, seus requisitos também são eliminados. |

---

### 3. Cardinalidades

| Cardinalidade | Entidades Relacionadas | Anotação JPA | Contexto do Domínio |
| :--- | :--- | :--- | :--- |
| **1:1 (Um para Um)** | `Jogo` (1) ⟷ (1) `RequisitoSistema` | `@OneToOne` | Cada jogo de PC/console possui um conjunto único de requisitos recomendados/mínimos de sistema. |
| **1:N (Um para Muitos)** | `Desenvolvedora` (1) ⟷ (N) `Jogo` | `@ManyToOne` / `@OneToMany` | Uma desenvolvedora produz e publica múltiplos jogos no catálogo da loja. |
| **N:N (Muitos para Muitos)** | `Jogo` (N) ⟷ (N) `Plataforma` | `@ManyToMany` (`jogo_plataforma`) | Um jogo moderno é lançado para múltiplas plataformas (PC, PS5, Xbox, Switch), e cada plataforma suporta múltiplos jogos. |

---

## 🚀 Como Executar a Aplicação

### Pré-requisitos
- Java 25 (ou compatível com o release configurado no pom.xml)
- Banco de dados PostgreSQL rodando na porta `5432` com banco `jogosdb` (usuário `postgres`, senha `123456`)

### Executando em Modo Desenvolvimento
```shell
./mvnw.cmd quarkus:dev
```

A aplicação iniciará em `http://localhost:8080`.
- Swagger UI / OpenAPI: [http://localhost:8080/q/swagger-ui/](http://localhost:8080/q/swagger-ui/)
- Quarkus Dev UI: [http://localhost:8080/q/dev/](http://localhost:8080/q/dev/)

### Compilação do Projeto
```shell
./mvnw.cmd compile
```
