/* 
 * Oscar
 */


<!--
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
    if (init==true) with (navigator) {
        if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
            document.MM_pgW=innerWidth;
            document.MM_pgH=innerHeight;
            onresize=MM_reloadPage;
        }
        }
    else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);
var Aut = false;
function ventanaNueva() {
    if(document.getElementById("chkVerSaldo").checked==true){
        document.getElementById("chkVerSaldo").checked=false;
        Dialog.confirm($('login').innerHTML,
        {
            className:"alphacube",
            width:400,
            okLabel: "login",
            title: "Autorizar Usuario",
            cancelLabel: "cancel",
            onOk:function(){
                var user =document.getElementById('user').value
                var pass =document.getElementById('passwd').value
                document.getElementById('res').innerHTML='';
                Autorizar(user,pass);
                if(document.getElementById('res').innerHTML==1){
                    Aut = true;
                    document.getElementById("chkVerSaldo").checked=true;
                    consultartasa();
                    return true;
                }else{
                    document.fcambio.chkVerSaldo.checked=false;
                    $('login_error_msg').innerHTML='Usuario o Contrase&ntilde;a incorrecto';
                    $('login_error_msg').show();
                    Windows.focusedWindow.updateHeight();
                    new Effect.Shake(Windows.focusedWindow.getId());
                    return false;
                }
            }
        });
    } else{
        document.getElementById("divSaldos").style.display="none";
        document.getElementById("divSaldos2").style.display="none";
    }
}

function Autorizar(login,contrasenia) {
    req2 = false;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2!=null){
        req2.onreadystatechange = procesar1;
        var PATH = "/financeBank";
        var url = PATH + "/SAutorizarUsuario?login="+login+"&contrasenia="+contrasenia;
        req2.open("GET",url,true);
        req2.send(null);
        window.alert("DATOS ENVIADOS!!!");
    }
}

function procesar1(){
    contenido = document.getElementById('res');
    contenido.innerHTML="Cargando los datos...";
    contenido.innerHTML="";
    if (req2.readyState==4 && req2.status==200) {
        contenido.innerHTML = req2.responseText;
    }
}

var req3;
function consultartasa(){
    var numcuentatick;
    numcuentatick=document.getElementById("tdnumcuenta").innerHTML;
    req3 = false;
    if (window.XMLHttpRequest) {
        req3 = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        req3 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req3!=null){
        var PATH = "/financeBank";
        var url = PATH + "/SBuscarSaldoCuenta?numcuentatick="+numcuentatick.replace("-", "", "g");
        req3.open("GET",url,true);
        req3.onreadystatechange = procesar2;
        req3.send(null);
    }
}

function procesar2(){
    contenido = document.getElementById('divSaldos');
    contenido.innerHTML="Cargando los datos...";
    contenido.innerHTML="";
    if (req3.readyState==4 && req3.status==200) {
        contenido.innerHTML = req3.responseText;
        document.getElementById("divSaldos2").innerHTML=document.getElementById("divSaldos").innerHTML;
        document.getElementById("divSaldos2").style.display="";
        document.getElementById("divSaldos").style.display="";
    }
}

//var Aut = false;
function ventanaNueva2() {
    Dialog.confirm($('login').innerHTML,
    {
        className:"alphacube",
        width:400,
        okLabel: "login",
        title: "Autorizar Usuario",
        cancelLabel: "cancel",
        onOk:function(){
            var user =document.getElementById('user').value
            var pass =document.getElementById('passwd').value
            document.getElementById('res').innerHTML='';
            Autorizar(user,pass);
            alert('¡¡ DATOS ENVIADOS !!')
            if(document.getElementById('res').innerHTML==1){
                Aut = true;
                extornar();
                return true;
            }else{
                document.fcambio.chkVerSaldo.checked=false;
                $('login_error_msg').innerHTML='Usuario o Contrase&ntilde;a incorrecto';
                $('login_error_msg').show();
                Windows.focusedWindow.updateHeight();
                new Effect.Shake(Windows.focusedWindow.getId());
                return false;
            }
        }
    });
}

var req4;
function extornar(){
    var userAdmin =document.getElementById('user').value
    var IdOperacion=document.getElementById("tdIdOperacion").innerHTML;
    req4 = false;
    if (window.XMLHttpRequest) {
        req4 = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        req4 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req4!=null){
        var PATH = "/financeBank";
        var url = PATH + "/SExtornarOperacion?IdOperacion="+IdOperacion+"&userAdminExt="+userAdmin;
        req4.open("GET",url,true);
        req4.onreadystatechange = procesar4;
        req4.send(null);
    }
}

function procesar4(){
    contenido = document.getElementById('divExtorno');
    contenido.innerHTML="Cargando los datos...";
    contenido.innerHTML="";
    if (req4.readyState==4 && req4.status==200) {
        contenido.innerHTML = req4.responseText;
        if(document.getElementById("txtExtorno").value=="SI"){
            window.alert("EL EXTORNO SE REALIZO CON EXITO");
            document.frmFin.submit();
        } else {
            window.alert("FALLO LA EXTORNACION");
        }
    }
}