# Sistema bancário
Projeto final do módulo Arquitetura de Software I do treinamento de Arquitetura da ADA, cujo objetivo era criar uma aplicação onde seja possível, através de endpoints REST, fazer operações CRUD das entidades mencionadas abaixo e também algumas funcionalidades.

## Desenvolvido com

[![Java 17](https://img.shields.io/badge/Java-17-red.svg)](https://www.oracle.com/java/technologies/downloads/)
[![Spring Boot 3](https://img.shields.io/badge/Spring_Boot-3.2.5-green.svg)](https://spring.io/projects/spring-boot)
[![H2 Database](https://img.shields.io/badge/H2_Database-2.2.224-blue.svg)](https://www.h2database.com/html/main.html)

## Funcionalidades CRUD

* <b>Segurança:</b> Roles com permissão para realizar as operações dos CRUDs: ADMIN E FUNCIONARIO

### CRUD DE CLIENTE
O cliente deve conter NOME, CPF, Data de nascimento, e Status. O cliente deve ter seu CPF devidamente validado. O cliente deve ser maior de 18 anos (validar data de nascimento).

### CRUD DE USUÁRIO
O usuário deverá ter username, password e roles
(ADMIN, CLIENTE e FUNCIONARIO)

### CRUD DE CONTA
A conta deve ter saldo, ID e o id do cliente associado a ela. Não é permitido criar conta com um cliente inexistente, criar conta com 0 ou saldo negativo.

---

## API de operações bancárias básicas

* <b>Segurança:</b> Roles com permissão para realizar as operações da API: CLIENTE. E o id do cliente tem que ser o mesmo da conta.

### SALDO
Mostra o saldo existente na conta. Não é possível ver uma conta da qual você não esteja associado.

### SAQUE
Dado o id da conta, reduzir da conta o saldo ao valor já existente. O valor a ser reduzido não pode ser negativo (Ex: -500,30), o valor a ser sacado não pode ser maior do que o saldo existente.

### DEPOSITO
Dado o id da conta, adicionar saldo ao valor já existente. O valor a ser adicionado não pode ser zero ou negativo (Ex: -500,30).

### TRANSFERÊNCIA
Dado o id da conta 1(que vai transferir) e o id da
conta2 (que vai receber o valor), e um valor x, transfira o valor de uma conta a outra. O valor a ser transferido não pode ser zero ou negativo (Ex: -500,30).o valor a ser transferido não pode ser maior do que o saldo existente.

### INVESTIMENTO
Realizar investimento de um valor disponível em uma conta investimento.
- Clientes PF possuem rendimentos de 1% no momento do investimento.
- Clientes PJ possuem rendimentos de 2% no momento do investimento

---

## Princípios do SOLID

### Single Responsibility Principle, ou Princípio da Responsabilidade Única

Classes: [Saque.java](./src/main/java/tech/ada/banco/service/operacao/saque/Saque.java)

No código um exemplo da aplicação desse princípio está presente nas operações bancárias.

A interface `Saque` indica que a responsabilidade dessa operação é realizar o saque de um valor em uma conta. Assim todas as classes que implementam essa interface terão essa responsabilidade.

Segue o método definido para essa interface.

```java
	void sacar(BigDecimal valor, T conta) throws SaldoIndisponivelException, ValorInvalidoException;
```

### Open-closed principle, ou Princípio Aberto/Fechado ou ainda Princípio da Coesão

Classes: [Saque.java](./src/main/java/tech/ada/banco/service/operacao/saque/Saque.java), [SaqueComTarifa.java](./src/main/java/tech/ada/banco/service/operacao/saque/SaqueComTarifa.java), [SaqueConta.java](./src/main/java/tech/ada/banco/service/operacao/saque/SaqueConta.java), [Transferencia.java](./src/main/java/tech/ada/banco/service/operacao/transferencia/Transferencia.java), [TransferenciaBancaria.java](./src/main/java/tech/ada/banco/service/operacao/transferencia/TransferenciaBancaria.java), [TransferenciaComTarifa.java](./src/main/java/tech/ada/banco/service/operacao/transferencia/TransferenciaComTarifa.java)

Esse princípio foi aplicado na resolução da regra de negócio seguinte:

- Para cliente PJ existe a cobrança de uma taxa de 0.5% para cada saque ou transferência.

Foram criadas as interfaces `Saque` e `Transferencia` que possuem um comportamento definido para essas operações bancárias. Temos as classes `SaqueConta.java` e `TransferenciaBancaria.java` que possuem uma implementação básica de um saque e uma transferência bancárias.

Para implementar a regra de negócio de cobrança da taxa para PJ, no lugar de mudar essas implementações com a inclusão de condicionais, foi aplicado o princípio Open-closed, com a criação das classes `SaqueComTarifa.java` e `TransferenciaComTarifa.java` que extendem o comportamento das interfaces, porém sem modificar o comportamento das classes padrão já implementadas.

### Liskov Substitution Principle, ou Princípio da Substituição de Liskov

Classes: [Saque.java](./src/main/java/tech/ada/banco/service/operacao/saque/Saque.java), [SaqueComTarifa.java](./src/main/java/tech/ada/banco/service/operacao/saque/SaqueComTarifa.java), [SaqueConta.java](./src/main/java/tech/ada/banco/service/operacao/saque/SaqueConta.java)

Pelo princípio onde está declarada a interface Saque pode ser utilizado tanto o SaqueComTarifa, como o SaqueConta. Ou seja, qualquer das suas implementações pode ser utilizada.

Na classe `OperacoesBancariasController.java` é declarada a interface Saque que o Spring injeta por padrão a classe `SaqueConta.java` por estar marcada com `@Primary`. Porém se mudar a anotação para a outra classe ou se passar o qualificador da outra classe, ela poderá ser substuída sem prejuízo pela classe `SaqueComTarifa.java`.

### Interface Segregation Principle, ou Princípio da Segregação de Interfaces

Classe: [TransferenciaComTarifa.java](./src/main/java/tech/ada/banco/service/operacao/transferencia/TransferenciaComTarifa.java)

```java
public class TransferenciaComTarifa implements Transferencia<Conta<?>>, Tarifavel
```

Essa classe ilustra bem esse princípio, pois utiliza a interface de `Transferencia`, porém o comportamento de ser tarifável não foi incluído na interface de `Transferencia` justamento por quebrar esse princípio. Foi realizada uma segreção de interfaces, com a criação da interface `Tarifavel` para implementação do comportamento de `calcularTarifa`.

```java
public interface Transferencia<T extends Conta<?>> extends OperacaoBancaria {
	
	void transferir(BigDecimal valor, T contaOrigem, T contaDestino) throws SaldoIndisponivelException, ValorInvalidoException;

}
```

```java
public interface Tarifavel {
	
	BigDecimal calcularTarifa(BigDecimal valor, BigDecimal taxa);

}
```

### Dependency Inversion Principle, ou Princípio da Inversão de Dependências

Classe: [OperacoesBancariasController.java](./src/main/java/tech/ada/banco/controller/OperacoesBancariasController.java)

Nessa classe tem vários exemplos de aplicação desse princípio.

Todos os serviços declarados no código abaixo são injetados pelo Spring utilizando justamente o conceito de Inversão de Dependências. O código está dependendo apenas das interfaces dos serviços, sendo injetadas as implementações de acordo com o contexto de negócios.

```java
public class OperacoesBancariasController {    
    private final ModelMapper modelMapper;
    private final ContaService contaService;
    private final Saque saque;
    private final Deposito deposito;
    private final Transferencia transferencia;
    private final Investimento investimento;
    private final JwtService jwtService;
}
```

Classe: [TransferenciaComTarifa.java](./src/main/java/tech/ada/banco/service/operacao/transferencia/TransferenciaComTarifa.java)

Outro exemplo da aplicação desse conceito está na classe `TransferenciaComTarifa` que possui uma declaração da interface `Tarifa`, porém ela não sabe qual tarifa será injetada, pois foi aplicado o conceito de Inversão de Dependência. Portanto, na criação do objeto que será passada um implementação da Tarifa que será utilizada na operação, dando assim mais flexibilidade para essa operação.

```java
public class TransferenciaComTarifa implements Transferencia<Conta<?>>, Tarifavel {
	private Tarifa tarifa;
}
```


---

## Padrões de projeto (Design Patterns)

### Builder

Classe: [Usuario.java](./src/main/java/tech/ada/banco/model/Usuario.java)

A anotação `@Builder` do Lombok é uma forma de implementação do padrão de projeto Builder. No exemplo a seguir está sendo criado um Builder de Usuário.

```java
@Builder
@Entity
public class Usuario implements UserDetails
```

A utilização desse builder é demonstrada na classe [JWTService.java](./src/main/java/tech/ada/banco/service/login/JWTService.java).

```java
public UserDetails getUserDetails(String token) {
        Claims claims = extractAllClaims(token);
        String subject = (String) claims.get(Claims.SUBJECT);
        String roles = (String) claims.get("roles");
        String cpf = (String) claims.get("cpf");
        Integer id = (Integer) claims.get("id_usuario");

        String[] jwtSubject = subject.split(",");

        return Usuario.
                builder()
                .roles(roles)
                .cpf(cpf)
                .id(Long.valueOf(id))
                .username(jwtSubject[0])
                .build();
    }
```

Podemos observar que o Builder é utilizado para construir um Objeto UserDetails com os atributos de roles, cpf, id e username.


### Singleton

Classe: [ClienteService.java](./src/main/java/tech/ada/banco/service/ClienteService.java)

```java
@Service
public class ClienteService
```

O Spring utiliza o padrão Singleton para os Beans declarados por exemplo com a anotação `@Service`.
Desta forma existe uma única instância desse Bean em memória que é provida todas as vezes que é necessário utilizar os serviços providos por ele.


### Factory Method

### Proxy

### Observer

### Decorator

### Facade

Classe: [ClienteService.java](./src/main/java/tech/ada/banco/service/ClienteService.java)

```java
@Service
public class ClienteService
```

A anotação `@Service` implementa o padrão de projeto `Facade`.

Essa anotação indica que a classe é uma `Business Service Facade`.

### Template Method

### Iterator

Classe: 

### Chain of Responsability
Uma implementação do Chain of Responsability está presente na classe JwtAuthFilter.

```java
@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, java.io.IOException {
        final String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            final String token = header.substring(7);
            this.authenticateUserFromToken(request, token);
        }
        filterChain.doFilter(request, response);
    }
```

### Strategy

### 





