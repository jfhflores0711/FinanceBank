/**
 *
 * @author admin
 */


var req2;
function cobrogiro() {
    /***************DATOS DEL GIRO**************************/
    var comision = document.getElementById('div_comision').innerHTML;
    if(comision==""){
        comision=0;
    }
    else{
        comision = comision.replace(/,/gi, "");
    }
    var j2;
    for (j2=0;j2<document.f1.formap.length;j2++){
        if (document.f1.formap[j2].checked)
            break;
    }
    var fpago_comision = document.f1.formap[j2].value;
    var idregistro_giro=1;
    if(document.form1.giro!=null){
        var F;
        for (F=0;F<document.form1.giro.length;F++){
            if (document.form1.giro[F].checked)
                break;
        }
        if(document.form1.giro.length==null){
            idregistro_giro= document.form1.giro.value;
        }else{
            idregistro_giro= document.form1.giro[F].value;
        }
    }
    req2 = null;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(idregistro_giro==1){
        window.alert("¡¡DEBE SELECCIONAR UN GIRO!!");
    }else{
        var answer = window.confirm ("¿ESTA SEGURO REALIZAR EL COBRO DEL GIRO?");
        if (answer){
            if(req2!=null){
                var PATH = "/financeBank";
                var url = PATH + "/SCobroGiro?"+
                "comision="+comision +
                "&idregistro_giro="+idregistro_giro+
                "&fpago_comision="+fpago_comision;
                req2.open("GET",url,true);
                req2.send(null);
            }
            window.alert('EL RETIRO SE REALIZO CON EXITO')
            window.location="SReciboCobroGiro";
        }
    }
}


function selectGiro(idGiro,dniE,rucE,dniR,rucR,Simbolo,importe,Comicion,PagadorComi,nCatE,nCatR,filial){
    buscarpersonaGiroE('EF',idGiro);
    buscarpersonaGiroR('RF',idGiro);
    document.getElementById('Rdni').value=dniR;
    document.getElementById('Rruc').value=rucR;
    document.getElementById('Eruc').value=rucE;
    document.getElementById('Edni').value=dniE;
    document.getElementById('filial').innerHTML =filial;
    document.getElementById('giroSimbolo').innerHTML =Simbolo;
    document.getElementById('giroImporte').innerHTML =importe;
    document.getElementById('simbolo2').innerHTML= Simbolo;
    document.getElementById('simbolo3').innerHTML= Simbolo;
    document.getElementById('cateE').innerHTML= nCatE;
    document.getElementById('cateR').innerHTML= nCatR;
    if(PagadorComi=='PRECEPTOR'){
        document.getElementById('tr_comi').style.visibility='';
        document.getElementById('tr_formap').style.visibility='';
        document.getElementById('simbolo1').innerHTML =Simbolo;
        document.getElementById('div_comision').innerHTML =Comicion;
        forma_pago();
    }else{
        document.getElementById('tr_comi').style.visibility='hidden';
        document.getElementById('tr_formap').style.visibility='hidden';
        document.getElementById('pago').value=importe
        document.getElementById('cobro').value="0.00"
    }
}

function forma_pago(){
    var importe =document.getElementById('giroImporte').innerHTML;
    var comision =document.getElementById('div_comision').innerHTML;
    var j;
    for (j=0;j<document.f1.formap.length;j++){
        if (document.f1.formap[j].checked)
            break;
    }
    var forp = document.f1.formap[j].value;
    if(forp=='EFECTIVO'){
        document.getElementById('pago').value=importe;
        document.getElementById('cobro').value=comision;
    } else {
        var valor =importe;
        valor = valor.replace(/,/gi, "");
        var valor1 =comision;
        valor1 = valor1.replace(/,/gi, "");
        var res =parseFloat (valor)-parseFloat(valor1);
        res=res.toFixed(2);
        var Sres =""+res;
        document.getElementById('pago').value=currencyFormat2(Sres,',','.');
        document.getElementById('cobro').value="0.00";
    }
}

function buscarpersonaGiroE(er,id){
    req2 = null;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2!=null){
        var PATH = "/financeBank";
        var url = PATH + "/SResetCapital?idregistro="+id+"&er="+er;
        req2.open("GET",url,true);
        req2.onreadystatechange = procesarRespuestaE;
        req2.send(null);
    }
}

function procesarRespuestaE(){
    contenido = document.getElementById('Edetalle');
    contenido.innerHTML="Cargando los datos...";
    if (req2.readyState==4 && req2.status==200) {
        var values = new Array();
        var names;
        contenido.innerHTML = req2.responseText;
        if(document.getElementById('Edetalle').innerHTML=="E0"){
            document.getElementById("Eidpersona").value="";
        }else{
            var params = document.getElementById('Edetalle').innerHTML.split('-');
            for(var i=0; i<params.length; i++) {
                names = params[i].split('=');
                values[names[0]] = names[1];
                document.getElementById(names[0]).value= names[1];
            }
        }
    }
}

var req3;
function buscarpersonaGiroR(er,id){
    req3 = null;
    if (window.XMLHttpRequest) {
        req3 = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        req3 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req3!=null){
        var PATH = "/financeBank";
        var url = PATH + "/SResetCapital?"+"er="+er+"&idregistro="+id;
        req3.open("GET",url,true);
        req3.onreadystatechange = procesarRespuestaR;
        req3.send(null);
    }
}

function procesarRespuestaR(){
    contenido = document.getElementById('Rdetalle');
    contenido.innerHTML="Cargando los datos...";
    if (req3.readyState==4 && req3.status==200) {
        var values = new Array();
        var names;
        contenido.innerHTML = req3.responseText;
        if(document.getElementById('Rdetalle').innerHTML=="R0"){
            document.getElementById("Ridpersona").value="";
        }else{
            var params = document.getElementById('Rdetalle').innerHTML.split('-');
            for(var i=0; i<params.length; i++) {
                names = params[i].split('=');
                values[names[0]] = names[1];
                document.getElementById(names[0]).value= names[1];
            }
        }
    }
}

function extornar(idReg,t){
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
            Autorizar(user,pass);
            window.alert('¡¡ DATOS ENVIADOS !!')
            if(document.getElementById('res').innerHTML==1){
                Aut = true;
                sExtornar(idReg,t)
                window.alert('¡¡SE REALIZO EL EXTORNO CON EXITO!!');
                document.f1.submit();
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

function Autorizar(login,contrasenia) {
    req2 = false;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
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
    contenido.innerHTML="";
    if (req2.readyState==4 && req2.status==200) {
        contenido.innerHTML = req2.responseText;
    }
}