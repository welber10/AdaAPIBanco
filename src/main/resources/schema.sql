create table cliente (data_nascimento date not null, id bigint generated by default as identity, nome varchar(100), cpf varchar(255) unique, status varchar(255) check (status in ('ATIVO','INATIVO')), primary key (id));
create table conta (saldo numeric(38,2), cliente_id bigint, data_criacao timestamp(6), id bigint generated by default as identity, primary key (id));
create table usuario (account_expired boolean not null, account_locked boolean not null, active boolean not null, credentials_expired boolean not null, id bigint generated by default as identity, nome varchar(100), cpf varchar(255) unique, email varchar(255), password varchar(255), roles varchar(255), telefone varchar(255), username varchar(255), primary key (id));
alter table if exists conta add constraint FKfksaesgpmec0cph81iq5or1wn foreign key (cliente_id) references cliente;