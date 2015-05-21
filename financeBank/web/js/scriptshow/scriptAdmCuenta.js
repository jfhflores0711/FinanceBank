/* 
 * Oscar
 */

var req5,cad3="";
var cad2="";
function actualizarCuenta(casoOP){
    var idcuenta="",interes="",observacion="",estado="",tipocuenta="",cad="";
    if(casoOP==2){
        if(parseFloat(document.getElementById("txtImporteDep").value)!=0.0){
            window.alert("No se puede Eliminar una cuenta con monto diferente de cero!");
            return;
        }
    }
    idcuenta=document.getElementById("txidCuentaPersona").value;
    interes=document.getElementById("txtInteres").value;
    observacion=document.getElementById("txtaObservacion").value;
    estado=document.getElementById("selestado").value;
    tipocuenta=document.getElementById("txTipoCuenta").value;
    if(tipocuenta=="CUENTA A PLAZO FIJO"){
        cad="&fechaPF="+document.getElementById("txtPFFechaPlazoFijo").value;
    }
    req5 = null;
    if (window.XMLHttpRequest) {
        req5 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req5 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req5!=null){
        if(casoOP==1){
            cad2="ACTUALIZAR";
            cad3='ACUALIZADO';
        }else if(casoOP==2){
            cad2="ELIMINAR";
            cad3="ELIMINADO"; 
        }else{
            cad2="BLOQUEAR/DESBLOQUEAR";
            cad3="BLOQUEADO/DESBLOQUEADO";           
        }
        var answer = confirm ("¿ESTA SEGURO QUE DESEA "+cad2+" LA CUENTA?");
        if (answer){
            req5.onreadystatechange = procesarMiRespuesta;
            var PATH = "/financeBank";
            var url = PATH + "/SMantenerCuenta?idcuenta="+idcuenta+"&casoOP="+casoOP+"&interes="+interes+"&observacion="+observacion+"&estado="+estado+"&tipocuenta="+tipocuenta+""+cad;
            req5.open("GET",url,true);
            req5.send(null);
        }
    }
}

function procesarMiRespuesta(){
    contenido = document.getElementById('divesperarservlet');
    contenido.innerHTML="Cargando los datos...";
    if (req5.readyState==4 && req5.status==200) {
        contenido.innerHTML = req5.responseText;
        if(document.getElementById("divesperarservlet").innerHTML=="OK"){
            window.alert("LA CUENTA SE "+cad3+" SATISFACTORIAMENTE");
            document.frmActualizar.submit();
        }else{
            window.alert("ERROR AL "+cad2+" LA CUENTA");
        }
    }
}

function activarcuentaestado(indice){
    if (document.getElementById("fechaEstCuen").value!=""){
        var idcuenta="",fecha="", hasta="";
        idcuenta=document.getElementById("tdIdCuenPersona"+indice).innerHTML
        fecha=document.getElementById("fechaEstCuen").value;
        hasta=document.getElementById("hasta").value;
        document.frmIrEstadoCuenta.action="estadodecuenta.htm?idcuenta="+idcuenta+"&fecha="+fecha+"&hasta="+hasta;
        document.frmIrEstadoCuenta.submit();
    }else{
        window.alert("INGRESE FECHA DE INICIO PARA EL ESTADO DE CUENTA");
    }
}

function alertaCheckedHasta(){
    if(document.getElementById("checarHasta").checked)
        document.getElementById("hasta").style.visibility='';
    else
        document.getElementById("hasta").style.visibility='hidden';
}

var req;

