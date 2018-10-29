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

$(document).ready(function() {
  
});