$(document).ready(function() {
    $('#meuForm').submit(function(event) {
        event.preventDefault();

        let paisId = $('#pais').val();
        let atrib = $('#atrib').val();
        let anoInicio = $('#anoInicio').val();
        let anoFim = $('#anoFim').val();

        let formData = {
            'paisId': paisId,
            'atrib': atrib,
            'anoInicio': anoInicio,
            'anoFim': anoFim
        };

        $.ajax({
            type: 'POST',
            url: '/teste-ajax',
            data: JSON.stringify(formData),
            dataType: 'json',
            contentType:"application/json; charset=utf-8",
        }).done(function(data) {

            // let bla = data.dados.series.duplaAnoAtributo;
            // console.log(data.pais.nome);
            // for (let ano in bla) {
            //     if (bla.hasOwnProperty(ano)) {
            //         let valor = bla[ano];
            //         console.log("ANO: " + ano + " VALOR: " + valor);
            //     }
            // }

            let duplaAnoAtributo = data.dados.series.duplaAnoAtributo;
            let anos = Object.keys(duplaAnoAtributo);
            let valores = Object.values(duplaAnoAtributo);

            // Crie ou atualize o gráfico no elemento <canvas>
            updateChart('myChart', anos, valores, 400, 400);

        });
    });
});

function updateChart(chartId, labels, dataValues, canvasWidth, canvasHeight) {
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

    // Define a largura e a altura do canvas
    document.getElementById(chartId).width = canvasWidth;
    document.getElementById(chartId).height = canvasHeight;
}