function mostrarbotoncreate(indice) {
    var ind=document.getElementById("txtind").value;
    document.getElementById("txtind").value=indice;
    var tipo=document.getElementById("tdtipocuenta"+indice).innerHTML;
    var numcuenta,fechacuenta,miinteres,idPersona,idMoney,IdCuenPersona,observaciones,mon;
    var fechaplazoFijo,saldo,saldo1,cadena1,cadena2,cadena3,nombreMoneda="";
    var nombreTitular="",apellidosTitular="",dniTitular="",rucTitular;
    numcuenta=document.getElementById("tdNumCuenta"+indice).innerHTML;
    fechacuenta=document.getElementById("tdCuenFecha"+indice).innerHTML;
    miinteres=document.getElementById("tdInteres"+indice).innerHTML;
    idPersona=document.getElementById("tdIdPersonas"+indice).innerHTML;
    idMoney=document.getElementById("tdIdMoney"+indice).innerHTML;
    IdCuenPersona=document.getElementById("tdIdCuenPersona"+indice).innerHTML;
    observaciones=document.getElementById("tdObservacionCuen"+indice).innerHTML;
    saldo=document.getElementById("tdCuenSaldo"+indice).innerHTML;
    saldo=eedisplayFloatNDTh(eeparseFloatTh(saldo),2);
    saldo1=document.getElementById("tdCuenSaldo1"+indice).innerHTML;
    saldo1=eedisplayFloatNDTh(eeparseFloatTh(saldo1),2);
    var estado=document.getElementById("tdCuenEstado"+indice).innerHTML;
    nombreMoneda=document.getElementById("tdCuenMoneda"+indice).innerHTML;
    nombreTitular=document.getElementById("ttdN"+ind).innerHTML;
    apellidosTitular=document.getElementById("ttdA"+ind).innerHTML;
    dniTitular=document.getElementById("ttdDNI"+ind).innerHTML;
    rucTitular=document.getElementById("ttdRUC"+ind).innerHTML;
    if(estado=="ACTIVO"){
        cadena1="selected";
        cadena2="";
        cadena3="";
    } else if(estado=="INACTIVO"){
        cadena1="";
        cadena2="selected";
        cadena3="";
    }
    else if(estado=="SOBREGIRO"){
        cadena1="";
        cadena2="";
        cadena3="selected";
    }
    if(idMoney=="PEN")
        mon="S/.";
    else if(idMoney=="USD")
        mon="&#36;";
    else
        mon="&#8364;";
    if(tipo=="CUENTA A PLAZO FIJO"){
        fechaplazoFijo=document.getElementById("tdfechaplazoF"+indice).innerHTML;
        document.getElementById("divcuenta").innerHTML="<fieldset id='fielCuen' style='border-width:3px'>"+
        "<legend id='legcuenta'><b> "+tipo+(estado=="BLOQUEADO"?"LA CUENTA ESTÁ BLOQUEADA":"")+"</b></legend>"+
        "<table id='tablecuenta'>"+
        "<tr>"+
        "<td style='font-weight:bold' colspan='2'>&nbsp;</td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Numero Cuenta:</b></font></td>"+
        "<td><input type='text' id='txtNumCuentaDep' name='txtNumCuentaDep' value='"+numcuenta+"' disabled style='font-weight:bold;color:black'/></td>"+
        "</tr>"+
        "<tr>"+
        "<td id='tdfechadep'><font color='#385b88' style='font-size: 12px;'><b>Fecha de Creaci&oacute;n</b></font></td>"+
        "<td><input type='text' id='txtFechaDeposito' name='txtFechaDeposito' value='"+fechacuenta+"' disabled/></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Fecha plazo fijo:</b></font></td>"+
        "<td><input type='text' id='txtPFFechaPlazoFijo' name='txtPFFechaPlazoFijo' value='"+fechaplazoFijo+"' onclick=\"popUpCalendar(this, frmadmincuenta.txtPFFechaPlazoFijo , 'dd/mm/yyyy');\"/></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Saldo Total:</b></font>"+
        //"<input type='button' value='CAPITALIZAR' onclick='capitalizar(\""+IdCuenPersona+"\")'> Valor:<input type='text' id='txtC' name='txtC' maxlenth='20' value='0.00' style='font-weight:bold;color:black;width:60px;'/>Poner el monto de diferencia"+
        "</td><td><font color='#E08934' style='font-size: 20px;'><b>"+mon+"</b></font><input type='text' id='txtImporteDep' name='txtImporteDep' value='"+saldo+"' disabled/></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Saldo Disponible:</b></font></td>"+
        "<td><font color='#E08934' style='font-size: 20px;'><b>"+mon+"</b></font><input type='text' id='txtImporteDep1' name='txtImporteDep1' value='"+saldo1+"' disabled/></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>interes:</b></font></td>"+
        "<td><input type='text' id='txtInteres' name='txtInteres' value='"+miinteres+"' style='font-weight:bold;color:black;width: 60px;' /><font color='#385b88' style='font-size: 12px;'><b>%</b></font></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>estado:</b></font></td>"+
        "<td><select id='selestado' name='selestado' disabled><option "+cadena1+">ACTIVO</option><option "+cadena2+">INACTIVO</option><option "+cadena3+">SOBREGIRO</option></select></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Observaciones</b></font></td>"+
        "<td><textarea id='txtaObservacion' name='txtaObservacion' rows='4' cols='20' mmaxlength='500'>"+observaciones+"</textarea></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>interes:</b></font></td>"+
        "<td><input type='text' id='txtInteresCuen' name='txtInteresCuen' value='"+miinteres+"' style='font-weight:bold;color:black;width: 60px;' /><font color='#385b88' style='font-size: 12px;'><b>%</b></font></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Observaciones</b></font></td>"+
        "<td><textarea id='txtaObservacionCuen' name='txtaObservacionCuen' style='height:36px;width:504px' rows='4' cols='20' mmaxlength='500'>"+observaciones+"</textarea></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>nombreMoneda:</b></font></td>"+
        "<td><input type='text' id='txtNombreMoneda' name='txtNombreMoneda' value='"+nombreMoneda+"' style='font-weight:bold;color:black;width: 60px;' /><font color='#385b88' style='font-size: 12px;'><b>%</b></font></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Tipo Cuenta</td>"+
        "<td><input type='text' id='txTipoCuenta' name='txTipoCuenta' value='"+tipo+"' /></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id Persona</td>"+
        "<td><input type='text' id='txidPersona' name='txidPersona' value='"+idPersona+"'/></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id Money</td>"+
        "<td><input type='text' id='txidMoney' name='txidMoney' value='"+idMoney+"'/></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id CuentaPersona</td>"+
        "<td><input type='text' id='txidCuentaPersona' name='txidCuentaPersona' value='"+IdCuenPersona+"' /></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id CuentaPersona</td>"+
        "<td><input type='text' id='txNombreTitular' name='txNombreTitular' value='"+nombreTitular+"' /></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id CuentaPersona</td>"+
        "<td><input type='text' id='txApellidoTitular' name='txApellidoTitular' value='"+apellidosTitular+"' /></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id CuentaPersona</td>"+
        "<td><input type='text' id='txDNITitular' name='txDNITitular' value='"+dniTitular+"' /></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id CuentaPersona</td>"+
        "<td><input type='text' id='txRUCTitular' name='txRUCTitular' value='"+rucTitular+"' /></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td ><font color='#385b88' style='font-size: 12px;'><b>Numero Cuenta:</b></font></td>"+
        "<td><input type='text' id='txtNumCuentaDepo' name='txtNumCuentaDepo' value='"+numcuenta+"' style='font-weight:bold;color:black'/></td>"+
        "</tr>"+
        "</table>"+
        "<table><tr>"+
        "<td><input type='button' name='cmdActualizar' id='cmdActualizar' value='Actualizar' onclick='actualizarCuenta(1);'>"+
        "<input type='button' name='cmdEliminar' id='cmdEliminar' value='Eliminar Cuenta' onclick='actualizarCuenta(2);'>"+
        "<input type='button' name='cmdBloquear' id='cmdBloquear' value='"+(estado=="BLOQUEADO"?"Desbloquear":"Bloquear")+" Cuenta' onclick='actualizarCuenta(3);'>"+
        "<input type='button' name='cmdImprimir' id='cmdImprimir' value='Imprimir Ticket' onclick='ImprimirTicket();'>"+
        "<input type='button' name='cmdEstadoCuenta' id='cmdEstadoCuenta' value='Estado de Cuenta' onclick='activarcuentaestado("+indice+")'>"+
        "</td>"+
        "</tr><tr><td>"+
        "REPORTE Desde:&nbsp;<input type='text' size='10' maxlength='10' name='fechaEstCuen' id='fechaEstCuen' value='"+getNowDate()+"' onclick=\"popUpCalendar(this, frmadmincuenta.fechaEstCuen , 'yyyy/mm/dd');\">"+
        "&nbsp;HASTA <input type='checkbox' name='miCheck' id='checarHasta' onclick='alertaCheckedHasta();'>"+
        "<input type='text' size='10' maxlength='10' name='hasta' id='hasta' value='"+getNowDate()+"' onclick=\"popUpCalendar(this, frmadmincuenta.hasta , 'yyyy/mm/dd');\" style='visibility:hidden;'>"+
        "</td>"+
        "</tr>"+
        "<table>"+
        "</fieldset>";
    } else if(tipo=="CUENTA CORRIENTE") {
        document.getElementById("divcuenta").innerHTML="<fieldset id='fielCuen' style='border-width:3px'>"+
        "<legend id='legcuenta'><b>"+tipo+(estado=="BLOQUEADO"?"LA CUENTA ESTÁ BLOQUEADA":"")+"</b></legend>"+
        "<table id='tablecuenta'>"+
        "<tr>"+
        "<td style='font-weight:bold' colspan='2'>&nbsp;</td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Numero Cuenta:</b></font></td>"+
        "<td><input type='text' id='txtNumCuentaDep' name='txtNumCuentaDep' value='"+numcuenta+"' disabled style='font-weight:bold;color:black'/></td>"+
        "</tr>"+
        "<tr>"+
        "<td  id='tdfechadep'><font color='#385b88' style='font-size: 12px;'><b>Fecha de Creaci&oacute;n:</b></font></td>"+
        "<td><input type='text' id='txtFechaDeposito' name='txtFechaDeposito' value='"+fechacuenta+"' disabled/></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Saldo Total:</b></font></td>"+
        "<td><font color='#E08934' style='font-size: 20px;'><b>"+mon+"</b></font><input type='text' id='txtImporteDep' name='txtImporteDep' value='"+saldo+"' disabled/>"+
        //"<input type='button' value='CAPITALIZAR' onclick='capitalizar(\""+IdCuenPersona+"\")'> Valor:<input type='text' id='txtC' name='txtC' value='0.00' maxlenth='20' style='font-weight:bold;color:black;width:80px;'/>Poner el monto de diferencia"+
        "</td></tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Saldo Disponible:</b></font></td>"+
        "<td><font color='#E08934' style='font-size: 20px;'><b>"+mon+"</b></font><input type='text' id='txtImporteDep1' name='txtImporteDep1' value='"+saldo1+"' disabled/></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>interes:</b></font></td>"+
        "<td><input type='text' id='txtInteres' name='txtInteres' value='"+miinteres+"' onkeypress='return solodecimales(event);' style='font-weight:bold;color:black;width: 60px;'/><font color='#385b88' style='font-size: 12px;'><b>%</b></font></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>estado:</b></font></td>"+
        "<td><select id='selestado' name='selestado' disabled><option "+cadena1+">ACTIVO</option><option "+cadena2+">INACTIVO</option><option "+cadena3+">SOBREGIRO</option></select></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Observaciones</b></font></td>"+
        "<td><textarea id='txtaObservacion' style='height:36px;width:504px' name='txtaObservacion' rows='4' cols='20' mmaxlength='500'>"+observaciones+"</textarea></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Tipo Cuenta</td>"+
        "<td><input type='text' id='txTipoCuenta' name='txTipoCuenta' value='"+tipo+"' /></td>"+
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
        "<td><font color='#385b88' style='font-size: 12px;'><b>nombreMoneda:</b></font></td>"+
        "<td><input type='text' id='txtNombreMoneda' name='txtNombreMoneda' value='"+nombreMoneda+"' style='font-weight:bold;color:black;width: 60px;' /><font color='#385b88' style='font-size: 12px;'><b>%</b></font></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id CuentaPersona</td>"+
        "<td><input type='text' id='txidCuentaPersona' name='txidCuentaPersona' value='"+IdCuenPersona+"' /></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id CuentaPersona</td>"+
        "<td><input type='text' id='txNombreTitular' name='txNombreTitular' value='"+nombreTitular+"' /></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id CuentaPersona</td>"+
        "<td><input type='text' id='txApellidoTitular' name='txApellidoTitular' value='"+apellidosTitular+"' /></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id CuentaPersona</td>"+
        "<td><input type='text' id='txDNITitular' name='txDNITitular' value='"+dniTitular+"' /></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id CuentaPersona</td>"+
        "<td><input type='text' id='txRUCTitular' name='txRUCTitular' value='"+rucTitular+"' /></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td ><font color='#385b88' style='font-size: 12px;'><b>Numero Cuenta:</b></font></td>"+
        "<td><input type='text' id='txtNumCuentaDepo' name='txtNumCuentaDepo' value='"+numcuenta+"' style='font-weight:bold;color:black'/></td>"+
        "</tr>"+
        "</table>"+
        "<table><tr>"+
        "<td><input type='button' name='cmdActualizar' id='cmdActualizar' value='Actualizar' onclick='actualizarCuenta(1);'>"+
        "<input type='button' name='cmdEliminar' id='cmdEliminar' value='Eliminar Cuenta' onclick='actualizarCuenta(2);'>"+
        "<input type='button' name='cmdBloquear' id='cmdBloquear' value='"+(estado=="BLOQUEADO"?"Desbloquear":"Bloquear")+" Cuenta' onclick='actualizarCuenta(3);'>"+
        "<input type='button' name='cmdImprimir' id='cmdImprimir' value='Imprimir Ticket' onclick='ImprimirTicket();'>"+
        "<input type='button' name='cmdEstadoCuenta' id='cmdEstadoCuenta' value='Estado de Cuenta' onclick='activarcuentaestado("+indice+")'>"+
        "</td>"+
        "</tr><tr><td>"+
        "REPORTE Desde:&nbsp;<input type='text' size='10' maxlength='10' name='fechaEstCuen' id='fechaEstCuen' value='"+getNowDate()+"' onclick=\"popUpCalendar(this, frmadmincuenta.fechaEstCuen , 'yyyy/mm/dd');\">"+
        "&nbsp;HASTA <input type='checkbox' name='miCheck' id='checarHasta' onclick='alertaCheckedHasta();'>"+
        "<input type='text' size='10' maxlength='10' name='hasta' id='hasta' value='"+getNowDate()+"' onclick=\"popUpCalendar(this, frmadmincuenta.hasta , 'yyyy/mm/dd');\" style='visibility:hidden;'>"+
        "</td>"+
        "</tr>"+
        "<table>"+
        "</fieldset>";
    }else if(tipo=="CUENTA AHORRO") {
        document.getElementById("divcuenta").innerHTML="<fieldset id='fielCuen' style='border-width:3px'>"+
        "<legend id='legcuenta'><b>"+tipo+(estado=="BLOQUEADO"?"LA CUENTA ESTÁ BLOQUEADA":"")+"</b></legend>"+
        "<table id='tablecuenta'>"+
        "<tr>"+
        "<td style='font-weight:bold' colspan='2'>&nbsp;</td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Numero Cuenta:</b></font></td>"+
        "<td><input type='text' id='txtNumCuentaDep' name='txtNumCuentaDep' value='"+numcuenta+"' disabled style='font-weight:bold;color:black'/></td>"+
        "</tr>"+
        "<tr>"+
        "<td  id='tdfechadep'><font color='#385b88' style='font-size: 12px;'><b>Fecha de Creaci&oacute;n:</b></font></td>"+
        "<td><input type='text' id='txtFechaDeposito' name='txtFechaDeposito' value='"+fechacuenta+"' disabled/></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Saldo Total:</b></font></td>"+
        "<td><font color='#E08934' style='font-size: 20px;'><b>"+mon+"</b></font><input type='text' id='txtImporteDep' name='txtImporteDep' value='"+saldo+"' disabled/>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Saldo Disponible:</b></font></td>"+
        "<td><font color='#E08934' style='font-size: 20px;'><b>"+mon+"</b></font><input type='text' id='txtImporteDep1' name='txtImporteDep1' value='"+saldo1+"' disabled/></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>interes:</b></font></td>"+
        "<td><input type='text' id='txtInteres' name='txtInteres' value='"+miinteres+"' onkeypress='return solodecimales(event);' style='font-weight:bold;color:black;width: 60px;'/><font color='#385b88' style='font-size: 12px;'><b>%</b></font></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>estado:</b></font></td>"+
        "<td><select id='selestado' name='selestado' disabled><option "+cadena1+">ACTIVO</option><option "+cadena2+">INACTIVO</option><option "+cadena3+">SOBREGIRO</option></select></td>"+
        "</tr>"+
        "<tr>"+
        "<td><font color='#385b88' style='font-size: 12px;'><b>Observaciones</b></font></td>"+
        "<td><textarea id='txtaObservacion' style='height:36px;width:504px' name='txtaObservacion' rows='4' cols='20' mmaxlength='500'>"+observaciones+"</textarea></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Tipo Cuenta</td>"+
        "<td><input type='text' id='txTipoCuenta' name='txTipoCuenta' value='"+tipo+"' /></td>"+
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
        "<td><font color='#385b88' style='font-size: 12px;'><b>nombreMoneda:</b></font></td>"+
        "<td><input type='text' id='txtNombreMoneda' name='txtNombreMoneda' value='"+nombreMoneda+"' style='font-weight:bold;color:black;width: 60px;' /><font color='#385b88' style='font-size: 12px;'><b>%</b></font></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id CuentaPersona</td>"+
        "<td><input type='text' id='txidCuentaPersona' name='txidCuentaPersona' value='"+IdCuenPersona+"'/></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id CuentaPersona</td>"+
        "<td><input type='text' id='txNombreTitular' name='txNombreTitular' value='"+nombreTitular+"'/></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id CuentaPersona</td>"+
        "<td><input type='text' id='txApellidoTitular' name='txApellidoTitular' value='"+apellidosTitular+"' /></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id CuentaPersona</td>"+
        "<td><input type='text' id='txDNITitular' name='txDNITitular' value='"+dniTitular+"' /></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td>Id CuentaPersona</td>"+
        "<td><input type='text' id='txRUCTitular' name='txRUCTitular' value='"+rucTitular+"' /></td>"+
        "</tr>"+
        "<tr style='display:none'>"+
        "<td ><font color='#385b88' style='font-size: 12px;'><b>Numero Cuenta:</b></font></td>"+
        "<td><input type='text' id='txtNumCuentaDepo' name='txtNumCuentaDepo' value='"+numcuenta+"' style='font-weight:bold;color:black'/></td>"+
        "</tr>"+
        "</table>"+
        "<table><tr>"+
        "<td><input type='button' name='cmdActualizar' id='cmdActualizar' value='Actualizar' onclick='actualizarCuenta(1);'>"+
        "<input type='button' name='cmdEliminar' id='cmdEliminar' value='Eliminar Cuenta' onclick='actualizarCuenta(2);'>"+
        "<input type='button' name='cmdBloquear' id='cmdBloquear' value='"+(estado=="BLOQUEADO"?"Desbloquear":"Bloquear")+" Cuenta' onclick='actualizarCuenta(3);'>"+
        "<input type='button' name='cmdImprimir' id='cmdImprimir' value='Imprimir Ticket' onclick='ImprimirTicket();'>"+
        "<input type='button' name='cmdEstadoCuenta' id='cmdEstadoCuenta' value='Estado de Cuenta' onclick='activarcuentaestado("+indice+")'>"+"</td>"+
        "</tr><tr><td>"+
        "REPORTE Desde:&nbsp;<input type='text' size='10' maxlength='10' name='fechaEstCuen' id='fechaEstCuen' value='"+getNowDate()+"' onclick=\"popUpCalendar(this, frmadmincuenta.fechaEstCuen , 'yyyy/mm/dd');\">"+
        "&nbsp;HASTA <input type='checkbox' name='miCheck' id='checarHasta' onclick='alertaCheckedHasta();'>"+
        "<input type='text' size='10' maxlength='10' name='hasta' id='hasta' value='"+getNowDate()+"' onclick=\"popUpCalendar(this, frmadmincuenta.hasta , 'yyyy/mm/dd');\" style='visibility:hidden;'>"+
        "</td>"+
        "</tr>"+
        "<table>"+
        "</fieldset>";
    }
}

