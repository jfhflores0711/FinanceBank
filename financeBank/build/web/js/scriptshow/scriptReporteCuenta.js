/**
 *
 * @author admin
 */
function activarsubmit(ind){
    if(ind=="regresar"){
        document.fcambio.action="admincuenta.htm";
        document.fcambio.submit();
    }
}

function mostrarInteres(){
    if (document.getElementById("chkVerInteres").checked==true){
        document.getElementById("trInteres1").style.display="";
        document.getElementById("trInteres2").style.display="";
    }else if(document.getElementById("chkVerInteres").checked==false){
        document.getElementById("trInteres1").style.display="none";
        document.getElementById("trInteres2").style.display="none";
    }
}

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
                alert('¡¡ DATOS ENVIADOS !!')
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
    }else{
        document.getElementById("divSaldos").innerHTML="";
        document.getElementById("divSaldo").style.display="none";
        document.getElementById("divSaldos2").innerHTML="";
        document.getElementById("divSaldo2").style.display="none";
    }
}

var req2;
function Autorizar(login,contrasenia) {
    req2 = null;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2!=null){
        req2.onreadystatechange = procesar1;
        var PATH = "/financeBank";
        var url = PATH + "/SAutorizarUsuario?login="+login+"&contrasenia="+contrasenia;
        req2.open("GET",url,true);
        req2.send(null);
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
        var url = PATH + "/SBuscarSaldoCuenta?numcuentatick="+numcuentatick;
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

function verSaldoTicket(){
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
    var IdOperacion;
    IdOperacion=document.getElementById("tdIdOperacion").innerHTML;
    req4 = null;
    if (window.XMLHttpRequest) {
        req4 = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
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
        }else {
            window.alert("FALLO LA EXTORNACION");
        }
    }
}

