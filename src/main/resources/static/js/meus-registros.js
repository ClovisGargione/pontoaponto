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
    
    $('#datepicker').datepicker({
        format: 'dd/mm/yyyy',
        language: 'pt-BR'
    });
    
    $('#datepickerDia').datepicker({
        format: 'dd/mm/yyyy',
        language: 'pt-BR'
    });
    
   $('[data-toggle="popover"]').popover({
        trigger: 'hover'
    });
    
    $('#registroManualModal').on('hide.bs.modal', function (e) {
        limparCampoModal("registro");
    });
    
    /*
    $('#pageSizeSelect').change(function (evt) {
        window.location.replace("/meusregistros?pageSize=" + this.value + "&page=1");
    });*/
});

function changePageAndSize(select) {
    //$('#pageSizeSelect').change(function (evt) {
        var parametrosFiltro = montarUrlPesquisa();
        if(buscarComFiltros){
        $("#lista-registros").load(url + "pageSize=" + select.value + "&page=1&" + parametrosFiltro);
        }else{
            window.location.replace(url + "pageSize=" + select.value + "&page=1&" + parametrosFiltro);
        }
   //});
}
function applyStyleForElements(id, propertie, style_) {
    var elms = document.getElementById(id);
    elms.style[propertie] = style_;
}

function limpar() {
    buscarComFiltros = false;
    var dataInicial = document.getElementById('dataInicial');
    var dataFinal = document.getElementById('dataFinal');
    dataInicial.value = '';
    dataFinal.value = '';
    location.reload();
}

var url = "/meusregistros?";
var filtro = {};
var buscarComFiltros = false;

function pesquisar() {
    var parametros = montarUrlPesquisa();
    url = "/meusregistros/pesquisar?";
    $("#lista-registros").load(url+parametros);
    buscarComFiltros = true;
}

function montarUrlPesquisa(){
    var dataInicial = document.getElementById('dataInicial');
    var dataFinal = document.getElementById('dataFinal');
    var todos = document.getElementById('todos');
    var incompletos = document.getElementById('incompletos');
    var dataInicialParam = dataInicial.value;
    var dataFinalParam = dataFinal.value;
    var todosParam = todos.checked;
    var incompletosParam = incompletos.checked;
    var filtro = {};
    var urlParametros = "todos=" + todosParam + "&" + "incompletos=" + incompletosParam;
    if (dataInicialParam) {
        dataInicialParam = moment(dataInicialParam, "DD/MM/YYYY").valueOf();
        filtro.dataInicial = dataInicialParam;
        urlParametros += "&dataInicial=" + filtro.dataInicial;
    }
    if(dataFinalParam){
        dataFinalParam = moment(dataFinalParam, "DD/MM/YYYY").valueOf();
        filtro.dataFinal= dataFinalParam;
        urlParametros +=  "&dataFinal=" + filtro.dataFinal;
    }
    return urlParametros;
}

function paginacao(urn){
    var uri = url+urn;
    var parametrosFiltro = montarUrlPesquisa();
    if(buscarComFiltros){
        $("#lista-registros").load(uri+"&"+parametrosFiltro);
    }else{
        location.href = uri+"&"+parametrosFiltro;
    }
}
var registro = {};
function confirmarRemoverRegistro(registro_){
     registro = JSON.parse(registro_);
     $("#perguntaConfirmar").text("Deseja remover os registros do dia " + registro.dataRegistroFormatada + " ?");
     $('#removerRegistroModal').modal('show');
}

function removerRegistro() {
    deleteRegistro(registro.id);
}

function deleteRegistro(registroId) {
    var opcoesToastr = {timeOut: 500, hideMethod: 'slideUp', showMethod: 'slideDown', preventDuplicates: true, showEasing: "swing", hideEasing: "linear", onHidden: function () {
            location.reload();
        }};
    toastr.options = opcoesToastr;
    $.ajax({
        type: "DELETE",
        url: "/meusregistros/remover/" + registroId,
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

var idRegistro = 0;
function getRegistroId(id){
    idRegistro = id;
}

function fecharModal(idModal) {
    $(idModal).modal('hide');
}

function limparCampoModal(inputId) {
    var campo = document.getElementById(inputId);
    campo.value = "";
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
        url: "/meusregistros/manual",
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

function inserirRegistroData(inputId){
    var opcoesToastr = {timeOut: 500, hideMethod: 'slideUp', showMethod: 'slideDown', preventDuplicates: true, showEasing: "swing", hideEasing: "linear", onHidden: function () {
            location.reload();
        }};
    toastr.options = opcoesToastr;
    var registro = document.getElementById(inputId).value;
    $.ajax({
        type: "POST",
        url: "/meusregistros/datamanual",
        success: function (data) {
            toastr.success("Registro de data adicionado com sucesso!");
        },
        error: function (response) {
            toastr.error(response.responseText);
        },
        complete: function () {
            fecharModal('#meuRegistroManualModal');
            limparCampoModal("datepickerDia");
        },
        async: true,
        contentType: 'application/json',
        data: JSON.stringify(moment(registro, "DD/MM/YYYY").toDate()),
        cache: false,
        processData: false,
        timeout: 60000
    });
}