var express = require('express');
var app = express();

app.get('/', function (req, res) {
  res.send('Hello World!');
});

app.get('/api/rep', function (req, res) {
	res.setHeader('Content-Type', 'application/json');
	res.send(JSON.stringify([
{dataHoraRegistro: "2018-10-23 09:02:50", pis: "123456", nsr: "123"}, 
{dataHoraRegistro: "2018-10-23 11:46:50", pis: "123456", nsr: "124"},
{dataHoraRegistro: "2018-10-23 12:47:50", pis: "123456", nsr: "125"},
{dataHoraRegistro: "2018-10-27 00:47:50", pis: "123456", nsr: "131"},
{dataHoraRegistro: "2018-10-27 03:47:50", pis: "123456", nsr: "132"},
{dataHoraRegistro: "2018-10-27 04:47:50", pis: "123456", nsr: "133"},
{dataHoraRegistro: "2018-10-27 08:38:50", pis: "123456", nsr: "134"},
{dataHoraRegistro: "2018-10-27 09:00:50", pis: "123456", nsr: "135"},
{dataHoraRegistro: "2018-10-27 09:30:50", pis: "123456", nsr: "136"},
{dataHoraRegistro: "2018-10-28 08:30:50", pis: "123456", nsr: "137"},
{dataHoraRegistro: "2018-10-28 11:30:50", pis: "123456", nsr: "138"},
{dataHoraRegistro: "2018-10-28 12:30:50", pis: "123456", nsr: "139"},
{dataHoraRegistro: "2018-10-28 18:15:50", pis: "123456", nsr: "140"},
{dataHoraRegistro: "2018-10-28 19:15:50", pis: "123456", nsr: "141"},
{dataHoraRegistro: "2018-10-28 22:17:50", pis: "123456", nsr: "142"}							]));
})
app.listen(3000, function () {
  console.log('Example app listening on port 3000!')
});
