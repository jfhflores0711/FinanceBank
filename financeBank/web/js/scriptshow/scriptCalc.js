/**
 *
 * @author oscar
 */

var MonedaINI="PEN";

function sumarCalcularON(m){
    var limit = document.getElementById('totalB').value;
    var cadena="";
    for(var i=0;i<limit;i++){
        var idDenominacion= document.getElementById('montoMonedaB'+i).title;
        var cantidad= document.getElementById('CantidadB'+i).value;
        cadena=cadena+idDenominacion+"="+cantidad.replace(",", "")+" ";
    }
    req2 = null;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2!=null){
        var PATH = "/financeBank";
        var url = PATH + "/SSumarCalcular?cadena="+cadena+"&m="+m;
        req2.open("GET",url,true);
        req2.onreadystatechange = procesar2;
        req2.send(null);
    }
}

function procesar2(){
    contenido = document.getElementById('div_calcular');
    contenido.innerHTML="Cargando los datos...";
    contenido.innerHTML="";
    if (req2.readyState==4 && req2.status==200) {
        contenido.innerHTML = req2.responseText;
        if(contenido.innerHTML=='ok'){
            document.fcambio.submit();
        }else{
            window.alert(contenido.innerHTML);
        }

    }
}

function cerrar(){
    document.getElementById('div_calcular').innerHTML ='';
}

function calB(n){
    if(parseFloat(document.getElementById('sumaTotalB').value)==0.00){
        window.alert("El Monto total es cero");
        return 0;
    }
    var monto= document.getElementById('montoMonedaB'+n).innerHTML;
    monto = monto.replace(/,/gi, "");
    var cantidad= document.getElementById('CantidadB'+n).value;
    cantidad = cantidad.replace(/,/gi, "");
    if(cantidad=='')
        cantidad='0.00';
    var total =parseFloat (monto)*parseFloat (cantidad);
    total=total.toFixed(2);
    var Sres =""+total;
    this.document.getElementById('totalDineroB'+n).innerHTML=currencyFormat2(Sres,',','.');
    if(document.getElementById('totalDineroB'+n).innerHTML==''){
        document.getElementById('totalDineroB'+n).innerHTML='0.00'
    }
    return total;
}
function calTotalB(){
    var limit = document.getElementById('totalB').value;
    var SumaTotal=0;
    for(var i=0;i<limit;i++){
        SumaTotal=parseFloat(calB(i)) + parseFloat(SumaTotal);
    }
    SumaTotal=SumaTotal.toFixed(2);
    var Sres =""+SumaTotal;
    this.document.getElementById('sumaTotalB').value=currencyFormat2(Sres,',','.');
}
function recorrerValidos(){
    var limit = document.getElementById('total').value;
    var message="Monto \t\t Cantidad \t\t Total";
    for(var i=0;i<limit;i++){
        var monto= document.getElementById('montoMoneda'+i).innerHTML;
        monto = monto.replace(/,/gi, "");
        var cantidad= document.getElementById('Cantidad'+i).innerHTML;
        cantidad = cantidad.replace(/,/gi, "");
        if(parseFloat(cantidad)>0){
            var total=parseFloat(monto)* parseFloat(cantidad);
            total=total.toFixed(2);
            message = message +" <br/> " + monto + " \t x \t " + cantidad + " \t = \t " + total;
        }
    }
    return message;
}

var WindowObjectReference = null; // global variable

function printM(){
    if(parseFloat(document.getElementById('sumaTotal').value)==0.00){
        window.alert("El Monto total es cero");
        return;
    }
    if(WindowObjectReference == null || WindowObjectReference.closed) {
        var m=recorrerValidos();
        WindowObjectReference = window.open("printblank.jsp?m="+m,
            "print","menubar=no,location=no,resizable=no,scrollbars=no,status=no,height=400,width=300");
    } else {
        WindowObjectReference.focus();
    }
}

function cal(n){
    var monto= document.getElementById('montoMoneda'+n).innerHTML;
    monto = monto.replace(/,/gi, "");
    var cantidad= document.getElementById('Cantidad'+n).innerHTML;
    cantidad = cantidad.replace(/,/gi, "");
    if(monto=='')
        monto='0.00';
    var total =parseFloat(monto)* parseFloat(cantidad);
    total=total.toFixed(2);
    return total;
}

function calTotal(n){
    var monto= document.getElementById('montoMoneda'+n).innerHTML;
    monto = monto.replace(/,/gi, "");
    var cantidad= document.getElementById('totalDinero'+n).value;
    cantidad = cantidad.replace(/,/gi, "");
    var limit = document.getElementById('total').value;
    if((parseFloat(cantidad)*100)%(parseFloat(monto)*100)>0){
        window.alert("EL monto ingresado no es multiplo de "+monto);
        document.getElementById('totalDinero'+n).value='0.00';
        document.getElementById('totalDinero'+n).focus();
        return;
    }
    var total =parseFloat(cantidad)/parseFloat(monto);
    this.document.getElementById('Cantidad'+n).innerHTML=""+total.toFixed(0);
    if(document.getElementById('Cantidad'+n).innerHTML=='NaN'){
        document.getElementById('Cantidad'+n).innerHTML='0'
    }
    var SumaTotal=0;
    for(var i=0;i<limit;i++){
        SumaTotal=parseFloat(cal(i)) + parseFloat(SumaTotal);
    }
    SumaTotal=SumaTotal.toFixed(2);
    var Sres =""+SumaTotal;
    this.document.getElementById('sumaTotal').value=currencyFormat2(Sres,',','.');
}

function grabar(){
    if(parseFloat(document.getElementById('sumaTotal').value)==0.00){
        window.alert("El Monto total es cero");
        return;
    }
    var limit = document.getElementById('total').value;
    var cadena="";
    for(var i=0;i<limit;i++){
        var idDenominacion= document.getElementById('montoMoneda'+i).title;
        var cantidad= document.getElementById('Cantidad'+i).innerHTML;
        cadena=cadena+idDenominacion+"="+cantidad.replace(",", "")+" ";
    }
    req2 = null;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2!=null){
        var PATH = "/financeBank";
        var url = PATH + "/SGrabarSuma?cadena="+cadena;
        req2.open("GET",url,true);
        req2.onreadystatechange = proc;
        req2.send(null);
    }
}

function proc(){
    contenido = document.getElementById('div_sumas');
    contenido.innerHTML="Cargando los datos...";
    contenido.innerHTML="";
    if (req2.readyState==4 && req2.status==200) {
        contenido.innerHTML = req2.responseText;
        document.fcambio.submit();
    }
}

function RecargarSumarCalcular(idSuma){
    req2 = null;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2!=null){
        var PATH = "/financeBank";
        var url = PATH + "/SRecargarSuma?idSuma="+idSuma;
        req2.open("GET",url,true);
        req2.onreadystatechange = RecargarProcesar;
        req2.send(null);
    }
}

function RecargarProcesar(){
    contenido = document.getElementById('div_calcular');
    contenido.innerHTML="Cargando los datos...";
    contenido.innerHTML="";
    if (req2.readyState==4 && req2.status==200) {
        contenido.innerHTML = req2.responseText;
    }
}

