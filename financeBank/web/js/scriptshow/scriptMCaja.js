/**
 *
 * @author admin
 */
function mTotalCajas(cod){
    var Stotal=0;
    for(var i=0;i<TotalCaja;i++){
        var monto=document.getElementById('monto'+cod+i).value;
        if(monto==''){
            monto='0.0'
        }else{
            monto = monto.replace(/,/gi, "");
        }
        var res =parseFloat (monto);
        if(!document.getElementById('btn_tranferir'+i).disabled){
            Stotal=Stotal+res;
        }
    }
    return Stotal;
}

function actSaldo(cod,num){
    var valor = mTotalCajas(cod);
    var valor1 =document.getElementById('mDisponible'+num).innerHTML;
    if(valor1==''){
        valor1='0.0';
    }else{
        valor1 = valor1.replace(/,/gi, "");
    }
    var res =parseFloat (valor1)-parseFloat (valor);
    if(res<0){
        window.alert('NO DISPONE DE DINERO SUFICIENTE')
        document.getElementById('montoMoneda'+num).value='0.00';
    }else{
        res=res.toFixed(2);
        var Sres =""+res;
        document.getElementById('saldo'+num).innerHTML=currencyFormat2(Sres,',','.');
    }
}

function aMoneda(cod,num){
    for(var i=0;i<TotalCaja;i++){
        this.document.getElementById('monto'+cod+i).value=document.getElementById('montoMoneda'+num).value;
    }
    var valor = mTotalCajas(cod);
    var valor1 = document.getElementById('mDisponible'+num).innerHTML;
    if(valor1==''){
        valor1='0.00';
    }else{
        valor1 = valor1.replace(/,/gi, "");
    }
    var res = parseFloat(valor1) - parseFloat (valor);
    if(res<0){
        window.alert('NO DISPONE DE DINERO SUFICIENTE');
        document.getElementById('montoMoneda'+num).value='0.00';
    }else{
        res=res.toFixed(2);
        var Sres =""+res;
        if(Sres=='0.00'){
            document.getElementById('saldo'+num).innerHTML=Sres;
        }
        document.getElementById('saldo'+num).innerHTML=currencyFormat2(Sres,',','.');
    }
}

var TotalCaja;
var TotalMoneda;
function numeroTcajas(n){
    this.TotalCaja=n;
    var ca=0;
    for(var h=0;h<n;h++){
        if(!document.getElementById('btn_tranferir'+h).disabled){
            ca++;
        }
    }
    return ca;
}

function numeroTmonedas(m){
    this.TotalMoneda=m;
    for(var w=0;w<m;w++){
        var mDisp = document.getElementById('mDisponible'+w).innerHTML
        var res =parseFloat (mDisp)
        if(res==Number.NaN){
            res=0;
        }
        res=res.toFixed(2);
        document.getElementById('mDisponible'+w).innerHTML=currencyFormat2(res,',','.');
        document.getElementById('saldo'+w).innerHTML=currencyFormat2(res,',','.');
    }
}

function abilitarTxt(idTxt){
    this.document.getElementById(idTxt).disabled=false;
}

var req2;
function Transferirx(n){
    var codCaja = document.getElementById('caja'+n).innerHTML;
    var codM ="";
    var codD ="";
    for(var i=0;i<TotalMoneda;i++){
        var cod =document.getElementById('montoMoneda'+i).name;
        var monto =document.getElementById('monto'+cod+n).value;
        monto = monto.replace(/,/gi, "");
        codM = document.getElementById('montoMoneda'+i).name+"="+monto+"wxw"+codM;
        var mDisp = document.getElementById('mDisponible'+i).innerHTML;
        if(mDisp==''){
            document.getElementById('mDisponible'+i).innerHTML='0.00';
            mDisp='0.00';
        }
        mDisp = mDisp.replace(/,/gi, "");
        var res = parseFloat (mDisp)-parseFloat (monto);
        if(res==Number.NaN){
            res=0;
        }
        codD = document.getElementById('montoMoneda'+i).name+"="+res+"wxw"+codD;
        res=res.toFixed(2);
        var Sres =""+res;
        document.getElementById('mDisponible'+i).innerHTML=currencyFormat2(Sres,',','.');
    }
    req2 = null;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2!=null){
        var PATH = "/financeBank";
        var url = PATH +"/SGrabarMonto"+
        "?codCaja="+codCaja+
        "&codM="+codM+
        "&codD="+codD;
        req2.open("GET",url,true);
        req2.send(null);
    }
    document.getElementById('btn_tranferir'+n).disabled=true;
    document.getElementById('marco'+n).style.display='none';
    for(var i=0;i<TotalMoneda;i++){
        cod =document.getElementById('montoMoneda'+i).name;
        var valor1 =document.getElementById('mDisponible'+i).innerHTML;
        valor1 = valor1.replace(/,/gi, "");
        var valor = mTotalCajas(cod);
        res =parseFloat (valor1)-parseFloat (valor);
        if(res==Number.NaN){
            res=0;
        }
        res=res.toFixed(2);
        Sres =""+res;
        document.getElementById('saldo'+i).innerHTML=currencyFormat2(Sres,',','.');
    }
}

