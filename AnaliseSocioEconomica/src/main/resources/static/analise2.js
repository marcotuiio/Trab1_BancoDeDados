$(document).ready(function() {
    $('#analise2').click(function() {
        let paisAId = $('#paisA').val();
        let paisBId = $('#paisB').val();
        let atrib = $('#atrib').val();
        let anoInicio = $('#anoInicio').val();
        let anoFim = $('#anoFim').val();

        let formData = {
            'paisAId': paisAId,
            'paisBId': paisBId,
            'atrib': atrib,
        };

        if (anoInicio && anoFim) {
            if (anoInicio < 2010 || anoInicio > 2021 || anoFim > 2021 || anoFim < 2010 || anoFim < anoInicio) {
                alert('Intervalo incorreto de anos.\nAno mínimo: 2010\nAno máximo: 2021');
                return;
            }
        }

        $.ajax({
            type: 'POST',
            url: '/monta-pais-ajax',
            data: JSON.stringify(formData),
            dataType: 'json',
            contentType:"application/json; charset=utf-8",
        }).done(function(data) {
            let nomeAnalise = '';
            let seriesA = data.dadosA.series.duplaAnoAtributo;
            let seriesB = data.dadosB.series.duplaAnoAtributo;

            let anosFiltradosA = {};
            let valoresFiltradosA = {};
            let anosFiltradosB = {};
            let valoresFiltradosB = {};

            if (!anoFim || !anoInicio) {
                nomeAnalise = "Análise de contraste de " + atrib + " dos países " + data.paisA.nome + " e " + data.paisB.nome;
                anosFiltradosA = Object.keys(seriesA);
                valoresFiltradosA = Object.values(seriesA);
                anosFiltradosB = Object.keys(seriesB);
                valoresFiltradosB = Object.values(seriesB);
            } else {
                nomeAnalise = "Análise de contraste de " + atrib + " dos países " + data.paisA.nome + " e " + data.paisB.nome + " intervalo " + anoInicio + "-" + anoFim;
                // Filtrar os dados com base no intervalo de anos
                for (let ano in seriesA) {
                    if (ano >= anoInicio && ano <= anoFim) {
                        anosFiltradosA[ano] = ano;
                        valoresFiltradosA[ano] = seriesA[ano];
                    }
                }
                for (let ano in seriesB) {
                    if (ano >= anoInicio && ano <= anoFim) {
                        anosFiltradosB[ano] = ano;
                        valoresFiltradosB[ano] = seriesB[ano];
                    }
                }
            }

            let anosArrayA = Object.values(anosFiltradosA);
            let valoresArrayA = Object.values(valoresFiltradosA);
            let anosArrayB = Object.values(anosFiltradosB);
            let valoresArrayB = Object.values(valoresFiltradosB);

            const dataset = {
                labels: anosArrayA,
                datasets: [
                    {
                        label: paisAId,
                        data: valoresArrayA,
                        borderColor: 'rgba(128, 0, 128, 1)', // Cor da borda roxa
                        backgroundColor: 'rgba(128, 0, 128, 0.5)', // Cor de fundo roxa
                        yAxisID: 'y',
                    },
                    {
                        label: paisBId,
                        data: valoresArrayB,
                        borderColor: 'rgba(255, 255, 0, 1)', // Cor da borda amarela
                        backgroundColor: 'rgba(255, 255, 0, 0.5)', // Cor de fundo amarela
                        yAxisID: 'y1',
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
