$(document).ready(function() {
    $('#analise3').click(function() {
        let paisAId = $('#paisA').val();
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

        let ajax1 = $.ajax({
            type: 'POST',
            url: '/monta-rank-analise3-ajax',
            data: JSON.stringify(formData),
            dataType: 'json',
            contentType:"application/json; charset=utf-8",
        });

        let ajax2 = $.ajax({
            type: 'POST',
            url: '/monta-pais-analise3-ajax',
            data: JSON.stringify(formData),
            dataType: 'json',
            contentType:"application/json; charset=utf-8",
        });

        $.when(ajax1, ajax2).then(function(data1, data2) {
            let nomeAnalise = 'Percentual Imposto de Importação X Rank IDH'
            let labels = [];
            let todosPercentual = [];
            let maioresIdh = [];

            for (let i = 0; i < data1[0].length; i++) {
                labels.push(data1[0][i].ano);
                todosPercentual.push({x: data1[0][i].ano, y: data1[0][i].impImportDivReceita, nomePais: data1[0][i].nomePais});
                maioresIdh.push({x: data1[0][i].ano, y: data1[0][i].maiorIdhPaisAno, nomePais: data1[0][i].nomePais});
            }

            let duplaAnoAtributo = data2[0][0].idhEspecifico.series.duplaAnoAtributo;
            let valoresIdhEspecifico = Object.values(duplaAnoAtributo);
            let percentualEspecifico = [];
            for (let i = 1; i < data2[0].length; i++) {
                percentualEspecifico.push(data2[0][i].percentualEspecifico);
                console.log(percentualEspecifico);
            }

            const dataset1 = {
                labels: labels,
                datasets: [
                    {
                        label: 'PERCENTUAL PAÍS MAIOR IDH',
                        data: todosPercentual,
                        borderColor: 'rgba(0, 255, 0, 1)', // Cor da borda ver
                        backgroundColor: 'rgba(0, 255, 0, 0.5)', // Cor de fundo verde
                        yAxisID: 'y',
                    },
                    {
                        label: 'PERCENTUAL PAÍS ESCOLHIDO',
                        data: percentualEspecifico,
                        borderColor: 'rgba(139, 0, 0, 1)', // Cor da borda vermei
                        backgroundColor: 'rgba(139, 0, 0, 0.5)', // Cor de fundo vermei
                        yAxisID: 'y1',
                    }
                ]
            };

            const dataset2 = {
                labels: labels,
                datasets: [
                    {
                        label: 'MAIOR IDH',
                        data: maioresIdh,
                        borderColor: 'rgba(0, 255, 0, 1)', // Cor da borda ver
                        backgroundColor: 'rgba(0, 255, 0, 0.5)', // Cor de fundo verde
                        yAxisID: 'y',
                    },
                    {
                        label: 'IDH PAÍS ESCOLHIDO',
                        data: valoresIdhEspecifico,
                        borderColor: 'rgba(255, 40, 0, 1)', // Cor da borda vermei
                        backgroundColor: 'rgba(255, 40, 0, 0.5)', // Cor de fundo vermei
                        yAxisID: 'y',
                    }
                ]
            };

            createChart1Analise3('myChart1', dataset1);
            createChart2Analise3('myChart2', dataset2);

            $('h1').text(nomeAnalise);
        });
    });
});

function createChart1Analise3(chartId, dataset) {
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

function createChart2Analise3(chartId, dataset) {
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
