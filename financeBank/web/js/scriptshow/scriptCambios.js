/**
 *
 * @author admin
 */

function GuardarTasaMoneda(n){
    var tasav,tasac,tipo,codigo;
    tasav=document.getElementById("ventaM"+n).value;
    tasac=document.getElementById("compraM"+n).value;
    tipo=document.getElementById("tipoT"+n).value;
    codigo=document.getElementById("codmoneda"+n).innerHTML;
    if(tasac==""){
        window.alert("LA TASA DE COMPRA ESTA VACIA");
    }
    if(tasav==""){
        window.alert("LA TASA DE VENTA ESTA VACIA");
    }
    if(tasav!=="" && tasac!==""){
        var req2 = null;
        if (window.XMLHttpRequest){
            req2 = new XMLHttpRequest();
        }else if (window.ActiveXObject){
            req2 = new ActiveXObject("Microsoft.XMLHTTP");
        }
        if(req2!=null){
            var PATH = "/financeBank";
            var url = PATH + "/SGuardarTasa?tasaventa="+tasav+"&tasacompra="+tasac+"&tipotasa="+tipo+"&codigomoneda="+codigo+"&num="+n;
            window.alert("TASAS DE MONEDA MODIFICADO SATISFACTORIAMENTE ");
            req2.open("GET",url,true);
            req2.send(null);
        }
        document.getElementById("ventaM"+n).value="";
        document.getElementById("compraM"+n).value="";
    }
}

