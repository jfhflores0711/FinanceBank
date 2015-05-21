/**
 *
 * @author admin
 */

var reqCap;
function capitalizar(cuenta){
    if(cuenta==""){
        return;
    }
    var m=document.getElementById('txtC').value;
    if(parseFloat(m)==0.0||parseFloat(m)=='NaN'){
        window.alert("No se puede capitalizar dicho monto.");
        return;
    }
    m=eedisplayFloatNDTh(eeparseFloatTh(m),2);
    document.getElementById('txtC').value=m;
    var answer = window.confirm ("SE HA CORREGIDO EL MONTO \n A CAPITALIZAR A "+m+"\n ¿ESTA SEGURO QUE DESEA CONTINUAR?");
    if (!answer){
        return;
    }
    reqCap = null;
    if (window.XMLHttpRequest) {
        reqCap = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        reqCap = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(reqCap!=null){
        answer = window.confirm ("¿ESTA SEGURO QUE DESEA CAPITALIZAR LA CUENTA?");
        if (answer){
            reqCap.onreadystatechange = procesarCapitalizar;
            var PATH = "/financeBank";
            var url = PATH + "/SActualizarCuentas?idcuenta="+cuenta+"&m="+m;
            reqCap.open("GET",url,true);
            reqCap.send(null);
        }
    }
}

function procesarCapitalizar(){
    contenido = document.getElementById('divesperarCap');
    contenido.innerHTML="Cargando los datos...";
    if (reqCap.readyState==4 && reqCap.status==200) {
        contenido.innerHTML = reqCap.responseText;
        if(document.getElementById("divesperarCap").innerHTML=="OK"){
            window.alert("LA CUENTA SE CAPITALIZÓ SATISFACTORIAMENTE");
            document.frmActualizar.submit();
        }else{
            window.alert("ERROR AL CAPITALIZAR LA CUENTA, RECARGAR EL SISTEMA");
            document.frmActualizar.submit();
        }
    }
}

function activarcuentaestado(indice){
    if (document.getElementById("fechaEstCuen").value!=""){
        var idcuenta="",fecha="";
        idcuenta=document.getElementById("tdIdCuenPersona"+indice).innerHTML
        fecha=document.getElementById("fechaEstCuen").value;
        document.frmIrEstadoCuenta.action="estadodecuenta.htm?idcuenta="+idcuenta+"&fecha="+fecha;
        document.frmIrEstadoCuenta.submit();
    }else{
        window.alert("INGRESE FECHA DE INICIO PARA EL ESTADO DE CUENTA");
    }
}

var req;
function mostrarbotoncreate(indice) {
    document.getElementById("txtind").value=indice;
    var tipo=document.getElementById("tdtipocuenta"+indice).innerHTML;
    var numcuenta,miinteres,idMoney,IdCuenPersona,mon;
    var saldo,saldo1;
    var fec=document.getElementById("tdFechaCap"+indice).innerHTML;
    numcuenta=document.getElementById("tdNumCuenta"+indice).innerHTML;
    miinteres=document.getElementById("tdInteres"+indice).innerHTML;
    idMoney=document.getElementById("tdIdMoney"+indice).innerHTML;
    IdCuenPersona=document.getElementById("tdIdCuenPersona"+indice).innerHTML;
    saldo=document.getElementById("tdCuenSaldo"+indice).innerHTML;
    var sa=parseFloat(saldo.replace(",",""));
    saldo=eedisplayFloatNDTh(eeparseFloatTh(saldo),2);
    saldo1=document.getElementById("tdCuenSaldo1"+indice).innerHTML;
    var sa1=parseFloat(saldo1.replace(",", ""));
    saldo1=eedisplayFloatNDTh(eeparseFloatTh(saldo1),2);
    var monint=0.0;
    if(isFinite(sa)&&isFinite(sa1)){
        monint=round(sa-sa1, 2);
    }
    if(idMoney=="PEN")
        mon="S/.";
    else if(idMoney=="USD")
        mon="&#36;";
    else
        mon="&#8364;";
    document.getElementById("divcuenta").innerHTML="<fieldset id='fielCuen' style='border-width:3px'>"+
    "<legend id='legcuenta'><b> "+tipo+":"+numcuenta+"</b></legend>"+
    "<table id='tablecuenta'>"+
    "<tr>"+
    "<td style='font-weight:bold' colspan='4'>&nbsp;ACTUALIZA EL MONTO FINAL SOLO CUANDO CORRESPONDA UN CÁLCULO REAL.</td>"+
    "</tr>"+
    "<tr>"+
    "<td><font color='#385b88' style='font-size: 12px;'><b>MONTO DISPONIBLE:</b></font></td>"+
    "<td><input type='text' id='txtNumCuentaDep' name='txtNumCuentaDep' value='"+saldo1+"' disabled style='font-weight:bold;color:black'/></td>"+
    "<td><font color='#385b88' style='font-size: 12px;'><input type='button' value='VER ANT.' onclick='verants(\""+IdCuenPersona+"\")'></font></td>"+
    "<td>HOY: "+ (new Date())+ " TASA INT. ACTUAL: "+miinteres+"</td>"+
    "</tr>"+
    "<tr>"+
    "<td><font color='#385b88' style='font-size: 12px;'><b>MONTO DE INTERESES:</b></font></td>"+
    "<td><input type='text' id='txtInteres' name='txtInteres' value='"+(monint)+"' style='font-weight:bold;color:black;width: 160px;' readonly/></td>"+
    "<td><font color='#385b88' style='font-size: 12px;'><input type='button' value='VER CALCULOS' onclick='vercals(\""+IdCuenPersona+"\")'></font></td>"+
    "<td>FECHA DE CAPITALIZACI&Oacute;N: "+fec+"</td>"+
    "</tr>"+
    "<tr>"+
    "<td><font color='#385b88' style='font-size: 12px;'><b>MONTO CAPITALIZABLE:</b></font></td>"+
    "<td>Valor:<input type='text' id='txtC' name='txtC' value='"+(monint)+"' onkeypress='return solodecimales(event);' maxlenth='20' style='font-weight:bold;color:black;width:80px;'/></td>"+
    "<td><font color='#385b88' style='font-size: 12px;'><b><input type='button' value='CAPITALIZAR...' onclick='capitalizar(\""+IdCuenPersona+"\")'></b></font></td>"+
    "<td>-Poner el monto de diferencia.</td>"+
    "</tr>"+
    "<tr>"+
    "<td><font color='#385b88' style='font-size: 12px;'><b>SALDO FINAL:</b>("+mon+" "+saldo+")</font></td>"+
    "<td><font color='#E08934' style='font-size: 20px;'><b>"+mon+"</b></font><input type='text' id='txtImporteDep' name='txtImporteDep' value='"+saldo+"' onkeypress='return solodecimales(event);' /></td>"+
    "<td><font color='#385b88' style='font-size: 12px;'><b><input type='button' name='cmdActualizar' id='cmdActualizar' value='Reajustar' onclick='actualizarCuentaF(\""+IdCuenPersona+"\");'></b></font></td>"+
    "<td>Poner el monto que debe representar el Saldo Final.</td>"+
    "</tr>"+
    "<table>"+
    "</fieldset>";
}

function procesarrrRespuesta(){
    contenido = document.getElementById('divcuenta');
    contenido.innerHTML="Cargando los datos...";
    if (req.readyState==4 && req.status==200) {
        contenido.innerHTML = req.responseText;
    }
}

var da1,da2;
var req4;
function vercuenta(){
    //document.getElementById("");
    document.getElementById("divcuenta").innerHTML="";
    document.getElementById("txtTempSelTipB").value=document.getElementById("selTipoBus").value;
    document.getElementById("txtTempdataB").value=document.getElementById("txtBuscar").value;
    document.getElementById("divcuenta").innerHTML="";
    var contenido, tipoBusqueda="";
    contenido=document.getElementById("txtBuscar").value;
    tipoBusqueda=document.getElementById("selTipoBus").value;
    if(tipoBusqueda=='0'){
        return;
    }
    document.getElementById('divlistPerCuentas').innerHTML="CARGANDO ...<img alt='Cargando ...' src='images/ajax/ajax-loader1.gif'/>";
    req4 = null;
    if (window.XMLHttpRequest) {
        req4 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req4 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req4!=null){
        req4.onreadystatechange = procesarRespuesta3;
        var PATH = "/financeBank";
        var url = PATH + "/SDepositoRetiro2?a="+contenido+"&b="+tipoBusqueda;
        req4.open("GET",url,true);
        req4.send(null);
    }
}

function mostrarcuentadetalle(ind) {
    document.getElementById("divcuenta").innerHTML="";
    var contenido, tipoBusqueda="";
    contenido=document.getElementById("txtBuscar").value;
    tipoBusqueda=document.getElementById("selTipoBus").value;
    if (tipoBusqueda=="NUMERO DE CUENTA"){
        contenido=document.getElementById("ttdNumCuenta"+ind).innerHTML;
    }else if (tipoBusqueda=="DNI" || tipoBusqueda=="NOMBRE" || tipoBusqueda=="APELLIDO"){
        contenido=ind;
    }else if (tipoBusqueda=="RUC"){
        contenido=ind;
    }
    req2 = null;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2!=null){
        req2.onreadystatechange = procesarRespuesta2;
        var PATH = "/financeBank";
        var url = PATH + "/SDepositoRetiro?a="+contenido+"&b="+tipoBusqueda;
        req2.open("GET",url,true);
        req2.send(null);
    }
}

