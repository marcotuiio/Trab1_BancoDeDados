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
            'anoInicio': anoInicio,
            'anoFim': anoFim
        };

        $.ajax({
            type: 'POST',
            url: '/monta-pais-ajax',
            data: JSON.stringify(formData),
            dataType: 'json',
            contentType:"application/json; charset=utf-8",
        }).done(function(data) {

            let seriesA = data.dadosA.series.duplaAnoAtributo;
            console.log(data.paisA.nome);
            for (let ano in seriesA) {
                if (seriesA.hasOwnProperty(ano)) {
                    let valor = seriesA[ano];
                    console.log("ANO: " + ano + " VALOR: " + valor);
                }
            }
            let seriesB = data.dadosB.series.duplaAnoAtributo;
            console.log(data.paisB.nome);
            for (let ano in seriesB) {
                if (seriesB.hasOwnProperty(ano)) {
                    let valor = seriesB[ano];
                    console.log("ANO: " + ano + " VALOR: " + valor);
                }
            }
            let nomeAnalise = "Análise de contraste de " + atrib + " dos países " + data.paisA.nome + " e " + data.paisB.nome;

            let anos = Object.keys(seriesA);
            let valoresA = Object.values(seriesA);
            let valoresB = Object.values(seriesB);

            const dataset = {
                labels: anos,
                datasets: [
                    {
                        label: 'Dataset 1',
                        data: valoresA,
                        borderColor: 'rgba(0, 123, 255, 1)',
                        backgroundColor: 'rgba(0, 123, 255, 0.5)',
                        yAxisID: 'y',
                    },
                    {
                        label: 'Dataset 2',
                        data: valoresB,
                        borderColor: 'rgba(255, 99, 132, 1)',
                        backgroundColor: 'rgba(255, 99, 132, 0.5)',
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
            interaction: {
                mode: 'index',
                intersect: false,
            },
            stacked: false,
            plugins: {
                title: {
                    display: true,
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
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: 'Chart.js Bar Chart'
                }
            }
        },
    };
    new Chart(ctx, config);
}
