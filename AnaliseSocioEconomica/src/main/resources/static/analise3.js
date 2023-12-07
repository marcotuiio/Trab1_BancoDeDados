$(document).ready(function() {
    $('#analise2').click(function() {
        let anoInicio = $('#anoInicio').val();
        let anoFim = $('#anoFim').val();

        let formData = {
            'anoInicio': anoInicio,
            'anoFim': anoFim
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
            for (let i = 0; i < data.length; i++) {
                console.log('Pais: ' + data[i].nomePais);
                console.log('Ano: ' + data[i].ano);
                console.log('IDH: ' + data[i].maiorIdhPaisAno);
            }

            $('h1').text(nomeAnalise);
        });
    });
});
