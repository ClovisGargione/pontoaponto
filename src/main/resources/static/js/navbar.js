/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    $("a").each(function () {
        if ($(this).attr("href") == window.location.pathname) {
            $(this).addClass("active");
        }
    });
});




$(document).ready(function () {
    window.onload =  function (e) {
        mostrarLoading();
    };

    window.addEventListener("load", function () {
        esconderLoading();
    });
    
    function mostrarLoading() {
        document.getElementById("loader").style.display = "block";
        document.getElementById("overlay").style.display = "block";
    }

    function esconderLoading() {
        document.getElementById("loader").style.display = "none";
        document.getElementById("overlay").style.display = "none";
    }
});
