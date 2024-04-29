# Sistema bancário
Projeto final do módulo 3 do treinamento de Arquitetura da ADA, cujo objetivo era criar uma aplicação onde seja possível, através de endpoints REST, fazer operações CRUD das entidades mencionadas abaixo e também algumas funcionalidades.

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

### TRANFERÊNCIA
Dado o id da conta 1(que vai transferir) e o id da
conta2 (que vai receber o valor), e um valor x, transfira o valor de uma conta a outra. O valor a ser transferido não pode ser zero ou negativo (Ex: -500,30).o valor a ser transferido não pode ser maior do que o saldo existente.

