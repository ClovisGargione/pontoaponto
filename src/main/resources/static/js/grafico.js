/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
var ctx = document.getElementById("myChart").getContext('2d');
        var myChart = new Chart(ctx, {
        type: 'doughnut',
                data: {
                labels: ["Horas trabalhadas", "Horas restantes"],
                        datasets: [{
                        label: ['Horas trabalhadas',
                                'Horas restantes'],
                                data: [ [[${diaDeTrabalho.tempoTrabalhadoEmMinutos}]], [[${diaDeTrabalho.tempoFaltanteExtraEmMinutos}]] ],
                                backgroundColor: [
                                        'rgba(54, 162, 235, 0.2)',
                                        'rgba(255, 206, 86, 0.2)'
                                ],
                                borderColor: [
                                        'rgba(54, 162, 235, 1)',
                                        'rgba(255, 206, 86, 1)'
                                ],
                                borderWidth: 1
                        }]
                },
                options: {
                cutoutPercentage: 50,
                        circumference: Math.PI,
                        rotation: - Math.PI,
                        responsive: false,
                        maintainAspectRatio: true,
                        tooltips: {
                        callbacks: {
                        title: function(tooltipItem, data) {
                        return data['labels'][tooltipItem[0]['index']];
                        },
                                label: function(tooltipItem, data) {
                                return data['datasets'][0]['data'][tooltipItem['index']];
                                },
                                afterLabel: function(tooltipItem, data) {
                                console.log(data);
                                        var dataset = data['datasets'][0];
                                        var percent = Math.round((dataset['data'][tooltipItem['index']] / dataset["_meta"][0]['total']) * 100)
                                        return '(' + percent + '%)';
                                }
                        }
                        }

                }
        });
});
