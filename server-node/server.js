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
{dataHoraRegistro: "2018-10-23 12:47:50", pis: "123456", nsr: "125"}					]));
});
app.listen(3000, function () {
  console.log('Example app listening on port 3000!');
});