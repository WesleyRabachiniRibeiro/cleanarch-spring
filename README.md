# CleanArch-Spring

## Descrição

**CleanArch-Spring** é um projeto desenvolvido utilizando as melhores práticas de design de software, combinando conceitos como Clean Architecture, Arquitetura Hexagonal, Domain-Driven Design (DDD) e o padrão de projeto Command. O objetivo é criar um sistema modular, testável, de fácil manutenção e evolução.

---

## Estrutura do Projeto

### Clean Architecture

A arquitetura foi projetada seguindo os princípios de Clean Architecture, separando claramente as responsabilidades em camadas:

- **Entities:** Contêm as regras de negócio mais essenciais.

- **Use Cases:** Orquestram as regras de negócio e interagem com os dados externos.

- **Frameworks & Drivers:** Implementações de interface para comunicação com o mundo externo.

### Arquitetura Hexagonal

Os princípios da Arquitetura Hexagonal foram utilizados para isolar o núcleo do sistema (domínio) das dependências externas, garantindo que o domínio não seja impactado por mudanças em frameworks ou tecnologias.

### Domain-Driven Design (DDD)

O projeto incorpora os conceitos do DDD, como:

- **Entities** e **Value Objects** para modelar as regras de negócio.
- **Aggregates** para garantir consistência nas operações.
- **Repositories** como abstração para persistência.

### Command Pattern

O Command Pattern foi utilizado para encapsular operações em comandos, promovendo extensibilidade e reduzindo o acoplamento entre componentes.

---

## Tecnologias Utilizadas

- **Spring Framework**
- **Spring Boot**
- **Java 8**
- **JUnit** para testes unitários.
- **Spring Test** para testes de integração.
- **Maven** como gerenciador de dependências.

---

## Testes

O projeto inclui uma sólida cobertura de testes:

- **Testes Unitários:** Garantem o funcionamento adequado de componentes individuais.
- **Testes de Integração:** Validam a interação entre diferentes partes do sistema.

### Executando os Testes

Para executar os testes:

```bash
mvn test
```

---

## Como Executar

### Requisitos

- **Java 8** ou superior.
- **Maven** instalado.

### Passos

1. Clone o repositório:
   ```bash
   git clone https://github.com/WesleyRabachiniRibeiro/cleanarch-spring.git
   ```
2. Navegue até o diretório do projeto:
   ```bash
   cd cleanarch-spring
   ```
3. Compile o projeto:
   ```bash
   mvn clean install
   ```
4. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```

---

## Estrutura de Pastas

- **src/main/java:** Contém o código-fonte principal.
- **src/test/java:** Contém os testes unitários e de integração.

---

## Contribuição

Sinta-se à vontade para contribuir com melhorias! Realize um fork do repositório, crie uma nova branch e envie um pull request.


