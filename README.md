# Trab1_BancoDeDados

## RESUMO: 
O intuito do trabalho é montar tabelas para analisar socioeconomicamente os países, exibindo PIB e aprofundando-se em importações,
exportações e investimentos em pesquisas. Com essas tabelas montadas, devemos montar tabelas que mostrem dados mais específicos de caratér
social, como PIB per capita, acesso a internet e IDH. 
Agora, impostos de interesse precisam ser definidos (imposto de renda, imposto sob importação e exportação, imposto sob bens
industrializados) e a tabela projetada. Um problema é que existem diversos tipos de imposto e apesar de serem públicos, extrair e
encapsular os dados de interesse pode ser complexo. Talvez um csv contendo país-imposto precisará ser feito na mão, ou via scraping de uma
tabela online.  

Os países foco de estudo serão os membros do G20, sendo eles: África do SUl, Alemanha, Arábia Saudita, Argentina, Austrália, Brasil, Canadá, China, Coreia do Sul, Estados Unidos, França, Índia, Indonésia, Itália, Japão, México, Reino Unido, Rússia, Turquia.

Os impostos de interesse são: sob comércio internacional (%), sob exportação (%), receita fiscal (US$), alfândega e importações (US$) e imposto de renda (%). 

Com todas as tabelas montadas e preenchidas, devem ser feitas consultas e relatórios gráficos do tipo relacionamentos entre imposto
desenvolvimento, investimento x desenvolvimento x imposto, imposto x qualidade de vida devem ser possíveis. 

### LINKS ÚTEIS:

https://servicodados.ibge.gov.br/api/docs/paises

77827 - Economia - Total do PIB
77825 - Economia - Total de exportações
77826 - Economia - Total de importações
77821 - Economia - Investimentos em pesquisa e desenvolvimento
77823 - Economia - PIB per capita
77857 - Redes - Indivíduos com acesso à internet
77831 - Indicadores sociais - Índice de desenvolvimento humano

IMPOSTOS: 

Sob Comércio Internacional (%): https://data.worldbank.org/indicator/GC.TAX.INTT.RV.ZS?locations=BR
Sob Exportação (%): https://data.worldbank.org/indicator/GC.TAX.EXPT.ZS?locations=BR
Receita Fiscal (US$): https://data.worldbank.org/indicator/GC.TAX.TOTL.CN?locations=BR
Alfândega e Importações (US$): https://data.worldbank.org/indicator/GC.TAX.IMPT.CN?name_desc=false&locations=BR
Imposto de renda (%): https://data.worldbank.org/indicator/GC.TAX.YPKG.RV.ZS?locations=BR

## Etapas do trabalho:

* ETAPA 1: Descrição dos serviços de coleta de dados escolhidos e indicação de relatórios relevantes a serem construídos.
    a) Amostra dos dados de interesse;
    b) Diagrama Entidade Relacionamento, Modelo Relacional Normalizado e Script SQL;
    c) Instruções SQL da carga de dados;
    d) Banco de dados rodando e com alguns dados.

* ETAPA 2: Sistema rodando com funcionalidades de carga de dados.

* ETAPA 3: Sistema finalizado, incluindo os relatórios.

### Andamento do trabalho:

* Etapa 1:
    a) Amostra dos dados de interesse: conseguimos pegar a maior parte bruta do site do IBGE e o filtro está indo bem. Ainda não conseguimos organizar os impostos.
    b) Vini e eu fizemos boa parte da modelagem juntos, mas ainda faltam ajustes. Vini ficou encarregado de formalizar os diagramas e fez um script inicial do SQL 
    c) So precisamos ajustar os dados (principalmente impostos) e acabar o encapsulamentos nas classes. Depois é so fazer o script de carga de dados.
    d) Com os itens acima feitos, basta juntar tudo e o banco de dados estará rodando com alguns dados.

# TODO:
* Ajustar impostos -> faremos depois pois alguns dados estão faltando, mas os tipos ja foram definidos
* Fazer script de criação de tabelas e verificar diagramas -> conferir
* Fazer script de carga de dados
* Juntar tudo e rodar o banco de dados