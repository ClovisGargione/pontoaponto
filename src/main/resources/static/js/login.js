function showHidePassword(input, icone){
        if($('#'+input).attr("type") == "text"){
            $('#'+input).attr('type', 'password');
            $('#'+icone).addClass( "fa-eye-slash" );
            $('#'+icone).removeClass( "fa-eye" );
        }else if($('#'+input).attr("type") == "password"){
            $('#'+input).attr('type', 'text');
            $('#'+icone).removeClass( "fa-eye-slash" );
            $('#'+icone).addClass( "fa-eye" );
        }
}

function lembrarLogin(){
    var lembrar = document.getElementById('lembrarCheck').checked;
    if(lembrar){
        var login = document.getElementById('username').value;
        var senha = document.getElementById('password').value;
        setCookie("login", login, 365);
        setCookie("senha", senha, 365);
        setCookie("lembrar", lembrar, 365);
    }else{
        setCookie("login", login, 0);
        setCookie("senha", senha, 0);
        setCookie("lembrar", lembrar, 0);
    }
}

function setCookie(cname, cvalue, exdays) {
  var d = new Date();
  d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
  var expires = "expires="+d.toUTCString();
  document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
  var name = cname + "=";
  var ca = document.cookie.split(';');
  for(var i = 0; i < ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}

function checkCookies(){
  var lembrar = getCookie("lembrar");
    if(lembrar){
        var login = getCookie("login");
        var senha =  getCookie("senha");
        document.getElementById('username').value = login;
        document.getElementById('password').value = senha;
        document.getElementById('lembrarCheck').checked = lembrar;
    }
};


$(document).ready(function() {
    checkCookies();
});