function procesarRespuesta2(){
    contenido = document.getElementById('tableMiscuentas');
    contenido.innerHTML="Cargando los datos...";
    if (req2.readyState==4 && req2.status==200) {
        contenido.innerHTML = req2.responseText;
        var tamTabla;
        tamTabla=document.getElementById("tablamiscuentas").rows.length;
        if(tamTabla>=6){
            contenido = document.getElementById('tableMiscuentas').style.overflow="auto";
            contenido = document.getElementById('tableMiscuentas').style.height="300px";
        }else{
            contenido = document.getElementById('tableMiscuentas').style.overflow="";
            contenido = document.getElementById('tableMiscuentas').style.height="";
        }
    }
}

function procesarRespuesta3(){
    contenido = document.getElementById('divlistPerCuentas');
    contenido.innerHTML="Cargando los datos...<img alt='Cargando ...' src='images/ajax/ajax-loader1.gif' style='display: none'/>";
    if (req4.readyState==4 && req4.status==200) {
        contenido.innerHTML = req4.responseText;
        var tamTabla;
        tamTabla=document.getElementById("tablapersonas").rows.length;
        if(tamTabla>=7){
            contenido = document.getElementById('divlistPerCuentas').style.overflow="auto";
            contenido = document.getElementById('divlistPerCuentas').style.height="320px";
        }else{
            contenido = document.getElementById('divlistPerCuentas').style.overflow="";
            contenido = document.getElementById('divlistPerCuentas').style.height="";
        }
    }
}

