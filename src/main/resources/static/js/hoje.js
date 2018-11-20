/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    $(function () {
        $('#horaRegistroManual').datetimepicker({
            format: 'LT',
            locale: 'pt-br'
        });
    });
    
    $('#registroManualModal').on('hide.bs.modal', function (e) {
        limparCampoModal("registro");
    });
    //alterar para buscar valor em um endpoint atualizando o fragmento do html
    setInterval(function(){ location.reload(); }, 300000);
});
function horaStringParaDataAtualHora(string){
    var hora = string.split(":");
    var data = new Date();
    data.setHours(hora[0]);
    data.setMinutes(hora[1]);
    return data;
}

function fecharModal(){
    $('#registroManualModal').modal('hide');
}

function limparCampoModal(inputId){
    var campo = document.getElementById(inputId);
    campo.value = "";
}

function inserirRegistro(inputId){
    var registro = document.getElementById(inputId).value;
    var dataHoraRegistro = horaStringParaDataAtualHora(registro);
    var opcoesToastr = {timeOut: 500, hideMethod: 'slideUp', showMethod: 'slideDown', preventDuplicates: true, showEasing: "swing", hideEasing: "linear", onHidden: function() { location.reload(); }};
    toastr.options = opcoesToastr;
    $.ajax({
            type: "POST",
            url: "/registros",
            success: function (data) {
                toastr.success("Registro adicionado com sucesso!");
            },
            error: function (response) {
                    toastr.error("Ops.. não foi possível adicionar o registro!");
            },
            complete: function(){
                fecharModal();
            },
            async: true,
            contentType: 'application/json',
            data: JSON.stringify(dataHoraRegistro),
            cache: false,
            processData: false,
            timeout: 60000
    });
}