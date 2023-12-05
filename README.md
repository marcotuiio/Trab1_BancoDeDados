# Trab1_BancoDeDados

## RESUMO GERAL: 
O intuito do trabalho é montar tabelas para analisar socioeconomicamente os países, exibindo PIB e aprofundando-se em importações,
exportações, investimentos em pesquisas e IDH. 

Um problema é que existem diversos tipos de imposto e apesar de serem públicos, extrair e encapsular os dados de interesse 
mostrou-se uma tarefa complexa, pois cada país possui sua particularidade fiscal específica. 

A aplicação terá a capacidade de atualizar o banco de dados, usando as APIs, a cada X intervalo de tempo.

Com esses dados filtrados, pretendemos realizar consultadas e comparações cruzando dados variados, como por exemplo, 
imposto x arrecadação, imposto x desenvolvimento, imposto x investimento, imposto x qualidade de vida, etc.

Os países foco de estudo serão os membros do G20, por questões de importância, relacionamentos com o Brasil e influência. 
Sendo eles: África do Sul, Alemanha, Arábia Saudita, Argentina, Austrália, Brasil, Canadá, China, Coreia do Sul, 
Estados Unidos, França, Índia, Indonésia, Itália, Japão, México, Reino Unido, Rússia, Turquia.

![alt text](https://static.mundoeducacao.uol.com.br/mundoeducacao/2023/09/1-bandeira-dos-participantes-do-g20-grupo-dos-20-ate-o-ano-de-2023.jpg)



Os impostos de interesse são: sob comércio internacional (%), sob exportação (%), receita fiscal (DÓLAR), alfândega e importações (DÓLAR) e imposto de renda (%). 

Portanto ao final das consultas, filtros e encapsulamentos, os dados serão armazenados em um banco de dados relacional, e futuras consultas para
geração de relatórios serão feitas com SQL. 
* Relatórios gráficos almejados:
    - PIB x IDH
    - PIB x Investimento
    - PIB x Exportação x Importação x Impostos
    - PIB x Impostos
    - IDH x Impostos
    - Investimento x Impostos
    - Exportação x Impostos
    - Importação x Impostos
    - IDH x Investimento x Impostos

##### Rascunho estudo dos estados e cidades
Por ser um caso particular e merecedor de destaque, os estados brasileiros serão inclusos no estudo, e algumas cidades em especial com Londrina, São Paulo, Curitiba,
Brasília e Rio de Janeiro. Dados inclusos serão: população, PIB estadual e partipação no PIB nacional, arrecadação total e ICMS (imposto estadual sob circulação de produtos).

* links:
https://www.undp.org/pt/brazil/desenvolvimento-humano/painel-idhm IDH
https://servicodados.ibge.gov.br/api/docs/localidades IBGE ESTADOS
https://apidatalake.tesouro.gov.br/docs/siconfi/ API TESOURO NACIONAL
https://www.tesourotransparente.gov.br/temas/estados-e-municipios/transferencias-a-estados-e-municipios TRANSFERÊNCIAS ESTADOS
https://taxshape.com/utilitario/tabela-icms-em-excel-interna-e-interestadual-download/ ICMS ESTADOS
http://www.ipeadata.gov.br/Default.aspx  API IPEA

### FONTES DE DADOS:

Para a extração dos dados foram utilizados as seguintes fontes:

#### IBGE: 

A API do IBGE foi utilizada para modelar os países e os indicativos fundamentais. 

    - https://servicodados.ibge.gov.br/api/docs/paises

* Indicativos e os códigos necessários para os requests:
    - 77827 - Economia - Total do PIB
    - 77825 - Economia - Total de exportações
    - 77826 - Economia - Total de importações
    - 77821 - Economia - Investimentos em pesquisa e desenvolvimento
    - 77823 - Economia - PIB per capita
    - 77857 - Redes - Indivíduos com acesso à internet
    - 77831 - Indicadores sociais - Índice de desenvolvimento humano

* Exemplo de request:
    ```json
        [ {
    "id" : 77831,
    "indicador" : "Indicadores sociais - Índice de desenvolvimento humano",
    "series" : [ {
        "pais" : {
        "id" : "BR",
        "nome" : "Brasil"
        },
        "serie" : [ {
        "-" : null
        }, {
        "2008-2010" : null
        }, {
        "2009" : "0.717"
        }, {
        "2009-2011" : null
        }, {
        "2010" : "0.723"
        }, {
        "2010-2012" : null
        }, {
        "2010-2015" : null
        }, {
        "2011" : "0.728"
        }, {
        "2011-2013" : null
        }, {
        "2012" : "0.732"
        }, {
        "2012-2014" : null
        }, {
        "2013" : "0.75"
        }, {
        "2013-2015" : null
        }, {
        "2014" : "0.754"
        }, {
        "2014-2016" : null
        }, {
        "2015" : "0.753"
        }, {
        "2015-2017" : null
        }, {
        "2015-2020" : null
        }, {
        "2016" : "0.755"
        }, {
        "2016-2018" : null
        }, {
        "2017" : "0.759"
        }, {
        "2017-2019" : null
        }, {
        "2018" : "0.764"
        }, {
        "2018-2020" : null
        }, {
        "2019" : "0.766"
        }, {
        "2020" : "0.758"
        }, {
        "2021" : "0.754"
        }, {
        "2022" : null
        } ]
    } ]
    } ]

    ```

#### WorldBank: 

Para extração dos impostos, foi utilizado o site WorldBank, que possui um conjunto de datasets sobre 
assuntos variados de diversos países, e os impostos são organizados por tipo de imposto e por país.

    - https://data.worldbank.org/

* Impostos de interesse e link para acessar dataset original: 

    - Sob Comércio Internacional (%): https://data.worldbank.org/indicator/GC.TAX.INTT.RV.ZS?locations=BR
    - Sob Exportação (%): https://data.worldbank.org/indicator/GC.TAX.EXPT.ZS?locations=BR
    - Receita Fiscal (US$): https://data.worldbank.org/indicator/GC.TAX.TOTL.CN?locations=BR
    - Alfândega e Importações (US$): https://data.worldbank.org/indicator/GC.TAX.IMPT.CN?name_desc=false&locations=BR
    - Imposto de renda (%): https://data.worldbank.org/indicator/GC.TAX.YPKG.RV.ZS?locations=BR

<!-- * Exemplo do CSV:
    ![alt text](Trab1_BancoDeDados\exemploCSV.png?raw=true "Exemplo CSV") -->

### Análises Planejadas

Para tal, será utilizada a biblioteca ChartJS do JavaScript, que permite a criação de gráficos de diversos tipos, como linhas, barras, pizza, etc.
Os gráficos serão montados por requisição AJAX e existe na interface da aplicação pre sets das análises propostas, permitindo que o usuário escolha
ainda alguns parêmetros, como país e intevalo.

* Páis individualmente e seus atributos.
* Comparação entre dois países e seus atributos. 
* Países com maior IDH x Países com maiores impostos
* Imposto Importação x Imposto Renda x Arrecadação
* 
* 

## Etapas do trabalho:

* ETAPA 1: Descrição dos serviços de coleta de dados escolhidos e indicação de relatórios relevantes a serem construídos.

    a) Amostra dos dados de interesse;
    b) Diagrama Entidade Relacionamento, Modelo Relacional Normalizado e Script SQL;
    c) Instruções SQL da carga de dados;
    d) Banco de dados rodando e com alguns dados.

* ETAPA 2: Sistema rodando com funcionalidades de carga de dados.

* ETAPA 3: Sistema finalizado, incluindo os relatórios.

<!-- ### Banco de dados:

#### DER:
![alt text](inserir imagem do der)

#### Modelo Relacional:
![alt text](inserir imagem do modelo relacional)

#### Script SQL:
encaminhar script sql -->

