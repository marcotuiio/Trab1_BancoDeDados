$(document).ready(function() {
    $('#pais').change(function() {
        let paisId = $(this).val();
        $.ajax({
            url: '/getNomesAtributos/' + paisId,
            type: 'GET',
            success: function(response) {
                let dados = response.dados;
                let options = '';
                for(let i = 0; i < dados.length; i++) {
                    options += '<option value="' + dados[i] + '">' + dados[i] + '</option>';
                }
                $('#atrib').html(options);
            }
        });
    });
});