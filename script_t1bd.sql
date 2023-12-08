-- create schema T1BD;

create table T1BD.pais (
	sigla char(2),
	nome_extenso varchar(30) not null,
	
	constraint pk_paises primary key(sigla),
	constraint uk_paises unique(nome_extenso)
);


create table T1BD.pib_total (
	ano int,
	sigla char(2),
	valor numeric(24, 5),
	
	constraint pk_pib_total primary key(ano, sigla),
	constraint fk_pib_total foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
    constraint const_pib_total check(valor >= 0 or valor = null)
);


create table T1BD.pib_per_capita (
	ano int,
	sigla char(2),
	valor numeric(12, 5),
	
	constraint pk_pib_per_capita primary key(ano, sigla),
	constraint fk_pib_per_capita foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_pib_cap check(valor >= 0 or valor = null)
);


create table T1BD.total_exportacao (
	ano int,
	sigla char(2),
	valor numeric(24, 5),
	
	constraint pk_total_exportacao primary key(ano, sigla),
	constraint fk_total_exportacao foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_tot_exp check(valor >= 0 or valor = null)
);


create table T1BD.total_importacao (
	ano int,
	sigla char(2),
	valor numeric(24, 5),
	
	constraint pk_total_importacao primary key(ano, sigla),
	constraint fk_total_importacao foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_tot_imp check(valor >= 0 or valor = null)
);


create table T1BD.invest_pesq_desenv (
	ano int,
	sigla char(2),
	valor numeric(6, 5),
	
	constraint pk_invest_pesq_desenv primary key(ano, sigla),
	constraint fk_invest_pesq_desenv foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_tot_pesq check((valor >= 0 and valor < 100) or valor = null)
);


create table T1BD.indiv_aces_net (
	ano int,
	sigla char(2),
	valor numeric(8, 5),
	
	constraint pk_indiv_aces_net primary key(ano, sigla),
	constraint fk_indiv_aces_net foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_aces_net check((valor >= 0 and valor <= 100) or valor = null) 
);


create table T1BD.idh (
	ano int,
	sigla char(2),
	valor numeric(6, 3),
	
	constraint pk_idh primary key(ano, sigla),
	constraint fk_idh foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint conts_idh check((valor >= 0 and valor <= 1) or valor = null)
);


create table T1BD.imp_com_inter (
	ano int,
	sigla char(2),
	valor numeric(8, 5),
	
	constraint pk_imp_com_inter primary key(ano, sigla),
	constraint fk_imp_com_inter foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_imp_inter check((valor >= 0 and valor < 100) or valor = null) 
);


create table T1BD.imp_exportacao (
	ano int,
	sigla char(2),
	valor numeric(24, 5),
	
	constraint pk_imp_exportacao primary key(ano, sigla),
	constraint fk_imp_exportacao foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_imp_exp check(valor >= 0 or valor = null) 
);


create table T1BD.imp_receita_fiscal (
	ano int,
	sigla char(2),
	valor numeric(24, 5),
	
	constraint pk_imp_receita_fiscal primary key(ano, sigla),
	constraint fk_imp_receita_fiscal foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_imp_receita check((valor >= 0) or valor = null) 
);


create table T1BD.imp_alfan_import (
	ano int,
	sigla char(2),
	valor numeric(24, 5),
	
	constraint pk_imp_alfan_import primary key(ano, sigla),
	constraint fk_imp_alfan_import foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_imp_alfan check((valor >= 0) or valor = null) 
);


create table T1BD.imp_renda (
	ano int,
	sigla char(2),
	valor numeric(8, 5),
	
	constraint pk_imp_renda primary key(ano, sigla),
	constraint fk_imp_renda foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_imp_renda check((valor >= 0 and valor < 100) or valor = null) 
);


create table T1BD.intervalos (
	anoInicial int,
    anoFinal int NOT NULL,
    requestDate date NOT NULL,
    
    constraint pk_intervalos primary key(anoInicial)
);

insert into t1bd.intervalos(anoInicial, anoFinal, requestDate) values (2010, 2021, '2022-11-19');

select * from t1bd.intervalos;
select * from t1bd.pais;
select * from t1bd.idh;
select * from t1bd.imp_alfan_import;
select * from t1bd.imp_com_inter where sigla = 'BR';
select * from t1bd.imp_receita_fiscal where sigla = 'BR';
select * from t1bd.imp_exportacao where sigla = 'CN';
select * from t1bd.imp_renda where sigla = 'BR';
select * from t1bd.indiv_aces_net;
select * from t1bd.pib_total where sigla = 'BR';
select * from t1bd.pib_per_capita where sigla = 'MX';
select * from t1bd.invest_pesq_desenv where sigla = 'BR';
select * from t1bd.total_exportacao where sigla = 'BR';
select * from t1bd.total_importacao;

-- -- DROPs
-- drop table t1bd.intervalos;
-- drop table t1bd.idh;
-- drop table t1bd.imp_alfan_import;
-- drop table t1bd.imp_com_inter;
-- drop table t1bd.imp_receita_fiscal;
-- drop table t1bd.imp_exportacao;
-- drop table t1bd.imp_renda;
-- drop table t1bd.indiv_aces_net;
-- drop table t1bd.pib_total;
-- drop table t1bd.pib_per_capita;
-- drop table t1bd.invest_pesq_desenv;
-- drop table t1bd.total_exportacao;
-- drop table t1bd.total_importacao;
-- drop table t1bd.pais;