function procesgot(){
    contenido = document.getElementById('divExtorno');
    contenido.innerHTML="Cargando los datos...";
    contenido.innerHTML="";
    if (req2.readyState==4 && req2.status==200) {
        contenido.innerHTML = req2.responseText;
        window.alert('¡¡SE REALIZO LA TRANSFERENCIA CON EXITO!!');
        window.location="SReciboTransferencia";
    }


}

function TransAll(){
    for(var j=0;j<TotalCaja;j++){
        if(!document.getElementById('btn_tranferir'+j).disabled){
            Transferirx(j)
        }
    }
}

function actualizarM(i){
    var valor1 =document.getElementById('montoActual'+i).innerHTML;
    if(valor1==''){
        valor1='0';
    }else{
        valor1 = valor1.replace(/,/gi, "");
    }
    var valor =document.getElementById('montoTranferir'+i).value;
    if(valor==''){
        valor='0';
    }else{
        valor = valor.replace(/,/gi, "");
    }
    var res =parseFloat (valor1)-parseFloat (valor);
    if(res==Number.NaN){
        res=0;
    }
    if(res<0){
        window.alert('NO DISPONE DE DINERO SUFICIENTE')
        document.getElementById('montoTranferir'+i).value='0.00'
    }else{
        res=res.toFixed(2);
        var Sres =""+res;
        if(Sres=="0.00"){
            document.getElementById('montoSaldo'+i).innerHTML=Sres;
            return;
        }
        document.getElementById('montoSaldo'+i).innerHTML=currencyFormat2(Sres,',','.');
    }
}

function TransferirMonto(n){
    var CodigoMoneda=document.getElementById('CodigoMoneda'+n).innerHTML;
    var MontoTransferir =document.getElementById('montoTranferir'+n).value;
    if(MontoTransferir==''){
        MontoTransferir='0.00';
    }else{
        MontoTransferir = MontoTransferir.replace(/,/gi, "");
    }
    var MontoSaldo =document.getElementById('montoSaldo'+n).innerHTML;
    if(MontoSaldo==''){
        MontoSaldo='0.00';
    }else{
        MontoSaldo = MontoSaldo.replace(/,/gi, "");
    }
    if(parseFloat(MontoTransferir)==0){
        window.alert("No se puede transferir el monto");
        return;
    }else{
        var resp = window.confirm("DESEA TRANSFERIR EL MONTO?");
        if(resp){
            var CajaDestino = document.getElementById('Caja'+n)[document.getElementById('Caja'+n).selectedIndex].value;
            req2 = null;
            if (window.XMLHttpRequest) {
                req2 = new XMLHttpRequest();
            }else if (window.ActiveXObject) {
                req2 = new ActiveXObject("Microsoft.XMLHTTP");
            }
            if(req2!=null){
                req2.onreadystatechange = procesgot;
                var PATH = "/financeBank";
                var url = PATH +"/STransferirEntreCajas"+
                "?CodigoMoneda="+CodigoMoneda+
                "&MontoTransferir="+MontoTransferir+
                "&MontoSaldo="+MontoSaldo+
                "&CajaDestino="+CajaDestino;
                req2.open("GET",url,true);
                req2.send(null);
            }
        }
    }
}

function CerrarCaja(tipo){
    if(tipo=='PRIMARY'){
        window.alert("Esta caja no puede cerrarse.");
        return;
    }
    var answer = confirm ("¿ESTA SEGURO DE CERRARLO?")
    if (answer){
        req2 = null;
        if (window.XMLHttpRequest) {
            req2 = new XMLHttpRequest();
        }else if (window.ActiveXObject) {
            req2 = new ActiveXObject("Microsoft.XMLHTTP");
        }
        if(req2!=null){
            var PATH = "/financeBank";
            var url = PATH +"/SCerrarCaja";
            req2.open("GET",url,true);
            req2.send(null);
        }
        document.foper.submit();
    }
}

