/**
 *
 * @author admin
 */

var req2,req2r;

function rechargeInput(){
    var seltipo=document.getElementById("busca")[document.getElementById("busca").selectedIndex].value;
    if(seltipo=="MONTOX1"){
        document.getElementById("stylist").innerHTML="<table border='0' cellpadding='5' cellspacing='5' class='tabla' width='100%'>"+
        "<tr>"+
        "<td align='right'><font color='#385B88' style='font-size:12px'><b>INGRESE EL DATO A BUSCAR:</b</font></td>"+
        "<td style='font-size:20px;color:#000000;'><input id='inn' type='text' style='font-size:20px;text-align:right;' name='inn' value='' />"+
        "</td></tr></table>";
        document.getElementById('BUSQUEDAS').innerHTML="";
        document.getElementById("divshowre").innerHTML="<fieldset><legend style='font-size:9px'><b>DATOS DE LA OPERACION</b></legend>"+
    "<table border='0' cellpadding='5' cellspacing='5' id='h1' class='tabla' width='100%'><tr><td valign='top'>"+
    "NO RESULTS.</td></tr></table></fieldset>";
    }else if(seltipo=='MONTOX2'){
        document.getElementById("stylist").innerHTML="<table border='0' cellpadding='5' cellspacing='5' class='tabla' width='100%'>"+
        "<tr>"+
        "<td align='right'><font color='#385B88' style='font-size:12px'><b>INGRESE LA REFERENCIA:</b</font></td>"+
        "<td style='font-size:20px;color:#000000;'><input id='inn' type='text' style='font-size:20px;text-align:left;' name='inn' value='' />"+
        "</td></tr></table>";
        document.getElementById('BUSQUEDAS').innerHTML="";
        document.getElementById("divshowre").innerHTML="<fieldset><legend style='font-size:9px'><b>DATOS DE LA OPERACION</b></legend>"+
    "<table border='0' cellpadding='5' cellspacing='5' id='h1' class='tabla' width='100%'><tr><td valign='top'>"+
    "NO RESULTS.</td></tr></table></fieldset>";
    }else if(seltipo=='MONTOX3'){
        document.getElementById("stylist").innerHTML="<table border='0' cellpadding='5' cellspacing='5' class='tabla' width='100%'>"+
        "<tr>"+
        "<td align='right'><font color='#385B88' style='font-size:12px'><b>INGRESE EL NÚMERO DE CUENTA:</b</font></td>"+
        "<td style='font-size:20px;color:#000000;'><input id='inn' type='text' style='font-size:20px;text-align:left;' onkeypress='return solonumeros(event);' name='inn' value='' />"+
        "</td></tr></table>";
        document.getElementById('BUSQUEDAS').innerHTML="";
        document.getElementById("divshowre").innerHTML="<fieldset><legend style='font-size:9px'><b>DATOS DE LA OPERACION</b></legend>"+
    "<table border='0' cellpadding='5' cellspacing='5' id='h1' class='tabla' width='100%'><tr><td valign='top'>"+
    "NO RESULTS.</td></tr></table></fieldset>";
    }else if(seltipo=='MONTOX4'){
        document.getElementById("stylist").innerHTML="<table border='0' cellpadding='5' cellspacing='5' class='tabla' width='100%'>"+
        "<tr>"+
        "<td align='right'><font color='#385B88' style='font-size:12px'><b>INGRESE LA HORA DEL DÍA (00-24):</b</font></td>"+
        "<td style='font-size:20px;color:#000000;'><input id='inn' type='text' style='font-size:20px;text-align:left;' maxlength='2' onkeypress='return solonumeros(event);' onblur='return horax();' name='inn' value='' />"+
        "</td></tr></table>";
        document.getElementById('BUSQUEDAS').innerHTML="";
        document.getElementById("divshowre").innerHTML="<fieldset><legend style='font-size:9px'><b>DATOS DE LA OPERACION</b></legend>"+
    "<table border='0' cellpadding='5' cellspacing='5' id='h1' class='tabla' width='100%'><tr><td valign='top'>"+
    "NO RESULTS.</td></tr></table></fieldset>";
    }else if(seltipo=='MONTOX5'){
        document.getElementById("stylist").innerHTML="<table border='0' cellpadding='5' cellspacing='5' class='tabla' width='100%'>"+
        "<tr>"+
        "<td align='right'><font color='#385B88' style='font-size:12px'><b>SELECCIONE (SOLO HOY):</b</font></td>"+
        "<td style='font-size:20px;color:#000000;'><select id='inn' style='font-size:20px;text-align:left;' name='inn' >"+
        "<option label='COMPRA DE MONEDA' value='TIPC1'>COMPRA DE MONEDA</option>"+
        "<option label='VENTA DE MONEDA' value='TIPC2'>VENTA DE MONEDA</option>"+
        "<option label='GIRO DE DINERO' value='TIPC5'>GIRO DE DINERO</option>"+
        "<option label='PRESTAMO A CLIENTE' value='TIPC6'>PRESTAMO A CLIENTE</option>"+
        "<option label='PAGO DE PRESTAMO' value='TIPC7'>PAGO DE PRESTAMO</option>"+
        "<option label='DEPOSITO A CUENTA' value='TIPC3'>DEPOSITO A CUENTA</option>"+
        "<option label='RETIRO DE CUENTA' value='TIPC4'>RETIRO DE CUENTA</option>"+
        "<option label='COBRO DE GIRO' value='TIPC8'>COBRO DE GIRO</option>"+
        "<option label='TRANSFERENCIA INT.' value='TIPC9'>TRANSFERENCIA INT.</option>"+
        "<option label='RETIRO OTROS' value='TIPC10'>RETIRO OTROS</option>"+
        "<option label='INTERES PAGADO' value='TIPC11'>INTERES PAGADO</option>"+
        "<option label='RESERVADOS' value='TIPC12'>RESERVADOS</option>"+
        "<option label='AUMENTO DE CAPITAL' value='TIPC13'>AUMENTO DE CAPITAL</option>"+
        "<option label='TRANSF. ENTRE FILIALES' value='TIPC14'>TRANSF. ENTRE FILIALES</option>"+"<select/>"+
        "</td></tr></table>";
        document.getElementById('BUSQUEDAS').innerHTML="";
        document.getElementById("divshowre").innerHTML="<fieldset><legend style='font-size:9px'><b>DATOS DE LA OPERACION</b></legend>"+
    "<table border='0' cellpadding='5' cellspacing='5' id='h1' class='tabla' width='100%'><tr><td valign='top'>"+
    "NO RESULTS.</td></tr></table></fieldset>";
    }else if(seltipo=='MONTOX6'){
        document.getElementById("stylist").innerHTML="<table border='0' cellpadding='5' cellspacing='5' class='tabla' width='100%'>"+
        "<tr>"+
        "<td align='right'><font color='#385B88' style='font-size:12px'><b>INGRESE EL MONTO:</b</font></td>"+
        "<td style='font-size:20px;color:#000000;'><input id='inn' type='text' style='font-size:20px;text-align:right;' onkeypress=\"return(currencyFormat(this,',','.',event))\" name='inn' value='' />"+
        "</td></tr></table>";
        document.getElementById('BUSQUEDAS').innerHTML="";
        document.getElementById("divshowre").innerHTML="<fieldset><legend style='font-size:9px'><b>DATOS DE LA OPERACION</b></legend>"+
    "<table border='0' cellpadding='5' cellspacing='5' id='h1' class='tabla' width='100%'><tr><td valign='top'>"+
    "NO RESULTS.</td></tr></table></fieldset>";
    }else{
        document.getElementById("stylist").innerHTML="";
        document.getElementById('BUSQUEDAS').innerHTML="";
        document.getElementById("divshowre").innerHTML="<fieldset><legend style='font-size:9px'><b>DATOS DE LA OPERACION</b></legend>"+
    "<table border='0' cellpadding='5' cellspacing='5' id='h1' class='tabla' width='100%'><tr><td valign='top'>"+
    "NO RESULTS.</td></tr></table></fieldset>";
    }
}

