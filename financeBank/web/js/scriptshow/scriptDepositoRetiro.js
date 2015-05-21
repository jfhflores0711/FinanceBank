/**
 *
 * @author admin
 */

function cambiartipoTrans(){
    if (document.getElementById("divcuenta").innerHTML!=""){
        var tipoTrans ;
        var newcadena,cadenalegend;
        cadenalegend=document.getElementById('legcuenta').innerHTML;
        if(document.getElementById("rdDeposito").checked==true){
            tipoTrans="DEPOSITO";
            cadenalegend=cadenalegend.replace("undefined",tipoTrans);
            newcadena=cadenalegend.replace("RETIRO",tipoTrans);
            document.getElementById("tdfechadep").innerHTML="<font color='#385b88' style='font-size: 12px;'><b>Fecha de Deposito:</b></font>";
            document.getElementById("cmdRegistrar").value="Registrar Dep&oacute;sito";
            document.getElementById("divpermisoKnegativo").style.display="none";
        }else if(document.getElementById("rdRetiro").checked==true){
            tipoTrans="RETIRO";
            cadenalegend=cadenalegend.replace("undefined",tipoTrans);
            newcadena=cadenalegend.replace("DEPOSITO",tipoTrans);
            document.getElementById("tdfechadep").innerHTML="<font color='#385b88' style='font-size: 12px;'><b>Fecha de Retiro:</b></font>";
            document.getElementById("cmdRegistrar").value="Registrar Retiro";
            document.getElementById("divpermisoKnegativo").style.display="";
            if(document.getElementById("divcuenta").innerHTML!=""){
                var tipo,indice;
                indice=document.getElementById("txtind").value;
                tipo=document.getElementById("tdtipocuenta"+indice).innerHTML;
                if(tipo=="CUENTA A PLAZO FIJO"){
                    document.getElementById("chkKnegativo").style.display="";
                    document.getElementById("blqKnegativo").style.display="";
                    document.getElementById("lblinteres").style.display="none";
                    document.getElementById("txtinteress").style.display="none";
                    document.getElementById("lblCobroKnegativo").style.display="none";
                    document.getElementById("txtCobroKnegativo").style.display="none";
                    document.getElementById("chkCobroPF").style.display="";
                    document.getElementById("blqCobroPF").style.display="";
                }else if(tipo=="CUENTA CORRIENTE"){
                    document.getElementById("chkKnegativo").style.display="";
                    document.getElementById("blqKnegativo").style.display="";
                    document.getElementById("lblinteres").style.display="none";
                    document.getElementById("txtinteress").style.display="none";
                    document.getElementById("lblCobroKnegativo").style.display="none";
                    document.getElementById("txtCobroKnegativo").style.display="none";
                    document.getElementById("chkCobroPF").style.display="none";
                    document.getElementById("blqCobroPF").style.display="none";
                }else if(tipo=="CUENTA AHORRO"){
                    document.getElementById("chkKnegativo").style.display="";
                    document.getElementById("blqKnegativo").style.display="";
                    document.getElementById("lblinteres").style.display="none";
                    document.getElementById("txtinteress").style.display="none";
                    document.getElementById("lblCobroKnegativo").style.display="none";
                    document.getElementById("txtCobroKnegativo").style.display="none";
                    document.getElementById("chkCobroPF").style.display="none";
                    document.getElementById("blqCobroPF").style.display="none";
                }
            }
        }
        document.getElementById('legcuenta').innerHTML=newcadena;
    }
}

