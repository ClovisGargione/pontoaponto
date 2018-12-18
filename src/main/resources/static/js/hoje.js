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
    setInterval(function () {
        location.reload();
    }, 300000);
});
function horaStringParaDataAtualHora(string) {
    var hora = string.split(":");
    var data = new Date();
    data.setHours(hora[0]);
    data.setMinutes(hora[1]);
    return data;
}

function fecharModal(idModal) {
    $(idModal).modal('hide');
}

function limparCampoModal(inputId) {
    var campo = document.getElementById(inputId);
    campo.value = "";
}

var idRegistro = 0;
function getRegistroId(id){
    idRegistro = id;
}

function inserirRegistro(inputId) {
    var registro = document.getElementById(inputId).value;
    var data = {registroId: idRegistro, registroDia: registro};
    var opcoesToastr = {timeOut: 500, hideMethod: 'slideUp', showMethod: 'slideDown', preventDuplicates: true, showEasing: "swing", hideEasing: "linear", onHidden: function () {
            location.reload();
        }};
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
        complete: function () {
            fecharModal('#registroManualModal');
        },
        async: true,
        contentType: 'application/json',
        data: JSON.stringify(data),
        cache: false,
        processData: false,
        timeout: 60000
    });

}

function deleteRegistro(registroId) {
    var opcoesToastr = {timeOut: 500, hideMethod: 'slideUp', showMethod: 'slideDown', preventDuplicates: true, showEasing: "swing", hideEasing: "linear", onHidden: function () {
            location.reload();
        }};
    toastr.options = opcoesToastr;
    $.ajax({
        type: "DELETE",
        url: "/registros/remover/" + registroId,
        success: function (data) {
            toastr.success("Registro removido com sucesso!");
        },
        error: function (response) {
            toastr.error("Ops.. não foi possível remover o registro!");
        },
        complete: function () {
            fecharModal('#removerRegistroModal');
        },
        async: true,
        cache: false,
        processData: false,
        timeout: 60000
    });
}
var registro = {};
function confirmarExclusaoDeRegistro(item) {
    registro = JSON.parse(item);
    $("#perguntaConfirmar").text("Deseja remover o registro " + registro.horaFormatada + " ?");
    $('#removerRegistroModal').modal('show');
}

function removerRegistro() {
    deleteRegistro(registro.id);
}