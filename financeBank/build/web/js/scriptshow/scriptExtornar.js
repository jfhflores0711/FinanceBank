/**
 *
 * @author admin
 */
var req6;
function actualizarK(){
    req6 = null;
    if (window.XMLHttpRequest) {
        req6 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req6 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req6!=null){
        var PATH = "/financeBank";
        var url = PATH + "/SActualizarCuentas";
        req6.open("GET",url,true);
        req6.send(null);
    }
}

var req;
function buscarOperacione(){
    var tipoOperacion,numOP;
    tipoOperacion=document.getElementById("selTipoOperacion").value;
    if(tipoOperacion!=""){
        numOP=document.getElementById("txtNumOperacion").value;
    }else{
        numOP="";
    }
    if(numOP!="" || tipoOperacion=="TODO"){
        req = null;
        if (window.XMLHttpRequest) {
            req = new XMLHttpRequest();
        }else if (window.ActiveXObject) {
            req = new ActiveXObject("Microsoft.XMLHTTP");
        }
        if(req!=null){
            req.onreadystatechange = pprocesar;
            var PATH = "/financeBank";
            var url = PATH + "/SBuscarOperacion?numOP="+numOP+"&tipoOperacion="+tipoOperacion;
            req.open("GET",url,true);
            req.send(null);
        }
    }else{
        window.alert("ingrese numero de operacion");
    }
}

function pprocesar(){
    var contenido = document.getElementById('divOperacion');
    contenido.innerHTML="Cargando los datos...";
    if (req.readyState==4 && req.status==200) {
        contenido.innerHTML = req.responseText;
        var tamTabla;
        tamTabla=document.getElementById("tablaOperacion").rows.length;
        if(tamTabla>=15){
            contenido = document.getElementById('divOperacion').style.overflow="auto";
            contenido = document.getElementById('divOperacion').style.height="600px";
        }else{
            contenido = document.getElementById('divOperacion').style.overflow="";
            contenido = document.getElementById('divOperacion').style.height="";
        }
    }
}

var Aut = false;
function ventanaNueva2(ind) {
    Dialog.confirm($('login').innerHTML,
    {
        className:"alphacube",
        width:400,
        okLabel: "Login",
        title: "Autorizar Usuario",
        cancelLabel: "Cancelar",
        onOk:function(){
            var user =document.getElementById('user').value;
            var pass =document.getElementById('passwd').value;
            document.getElementById('res').innerHTML='';
            Autorizar(user,pass, ind);
        }
    });
    document.getElementById('user').focus();
}

var req4;
function extornar(ind){
    var userAdmin =document.getElementById('tdIdAdmin').innerHTML;
    req4 = null;
    if (window.XMLHttpRequest) {
        req4 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req4 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req4!=null){
        var answer = window.confirm ("¿ESTA SEGURO QUE DESEA EXTORNAR ESTA OPERACIONES?")
        if (answer){
            var PATH = "/financeBank";
            var url = PATH + "/SExtornarOperacion?IdOperacion="+ind+"&userAdminExt="+userAdmin;
            req4.open("GET",url,true);
            req4.onreadystatechange = procesar4;
            req4.send(null);
        }
        return true;
    }else return false;
}

function procesar4(){
    contenido = document.getElementById('divExtorno');
    contenido.innerHTML="Cargando los datos...";
    contenido.innerHTML="";
    if (req4.readyState==4 && req4.status==200) {
        contenido.innerHTML = req4.responseText;
        if(document.getElementById("txtExtorno").value=="SI"){
            window.alert("EL EXTORNO SE REALIZO CON EXITO");
            document.formextor.submit();
        }else {
            window.alert("FALLO LA EXTORNACION");
        }
    }
}

function Autorizar(login,contrasenia, ind) {
    req2 = null;
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
        window.alert('¡¡ DATOS ENVIADOS !!')
        if(document.getElementById('res').innerHTML==1){
            Aut = true;
            return extornar(ind);
        }else{
            //document.fcambio.chkVerSaldo.checked=false;
            $('login_error_msg').innerHTML='Usuario o Contrase&ntilde;a incorrecto';
            $('login_error_msg').show();
            Windows.focusedWindow.updateHeight();
            new Effect.Shake(Windows.focusedWindow.getId());
            return false;
        }
    }else return false;
}

function procesar1(){
    contenido = document.getElementById('res');
    contenido.innerHTML="Cargando los datos...";
    contenido.innerHTML="";
    if (req2.readyState==4 && req2.status==200) {
        contenido.innerHTML = req2.responseText;

    }
}