function mostrarbotoncreate(indice) {
    document.getElementById("chkKnegativo").checked=false;
    document.getElementById("txtind").value=indice;
    var tipo=document.getElementById("tdtipocuenta"+indice).innerHTML;
    var numcuenta,fechahoy,miinteres,idPersona,idMoney,IdCuenPersona,observaciones,mon;
    var fechaplazoFijo;
    numcuenta=document.getElementById("tdNumCuenta"+indice).innerHTML;
    fechahoy=document.getElementById("tdFechaHoy"+indice).innerHTML;
    miinteres=document.getElementById("tdInteres"+indice).innerHTML;
    idPersona=document.getElementById("tdIdPersonas"+indice).innerHTML;
    idMoney=document.getElementById("tdIdMoney"+indice).innerHTML;
    IdCuenPersona=document.getElementById("tdIdCuenPersona"+indice).innerHTML;
    observaciones=document.getElementById("tdCuenObservacion"+indice).innerHTML;
    document.getElementById("cmdRegistrar").disabled=false;
    document.getElementById("divpermisoKnegativo").style.display="";
    if(idMoney=="PEN")
        mon="S/.";
    else if(idMoney=="USD")
        mon="&#36;";
    else
        mon="&#8364;";
    var tipoTrans, tipoTransMinusculas ;
    if(document.getElementById("rdDeposito").checked==true){
        tipoTrans="DEPOSITO";
        tipoTransMinusculas="Depósito";
        document.getElementById("cmdRegistrar").value="Registrar Depósito";
        document.getElementById("divpermisoKnegativo").style.display="none";
    }else if(document.getElementById("rdRetiro").checked==true){
        tipoTrans="RETIRO";
        tipoTransMinusculas="Retiro";
        document.getElementById("cmdRegistrar").value="Registrar Retiro";
        document.getElementById("divpermisoKnegativo").style.display="";
    }
    if(tipo=="CUENTA A PLAZO FIJO"){
        fechaplazoFijo=document.getElementById("tdfechaplazoF"+indice).innerHTML;
        document.getElementById("divcuenta").innerHTML="<fieldset id='fielCuen' style='border-width:3px'>"+
        "<legend id='legcuenta'><b>"+tipoTrans+" CUENTA A PLAZO FIJO</b></legend>"+
        "<table id='tablecuenta'>"+
        "<tr>"+
        "<td style='font-weight:bold' colspan='2'>&nbsp;</td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Numero Cuenta:</b></font></td>"+
        "<td><input type='text' id='txtNumCuentaDep' name='txtNumCuentaDep' value='"+numcuenta+"' disabled style='font-weight:bold;color:black'/></td>"+
        "</tr>"+
        "<tr>"+
        "<td id='tdfechadep'><font color='#385b88' style='font-size: 12px;'><b>Fecha de "+tipoTransMinusculas+"</b></font></td>"+
        "<td><input type='text' id='txtFechaDeposito' name='txtFechaDeposito' value='"+fechahoy+"' disabled/></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Fecha de Vencimiento de plazo fijo:</b></font></td>"+
        "<td><input type='text' id='txtPFFechaPlazoFijo' name='txtPFFechaPlazoFijo' value='"+fechaplazoFijo+"' readonly/></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>importe:</b></font></td>"+
        "<td><font color='#E08934' style='font-size: 20px;'><b>"+mon+"</b></font><input type='text' id='txtImporteDep' name='txtImporteDep' value='' onfocus='this.select();' onblur='this.value=eedisplayFloatNDTh(eeparseFloatTh(this.value),2);' onkeypress='return validar(event);' /></td>"+
        "</tr>"+
        "<tr>"+
        "<td></td>"+
        "<td ><input type='button' value='Limpiar monto' onclick=\"document.getElementById('txtImporteDep').value=''; document.getElementById('txtImporteDep').focus();\" ></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>interes:</b></font></td>"+
        "<td><input type='text' id='txtInteres' name='txtInteres' value='"+miinteres+"' disabled style='font-weight:bold;color:black;width: 60px;' /><font color='#385b88' style='font-size: 12px;'><b>%</b></font></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Nombre:</b></font></td>"+
        "<td><input type='text' id='txtNombreDep' name='txtNombreDep' value='' />(Nombre del cliente que realiza transacci&oacute;n)</td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Apellidos:</b></font></td>"+
        "<td><input type='text' id='txtApellidoDep' name='txtApellidoDep' value=''/>(Apellidos del cliente que realiza transacci&oacute;n)</td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Numero Cheque:</b></font></td>"+
        "<td><input type='text' id='txtNumCheque' name='txtNumCheque' value=''/></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Observaciones</b></font></td>"+
        "<td><textarea id='txtaObservacion' name='txtaObservacion' rows='4' cols='20' mmaxlength='500'>"+observaciones+"</textarea></td>"+
        "</tr>"+
        "<tr>"+
        "<td>Tipo Cuenta</td>"+
        "<td><input type='text' id='txTipoCuenta' name='txTipoCuenta' value='"+tipo+"' readonly/></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id Persona</td>"+
        "<td><input type='text' id='txidPersona' name='txidPersona' value='"+idPersona+"' /></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id Money</td>"+
        "<td><input type='text' id='txidMoney' name='txidMoney' value='"+idMoney+"' /></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id CuentaPersona</td>"+
        "<td><input type='text' id='txidCuentaPersona' name='txidCuentaPersona' value='"+IdCuenPersona+"' /></td>"+
        "</tr>"+
        "</table>"+
        "</fieldset>";
        document.getElementById("chkKnegativo").style.display="";
        document.getElementById("blqKnegativo").style.display="";
        document.getElementById("lblinteres").style.display="none";
        document.getElementById("txtinteress").style.display="none";
        document.getElementById("lblCobroKnegativo").style.display="none";
        document.getElementById("txtCobroKnegativo").style.display="none";
        document.getElementById("chkCobroPF").style.display="";
        document.getElementById("blqCobroPF").style.display="";
    }else if(tipo=="CUENTA CORRIENTE") {
        document.getElementById("divcuenta").innerHTML="<fieldset id='fielCuen' style='border-width:3px'>"+
        "<legend id='legcuenta'><b>"+tipoTrans+" "+tipo+"</b></legend>"+
        "<table id='tablecuenta'>"+
        "<tr>"+
        "<td style='font-weight:bold' colspan='2'>&nbsp;</td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Numero Cuenta:</b></font></td>"+
        "<td><input type='text' id='txtNumCuentaDep' name='txtNumCuentaDep' value='"+numcuenta+"' disabled style='font-weight:bold;color:black'/></td>"+
        "</tr>"+
        "<tr>"+
        "<td  id='tdfechadep'><font color='#385b88' style='font-size: 12px;'><b>Fecha de "+tipoTransMinusculas+":</b></font></td>"+
        "<td><input type='text' id='txtFechaDeposito' name='txtFechaDeposito' value='"+fechahoy+"' disabled/></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>importe:</b></font></td>"+
        "<td><font color='#E08934' style='font-size: 20px;'><b>"+mon+"</b></font><input type='text' id='txtImporteDep' name='txtImporteDep' value='' onfocus='this.select();' onblur='this.value=eedisplayFloatNDTh(eeparseFloatTh(this.value),2);' onkeypress='return validar(event);'/></td>"+
        "</tr>"+
        "<tr>"+
        "<td></td>"+
        "<td ><input type='button' value='Limpiar importe' onclick=\"document.getElementById('txtImporteDep').value=''; document.getElementById('txtImporteDep').focus();\" ></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>interes:</b></font></td>"+
        "<td><input type='text' id='txtInteres' name='txtInteres' value='"+miinteres+"' onkeypress='return solodecimales(event);' disabled style='font-weight:bold;color:black;width: 60px;'/><font color='#385b88' style='font-size: 12px;' ><b>%</b></font></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Nombre:</b></font></td>"+
        "<td><input type='text' id='txtNombreDep' name='txtNombreDep' value='' />(Nombre del cliente que realiza transacci&oacute;n)</td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Apellidos:</b></font></td>"+
        "<td><input type='text' id='txtApellidoDep' name='txtApellidoDep' value=''/>(Apellido del cliente que realiza transacci&oacute;n)</td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Numero Cheque:</b></font></td>"+
        "<td><input type='text' id='txtNumCheque' name='txtNumCheque' value='' onkeypress='return soloNumeroTelefonico(event);'/></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Observaciones</b></font></td>"+
        "<td><textarea id='txtaObservacion' name='txtaObservacion' rows='4' cols='20' mmaxlength='500'>"+observaciones+"</textarea></td>"+
        "</tr>"+
        "<tr>"+
        "<td>Tipo Cuenta</td>"+
        "<td><input type='text' id='txTipoCuenta' name='txTipoCuenta' value='"+tipo+"' readonly/></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id Persona</td>"+
        "<td><input type='text' id='txidPersona' name='txidPersona' value='"+idPersona+"' /></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id Money</td>"+
        "<td><input type='text' id='txidMoney' name='txidMoney' value='"+idMoney+"' /></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id CuentaPersona</td>"+
        "<td><input type='text' id='txidCuentaPersona' name='txidCuentaPersona' value='"+IdCuenPersona+"' /></td>"+
        "</tr>"+
        "</table>"+
        "</fieldset>";
        document.getElementById("chkKnegativo").style.display="";
        document.getElementById("blqKnegativo").style.display="";
        document.getElementById("lblinteres").style.display="none";
        document.getElementById("txtinteress").style.display="none";
        document.getElementById("lblCobroKnegativo").style.display="none";
        document.getElementById("txtCobroKnegativo").style.display="none";
        document.getElementById("chkCobroPF").style.display="none";
        document.getElementById("blqCobroPF").style.display="none";
    }else if(tipo=="CUENTA AHORRO") {
        document.getElementById("divcuenta").innerHTML="<fieldset id='fielCuen' style='border-width:3px'>"+
        "<legend id='legcuenta'><b>"+tipoTrans+" "+tipo+"</b></legend>"+
        "<table id='tablecuenta'>"+
        "<tr>"+
        "<td style='font-weight:bold' colspan='2'>&nbsp;</td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Numero Cuenta:</b></font></td>"+
        "<td><input type='text' id='txtNumCuentaDep' name='txtNumCuentaDep' value='"+numcuenta+"' disabled style='font-weight:bold;color:black'/></td>"+
        "</tr>"+
        "<tr>"+
        "<td  id='tdfechadep'><font color='#385b88' style='font-size: 12px;'><b>Fecha de "+tipoTransMinusculas+":</b></font></td>"+
        "<td><input type='text' id='txtFechaDeposito' name='txtFechaDeposito' value='"+fechahoy+"' disabled/></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>importe:</b></font></td>"+
        "<td><font color='#E08934' style='font-size: 20px;'><b>"+mon+"</b></font><input type='text' id='txtImporteDep' name='txtImporteDep' value='' onfocus='this.select();' onblur='this.value=eedisplayFloatNDTh(eeparseFloatTh(this.value),2);' onkeypress='return validar(event);'/></td>"+
        "</tr>"+
        "<tr>"+
        "<td></td>"+
        "<td ><input type='button' value='Limpiar importe' onclick=\"document.getElementById('txtImporteDep').value=''; document.getElementById('txtImporteDep').focus();\" ></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>interes:</b></font></td>"+
        "<td><input type='text' id='txtInteres' name='txtInteres' value='"+miinteres+"' onkeypress='return solodecimales(event);' disabled style='font-weight:bold;color:black;width: 60px;'/><font color='#385b88' style='font-size: 12px;'><b>%</b></font></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Nombre:</b></font></td>"+
        "<td><input type='text' id='txtNombreDep' name='txtNombreDep' value='' />(Nombre del cliente que realiza transacci&oacute;n)</td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Apellidos:</b></font></td>"+
        "<td><input type='text' id='txtApellidoDep' name='txtApellidoDep' value=''/>(Apellido del cliente que realiza transacci&oacute;n)</td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Numero Cheque:</b></font></td>"+
        "<td><input type='text' id='txtNumCheque' name='txtNumCheque' value='' onkeypress='return soloNumeroTelefonico(event);'/></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Observaciones</b></font></td>"+
        "<td><textarea id='txtaObservacion' name='txtaObservacion' rows='4' cols='20' mmaxlength='500'>"+observaciones+"</textarea></td>"+
        "</tr>"+
        "<tr>"+
        "<td>Tipo Cuenta</td>"+
        "<td><input type='text' id='txTipoCuenta' name='txTipoCuenta' value='"+tipo+"' readonly/></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id Persona</td>"+
        "<td><input type='text' id='txidPersona' name='txidPersona' value='"+idPersona+"' /></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id Money</td>"+
        "<td><input type='text' id='txidMoney' name='txidMoney' value='"+idMoney+"' /></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id CuentaPersona</td>"+
        "<td><input type='text' id='txidCuentaPersona' name='txidCuentaPersona' value='"+IdCuenPersona+"' /></td>"+
        "</tr>"+
        "</table>"+
        "</fieldset>";
        document.getElementById("chkKnegativo").style.display="";
        document.getElementById("blqKnegativo").style.display="";
        document.getElementById("lblinteres").style.display="none";
        document.getElementById("txtinteress").style.display="none";
        document.getElementById("lblCobroKnegativo").style.display="none";
        document.getElementById("txtCobroKnegativo").style.display="none";
        document.getElementById("chkCobroPF").style.display="none";
        document.getElementById("blqCobroPF").style.display="none";
    }
}

