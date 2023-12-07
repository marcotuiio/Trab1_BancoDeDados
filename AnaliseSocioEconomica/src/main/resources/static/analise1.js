$(document).ready(function() {
    $('#analise1').click(function() {
        let paisAId = $('#paisA').val();
        let atrib = $('#atrib').val();
        let anoInicio = $('#anoInicio').val();
        let anoFim = $('#anoFim').val();

        let formData = {
            'paisAId': paisAId,
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
            let duplaAnoAtributo = data.dadosA.series.duplaAnoAtributo;

            let anosFiltrados = {};
            let valores = {};

            if (!anoFim || !anoInicio) {
                nomeAnalise = "Análise de " + atrib + " do país " + data.paisA.nome;
                anosFiltrados = Object.keys(duplaAnoAtributo);
                valores = Object.values(duplaAnoAtributo);

            } else {
                nomeAnalise = "Análise de " + atrib + " do país " + data.paisA.nome + " intervalo " + anoInicio + "-" + anoFim;
                // Filtrar os dados com base no intervalo de anos
                for (let ano in duplaAnoAtributo) {
                    if (ano >= anoInicio && ano <= anoFim) {
                        anosFiltrados[ano] = ano;
                        valores[ano] = duplaAnoAtributo[ano];
                    }
                }
            }

            let anosArray = Object.values(anosFiltrados);
            let valoresArray = Object.values(valores);

            createChart1Analise1('myChart1', anosArray, valoresArray);
            createChart2Analise1('myChart2', anosArray, valoresArray);

            $('h1').text(nomeAnalise);
        });
    });
});

function createChart1Analise1(chartId, labels, dataValues) {
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
                label: 'Valor',
                data: dataValues,
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

function createChart2Analise1(chartId, labels, dataValues) {
    let existingChart = Chart.getChart(chartId);

    if (existingChart) {
        existingChart.destroy(); // Destrói o gráfico existente
    }

    let ctx = document.getElementById(chartId).getContext('2d');
    let newChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Valor',
                data: dataValues,
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
