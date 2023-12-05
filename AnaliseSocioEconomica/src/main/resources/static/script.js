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
            console.log(data);

            let bla = data.dados.series.duplaAnoAtributo;
            console.log(data.pais.nome);
            for (let ano in bla) {
                if (bla.hasOwnProperty(ano)) {
                    let valor = bla[ano];
                    console.log("ANO: " + ano + " VALOR: " + valor);
                }
            }

            // let duplaAnoAtributo = data.dados.series.duplaAnoAtributo;
            // let anos = Object.keys(duplaAnoAtributo);
            // let valores = Object.values(duplaAnoAtributo);
            //
            // let ctx = document.getElementById('myChart').getContext('2d');
            // new Chart(ctx, {
            //     type: 'line',  // Altere isso para 'bar', 'pie', etc.
            //     data: {
            //         labels: anos,
            //         datasets: [{
            //             label: 'Valor',
            //             data: valores,
            //             backgroundColor: 'rgba(0, 123, 255, 0.5)',
            //             borderColor: 'rgba(0, 123, 255, 1)',
            //             borderWidth: 1
            //         }]
            //     },
            //     options: {
            //         responsive: true,
            //         scales: {
            //             y: {
            //                 beginAtZero: true
            //             }
            //         }
            //     }
            // });
        });
    });
});