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
	valor numeric(20, 2),
	
	constraint pk_pib_total primary key(ano, sigla),
	constraint fk_pib_total foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
    constraint const_pib_total check(valor >= 0 or valor = null)
);


create table T1BD.pib_per_capita (
	ano int,
	sigla char(2),
	valor numeric(8, 2),
	
	constraint pk_pib_per_capita primary key(ano, sigla),
	constraint fk_pib_per_capita foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_pib_cap check(valor >= 0 or valor = null)
);


create table T1BD.total_exportacao (
	ano int,
	sigla char(2),
	valor numeric(20, 2),
	
	constraint pk_total_exportacao primary key(ano, sigla),
	constraint fk_total_exportacao foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_tot_exp check(valor >= 0 or valor = null)
);


create table T1BD.total_importacao (
	ano int,
	sigla char(2),
	valor numeric(20, 2),
	
	constraint pk_total_importacao primary key(ano, sigla),
	constraint fk_total_importacao foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_tot_imp check(valor >= 0 or valor = null)
);


create table T1BD.invest_pesq_desenv (
	ano int,
	sigla char(2),
	valor numeric(4, 3),
	
	constraint pk_invest_pesq_desenv primary key(ano, sigla),
	constraint fk_invest_pesq_desenv foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_tot_pesq check((valor >= 0 and valor < 100) or valor = null)
);


create table T1BD.indiv_aces_net (
	ano int,
	sigla char(2),
	valor numeric(5, 2),
	
	constraint pk_indiv_aces_net primary key(ano, sigla),
	constraint fk_indiv_aces_net foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_aces_net check((valor >= 0 and valor <= 100) or valor = null) 
);


create table T1BD.idh (
	ano int,
	sigla char(2),
	valor numeric(4, 3),
	
	constraint pk_idh primary key(ano, sigla),
	constraint fk_idh foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint conts_idh check((valor >= 0 and valor <= 1) or valor = null)
);


create table T1BD.imp_com_inter (
	ano int,
	sigla char(2),
	valor numeric(5, 3),
	
	constraint pk_imp_com_inter primary key(ano, sigla),
	constraint fk_imp_com_inter foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_imp_inter check((valor >= 0 and valor < 100) or valor = null) 
);


create table T1BD.imp_exportacao (
	ano int,
	sigla char(2),
	valor numeric(20, 2),
	
	constraint pk_imp_exportacao primary key(ano, sigla),
	constraint fk_imp_exportacao foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_imp_exp check(valor >= 0 or valor = null) 
);


create table T1BD.imp_receita_fiscal (
	ano int,
	sigla char(2),
	valor numeric(20, 2),
	
	constraint pk_imp_receita_fiscal primary key(ano, sigla),
	constraint fk_imp_receita_fiscal foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_imp_receita check((valor >= 0) or valor = null) 
);


create table T1BD.imp_alfan_import (
	ano int,
	sigla char(2),
	valor numeric(20, 2),
	
	constraint pk_imp_alfan_import primary key(ano, sigla),
	constraint fk_imp_alfan_import foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_imp_alfan check((valor >= 0) or valor = null) 
);


create table T1BD.imp_renda (
	ano int,
	sigla char(2),
	valor numeric(5, 2),
	
	constraint pk_imp_renda primary key(ano, sigla),
	constraint fk_imp_renda foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_imp_renda check((valor >= 0 and valor < 100) or valor = null) 
);

use t1bd;
select * from pais;
select * from idh;
select * from imp_alfan_import;
select * from imp_com_inter where sigla = 'BR';
select * from imp_receita_fiscal where sigla = 'BR';
select * from imp_exportacao where sigla = 'BR';
select * from imp_renda where sigla = 'BR';
select * from indiv_aces_net;
select * from pib_total where sigla = 'BR';
select * from pib_per_capita where sigla = 'MX';
select * from invest_pesq_desenv where sigla = 'BR';
select * from total_exportacao where sigla = 'BR';
select * from total_importacao;

-- -- DROPs
-- drop table pais;
-- drop table idh;
-- drop table imp_alfan_import;
-- drop table imp_com_inter;
-- drop table imp_receita_fiscal;
-- drop table imp_exportacao;
-- drop table imp_renda;
-- drop table indiv_aces_net;
-- drop table pib_total;
-- drop table pib_per_capita;
-- drop table invest_pesq_desenv;
-- drop table total_exportacao;
-- drop table total_importacao;
DELETE FROM pais WHERE sigla = 'DE'; 

