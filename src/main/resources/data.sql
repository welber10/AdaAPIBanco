INSERT INTO cliente (nome, cpf, data_nascimento, tipo_cliente, status) 
VALUES ('Jose Maria do Nascimento', '28528362035', '1980-04-20', 1, 'ATIVO');
INSERT INTO cliente (nome, cpf, data_nascimento, tipo_cliente, status) 
VALUES ('Cliente da silva', '71061189074', '1990-10-05', 1, 'ATIVO');
INSERT INTO cliente (nome, cnpj, tipo_cliente, status) 
VALUES ('Empresa W11', '96350282000126', 2, 'ATIVO');

INSERT INTO conta (tipo_conta, cliente_id, saldo, data_criacao)
VALUES (1, 1, '3000', '2024-04-25T09:00:00Z');
INSERT INTO conta (tipo_conta, cliente_id, saldo, data_criacao)
VALUES (1, 2, '3000', '2024-04-25T09:00:00Z');
INSERT INTO conta (tipo_conta, cliente_id, saldo, data_criacao)
VALUES (1, 3, '20000', '2024-04-25T09:00:00Z');
INSERT INTO conta (tipo_conta, cliente_id, saldo, data_criacao)
VALUES (2, 2, '5000', '2024-04-25T09:00:00Z');
INSERT INTO conta (tipo_conta, cliente_id, saldo, data_criacao)
VALUES (2, 3, '10000', '2024-04-25T09:00:00Z');

insert into usuario (nome, account_expired, account_locked, active, cpf, credentials_expired, email, password, roles, telefone, username) VALUES
('Admin',false,false,true,'07120220403',false,'user@admin.com' ,'$2a$12$yrp1A9bCUvgGjOjY.sf4GeSmFIRUR3B.7naToAD5OuBpG/VKgG2EW','ADMIN','99999999','user_admin');

insert into usuario (nome, account_expired, account_locked, active, cpf, credentials_expired, email, password, roles, telefone, username) VALUES
('Cliente PF',false,false,true,'42118950012',false,'user@cliente.com' ,'$2a$12$h8tyqe5VmXqmKwWmmKOLV.Wl038pn5zQIkyinuws06pIVhzCuWuju','CLIENTE','99999999','user_cliente');

insert into usuario (nome, account_expired, account_locked, active, cpf, credentials_expired, email, password, roles, telefone, username) VALUES
('Cliente PJ',false,false,true,'96350282000126',false,'user@cliente.com' ,'$2a$12$h8tyqe5VmXqmKwWmmKOLV.Wl038pn5zQIkyinuws06pIVhzCuWuju','CLIENTE','99999999','user_cliente_pj');


insert into usuario (nome, account_expired, account_locked, active, cpf, credentials_expired, email, password, roles, telefone, username) VALUES
('Funcionario',false,false,true,'88385302034',false,'user@funcionario.com' ,'$2a$12$JLicT3XBxu9l/KK5ceE8KOLDp7oopCGXo2eOkQsQT9Jeosp2UOZf6','FUNCIONARIO','99999999','user_funcionario');