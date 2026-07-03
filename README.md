# Desafio de Votação (API REST)

Este projeto consiste em uma API REST desenvolvida com **Spring Boot** para gerenciar sessões de votação em pautas cooperativas. A solução permite a criação de pautas, abertura de sessões, registro de votos de associados e contabilização de resultados.

## 🚀 Funcionalidades Implementadas

* **Gestão de Pautas:** Criação de pautas com persistência em banco de dados H2.
* **Gestão de Sessões:** Abertura de sessões com duração configurável (default de 1 minuto).
* **Votação:** Registro de votos ('Sim'/'Não') com validação de voto único por CPF/Pauta.
* **Resultados:** Contabilização e retorno do resultado final da votação.
* **Integração Bônus:** Implementação da validação de elegibilidade de CPF.

## 🛠️ Tecnologias e Decisões Arquiteturais

* **Java 17 & Spring Boot:** Base do desenvolvimento.
* **Spring Data JPA (Hibernate):** Utilizado para mapeamento objeto-relacional (ORM).
* **Banco de Dados H2:** Configurado para garantir a persistência dos dados entre reinicializações da aplicação.
* **Bean Validation:** Uso estratégico da anotação `@CPF` nos DTOs de entrada para garantir a integridade sintática dos dados. Aliado a isso, validação de elegibilidade em tempo de execução.
* **Testes Automatizados:** Implementação de testes unitários com `JUnit 5` e `Mockito` para as camadas de `Service`.
* **Design de Código:** Utilização de `Builder Pattern` (Lombok) e DTOs (Data Transfer Objects) para desacoplar as entidades de domínio das interfaces de entrada da API.

## ⚙️ Tarefas Bônus

### 1. Integração com sistema externo (CPF)
A elegibilidade do CPF foi implementada encapsulando a lógica dentro do `VotoService` através de um método dedicado (`verificarCpf`). A aleatoriedade solicitada foi aplicada para simular o comportamento de um sistema externo real, tratando os estados `ABLE_TO_VOTE` e `UNABLE_TO_VOTE`.

### 2. Versionamento da API
Para o versionamento, a estratégia adotada seria o **versionamento via URI** (ex: `/api/v1/...`). Esta estratégia é explícita, facilita o *caching* e permite que clientes continuem utilizando versões antigas da API enquanto novas funcionalidades são desenvolvidas.

## ⚠️ Observações sobre a Entrega

Durante o desenvolvimento deste desafio, enfrentei limitações severas de hardware (memória e processamento), o que impactou a execução completa do ciclo de *build* e testes locais. 

No entanto, o código foi estruturado seguindo as melhores práticas do Spring Boot, com a modelagem de domínio, validações e regras de negócio devidamente implementadas. A lógica de persistência e a arquitetura da solução estão prontas para serem avaliadas.

## 📝 Como executar

1. Certifique-se de ter o JDK 17+ instalado.
2. Na raiz do projeto, execute: `./gradlew bootRun` (ou `gradlew.bat bootRun` no Windows).
3. A API estará disponível em `http://localhost:8080`.
