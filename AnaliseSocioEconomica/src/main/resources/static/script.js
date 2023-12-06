$(document).ready(function() {
    $('#analise1').click(function() {

        let paisAId = $('#paisA').val();
        let atrib = $('#atrib').val();
        let anoInicio = $('#anoInicio').val();
        let anoFim = $('#anoFim').val();

        let formData = {
            'paisAId': paisAId,
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

            let bla = data.dadosA.series.duplaAnoAtributo;
            console.log(data.paisA.nome);
            for (let ano in bla) {
                if (bla.hasOwnProperty(ano)) {
                    let valor = bla[ano];
                    console.log("ANO: " + ano + " VALOR: " + valor);
                }
            }
            let nomeAnalise = "Análise de " + atrib + " do país " + data.paisA.nome;

            let duplaAnoAtributo = data.dadosA.series.duplaAnoAtributo;
            let anos = Object.keys(duplaAnoAtributo);
            let valores = Object.values(duplaAnoAtributo);

            createChart1Analise1('myChart1', anos, valores);
            createChart2Analise1('myChart2', anos, valores);
            $('h1').text(nomeAnalise);

        });
    });

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

            let bla = data.dadosA.series.duplaAnoAtributo;
            console.log(data.paisA.nome);
            for (let ano in bla) {
                if (bla.hasOwnProperty(ano)) {
                    let valor = bla[ano];
                    console.log("ANO: " + ano + " VALOR: " + valor);
                }
            }
            let bla2 = data.dadosB.series.duplaAnoAtributo;
            console.log(data.paisB.nome);
            for (let ano in bla) {
                if (bla.hasOwnProperty(ano)) {
                    let valor = bla[ano];
                    console.log("ANO: " + ano + " VALOR: " + valor);
                }
            }
            let nomeAnalise = "Análise de contraste de " + atrib + " dos países " + data.paisA.nome + " e " + data.paisB.nome;

            // let duplaAnoAtributo = data.dados.series.duplaAnoAtributo;
            // let anos = Object.keys(duplaAnoAtributo);
            // let valores = Object.values(duplaAnoAtributo);
            //
            // createChart1Analise1('myChart1', anos, valores);
            // createChart2Analise1('myChart2', anos, valores);
            $('h1').text(nomeAnalise);

        });
    });

    document.getElementById("visualizarDados").addEventListener("click", function () {
        window.location.href = "/visualizar-dados";
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