function horax(){
    var d=document.getElementById('inn').value;
    var f=parseInt(d, 10);
    if(f>24.0){
        window.alert("Ingrese número en 00 y 24.");
        document.getElementById("inn").focus();
        return false;
    }
    return true;
}

function busqueda(type){
    var seltipo=document.getElementById("busca")[document.getElementById("busca").selectedIndex].value;
    var data=seltipo+";"+document.getElementById("inn").value.replace(';','').replace("&", ":");
    if(type=='hard'){
        data +=";"+document.getElementById("desde").value+";"+document.getElementById("desde").value;
    }
    if(document.getElementById("WITHMORE").checked){
        if(document.getElementById("more1").checked){
            data +=";n";
        }else{
            data +=";e";
        }
    }else{
        data +=";n";
    }
    var answer = document.getElementById("inn").value.replace(';','');
    if (answer!=''){
        req2r = null;
        if (window.XMLHttpRequest) {
            req2r = new XMLHttpRequest();
        }else if (window.ActiveXObject) {
            req2r = new ActiveXObject("Microsoft.XMLHTTP");
        }
        if(req2r!=null){
            req2r.onreadystatechange=processBusqueda;
            var PATH = "/financeBank";
            var url = PATH +"/SListarDistrito?type="+type+"&data="+data;
            req2r.open("GET",url,true);
            req2r.send(null);
        }
    }else{
        window.alert('Ingrese el dato a buscar...');
    }
    document.getElementById("divshowre").innerHTML="<fieldset>"+
"<legend style='font-size:9px'><b>DATOS DE LA OPERACION</b></legend>"+
"<table border='0' cellpadding='5' cellspacing='5' id='h1' class='tabla' width='100%'>"+
"<tr>"+
"<td valign='top'>"+
"NO RESULTS."+
"</td>"+
"</tr>"+
"</table>"+
"</fieldset>";
}

