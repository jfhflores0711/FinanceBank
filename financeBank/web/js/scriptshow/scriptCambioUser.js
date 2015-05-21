/**
 *
 * @author admin
 */

var MonedaINI="";
var MonedaFIN="";
function compraVenta(){
    document.getElementById('monto1').disabled=false;
    document.getElementById('monto2').disabled=false;
    var j;
    for (j=0;j<document.fcambio.cambio.length;j++){
        if (document.fcambio.cambio[j].checked)
            break;
    }
    ddeterioro='0.0';
    document.getElementById('descuento_deterioro').value='0.0';
    document.getElementById('descuento_deterioro').disabled=false;
    if(j==2){
        window.alert("Seleccione el tipo de OPERACIÓN!!!");
        return;
    }
    var opcion = document.fcambio.cambio[j].value;
    var moneda = document.fcambio.moneda[document.fcambio.moneda.selectedIndex].value;
    var simbolo = document.fcambio.moneda[document.fcambio.moneda.selectedIndex].label;
    if(opcion=='COMPRA'){
        MonedaINI=moneda;
        MonedaFIN="PEN";
        document.getElementById('tipo1').innerHTML="<img src='images/cobrar.png' width='86' height='47' alt='cobrar'/> <br> COBRO";
        document.getElementById('tipo2').innerHTML="<img src='images/pagar.png' width='86' height='47' alt='cobrar'/> <br>PAGO";
    }else{
        MonedaINI="PEN";
        MonedaFIN=moneda;
        document.getElementById('tipo1').innerHTML="<img src='images/pagar.png' width='86' height='47' alt='cobrar'/> <br>PAGO";
        document.getElementById('tipo2').innerHTML="<img src='images/cobrar.png' width='86' height='47' alt='cobrar'/> <br> COBRO";
    }
    document.getElementById('SinMonedaFIN').innerHTML="S/.";
    document.getElementById('SinMonedaINI').innerHTML=simbolo;
    document.getElementById('monto1button').label=moneda;
    document.getElementById('monto2button').label="PEN";
    buscarTasa(MonedaINI,MonedaFIN)
}

function buscarTasa(mIni,mFin) {
    var especial=document.fcambio.tasaEspecial.checked
    req2 = null;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2!=null){
        var PATH = "/financeBank";
        var url = PATH + "/SBuscarTasa?mIni="+mIni+"&mFin="+mFin+"&especial="+especial;
        req2.open("GET",url,true);
        req2.onreadystatechange = procesar;
        req2.send(null);
    }
}

function procesar(){
    contenido = document.getElementById('factor');
    contenido.innerHTML="Cargando los datos...";
    if (req2.readyState==4 && req2.status==200) {
        contenido.innerHTML = req2.responseText;
        document.getElementById('Tasa_Especial').value=req2.responseText;
        calculo();
    }
}

function calculo(){
    var monto1 =document.getElementById('monto1').value;
    monto1 = monto1.replace(/,/gi, "");
    if(ddeterioro.length==0){
        ddeterioro="0.0";
    }
    var ddet = ddeterioro;
    ddet = ddet.replace(/,/gi, "");
    var factor = document.getElementById('factor').innerHTML;
    if(document.fcambio.tasaEspecial.checked){
        factor = document.getElementById('Tasa_Especial').value;
    }
    var j;
    for (j=0;j<document.fcambio.cambio.length;j++){
        if (document.fcambio.cambio[j].checked)
            break;
    }
    if(j==2){
        window.alert("Seleccione el tipo de OPERACIÓN!!!");
        return;
    }
    var opcion = document.fcambio.cambio[j].value;
    if(opcion=='COMPRA'){
        var res = parseFloat(monto1) * parseFloat(factor);
        res= parseFloat(res)-parseFloat(ddet);
        res=res.toFixed(2);
        var Sres =""+res;
        this.document.getElementById('monto2').value=currencyFormat2(Sres,',','.');
    }else{
        var res = parseFloat(monto1) * parseFloat(factor);
        res=res.toFixed(2);
        var Sres =""+res;
        this.document.getElementById('monto2').value=currencyFormat2(Sres,',','.');
    }
}

