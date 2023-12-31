create schema T1BD;
use T1BD;

create table pais (
	sigla char(2),
	nome_extenso varchar(30) not null,
	
	constraint pk_paises primary key(sigla),
	constraint uk_paises unique(nome_extenso)
);


create table t_pib_total (
	ano int,
	sigla char(2),
	pib_total_valor numeric(14, 2),
	
	constraint pk_pib_total primary key(ano, sigla),
	constraint fk_pib_total foreign key(sigla) references pais(sigla) on delete cascade,
    constraint const_pib_total check(pib_total_valor >= 0)
);


create table t_pib_per_capita (
	ano int,
	sigla char(2),
	pib_per_capita_valor numeric(8, 2),
	
	constraint pk_pib_per_capita primary key(ano, sigla),
	constraint fk_pib_per_capita foreign key(sigla) references pais(sigla) on delete cascade,
	constraint const_pib_cap check(pib_per_capita_valor >= 0)
);


create table t_total_exportacao (
	ano int,
	sigla char(2),
	total_exportacao_valor numeric(14, 2),
	
	constraint pk_total_exportacao primary key(ano, sigla),
	constraint fk_total_exportacao foreign key(sigla) references pais(sigla) on delete cascade,
	constraint const_tot_exp check(total_exportacao_valor >= 0)
);


create table t_total_importacao (
	ano int,
	sigla char(2),
	total_importacao_valor numeric(14, 2),
	
	constraint pk_total_importacao primary key(ano, sigla),
	constraint fk_total_importacao foreign key(sigla) references pais(sigla) on delete cascade,
	constraint const_tot_imp check(total_importacao_valor >= 0)
);


create table t_invest_pesq_desenv (
	ano int,
	sigla char(2),
	invest_pesq_desenv_valor numeric(4, 3),
	
	constraint pk_invest_pesq_desenv primary key(ano, sigla),
	constraint fk_invest_pesq_desenv foreign key(sigla) references pais(sigla) on delete cascade,
	constraint const_tot_pesq check(invest_pesq_desenv_valor >= 0 and invest_pesq_desenv_valor <= 1)
);


create table t_indiv_aces_net (
	ano int,
	sigla char(2),
	indiv_aces_net_valor numeric(5, 2),
	
	constraint pk_indiv_aces_net primary key(ano, sigla),
	constraint fk_indiv_aces_net foreign key(sigla) references pais(sigla) on delete cascade,
	constraint const_aces_net check(indiv_aces_net_valor >= 0 and indiv_aces_net_valor <= 100) 
);


create table t_idh (
	ano int,
	sigla char(2),
	idh_valor numeric(4, 3),
	
	constraint pk_idh primary key(ano, sigla),
	constraint fk_idh foreign key(sigla) references pais(sigla) on delete cascade,
	constraint conts_idh check(idh_valor >= 0 and idh_valor <= 1)
);


create table t_imp_com_inter (
	ano int,
	sigla char(2),
	imp_com_inter_valor numeric(5, 3),
	
	constraint pk_imp_com_inter primary key(ano, sigla),
	constraint fk_imp_com_inter foreign key(sigla) references pais(sigla) on delete cascade,
	constraint const_imp_inter check(imp_com_inter_valor >= 0 and imp_com_inter_valor <= 100) 
);


create table t_imp_exportacao (
	ano int,
	sigla char(2),
	imp_exportacao_valor numeric(16, 1),
	
	constraint pk_imp_exportacao primary key(ano, sigla),
	constraint fk_imp_exportacao foreign key(sigla) references pais(sigla) on delete cascade,
	constraint const_imp_exp check(imp_exportacao_valor >= 0) 
);


create table t_imp_receita_fiscal (
	ano int,
	sigla char(2),
	imp_receita_fiscal_valor numeric(14, 2),
	
	constraint pk_imp_receita_fiscal primary key(ano, sigla),
	constraint fk_imp_receita_fiscal foreign key(sigla) references pais(sigla) on delete cascade,
	constraint const_imp_receita check(imp_receita_fiscal_valor >= 0) 
);


create table t_imp_alfan_import (
	ano int,
	sigla char(2),
	imp_alfan_import_valor numeric(14, 1),
	
	constraint pk_imp_alfan_import primary key(ano, sigla),
	constraint fk_imp_alfan_import foreign key(sigla) references pais(sigla) on delete cascade,
	constraint const_imp_alfan check(imp_alfan_import_valor >= 0) 
);


create table t_imp_renda (
	ano int,
	sigla char(2),
	imp_renda_valor numeric(4, 2),
	
	constraint pk_imp_renda primary key(ano, sigla),
	constraint fk_imp_renda foreign key(sigla) references pais(sigla) on delete cascade,
	constraint const_imp_renda check(imp_renda_valor >= 0 and imp_renda_valor < 100) 
);


INSERT INTO pais (sigla, nome_extenso) VALUES ('BR', 'Brasil');

INSERT INTO t_pib_total (ano, sigla, pib_total_valor) VALUES (2021, 'BR', 1234567890.12);

INSERT INTO t_pib_per_capita (ano, sigla, pib_per_capita_valor) VALUES (2018, 'BR', 123456.78);

INSERT INTO t_total_exportacao (ano, sigla, total_exportacao_valor) VALUES (2018, 'BR', 1234567890.12);

INSERT INTO t_total_importacao (ano, sigla, total_importacao_valor) VALUES (2015, 'BR', 1234567890.12);

INSERT INTO t_invest_pesq_desenv (ano, sigla, invest_pesq_desenv_valor) VALUES (2021, 'BR', 0.123);

INSERT INTO t_indiv_aces_net (ano, sigla, indiv_aces_net_valor) VALUES (2021, 'BR', 12.34);

INSERT INTO t_idh (ano, sigla, idh_valor) VALUES (2019, 'BR', 0.123);

INSERT INTO t_imp_com_inter (ano, sigla, imp_com_inter_valor) VALUES (2016, 'BR', 1.34);

INSERT INTO t_imp_exportacao (ano, sigla, imp_exportacao_valor) VALUES (2016, 'BR', 1234563456.1);

INSERT INTO t_imp_receita_fiscal (ano, sigla, imp_receita_fiscal_valor) VALUES (2021, 'BR', 1234567890.12);

INSERT INTO t_imp_alfan_import (ano, sigla, imp_alfan_import_valor) VALUES (2021, 'BR', 1234123456.1);

INSERT INTO t_imp_renda (ano, sigla, imp_renda_valor) VALUES (2020, 'BR', 55.1);

DROP TABLE IF EXISTS t_imp_alfan_import;
DROP TABLE IF EXISTS t_imp_receita_fiscal;
DROP TABLE IF EXISTS t_imp_exportacao;
DROP TABLE IF EXISTS t_imp_com_inter;
DROP TABLE IF EXISTS t_imp_renda;
DROP TABLE IF EXISTS t_idh;
DROP TABLE IF EXISTS t_indiv_aces_net;
DROP TABLE IF EXISTS t_invest_pesq_desenv;
DROP TABLE IF EXISTS t_total_importacao;
DROP TABLE IF EXISTS t_total_exportacao;
DROP TABLE IF EXISTS t_pib_per_capita;
DROP TABLE IF EXISTS t_pib_total;
DROP TABLE IF EXISTS pais;