var da1,da2;
var req4;
function vercuenta(){
    document.getElementById("divcuenta").innerHTML="";
    document.getElementById("txtTempSelTipB").value=document.getElementById("selTipoBus").value;
    document.getElementById("txtTempdataB").value=document.getElementById("txtBuscar").value;
    document.getElementById("divpermisoKnegativo").style.display="none";
    document.getElementById("cmdRegistrar").disabled=true;
    document.getElementById("divcuenta").innerHTML="";
    var contenido, tipoBusqueda="";
    contenido=document.getElementById("txtBuscar").value;
    if(contenido.length<2){
        window.alert("Escriba al menos dos caracteres\n para realizar la búsqueda.");
        return;
    }
    tipoBusqueda=document.getElementById("selTipoBus").value;
    if(tipoBusqueda=='0'){
        window.alert("Seleccione un tipo de busqueda");
        return;
    }
    req4 = null;
    if (window.XMLHttpRequest) {
        req4 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req4 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req4!=null){
        req4.onreadystatechange = procesarRespuesta3;
        var PATH = "/financeBank";
        var url = PATH + "/SDepositoRetiro2?a="+contenido.trim()+"&b="+tipoBusqueda;
        req4.open("GET",url,true);
        req4.send(null);
    }
}

var req2;
function mostrarcuentadetalle(ind) {
    document.getElementById("divpermisoKnegativo").style.display="none";
    document.getElementById("cmdRegistrar").disabled=true;
    document.getElementById("divcuenta").innerHTML="";
    var contenido, tipoBusqueda="";
    contenido=document.getElementById("txtBuscar").value;
    if(contenido.length<2){
        window.alert("Debe existir al menos dos caracteres\n para realizar la búsqueda.");
        return;
    }
    tipoBusqueda=document.getElementById("selTipoBus").value;
    if (tipoBusqueda=="NUMERO DE CUENTA"){
        contenido=document.getElementById("ttdNumCuenta"+ind).innerHTML;
    }else if (tipoBusqueda=="DNI" || tipoBusqueda=="NOMBRE" || tipoBusqueda=="APELLIDO"){
        contenido=ind;
    }else if (tipoBusqueda=="RUC"){
        contenido=ind;
    }
    req2 = false;
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
        tamTabla=document.getElementById("tablamiscuentas").rows.length
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
    contenido.innerHTML="Cargando los datos...";
    if (req4.readyState==4 && req4.status==200) {
        contenido.innerHTML = req4.responseText;
        var tamTabla;
        tamTabla=document.getElementById("tablapersonas").rows.length
        if(tamTabla>=7){
            contenido = document.getElementById('divlistPerCuentas').style.overflow="auto";
            contenido = document.getElementById('divlistPerCuentas').style.height="320px";
        }else{
            contenido = document.getElementById('divlistPerCuentas').style.overflow="";
            contenido = document.getElementById('divlistPerCuentas').style.height="";
        }
    }
}

