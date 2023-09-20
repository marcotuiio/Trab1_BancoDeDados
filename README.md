# Trab1_BancoDeDados

## RESUMO: 
O intuito do trabalho é montar tabelas para analisar socioeconomicamente os países, exibindo PIB e aprofundando-se em importações,
exportações e investimentos em pesquisas. Com essas tabelas montadas, devemos montar tabelas que mostrem dados mais específicos de caratér
social, como PIB per capita, acesso a internet e IDH. 
Agora, impostos de interesse precisam ser definidos (imposto de renda, imposto sob importação e exportação, imposto sob bens
industrializados) e a tabela projetada. Um problema é que existem diversos tipos de imposto e apesar de serem públicos, extrair e
encapsular os dados de interesse pode ser complexo. Talvez um csv contendo país-imposto precisará ser feito na mão, ou via scraping de uma
tabela online.  
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

IMPOSTOS: https://zonos.com/docs/landed-cost/decoder-guides/tax/import-tax-rates-by-country 
	  https://en.wikipedia.org/wiki/List_of_countries_by_tax_rates
          https://data.worldbank.org/indicator/GC.TAX.EXPT.ZS imposto de exportação
          https://data.worldbank.org/indicator/GC.TAX.IMPT.CN imposto de importação
	  https://data.worldbank.org/indicator/GC.TAX.TOTL.CN imposto de renda	

## Etapas do trabalho:

* ETAPA 1: Descrição dos serviços de coleta de dados escolhidos e indicação de relatórios relevantes a serem construídos.
    a) Amostra dos dados de interesse;
    b) Diagrama Entidade Relacionamento, Modelo Relacional Normalizado e Script SQL;
    c) Instruções SQL da carga de dados;
    d) Banco de dados rodando e com alguns dados.

* ETAPA 2: Sistema rodando com funcionalidades de carga de dados.

* ETAPA 3: Sistema finalizado, incluindo os relatórios.
	
