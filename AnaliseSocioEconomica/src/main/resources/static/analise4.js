$(document).ready(function() {
    $('#analise4').click(function() {
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

        $.ajax({
            type: 'POST',
            url: '/monta-pais-analise4-ajax',
            data: JSON.stringify(formData),
            dataType: 'json',
            contentType:"application/json; charset=utf-8",
        }).done(function(data) {
            let nomeAnalise = 'Análise de Imposto de Renda X Imposto de Importação X PIB Total ';
            let impImport = data.impImport.series.duplaAnoAtributo;
            let pibTotal = data.pibTotal.series.duplaAnoAtributo;
            let impRenda = data.impRenda.series.duplaAnoAtributo;

            let anosFiltradosImpImport = {};
            let valoresFiltradosImpImport = {};
            let anosFiltradosPibTotal = {};
            let valoresFiltradosPibTotal = {};
            let anosFiltradosImpRenda = {};
            let valoresFiltradosImpRenda = {};

            if (!anoFim || !anoInicio) {
                nomeAnalise = nomeAnalise + data.paisA.nome;
                anosFiltradosImpImport = Object.keys(impImport);
                valoresFiltradosImpImport = Object.values(impImport);
                anosFiltradosPibTotal = Object.keys(pibTotal);
                valoresFiltradosPibTotal = Object.values(pibTotal);
                anosFiltradosImpRenda = Object.keys(impRenda);
                valoresFiltradosImpRenda = Object.values(impRenda);
            } else {
                nomeAnalise = nomeAnalise + data.paisA.nome + ' intervalo ' + anoInicio + '-' + anoFim;
                for (let ano in impImport) {
                    if (ano >= anoInicio && ano <= anoFim) {
                        anosFiltradosImpImport[ano] = ano;
                        valoresFiltradosImpImport[ano] = impImport[ano];
                    }
                }
                for (let ano in pibTotal) {
                    if (ano >= anoInicio && ano <= anoFim) {
                        anosFiltradosPibTotal[ano] = ano;
                        valoresFiltradosPibTotal[ano] = pibTotal[ano];
                    }
                }
                for (let ano in impRenda) {
                    if (ano >= anoInicio && ano <= anoFim) {
                        anosFiltradosImpRenda[ano] = ano;
                        valoresFiltradosImpRenda[ano] = impRenda[ano];
                    }
                }
            }

            let anosImpImport = Object.values(anosFiltradosImpImport);
            let valoresImpImport = Object.values(valoresFiltradosImpImport);
            let anosPibTotal = Object.values(anosFiltradosPibTotal);
            let valoresPibTotal = Object.values(valoresFiltradosPibTotal);
            let anosImpRenda = Object.values(anosFiltradosImpRenda);
            let valoresImpRenda = Object.values(valoresFiltradosImpRenda);

            const dataset = {
                labels: anosImpImport,
                datasets: [
                    {
                        label: 'IMPOSTO DE IMPORTAÇÃO',
                        fill: false,
                        backgroundColor: 'rgba(123, 172, 32, 0.5)',
                        borderColor: 'rgba(123, 172, 32, 1)',
                        data: valoresImpImport,
                    }, {
                        label: 'PIB TOTAL',
                        fill: false,
                        backgroundColor: 'rgba(223, 89, 22, 0.5)',
                        borderColor: 'rgba(223, 89, 22, 1)',
                        borderDash: [5, 5],
                        data: valoresPibTotal,
                    }
                ]
            };

            createChart1Analise4('myChart1', anosImpRenda, valoresImpRenda);
            createChart2Analise4('myChart2', dataset)

            $('h1').text(nomeAnalise);
        });
    });
});

function createChart1Analise4(chartId, labels, dataset) {
    let existingChart = Chart.getChart(chartId);
    if (existingChart) {
        existingChart.destroy(); // Destrói o gráfico existente
    }

    let ctx = document.getElementById(chartId).getContext('2d');
    let newChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'IMPOSTO DE RENDA',
                data: dataset,
                backgroundColor: 'rgba(0, 123, 255, 0.5)',
                borderColor: 'rgba(0, 123, 255, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false, // Permite ao gráfico não ocupar toda a tela
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

function createChart2Analise4(chartId, dataset) {
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