function validartxtBusqueda(){
    var seltipo;
    document.getElementById("cmdRegistrar").disabled=true;
    seltipo=document.getElementById("selTipoBus").value;
    if(seltipo=="NUMERO DE CUENTA"){
        document.getElementById('txtestados').value="0";
        document.getElementById("divtxtBuscar").innerHTML=" <input id='txtBuscar' type='text' name='txtBuscar' value='' onkeypress='return soloNumeroTelefonico(event);' onkeyup='activeSpecialKey(event);' />";
        document.getElementById("divlistPerCuentas").innerHTML="";
        document.getElementById("divlistPerCuentas").style.overflow="";
        document.getElementById("divlistPerCuentas").style.height="0px";
        document.getElementById("tableMiscuentas").innerHTML="";
        document.getElementById("divcuenta").innerHTML="";
        document.getElementById("divpermisoKnegativo").style.display="none";
    }else if(seltipo=="DNI"){
        document.getElementById('txtestados').value="0";
        document.getElementById("divtxtBuscar").innerHTML=" <input id='txtBuscar' type='text' name='txtBuscar' value='' onkeypress='return solonumeros(event);' onkeyup='activeSpecialKey(event);' maxlength='8' />";
        document.getElementById("divlistPerCuentas").innerHTML="";
        document.getElementById("divlistPerCuentas").style.height="0px";
        document.getElementById("tableMiscuentas").innerHTML="";
        document.getElementById("divcuenta").innerHTML="";
        document.getElementById("divpermisoKnegativo").style.display="none";
    }else if(seltipo=="RUC"){
        document.getElementById('txtestados').value="0";
        document.getElementById("divtxtBuscar").innerHTML=" <input id='txtBuscar' type='text' name='txtBuscar' value='' onkeypress='return solonumeros(event);' onkeyup='activeSpecialKey(event);'/>";
        document.getElementById("divlistPerCuentas").innerHTML="";
        document.getElementById("divlistPerCuentas").style.height="0px";
        document.getElementById("tableMiscuentas").innerHTML="";
        document.getElementById("divcuenta").innerHTML="";
        document.getElementById("divpermisoKnegativo").style.display="none";
    }else if(seltipo=="NOMBRE"){
        document.getElementById('txtestados').value="1";
        document.getElementById("divtxtBuscar").innerHTML=" <input id='txtBuscar' type='text' name='txtBuscar' value='' onkeypress='activeSpecialKey(event);' />";
        document.getElementById("divlistPerCuentas").innerHTML="";
        document.getElementById("divlistPerCuentas").style.height="0px";
        document.getElementById("tableMiscuentas").innerHTML="";
        document.getElementById("divcuenta").innerHTML="";
        document.getElementById("divpermisoKnegativo").style.display="none";
    }else if(seltipo=="APELLIDO"){
        document.getElementById('txtestados').value="1";
        document.getElementById("divtxtBuscar").innerHTML=" <input id='txtBuscar' type='text' name='txtBuscar' value='' onkeypress='activeSpecialKey(event);' />";
        document.getElementById("divlistPerCuentas").innerHTML="";
        document.getElementById("divlistPerCuentas").style.height="0px";
        document.getElementById("tableMiscuentas").innerHTML="";
        document.getElementById("divcuenta").innerHTML="";
        document.getElementById("divpermisoKnegativo").style.display="none";
    }else{
        document.getElementById('txtestados').value="0";
        document.getElementById("divtxtBuscar").innerHTML=" <input id='txtBuscar' disabled type='text' name='txtBuscar' value='' onkeypress='activeSpecialKey(event);' />";
        document.getElementById("divlistPerCuentas").innerHTML="";
        document.getElementById("divlistPerCuentas").style.height="0px";
        document.getElementById("tableMiscuentas").innerHTML="";
        document.getElementById("divcuenta").innerHTML="";
        document.getElementById("divpermisoKnegativo").style.display="none";
    }
    document.getElementById("txtBuscar").focus();
}