function calculo2(){
    var monto1 =document.getElementById('monto2').value;
    monto1 = monto1.replace(/,/gi, "");
    if(ddeterioro.length==0){
        ddeterioro="0.0";
    }
    var ddet = ddeterioro;
    ddet = ddet.replace(/,/gi, "");
    var factor =document.getElementById('factor').innerHTML;
    if(document.fcambio.tasaEspecial.checked){
        factor =document.getElementById('Tasa_Especial').value;
    }
    var j;
    for (j=0;j<document.fcambio.cambio.length;j++){
        if (document.fcambio.cambio[j].checked)
            break;
    }
    if(j==2){
        window.alert("Seleccione el tipo de OPERACIÓN!!!");
        return;
    }
    var opcion = document.fcambio.cambio[j].value;
    if(opcion=='COMPRA'){
        var res =((parseFloat (monto1)+parseFloat (ddet))/parseFloat (factor));
        res=res.toFixed(2);
        var Sres =""+res;
        this.document.getElementById('monto1').value=currencyFormat2(Sres,',','.');
    }else{
        var res =parseFloat (monto1)/parseFloat (factor);
        res=res.toFixed(2);
        var Sres =""+res;
        this.document.getElementById('monto1').value=currencyFormat2(Sres,',','.');
    }
}

var Aut = false;
function ventanaNueva() {
    document.getElementById('Tasa_Especial').disabled=true;
    if(!document.fcambio.tasaEspecial.checked){
        return;
    }
    document.fcambio.tasaEspecial.checked=false;
    var j;
    for (j=0;j<document.fcambio.cambio.length;j++){
        if (document.fcambio.cambio[j].checked)
            break;
    }
    if(j==2){
        window.alert("Seleccione el tipo de OPERACIÓN!!!");
        return;
    }
    document.getElementById("monto1").value='0.00';
    document.getElementById("monto2").value='0.00';
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
            Autorizar(user,pass);
            window.alert('¡¡ DATOS ENVIADOS !!');
            if(document.getElementById('res').innerHTML==1){
                Aut = true;
                document.getElementById("monto1").value='0.00';
                document.getElementById("monto2").value='0.00';
                document.getElementById('Tasa_Especial').disabled='';
                document.fcambio.tasaEspecial.checked=true;
                return true;
            }else{
                document.fcambio.tasaEspecial.checked=false;
                $('login_error_msg').innerHTML='Usuario o Contrase&ntilde;a incorrecto';
                $('login_error_msg').show();
                Windows.focusedWindow.updateHeight();
                new Effect.Shake(Windows.focusedWindow.getId());
                document.getElementById('Tasa_Especial').disabled=true;
                return false;
            }
        }
    });
}

function Autorizar(login,contrasenia) {
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
        req2.onreadystatechange = procesar1;
        req2.send(null);
    }
}

function procesar1(){
    contenido = document.getElementById('res');
    contenido.innerHTML="Cargando los datos...";
    if (req2.readyState==4 && req2.status==200) {
        contenido.innerHTML = req2.responseText;
    }
}

function RegistrarCambio() {
    var j;
    for (j=0;j<document.fcambio.cambio.length;j++){
        if (document.fcambio.cambio[j].checked)
            break;
    }
    if(j==2){
        window.alert("Aún no se puede registrar la OPERACIÓN!!!");
        return;
    }
    var opcion = document.fcambio.cambio[j].value;
    var cod_moneda = document.fcambio.moneda[document.fcambio.moneda.selectedIndex].value;
    var tasa = document.getElementById('Tasa_Especial').value;
    var recibido;
    var entregado;
    var tipo;
    if(opcion=='COMPRA'){
        recibido=document.getElementById('monto1').value;
        recibido = recibido.replace(/,/gi, "");
        entregado=document.getElementById('monto2').value;
        entregado = entregado.replace(/,/gi, "");
        tipo='COMPRA';
    }else{
        recibido=document.getElementById('monto2').value;
        recibido = recibido.replace(/,/gi, "")
        entregado=document.getElementById('monto1').value;
        entregado = entregado.replace(/,/gi, "")
        tipo='VENTA';
    }
    if(recibido=='0.00'||entregado==""){
        window.alert("No se puede efectuar esta operacion");
        return;
    }
    var dat="";
    if(document.getElementById('pe').value=='j'){
        if(document.getElementById('razonSocial').value.length>0){
            dat="R.S.: "+filtraParametroRequestC(document.getElementById('razonSocial').value) + " RUC:"+document.getElementById('txtRazonSocial').value;
        }
    }else{
        if(document.getElementById('nom').value.length>0){
            dat="Nombre: "+document.getElementById('nom').value + " DNI:"+document.getElementById('doc').value;
        }
    }
    var answer = window.confirm ("¿ESTA SEGURO REALIZAR ESTA OPERACION ?")
    if (answer){
        req2 = null;
        if (window.XMLHttpRequest) {
            req2 = new XMLHttpRequest();
        }else if (window.ActiveXObject) {
            req2 = new ActiveXObject("Microsoft.XMLHTTP");
        }
        if(req2!=null){
            window.location="SRegistrarCambio?recibido="+recibido+
            "&entregado="+entregado+
            "&tipo="+tipo+
            "&cod_moneda="+cod_moneda+
            "&ddeterioro="+ddeterioro+
            "&razonSocial="+dat+
            "&tasa="+tasa;
            disable();
        }
    }
}

