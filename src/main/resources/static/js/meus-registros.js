/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    $('#datepicker').datepicker({
        format: 'dd/mm/yyyy',
        language: 'pt-BR'
    });
   $('[data-toggle="popover"]').popover({
        trigger: 'hover'
    });
});

function changePageAndSize() {
    $('#pageSizeSelect').change(function (evt) {
        window.location.replace("/meusregistros/?pageSize=" + this.value + "&page=1");
    });
}
function applyStyleForElements(id, propertie, style_) {
    var elms = document.getElementById(id);
    elms.style[propertie] = style_;
}

function limpar() {
    var dataInicial = document.getElementById('dataInicial');
    var dataFinal = document.getElementById('dataFinal');
    dataInicial.value = '';
    dataFinal.value = '';
    location.reload();
}
var url = "/meusregistros?";
var filtro = {};
function pesquisar() {
    var parametros = montarUrlPesquisa();
    url = "/meusregistros/pesquisar?";
    $("#lista-registros").load(url+parametros);
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
    if(parametrosFiltro){
        $("#lista-registros").load(uri+"&"+parametrosFiltro);
    }else{
        location.href = uri;
    }
    
}