var req3;
function registrarDepRet(){
    var TipTrans,tipoCuentah,NumCuentaDep, miIdPersona, miIdMoney,miImporte,miInteres,miIdCuenPersona;
    var nombreDeposit, apellidosDeposit,observacion,Importe,Knegativo,PCobroPF="",interesKnegativo="";
    var fechaCobroKneagativo="";
    TipTrans=document.getElementById("txtTipTrans").value;
    Importe=document.getElementById("txtImporteDep").value;
    Knegativo=document.getElementById("txtKnegativo").value;
    PCobroPF=document.getElementById("txtCobroPF").value;
    if(document.getElementById("txTipoCuenta").value=="CUENTA A PLAZO FIJO"){
        var today = new Date();
        var dd = today.getDate();
        var mm = today.getMonth()+1;//January is 0!
        var yyyy = today.getFullYear();
        if(dd<10){
            dd='0'+dd
        }
        if(mm<10){
            mm='0'+mm
        }
        var f=dd+'/'+mm+'/'+yyyy;
        if(f!=document.getElementById("txtPFFechaPlazoFijo").value){
            if(document.getElementById("chkCobroPF").checked!=true){
                window.alert("La Cuenta Plazo fijo aún no ha vencido \nDebe autorizar esta transacción primero!");
                return;
            }
        }
    }
    if(TipTrans=="DEPOSITO" || TipTrans=="RETIRO"){
        if(Importe!=""){
            NumCuentaDep=document.getElementById("txtNumCuentaDep").value;
            miIdPersona=document.getElementById("txidPersona").value;
            miIdMoney=document.getElementById("txidMoney").value;
            miImporte=document.getElementById("txtImporteDep").value;
            miInteres=document.getElementById("txtInteres").value;
            nombreDeposit=document.getElementById("txtNombreDep").value;
            apellidosDeposit=document.getElementById("txtApellidoDep").value;
            miIdCuenPersona=document.getElementById("txidCuentaPersona").value;
            observacion=document.getElementById("txtaObservacion").value;
            tipoCuentah=document.getElementById("txTipoCuenta").value;
            var miNumCheque=document.getElementById("txtNumCheque").value;
            interesKnegativo=document.getElementById("txtinteress").value;
            fechaCobroKneagativo=document.getElementById("txtCobroKnegativo").value;
            if (window.XMLHttpRequest) {
                req3 = new XMLHttpRequest();
            }else if (window.ActiveXObject) {
                req3 = new ActiveXObject("Microsoft.XMLHTTP");
            }
            if(req3!=null){
                var rpta=TipTrans;
                if(document.getElementById('chkKnegativo').checked==true && document.getElementById('txtinteress').value==""){
                    window.alert("Ingrese Interes para el prestamo");
                    document.frmdeposito.txtinteress.focus();
                    return;
                }
                var answer = confirm ("¿ESTA SEGURO QUE DESEA REGISTRAR "+rpta+"?");
                if (answer){
                    req3.onreadystatechange = procesarrResspuesta;
                    var PATH = "/financeBank";
                    var url = PATH + "/SRegistrarDepositoRetiro?a="+TipTrans+"&b="+
                    NumCuentaDep+"&c="+miIdPersona+"&d="+
                    miIdMoney+"&e="+miImporte+"&f="+miInteres+
                    "&g="+nombreDeposit.replace("&", ";", "gi")+"&h="+
                    apellidosDeposit.replace("&", ";", "gi")+"&i="+miIdCuenPersona+
                    "&j="+observacion+"&k="+Knegativo+"&l="+PCobroPF+
                    "&m="+miNumCheque+"&n="+tipoCuentah+
                    "&o="+interesKnegativo+"&p="+fechaCobroKneagativo;
                    req3.open("GET",url,true);
                    req3.send(null);
                }
            }
        }else{
            window.alert("DEBE INGRESAR IMPORTE PARA LA TRANSACCION");
            document.frmdeposito.txtImporteDep.focus();
        }
    }else{
        window.alert("Debe seleccionar el TIPO DE TRANSACCION (DEPOSITO o RETIRO)");
    }
}