var req1;
function GuardarMoneda(){
    var nombreM,simboloM,codigoM,colorM;
    nombreM=document.getElementById("txtNombre").value.toString().toUpperCase();
    simboloM=document.getElementById("txtSimbolo").value.toString().toUpperCase();
    codigoM=document.getElementById("txtCodigo").value.toString().toUpperCase();
    colorM=document.getElementById("txtColor").value;
    colorM =  colorM.replace(/#/gi, "")
    if(nombreM==""){
        alert("INGRESAR NOMBRE DE LA MONEDA");
    }
    if(simboloM==""){
        alert("INGRESAR SIMBOLO DE LA MONEDA");
    }
    if(codigoM==""){
        alert("INGRESAR CODIGO DE LA MONEDA");
    }
    if(colorM==""){
        alert("INGRESAR COLOR DE LA MONEDA");
    }
    if(nombreM!=""&&simboloM!=""&&codigoM!=""&&colorM!=""){
        req1 = null;
        if (window.XMLHttpRequest){
            req1 = new XMLHttpRequest();
        }else if (window.ActiveXObject){
            req1 = new ActiveXObject("Microsoft.XMLHTTP");
        }
        if(req1!=null){
            var PATH = "/financeBank";
            var url = PATH + "/SGuardarMoneda?nombre="+nombreM+"&simbolo="+simboloM+"&codigo="+codigoM+"&color="+colorM;
            req1.open("GET",url,true);
            req1.onreadystatechange = procesarResppuestaG;
            req1.send(null);
            document.getElementById("txtNombre").value="";
            document.getElementById("txtSimbolo").value="";
            document.getElementById("txtCodigo").value="";
            document.getElementById("txtColor").value="";
        }
    }
}

function procesarResppuestaG(){
    contenido = document.getElementById('divdos');
    contenido.innerHTML="Cargando los datos...";
    if (req1.readyState==4 && req1.status==200) {
        contenido.innerHTML = req1.responseText;
    }
}

var req4;
function EliminarMoneda(n1){
    if(window.confirm("Está seguro de eliminar")){
        var codigoM;
        codigoM=document.getElementById("codmoneda"+n1).innerHTML;
        req4 = null;
        if (window.XMLHttpRequest){
            req4 = new XMLHttpRequest();
        }else if (window.ActiveXObject){
            req4 = new ActiveXObject("Microsoft.XMLHTTP");
        }
        if(req4!=null){
            var PATH = "/financeBank";
            var url = PATH + "/SEliminarMonedas?codigo="+codigoM;
            req4.open("GET",url,true);
            req4.onreadystatechange = procesarResppuestae;
            req4.send(null);
        }
    }
}

function procesarResppuestae(){
    contenido = document.getElementById('divdos');
    contenido.innerHTML="Cargando los datos...";
    if (req4.readyState==4 && req4.status==200) {
        contenido.innerHTML = req4.responseText;
        window.alert("MONEDA DESHABILITADA SATISFACTORIAMENTE");
    }
}

var reqm;
function ActivarMoneda(n2){
    var codigoM;
    codigoM=document.getElementById("codmoneda2"+n2).innerHTML;
    reqm = false;
    if (window.XMLHttpRequest){
        reqm = new XMLHttpRequest();
    }else if (window.ActiveXObject){
        reqm = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(reqm!=null){
        var PATH = "/financeBank";
        var url = PATH + "/SActivarMonedas?codigo="+codigoM;
        reqm.open("GET",url,true);
        reqm.onreadystatechange = procesarResppuestaa;
        reqm.send(null);
    }
}

function procesarResppuestaa(){
    contenido = document.getElementById('divdos');
    contenido.innerHTML="Cargando los datos...";
    if (reqm.readyState==4 && reqm.status==200) {
        contenido.innerHTML = reqm.responseText;
        window.alert("MONEDA HABILITADA SATISFACTORIAMENTE");
    }
}

var reqD;
function GuardarDenominacion(){
    var tipoM,montoM,codigoM;
    codigoM=document.getElementById("idCodigo").value;
    tipoM=document.getElementById("idTipo").value;
    montoM=document.getElementById("idMonto").value;
    if(montoM==""){
        window.alert("INGRESAR MONTO");
    }
    if(montoM!=""){
        reqD = null;
        if (window.XMLHttpRequest){
            reqD = new XMLHttpRequest();
        }else if (window.ActiveXObject){
            reqD = new ActiveXObject("Microsoft.XMLHTTP");
        }
        if(reqD!=null){
            var PATH = "/financeBank";
            var url = PATH + "/SDenominacionMonedas?tipo="+tipoM+"&monto="+montoM+"&codigo="+codigoM;
            reqD.open("GET",url,true);
            reqD.onreadystatechange = procesarResppuestaD;
            reqD.send(null);
        }
    }
}

function procesarResppuestaD(){
    contenido = document.getElementById('divdos');
    contenido.innerHTML="Cargando los datos...";
    if (reqD.readyState==4 && reqD.status==200) {
        contenido.innerHTML = reqD.responseText;
    }
}

var req5;
function enviarsessionimagen(){
    var foto;
    foto=document.getElementById("ExaminarFoto").value;
    if(foto==""){
        document.getElementById("cmdsubir").disabled=true;
        document.getElementById("cmdLimpiar").disabled=true;
    }else{
        document.getElementById("cmdsubir").disabled=false;
        document.getElementById("cmdLimpiar").disabled=false;
    }
    req5 = null;
    if (window.XMLHttpRequest) {
        req5 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req5 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req5!=null){
        var PATH = "/financeBank";
        var url = PATH + "/SSubirArchivo?sfoto="+foto;
        req5.open("GET",url,true);
        req5.send(null);
    }
}

function limpiarcelda(){
    document.getElementById("ExaminarFoto").value="";
    document.getElementById("ExaminarFirma").value="";
    document.getElementById("cmdsubir").disabled=true;
}

function habilitar(){
    var filastabla=document.getElementById('tablamoneda').rows.length;
    for(var i=0;i<filastabla;i++){
        if(i>1&&i<filastabla){
            document.getElementById('eliminarTasa'+(i-2)).disabled=false;
        }
    }
}

function ventanaCodigo() {
    Dialog.confirm($('login').innerHTML,
    {   className:"alphacube",
        width:400,
        okLabel: "login",
        title: "Autorizar Usuario (Para Activar los botones de eliminar)",
        cancelLabel: "cancel",
        onOk:function(){
            var user = document.getElementById('user').value
            var pass = document.getElementById('passwd').value
            document.getElementById('res').innerHTML='';
            Autoriza(user,pass);
            window.alert('¡¡ DATOS EN REVISIÓN !!')
            if(document.getElementById('res').innerHTML==1){
                Aut = true;
                var filastabla=document.getElementById('tablamoneda').rows.length;
                for(var i=0;i<filastabla;i++){
                    if(i>1&&i<filastabla){
                        document.getElementById('eliminarTasa'+(i-2)).disabled=false;
                    }
                }
                document.getElementById('Eliminacion').disabled=true;
                return true;
            }else{
                $('login_error_msg').innerHTML='Usuario o Contrase&ntilde;a incorrecto';
                $('login_error_msg').show();
                Windows.focusedWindow.updateHeight();
                new Effect.Shake(Windows.focusedWindow.getId());
                return false;
            }
        }
    });
}

function Autoriza(login,contrasenia) {
    req2 = null;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2!=null){
        var PATH = "/financeBank";
        var url = PATH + "/SAutorizarUsuario?login="+login+"&contrasenia="+contrasenia;
        req2.open("GET",url,true);
        req2.onreadystatechange = procesando;
        req2.send(null);
    }
}

function procesando(){
    contenido = document.getElementById('res');
    contenido.innerHTML="Cargando los datos...";
    contenido.innerHTML="";
    if (req2.readyState==4 && req2.status==200) {
        contenido.innerHTML = req2.responseText;
    }
}

