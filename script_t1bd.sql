create schema T1BD; 

create table T1BD.pais (
	sigla char(2),
	nome_extenso varchar(30) not null,
	
	constraint pk_paises primary key(sigla),
	constraint uk_paises unique(nome_extenso)
);


create table T1BD.t_pib_total (
	ano int,
	sigla char(2),
	pib_total_valor numeric(20, 2),
	
	constraint pk_pib_total primary key(ano, sigla),
	constraint fk_pib_total foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
    constraint const_pib_total check(pib_total_valor >= 0 or pib_total_valor = -1)
);


create table T1BD.t_pib_per_capita (
	ano int,
	sigla char(2),
	pib_per_capita_valor numeric(8, 2),
	
	constraint pk_pib_per_capita primary key(ano, sigla),
	constraint fk_pib_per_capita foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_pib_cap check(pib_per_capita_valor >= 0 or pib_per_capita_valor = -1)
);


create table T1BD.t_total_exportacao (
	ano int,
	sigla char(2),
	total_exportacao_valor numeric(20, 2),
	
	constraint pk_total_exportacao primary key(ano, sigla),
	constraint fk_total_exportacao foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_tot_exp check(total_exportacao_valor >= 0 or total_exportacao_valor = -1)
);


create table T1BD.t_total_importacao (
	ano int,
	sigla char(2),
	total_importacao_valor numeric(20, 2),
	
	constraint pk_total_importacao primary key(ano, sigla),
	constraint fk_total_importacao foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_tot_imp check(total_importacao_valor >= 0 or total_importacao_valor = -1)
);


create table T1BD.t_invest_pesq_desenv (
	ano int,
	sigla char(2),
	invest_pesq_desenv_valor numeric(4, 3),
	
	constraint pk_invest_pesq_desenv primary key(ano, sigla),
	constraint fk_invest_pesq_desenv foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_tot_pesq check((invest_pesq_desenv_valor >= 0 and invest_pesq_desenv_valor < 100) or invest_pesq_desenv_valor = -1)
);


create table T1BD.t_indiv_aces_net (
	ano int,
	sigla char(2),
	indiv_aces_net_valor numeric(5, 2),
	
	constraint pk_indiv_aces_net primary key(ano, sigla),
	constraint fk_indiv_aces_net foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_aces_net check((indiv_aces_net_valor >= 0 and indiv_aces_net_valor <= 100) or indiv_aces_net_valor = -1) 
);


create table T1BD.t_idh (
	ano int,
	sigla char(2),
	idh_valor numeric(4, 3),
	
	constraint pk_idh primary key(ano, sigla),
	constraint fk_idh foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint conts_idh check((idh_valor >= 0 and idh_valor <= 1) or idh_valor = -1)
);


create table T1BD.t_imp_com_inter (
	ano int,
	sigla char(2),
	imp_com_inter_valor numeric(5, 3),
	
	constraint pk_imp_com_inter primary key(ano, sigla),
	constraint fk_imp_com_inter foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_imp_inter check((imp_com_inter_valor >= 0 and imp_com_inter_valor < 100) or imp_com_inter_valor = -1) 
);


create table T1BD.t_imp_exportacao (
	ano int,
	sigla char(2),
	imp_exportacao_valor numeric(20, 2),
    -- imp_exportacao_valor numeric(6, 5), mudar pra isso?
	
	constraint pk_imp_exportacao primary key(ano, sigla),
	constraint fk_imp_exportacao foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_imp_exp check(imp_exportacao_valor >= 0 or imp_exportacao_valor = -1) 
);


create table T1BD.t_imp_receita_fiscal (
	ano int,
	sigla char(2),
	imp_receita_fiscal_valor numeric(20, 2),
	
	constraint pk_imp_receita_fiscal primary key(ano, sigla),
	constraint fk_imp_receita_fiscal foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_imp_receita check((imp_receita_fiscal_valor >= 0) or imp_receita_fiscal_valor = -1) 
);


create table T1BD.t_imp_alfan_import (
	ano int,
	sigla char(2),
	imp_alfan_import_valor numeric(20, 2),
	
	constraint pk_imp_alfan_import primary key(ano, sigla),
	constraint fk_imp_alfan_import foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_imp_alfan check((imp_alfan_import_valor >= 0) or imp_alfan_import_valor = -1) 
);


create table T1BD.t_imp_renda (
	ano int,
	sigla char(2),
	imp_renda_valor numeric(5, 2),
	
	constraint pk_imp_renda primary key(ano, sigla),
	constraint fk_imp_renda foreign key(sigla) references T1BD.pais(sigla) on delete cascade,
	constraint const_imp_renda check((imp_renda_valor >= 0 and imp_renda_valor < 100) or imp_renda_valor = -1) 
);

-- use t1bd;
select * from pais;
select * from t_idh where sigla = 'BR';
select * from t_imp_alfan_import where sigla = 'BR';
select * from t_imp_com_inter where sigla = 'BR';
select * from t_imp_receita_fiscal where sigla = 'BR';
select * from t_imp_exportacao where sigla = 'BR';
select * from t_imp_renda where sigla = 'BR';
select * from t_indiv_aces_net where sigla = 'BR';
select * from t_pib_total where sigla = 'BR';
select * from t_pib_per_capita where sigla = 'BR';
select * from t_invest_pesq_desenv where sigla = 'BR';
select * from t_total_exportacao where sigla = 'BR';
select * from t_total_importacao where sigla = 'BR';

-- -- DROPs
-- drop table pais;
-- drop table t_idh;
-- drop table t_imp_alfan_import;
-- drop table t_imp_com_inter;
-- drop table t_imp_receita_fiscal;
-- drop table t_imp_exportacao;
-- drop table t_imp_renda;
-- drop table t_indiv_aces_net;
-- drop table t_pib_total;
-- drop table t_pib_per_capita;
-- drop table t_invest_pesq_desenv;
-- drop table t_total_exportacao;
-- drop table t_total_importacao;