function procesarrResspuesta(){
    contenido = document.getElementById('divRespuestaRegistro');
    contenido.innerHTML="Cargando los datos...";
    if (req3.readyState==4 && req3.status==200) {
        contenido.innerHTML = req3.responseText;
        var tipTrans;
        tipTrans=document.getElementById('txtTipTrans').value;
        var es;
        es=document.getElementById("txtRespReg").value;
        var van;
        van=document.getElementById("txtExisteMontoCaja").value;
        var vanPF;
        vanPF=document.getElementById("txtVanPF").value;
        if(van=="OK"){
            if(es=="OK"){
                if(vanPF==""){
                    if(tipTrans=="DEPOSITO"){
                        window.alert("El DEPOSITO se ha realizado satisfactoriamente");
                        document.frmiraticket.submit();
                    }else{
                        window.alert("El RETIRO se ha realizado satisfactoriamente");
                        document.frmiraticket.submit();
                    }
                }else{
                    window.alert("La fecha de plazo fijo vence el :"+vanPF);
                }
            }else if (es=="NO HAY DINERO SUFICIENTE"){
                window.alert("NO HAY DINERO SUFICIENTE EN LA CUENTA DEL CLIENTE");
            }else{
                window.alert("La TRANSACCION NO se pudo realizar, revise sus datos e intentelo de nuevo");
            }
        }else if(van=="NO HAY DINERO EN CAJA"){
            window.alert("NO DISPONE DE DINERO EN CAJA EN ESTE TIPO DE MONEDA, PIDA UNA TRANSFERENCIA A LA BOVEDA O CAJERO");
        }else{
            window.alert("La TRANSACCION NO se pudo realizar, revise sus datos e intentelo de nuevo.");
        }
    }
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
                window.alert('¡¡ DATOS ENVIADOS !!')
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

//var Aut = false;
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
                window.alert('¡¡DATOS ENVIADOS!!')
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

var req9;
function Autorizar(login,contrasenia) {
    req9 = null;
    if (window.XMLHttpRequest) {
        req9 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req9 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req9!=null){
        req9.onreadystatechange = procesar1;
        var PATH = "/financeBank";
        var url = PATH + "/SAutorizarUsuario?login="+login+"&contrasenia="+contrasenia;
        req9.open("GET",url,true);
        req9.send(null);
    }
}

function procesar1(){
    contenido = document.getElementById('res');
    contenido.innerHTML="Cargando los datos...";
    contenido.innerHTML="";
    if (req9.readyState==4 && req9.status==200) {
        contenido.innerHTML = req9.responseText;
    }
}

function confirmarprestamo(){
    document.getElementById("txtKnegativo").value="SI";
}

            