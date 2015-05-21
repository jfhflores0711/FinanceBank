/**
 *
 * @author admin
 */

var Aut = false;
var req3;
var req2;


function ventanaNuevap() {
    //document.getElementById("trSaldo").style.display="none";
    //document.fcambio.chkVerSaldo.checked=false;
    //compraVenta();calculo();
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
                var user =document.getElementById('user').value;
                var pass =document.getElementById('passwd').value;
                document.getElementById('res').innerHTML='';
                Autorizarp(user,pass);
                /* while(!(document.getElementById('res').innerHTML==1 || document.getElementById('res').innerHTML==0)){
                                $('login_error_msg').innerHTML='Espere un momento';
                                sleep(1000);
                            }*/
                if(document.getElementById('res').innerHTML==1){
                    Aut = true;
                    document.getElementById("chkVerSaldo").checked=true;
                    consultartasap();
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
    } else {
        document.getElementById("divSaldo").innerHTML="NUEVO SALDO: *****.**";
        document.getElementById("divSaldo2").innerHTML="NUEVO SALDO: *****.**";
    }
}
function Autorizarp(login,contrasenia) {
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2!=null){
        req2.onreadystatechange = procesar1;
        var PATH = "/financeBank";
        var url = PATH + "/SAutorizarUsuario?login="+login+"&contrasenia="+contrasenia;
        req2.open("GET",url,true);
        req2.send(null);
        alert('¡¡ DATOS ENVIADOS !!');
    }
}
function procesar1(){
    contenido = document.getElementById('res');
    //contenido.innerHTML="Cargando los datos...";
    contenido.innerHTML="";
    if (req2.readyState==4 && req2.status==200) {
        contenido.innerHTML = req2.responseText;
    }
}
/****************************************************************************/
function consultartasap(){
    var numcuentatick;
    numcuentatick=document.getElementById("tdnumcuenta").innerHTML;
    if (window.XMLHttpRequest) {
        req3 = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
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
    var contenido = document.getElementById('divSaldo');
    var contenidoc = document.getElementById('divSaldo2');
    //contenido.innerHTML="Cargando los datos...";
    contenido.innerHTML="";
    //contenidoc.innerHTML="Cargando los datos...";
    contenidoc.innerHTML="";
    if (req3.readyState==4 && req3.status==200) {
        contenido.innerHTML = req3.responseText;
        contenidoc.innerHTML = req3.responseText;
    }
}

//var Aut = false;
var req4;
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
            Autorizarp(user,pass);
            /* alert('¡¡ DATOS ENVIADOS !!')
                        while(!(document.getElementById('res').innerHTML==1 || document.getElementById('res').innerHTML==0)){
                                $('login_error_msg').innerHTML='Espere un momento';
                                sleep(1000);
                            }*/
            if(document.getElementById('res').innerHTML==1){
                Aut = true;
                extornarp();
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
function extornarp(){
    var userAdmin =document.getElementById('user').value
    var IdOperacion;
    IdOperacion=document.getElementById("tdIdOperacion").innerHTML;
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
        if(document.getElementById("txtExtorno").value.trim()=="SI"){
            alert("EL EXTORNO SE REALIZO CON EXITO");
        //window.history.back();
        } else {
            alert("FALLO LA EXTORNACION");
        }
    }
}

