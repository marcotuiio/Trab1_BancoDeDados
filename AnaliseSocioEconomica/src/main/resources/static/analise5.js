$(document).ready(function() {
    $('#analise5').click(function() {
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
            url: '/monta-pais-analise5-ajax',
            data: JSON.stringify(formData),
            dataType: 'json',
            contentType:"application/json; charset=utf-8",
        }).done(function(data) {
            let nomeAnalise = 'Análise de IDH X Arrecadação ';
            let idh = data.idh.series.duplaAnoAtributo;
            let impReceitaFiscal = data.impReceitaFiscal.series.duplaAnoAtributo;

            let anosFiltradosIDH = {};
            let valoresFiltradosIDH = {};
            let anosFiltradosImpReceitaFiscal = {};
            let valoresFiltradosImpReceitaFiscal = {};

            if (!anoFim || !anoInicio) {
                nomeAnalise = nomeAnalise + data.paisA.nome;
                anosFiltradosIDH = Object.keys(idh);
                valoresFiltradosIDH = Object.values(idh);
                anosFiltradosImpReceitaFiscal = Object.keys(impReceitaFiscal);
                valoresFiltradosImpReceitaFiscal = Object.values(impReceitaFiscal);
            } else {
                nomeAnalise = nomeAnalise + data.paisA.nome + ' intervalo ' + anoInicio + '-' + anoFim;
                for (let ano in idh) {
                    if (ano >= anoInicio && ano <= anoFim) {
                        anosFiltradosIDH[ano] = ano;
                        valoresFiltradosIDH[ano] = idh[ano];
                    }
                }
                for (let ano in impReceitaFiscal) {
                    if (ano >= anoInicio && ano <= anoFim) {
                        anosFiltradosImpReceitaFiscal[ano] = ano;
                        valoresFiltradosImpReceitaFiscal[ano] = impReceitaFiscal[ano];
                    }
                }
            }

            let anosIdh = Object.values(anosFiltradosIDH);
            let valoresIdh = Object.values(valoresFiltradosIDH);
            let anosImpReceitaFiscal = Object.values(anosFiltradosImpReceitaFiscal);
            let valoresImpReceitaFiscal = Object.values(valoresFiltradosImpReceitaFiscal);

            createChart1Analise5('myChart1', anosIdh, valoresIdh);
            createChart2Analise5('myChart2', anosImpReceitaFiscal, valoresImpReceitaFiscal);

            $('h1').text(nomeAnalise);
        });
    });
});

function createChart1Analise5(chartId, labels, data) {
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
                label: 'IDH',
                data: data,
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

function createChart2Analise5(chartId, labels, data) {
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
                label: 'ARRECADAÇÃO',
                data: data,
                backgroundColor: 'rgba(255, 99, 132, 0.5)', // Cor diferente
                borderColor: 'rgba(255, 99, 132, 1)', // Cor diferente
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
            }
        }
    });
}