function formaterMontos(m){
    this.TotalMoneda=m;
    for(var w=0;w<m;w++){
        var montoActual = document.getElementById('montoActual'+w).innerHTML;
        if(montoActual==''){
            montoActual='0';
        }
        var resMontoActual =parseFloat (montoActual);
        if(resMontoActual==Number.NaN){
            resMontoActual=0;
        }
        if(resMontoActual==0){
            document.getElementById('montoActual'+w).innerHTML="0.00";
            document.getElementById('montoSaldo'+w).innerHTML="0.00";
        }else{
            resMontoActual=resMontoActual.toFixed(2);
            resMontoActual=resMontoActual+"";
            document.getElementById('montoActual'+w).innerHTML=currencyFormat2(resMontoActual,',','.');
            document.getElementById('montoSaldo'+w).innerHTML=currencyFormat2(resMontoActual,',','.');
        }
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
    if(numOP!="" || tipoOperacion=="TODO" ||tipoOperacion=="CAJA"){
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
        alert("ingrese numero de operacion");
    }
}

var req21;
function transferir(n){
    req21 = null;
    if (window.XMLHttpRequest) {
        req21 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req21 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req21!=null){
        var PATH = "/financeBank";
        var url = PATH +"/SRecepcion?idTransferencia="+n;
        req21.open("GET",url,true);
        req21.send(null);
        window.alert('¡¡SE REALIZO LA RECEPCION CON EXITO!!');
        //document.f1.submit();
        window.location="SReciboRecepcion?idTransferencia="+n;
    }
}

function pprocesar(){
    contenido = document.getElementById('divOperacion');
    contenido.innerHTML="Cargando los datos...";
    if (req.readyState==4 && req.status==200) {
        contenido.innerHTML = req.responseText;
        var tamTabla;
        tamTabla=document.getElementById("tablaOperacion").rows.length
        if(tamTabla>=15){
            contenido = document.getElementById('divOperacion').style.overflow="auto";
            contenido = document.getElementById('divOperacion').style.height="600px";
        }else{
            contenido = document.getElementById('divOperacion').style.overflow="";
            contenido = document.getElementById('divOperacion').style.height="";
        }
    }
}

var reqxx;
function full(){
    var tipoOperacion,numOP;
    tipoOperacion="TODO";
    numOP="";
    if(tipoOperacion=="TODO"){
        reqxx = null;
        if (window.XMLHttpRequest) {
            reqxx = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            reqxx = new ActiveXObject("Microsoft.XMLHTTP");
        }
        if(reqxx!=null){
            reqxx.onreadystatechange = pprocesarxx;
            var PATH = "/financeBank";
            var url = PATH + "/SBuscarOCaja?numOP="+numOP+"&tipoOperacion="+tipoOperacion;
            reqxx.open("GET",url,true);
            reqxx.send(null);
        }
    }else{
        window.alert("Fallo JAVASCRIPT!");
    }
}

function pprocesarxx(){
    contenido = document.getElementById('divOperacionf');
    contenido.innerHTML="Cargando los datos...";
    if (reqxx.readyState==4 && reqxx.status==200) {
        contenido.innerHTML = reqxx.responseText;
        if(document.getElementById("rows")!=null)
            if(document.getElementById("rows").value>15){
                document.getElementById('divOperacionf').style.overflow="auto";
                document.getElementById('divOperacionf').style.height="600px";
            }
    }
}

var Aut = false;
function ventanaNueva2(ind) {
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
            return Autorizar(user,pass, ind);
        }
    });
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
        var answer = confirm ("¿ESTA SEGURO QUE DESEA EXTORNAR ESTA OPERACION?");
        if (answer){
            var PATH = "/financeBank";
            var url = PATH + "/SExtornarOperacion?IdOperacion="+ind+"&userAdminExt="+userAdmin;
            req4.open("GET",url,true);
            req4.onreadystatechange = procesar4;
            req4.send(null);
        }
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
            buscarOperacione();
        }else {
            window.alert("FALLO EN LA EXTORNACION");
        }
        document.foper.submit();
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
            extornar(ind);
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
    return true;
}

function procesar1(){
    contenido = document.getElementById('res');
    contenido.innerHTML="Cargando los datos...";
    contenido.innerHTML="";
    if (req2.readyState==4 && req2.status==200) {
        contenido.innerHTML = req2.responseText;
    }
}
            