var NumeroMonto;
function sumarCalcular(codmoneda,n){
    NumeroMonto = n;
    req2 = false;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2!=null){
        var PATH = "/financeBank";
        var url = PATH + "/SSumarCalcular?codmoneda="+codmoneda;
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
    }
}

function cerrar(){
    document.getElementById('div_calcular').innerHTML ='';
}

function cal(n){
    var monto= document.getElementById('montoMoneda'+n).value;
    monto = monto.replace(/,/gi, "");
    var cantidad= document.getElementById('Cantidad'+n).value;
    cantidad = cantidad.replace(/,/gi, "");
    var total =parseFloat (monto)*parseFloat (cantidad);
    total=total.toFixed(2);
    var Sres =""+total;
    this.document.getElementById('totalDinero'+n).value=currencyFormat2(Sres,',','.');
    if(document.getElementById('totalDinero'+n).value==''){
        document.getElementById('totalDinero'+n).value='0.00'
    }
    return total;
}

function calTotal(){
    var limit = document.getElementById('sumaTotal').name;
    var SumaTotal=0;
    for(var i=0;i<limit;i++){
        SumaTotal=parseFloat(cal(i)) + parseFloat(SumaTotal);
    }
    SumaTotal=SumaTotal.toFixed(2);
    var Sres =""+SumaTotal;
    this.document.getElementById('sumaTotal').value=currencyFormat2(Sres,',','.');
    this.document.getElementById('monto'+NumeroMonto).value=currencyFormat2(Sres,',','.');
    if(NumeroMonto==1){
        calculo();
    }else{
        calculo2();
    }
}

function extornar(idReg,t){
    Dialog.confirm($('login').innerHTML,
    {
        className:"alphacube",
        width:400,
        okLabel: "Login",
        title: "Autorizar Usuario",
        cancelLabel: "Cancelar",
        onOk:function(){
            var user =document.getElementById('user').value
            var pass =document.getElementById('passwd').value
            document.getElementById('res').innerHTML='';
            Autorizar(user,pass);
            window.alert('¡¡ DATOS ENVIADOS !!');
            if(document.getElementById('res').innerHTML==1){
                Aut = true;
                sExtornar(idReg,t);
                window.alert('¡¡SE REALIZO EL EXTORNO CON EXITO!!');
                document.fcambio.submit();
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

function sExtornar(id,tipo) {
    req2 = null;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2!=null){
        var PATH = "/financeBank";
        var url = PATH + "/SExtornoOp?id="+id+"&tipo="+tipo;
        req2.open("GET",url,true);
        req2.send(null);
    }
}

function grabar(){
    var limit = document.getElementById('sumaTotal').name;
    var cadena="";
    for(var i=0;i<limit;i++){
        var idDenominacion= document.getElementById('montoMoneda'+i).name;
        var cantidad= document.getElementById('Cantidad'+i).value;
        cadena=cadena+idDenominacion+"="+cantidad+" ";
    }
    req2 = null;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
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

var ddeterioro='0.0';
function f_deterioro(d){
    ddeterioro=d;
    calculo();
}

function disable(){
    document.getElementById('monto1').disabled=true;
    document.getElementById('monto2').disabled=true;
}

function f_razonSocial(){
    if(document.getElementById('pe').value=='j'){
        document.getElementById('ju').style.display='block';
        document.getElementById('na').style.display='none';
    }else{
        document.getElementById('ju').style.display='none';
        document.getElementById('na').style.display='block';
    }
}