function processBusqueda(){
    contenido = document.getElementById('BUSQUEDAS');
    contenido.innerHTML="Cargando los datos...";
    contenido.innerHTML="";
    if (req2r.readyState==4 && req2r.status==200) {
        contenido.innerHTML = req2r.responseText;
    }
}

function mostrarOp(id){
    if (id!=''){
        req2 = null;
        if (window.XMLHttpRequest) {
            req2 = new XMLHttpRequest();
        }else if (window.ActiveXObject) {
            req2 = new ActiveXObject("Microsoft.XMLHTTP");
        }
        if(req2!=null){
            req2.onreadystatechange=mostrarOpProcess;
            var PATH = "/financeBank";
            var url = PATH +"/SMostrarOp?id="+id;
            req2.open("GET",url,true);
            req2.send(null);
        }
    }else{
        document.getElementById("divshowre").innerHTML="<fieldset>"+
    "<legend style='font-size:9px'><b>DATOS DE LA OPERACION</b></legend>"+
    "<table border='0' cellpadding='5' cellspacing='5' id='h1' class='tabla' width='100%'>"+
    "<tr>"+
    "<td valign='top'>"+
    "NO RESULTS."+
    "</td>"+
    "</tr>"+
    "</table>"+
    "</fieldset>";
    }
}
function mostrarOpProcess(){
    contenido = document.getElementById('divshowre');
    contenido.innerHTML="Cargando los datos...";
    contenido.innerHTML="";
    if (req2.readyState==4 && req2.status==200) {
        contenido.innerHTML = req2.responseText;
    }
}

function reimp(id){
    window.open("SRePrint?id="+id, "print", "width=900,height=600,menubar=yes,resizable=yes,scrollbars=yes,status=yes")
}

