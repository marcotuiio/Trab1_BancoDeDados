$(document).ready(function() {
    $('#analise3').click(function() {
        let anoInicio = $('#anoInicio').val();
        let anoFim = $('#anoFim').val();

        let formData = { };

        if (anoInicio && anoFim) {
            if (anoInicio < 2010 || anoInicio > 2021 || anoFim > 2021 || anoFim < 2010 || anoFim < anoInicio) {
                alert('Intervalo incorreto de anos.\nAno mínimo: 2010\nAno máximo: 2021');
                return;
            }
        }

        $.ajax({
            type: 'POST',
            url: '/monta-pais-analise3-ajax',
            data: JSON.stringify(formData),
            dataType: 'json',
            contentType:"application/json; charset=utf-8",
        }).done(function(data) {
            let nomeAnalise = 'Percentual Imposto de Importação X Rank IDH'
            let labels = [];
            let dataset1 = [];
            let dataset2 = [];

            for (let i = 0; i < data.length; i++) {
                labels.push(data[i].ano);
                dataset1.push({x: data[i].ano, y: data[i].impImportDivReceita, nomePais: data[i].nomePais});
                dataset2.push({x: data[i].ano, y: data[i].maiorIdhPaisAno, nomePais: data[i].nomePais});
            }

            createChart1Analise3('myChart1', labels, dataset1);
            createChart2Analise3('myChart2', labels, dataset2);

            $('h1').text(nomeAnalise);
        });
    });
});

function createChart1Analise3(chartId, labels, dataset) {
    let existingChart = Chart.getChart(chartId);
    if (existingChart) {
        existingChart.destroy();
    }

    let ctx = document.getElementById(chartId).getContext('2d');
    let newChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'PERCENTUAL IMPOSTO DE IMPORTAÇÃO',
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
            },
            plugins: {
                tooltip: {
                    callbacks: {
                        title: function(tooltipItem) {
                            return dataset[tooltipItem[0].dataIndex].nomePais;
                        }
                    }
                }
            }
        }
    });
}

function createChart2Analise3(chartId, labels, dataset) {
    let existingChart = Chart.getChart(chartId);
    if (existingChart) {
        existingChart.destroy();
    }

    let ctx = document.getElementById(chartId).getContext('2d');
    let newChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'MELHOR IDH',
                data: dataset,
                backgroundColor: 'rgba(255, 99, 132, 0.5)',
                borderColor: 'rgba(255, 99, 132, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false, // Permite ao gráfico não ocupar toda a tela
            scales: {
                y: {
                    stacked: true
                }
            },
            plugins: {
                tooltip: {
                    callbacks: {
                        title: function(tooltipItem) {
                            return dataset[tooltipItem[0].dataIndex].nomePais;
                        }
                    }
                }
            }
        }
    });
}
