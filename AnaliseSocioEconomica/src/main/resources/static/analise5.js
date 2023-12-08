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
            url: '/monta-pais-analise5-ajax',
            data: JSON.stringify(formData),
            dataType: 'json',
            contentType:"application/json; charset=utf-8",
        }).done(function(data) {
            let nomeAnalise = '';
            let idh = data.idh.series.duplaAnoAtributo;
            // Arrecadacao

            let anosFiltradosIDH = {};
            let valoresFiltradosIDH = {};
            // anosFiltradosArrecadacao
            // valoresFiltradosArrecadacao

            if (!anoFim || !anoInicio) {
                // nomeAnlise
                anosFiltradosIDH = Object.keys(idh);
                valoresFiltradosIDH = Object.values(idh);
                // Arrecadacao
                // Arrecadacao
            } else {
                // nomeAnalise
                for (let ano in idh) {
                    if (ano >= anoInicio && ano <= anoFim) {
                        anosFiltradosIDH[ano] = ano;
                        valoresFiltradosIDH[ano] = idh[ano];
                    }
                }
                // Arrecadacao
            }

            let anosIdh = Object.values(anosFiltradosIDH);
            let valoresIdh = Object.values(valoresFiltradosIDH);
            // anosArrecadacao
            // valoresArrecadacao

            // dataset???

            // createChart1Analise5('myChart1', ???);
            // createChart2Analise5('myChart2', ???);

            $('h1').text(nomeAnalise);
        });
    });
});

// function createChart1Analise5(chartId, ???) {
//     let existingChart = Chart.getChart(chartId);
//     if (existingChart) {
//         existingChart.destroy();
//     }
//
//     let ctx = document.getElementById(chartId).getContext('2d');
// }
//
// function createChart2Analise5(chartId, ???) {
//     let existingChart = Chart.getChart(chartId);
//     if (existingChart) {
//         existingChart.destroy();
//     }
//
//     let ctx = document.getElementById(chartId).getContext('2d');
// }
