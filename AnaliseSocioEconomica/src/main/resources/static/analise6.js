$(document).ready(function() {
    $('#analise6').click(function() {
        let paisAId = $('#paisA').val();
        let paisBId = $('#paisB').val();
        let atrib = $('#atrib').val();
        let anoInicio = $('#anoInicio').val();
        let anoFim = $('#anoFim').val();

        let formData = {
            'paisAId': paisAId
        };

        if (anoInicio && anoFim) {
            if (anoInicio < 2010 || anoInicio > 2021 || anoFim > 2021 || anoFim < 2010 || anoFim < anoInicio) {
                alert('Intervalo incorreto de anos.\nAno mínimo: 2010\nAno máximo: 2021');
                return;
            }
        }

        $.ajax({
            type: 'POST',
            url: '/monta-pais-analise6-ajax',
            data: JSON.stringify(formData),
            dataType: 'json',
            contentType:"application/json; charset=utf-8",
        }).done(function(data) {
            let nomeAnalise = "";
            let idh = data.idh.series.duplaAnoAtributo;
            let net = data.net.series.duplaAnoAtributo;
            let pesquisas = data.pesquisas.series.duplaAnoAtributo;

            let anosFiltradosIDH = {};
            let valoresFiltradosIDH = {};
            let anosFiltradosNet = {};
            let valoresFiltradosNet = {};
            let anosFiltradosPesquisas = {};
            let valoresFiltradosPesquisas = {};

            if (!anoFim || !anoInicio) {
                nomeAnalise = "Análise de IDX x Internet x Pesquisas do país " + data.paisA.nome;
                anosFiltradosIDH = Object.keys(idh);
                valoresFiltradosIDH = Object.values(idh);
                anosFiltradosNet = Object.keys(net);
                valoresFiltradosNet = Object.values(net);
                anosFiltradosPesquisas = Object.keys(pesquisas);
                valoresFiltradosPesquisas = Object.values(pesquisas);
            } else {
                nomeAnalise = "Análise de IDX x Internet x Pesquisas do país " + data.paisA.nome + " intervalo " + anoInicio + "-" + anoFim;
                // Filtrar os dados com base no intervalo de anos
                for (let ano in idh) {
                    if (ano >= anoInicio && ano <= anoFim) {
                        anosFiltradosIDH[ano] = ano;
                        valoresFiltradosIDH[ano] = idh[ano];
                    }
                }
                for (let ano in net) {
                    if (ano >= anoInicio && ano <= anoFim) {
                        anosFiltradosNet[ano] = ano;
                        valoresFiltradosNet[ano] = net[ano];
                    }
                }
                for (let ano in pesquisas) {
                    if (ano >= anoInicio && ano <= anoFim) {
                        anosFiltradosPesquisas[ano] = ano;
                        valoresFiltradosPesquisas[ano] = pesquisas[ano];
                    }
                }
            }

            let anosIdh = Object.values(anosFiltradosIDH);
            let valoresIdh = Object.values(valoresFiltradosIDH);
            let anosNet = Object.values(anosFiltradosNet);
            let valoresNet = Object.values(valoresFiltradosNet);
            let anosPesquisa = Object.values(anosFiltradosPesquisas);
            let valoresPesquisa = Object.values(valoresFiltradosPesquisas);

            const dataset = {
                labels: anosIdh,
                datasets: [
                    {
                        label: "IDH",
                        data: valoresIdh,
                        borderColor: 'rgba(123, 172, 32, 1)', // Cor da borda verde
                        backgroundColor: 'rgba(123, 172, 32, 0.5)', // Cor de fundo verde
                        yAxisID: 'y',
                    },
                    {
                        label: "NET",
                        data: valoresNet,
                        borderColor: 'rgba(223, 89, 22, 1)', // Cor da borda laranja
                        backgroundColor: 'rgba(223, 89, 22, 0.5)', // Cor de fundo laranja
                        yAxisID: 'y1',
                    },
                    {
                        label: "PESQUISAS",
                        data: valoresPesquisa,
                        borderColor: 'rgba(252, 96, 168, 1)', // Cor da borda rosa
                        backgroundColor: 'rgba(252, 96, 168, 0.5)', // Cor de fundo rosa
                        yAxisID: 'y2',
                    }
                ]
            };

            createChart1Analise2('myChart1', dataset);
            createChart2Analise2('myChart2', dataset);
            $('h1').text(nomeAnalise);

        });
    });
});

function createChart1Analise2(chartId, dataset) {
    let existingChart = Chart.getChart(chartId);
    if (existingChart) {
        existingChart.destroy();
    }

    let ctx = document.getElementById(chartId).getContext('2d');
    const config = {
        type: 'line',
        data: dataset,
        options: {
            responsive: true,
            maintainAspectRatio: false, // Permite ao gráfico não ocupar toda a tela
            interaction: {
                mode: 'index',
                intersect: false,
            },
            stacked: false,
            plugins: {
                title: {
                    display: false,
                    text: 'Chart.js Line Chart - Multi Axis'
                }
            },
            scales: {
                y: {
                    type: 'linear',
                    display: true,
                    position: 'left',
                },
                y1: {
                    type: 'linear',
                    display: true,
                    position: 'right',

                    // grid line settings
                    grid: {
                        drawOnChartArea: false, // only want the grid lines for one axis to show up
                    },
                },
            }
        },
    };
    new Chart(ctx, config);
}

function createChart2Analise2(chartId, dataset) {
    let existingChart = Chart.getChart(chartId);
    if (existingChart) {
        existingChart.destroy();
    }

    let ctx = document.getElementById(chartId).getContext('2d');
    const config = {
        type: 'bar',
        data: dataset,
        options: {
            responsive: true,
            maintainAspectRatio: false, // Permite ao gráfico não ocupar toda a tela
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: false,
                    text: 'Chart.js Bar Chart'
                }
            }
        },
    };
    new Chart(ctx, config);
}