var da1,da2;
var req4;
function vercuentaa(){
    document.getElementById("divcuenta").innerHTML="";
    document.getElementById("txtTempSelTipB").value=document.getElementById("selTipoBus").value;
    document.getElementById("txtTempdataB").value=document.getElementById("txtBuscar").value;
    document.getElementById("divcuenta").innerHTML="";
    var contenido, tipoBusqueda="", subcadena="",par;
    contenido=document.getElementById("txtBuscar").value;
    tipoBusqueda=document.getElementById("selTipoBus").value;
    if(tipoBusqueda=='0'){
        window.alert("Seleccione un tipo de busqueda");
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
        var PATH =  "/financeBank";
        var url = PATH + "/SDepositoRetiro2?a="+contenido+"&b="+tipoBusqueda;
        req4.open("GET",url,true);
        req4.send(null);
    }
}

function mostrarcuentadetalle(ind,ind2) {
    document.getElementById("txtind").value=ind2;
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
            document.getElementById('tableMiscuentas').style.overflow="auto";
            document.getElementById('tableMiscuentas').style.height="300px";
        }else{
            document.getElementById('tableMiscuentas').style.overflow="";
            document.getElementById('tableMiscuentas').style.height="";
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
            document.getElementById('divlistPerCuentas').style.overflow="auto";
            document.getElementById('divlistPerCuentas').style.height="320px";
        }else{
            document.getElementById('divlistPerCuentas').style.overflow="";
            document.getElementById('divlistPerCuentas').style.height="";
        }
    }
}

function validartxtBusquedaa(){
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
    }else{
        document.getElementById('txtestados').value="0";
        document.getElementById("divtxtBuscar").innerHTML=" <input id='txtBuscar' type='text' name='txtBuscar' value='' onkeypress='activeSpecialKey(event);' />";
    }
    document.getElementById("divlistPerCuentas").innerHTML="";
    document.getElementById("divlistPerCuentas").style.height="0px";
    document.getElementById("tableMiscuentas").innerHTML="";
    document.getElementById("divcuenta").innerHTML="";
    document.getElementById("txtBuscar").focus();
}
            
function ImprimirTicket(){
    document.frmadmincuenta.action="reportecuenta.htm";
    document.frmadmincuenta.submit();
}