var rant;
function verants(idc0){
    rant = null;
    if (window.XMLHttpRequest) {
        rant = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        rant = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(rant!=null){
        rant.onreadystatechange = procesarRespuestaAnt;
        var PATH = "/financeBank";
        var url = PATH + "/SUpdateMDisp?idcuenta="+idc0+"&tiny=a";
        rant.open("GET",url,true);
        rant.send(null);
    }
}

function procesarRespuestaAnt(){
    var contenidox = document.getElementById('ver');
    contenidox.innerHTML="Cargando los datos...";
    if (rant.readyState==4 && rant.status==200) {
        contenidox.innerHTML = rant.responseText;
    }
}

var rcal;
function vercals(idc1){
    rcal = null;
    if (window.XMLHttpRequest) {
        rcal = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        rcal = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(rcal!=null){
        rcal.onreadystatechange = procesarRespuestaCal;
        var PATH = "/financeBank";
        var url = PATH + "/SUpdateMDisp?idcuenta="+idc1+"&tiny=c";
        rcal.open("GET",url,true);
        rcal.send(null);
    }
}

function procesarRespuestaCal(){
    var contenidox = document.getElementById('ver');
    contenidox.innerHTML="Cargando los datos...";
    if (rcal.readyState==4 && rcal.status==200) {
        contenidox.innerHTML = rcal.responseText;
    }
}

var req2x;
function actualizarCuentaF(idc2){
    document.getElementById("tableReportx").innerHTML="NO";
    var nm=document.getElementById("txtImporteDep").value;
    if(!window.confirm("SU NUEVO VALOR SERÁ... "+nm)){
        return
    }
    if (idc2==""){
        window.alert("Error inicial, que verifique en el servicio de informática");
    }
    req2x = null;
    if (window.XMLHttpRequest) {
        req2x = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req2x = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2x!=null){
        req2x.onreadystatechange = procesarRespuesta2x;
        var PATH = "/financeBank";
        var url = PATH + "/SUpdateMDisp?idcuenta="+idc2+"&nm="+nm;
        req2x.open("GET",url,true);
        req2x.send(null);
    }
}

function procesarRespuesta2x(){
    var contenidox = document.getElementById('tableReportx');
    contenidox.innerHTML="Cargando los datos...";
    if (req2x.readyState==4 && req2x.status==200) {
        contenidox.innerHTML = req2x.responseText;
        var tamTabla;
        tamTabla=contenidox.innerHTML;
        if(tamTabla=="OK"){
            window.alert("SE HA ACTUALIZADO CORRECTAMENTE!!");
        }else{
            window.alert("Ha fallado la actualización");
        }
        document.frmadmincuenta.action="";
        document.frmadmincuenta.submit();
    }
}

function activeSpecialKey(e) {
    var whichCode = (window.Event) ? e.which : e.keyCode;
    if (whichCode == 13) vercuenta(); // Enter
}

function validartxtBusqueda(){
    var seltipo;
    seltipo=document.getElementById("selTipoBus").value;
    if(seltipo=='0'){
        return;
    }
    if(seltipo=="NUMERO DE CUENTA"){
        document.getElementById('txtestados').value="0";
        document.getElementById("divtxtBuscar").innerHTML=" <input id='txtBuscar' type='text' name='txtBuscar' value='' onkeypress='return soloNumeroTelefonico(event);' onkeyup='activeSpecialKey(event);' />";
        document.getElementById("divlistPerCuentas").style.overflow="";
    }else if(seltipo=="DNI"){
        document.getElementById('txtestados').value="0";
        document.getElementById("divtxtBuscar").innerHTML=" <input id='txtBuscar' type='text' name='txtBuscar' value='' onkeypress='return solonumeros(event);' onkeyup='activeSpecialKey(event);' maxlength='8' />";
    }else if(seltipo=="RUC"){
        document.getElementById('txtestados').value="0";
        document.getElementById("divtxtBuscar").innerHTML=" <input id='txtBuscar' type='text' name='txtBuscar' value='' onkeypress='return solonumeros(event);' onkeyup='activeSpecialKey(event)'/>";
    }else if(seltipo=="NOMBRE"){
        document.getElementById('txtestados').value="1";
        document.getElementById("divtxtBuscar").innerHTML=" <input id='txtBuscar' type='text' name='txtBuscar' value='' onkeypress='activeSpecialKey(event);' />";
    }else if(seltipo=="APELLIDO"){
        document.getElementById('txtestados').value="1";
        document.getElementById("divtxtBuscar").innerHTML=" <input id='txtBuscar' type='text' name='txtBuscar' value='' onkeypress='activeSpecialKey(event);' />";
    }else if(seltipo=="(Seleccione Tipo Busqueda)"){
        document.getElementById('txtestados').value="0";
        document.getElementById("divtxtBuscar").innerHTML=" <input id='txtBuscar' type='text' name='txtBuscar' value='' onkeypress='activeSpecialKey(event);' />";
    }
    document.getElementById("divlistPerCuentas").innerHTML="";
    document.getElementById("divlistPerCuentas").style.height="0px";
    document.getElementById("tableMiscuentas").innerHTML="";
    document.getElementById("divcuenta").innerHTML="";
    document.getElementById("txtBuscar").focus();
}

var Aut = false;
function ventanaNueva() {
    if(document.getElementById("chkKnegativo").checked==true){
        document.getElementById("chkKnegativo").checked=false;
        document.getElementById("lblinteres").style.display="none";
        document.getElementById("txtinteress").style.display="none";
        document.getElementById("lblCobroKnegativo").style.display="none";
        document.getElementById("txtCobroKnegativo").style.display="none";
        Dialog.confirm($('loginn').innerHTML,
        {
            className:"alphacube",
            width:400,
            okLabel: "Confirmar",
            title: "Autorizar Prestamo por falta de dinero en cuenta de Cliente",
            cancelLabel: "Cancel",
            onOk:function(){
                var user =document.getElementById('user').value
                var pass =document.getElementById('passwd').value
                document.getElementById('res').innerHTML='';
                Autorizar(user,pass);
                window.alert('¡¡ DATOS ENVIADOS !!');
                if(document.getElementById('res').innerHTML==1){
                    Aut = true;
                    document.getElementById("chkKnegativo").checked=true;
                    document.getElementById("lblinteres").style.display="";
                    document.getElementById("txtinteress").style.display="";
                    document.getElementById("lblCobroKnegativo").style.display="";
                    document.getElementById("txtCobroKnegativo").style.display="";
                    confirmarprestamo();
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
    }else{
        document.getElementById("txtKnegativo").value="NO";
        document.getElementById("lblinteres").style.display="none";
        document.getElementById("txtinteress").style.display="none";
        document.getElementById("lblCobroKnegativo").style.display="none";
        document.getElementById("txtCobroKnegativo").style.display="none";
    }
}

//var Aut = null;
function ventanaNueva2() {
    if(document.getElementById("chkCobroPF").checked==true){
        document.getElementById("chkCobroPF").checked=false;
        Dialog.confirm($('loginn').innerHTML,
        {
            className:"alphacube",
            width:400,
            okLabel: "Confirmar",
            title: "Autorizar Usuario",
            cancelLabel: "cancel",
            onOk:function(){
                var user =document.getElementById('user').value
                var pass =document.getElementById('passwd').value
                document.getElementById('res').innerHTML='';
                Autorizar(user,pass);
                window.alert('¡¡ DATOS ENVIADOS !!')
                if(document.getElementById('res').innerHTML==1){
                    Aut = true;
                    document.getElementById("txtCobroPF").value="2649232134";
                    document.getElementById("chkCobroPF").checked=true;
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
    }else{
        document.getElementById("txtCobroPF").value="x";
    }
}

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

function confirmarprestamo(){
    document.getElementById("txtKnegativo").value="SI";
}

function ImprimirTicket(){
    document.frmadmincuenta.action="reportecuenta.htm";
    document.frmadmincuenta.submit();
}

