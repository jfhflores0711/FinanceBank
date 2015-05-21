/**
 *
 * @author admin
 */

var req4;
function verpc(){
    var contenido, tipoBusqueda="";
    document.getElementById('divlistPers').innerHTML="<img alt='Cargando..' src='images/ajax/ajax-loader1.gif'>";
    contenido=document.getElementById("txtBuscar").value;
    tipoBusqueda=document.getElementById("selTipoBus").value;
    if(tipoBusqueda=='0'||contenido.length==0){
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
        var url = PATH + "/SCobranza?o=b&a="+contenido.trim()+"&b="+tipoBusqueda;
        req4.open("GET",url,true);
        req4.send(null);
    }
}

function procesarRespuesta3(){
    contenido = document.getElementById('divlistPers');
    contenido.innerHTML="Cargando los datos...";
    if (req4.readyState==4 && req4.status==200) {
        contenido.innerHTML = req4.responseText;
        var tamTabla=document.getElementById("tablapersonas").rows.length
        if(tamTabla>=12){
            document.getElementById('divlistPers').style.overflow="auto";
            document.getElementById('divlistPers').style.height="320px";
        }
        else{
            document.getElementById('divlistPers').style.overflow="";
            document.getElementById('divlistPers').style.height="";
        }
    }
}

var re2;
function mostrarcuentadetallec(ind) {
    document.getElementById("im_").value=document.getElementById("tdM"+ind).innerHTML;
    re2 = null;
    var al=document.getElementById("tdId"+ind).innerHTML;
    if (window.XMLHttpRequest) {
        re2 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        re2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(re2!=null){
        re2.onreadystatechange = procesarR2;
        var PATH = "/financeBank";
        var url = PATH + "/SCobranza?o=s&a="+al;
        re2.open("GET",url,true);
        re2.send(null);
    }
}
function procesarR2(){
    contenido = document.getElementById('tableMisSels');
    contenido.innerHTML="Cargando los datos...";
    if (re2.readyState==4 && re2.status==200) {
        contenido.innerHTML = re2.responseText;
    }
    $('#z').maskMoney();
}

function raise(id, ind){
    var com=document.getElementById(id).value*1;
    var tot=document.getElementById('z');
    tot.value= Math.round((parseFloat(tot.value) + com)* 100) / 100;
}

function fold(id,ind){
    var tot=document.getElementById('z');
    var quantidate=document.getElementById(id).value*1;
    tot.value=Math.round((parseFloat(tot.value)-quantidate)* 100) / 100 ;
}

function validartxtBusquedac(){
    var seltipo=document.getElementById("selTipoBus")[document.getElementById("selTipoBus").selectedIndex].value;
    if(seltipo=="ruc"){
        document.getElementById("divtxtBuscar").innerHTML=" <input id='txtBuscar' type='text' name='txtBuscar' value='' onkeypress='return soloNumeroTelefonico(event);' onkeyup='aSKey(event);' />";
    }
    else if(seltipo=="dni"){
        document.getElementById("divtxtBuscar").innerHTML=" <input id='txtBuscar' type='text' name='txtBuscar' value='' onkeypress='return solonumeros(event);' onkeyup='aSKey(event);'/>";
    }else if(seltipo=="ape" || seltipo=="nom"){
        document.getElementById("divtxtBuscar").innerHTML=" <input id='txtBuscar' type='text' name='txtBuscar' value='' onkeyup='aSKey(event);'/>";
    }else if(seltipo=="cre"){
        document.getElementById("divtxtBuscar").innerHTML=" <input id='txtBuscar' type='text' name='txtBuscar' value='' onkeypress='return solonumeros(event);' onkeyup='aSKey(event);'/>";
    }else{
        document.getElementById("divtxtBuscar").innerHTML=" <input id='txtBuscar' type='text' name='txtBuscar' value='No se puede realizar la búsqueda' readonly />";
    }
    document.getElementById("divlistPers").innerHTML="";
    document.getElementById("divlistPers").style.height="0px";
    document.getElementById("tableMisSels").innerHTML="<input type='hidden' value='' id='rus' />";
    document.getElementById("txtBuscar").focus();
}

function aSKey(e) {
    var whichCode = (window.Event) ? e.which : e.keyCode;
    if (whichCode == 13) verpc(); // Enter
}

function validarc(){
    if(document.getElementById("z").value*1==0){
        window.alert("No ha seleccionado ninguna opción a cobrar");
        return;
    }else{
        var mm=document.getElementById("z").value;
        document.formCobranza.action="comprobanteCobranza.htm";
        window.alert("El monto actual a cobrar es "+ document.getElementById("im_").value +" " + currencyFormat2(mm,',','.') );
    }
    var tf = window.confirm("Está seguro de que desea grabar los datos?");
    if (tf == true){        
        document.getElementById("grabarC").disabled=true;
        grabarc();        
    }
}

var rg;
function grabarc(){
    //    if(evaluarpr()){
    //return '_';
    var t=document.getElementById("w_").value;
    var a="",b="",c,z,y;
    var val=document.getElementById("ctx").innerHTML*1;
    var co = document.formCobranza.radio1;
    for (y = 0; y < co.length; y++) {
        if (co[y].checked) {
            a=co[y].value;
            break;
        }
    }
    co = document.formCobranza.radio2;
    for (y = 0; y < co.length; y++) {
        if (co[y].checked) {
            b = co[y].value;
            break;
        }
    }
    //y=document.getElementById("selG")[document.getElementById("selG").selectedIndex].value;
    z=document.getElementById("z").value;
    var f=true;
    var g=0;
    for(var i=1;i<=val;i++){
        if(document.getElementById("aa5"+i)){
            if(document.getElementById("aa5"+i).checked){
                //z=z+parseFloat(document.getElementById("a4"+i).value)-parseFloat(document.getElementById("ad"+i).value)+parseFloat(document.getElementById("au"+i).value);
                var az=document.getElementById("a4"+i).value;
                var am=document.getElementById("ad"+i).value;
                var al=document.getElementById("au"+i).value;
                var amf=parseFloat(az.replace(",", ""))+parseFloat(al.replace(",", ""))-parseFloat(am.replace(",",""));
                var aa=amf.toFixed(2);
                if(f){
                    //c=i+";"+document.getElementById("a4"+i).value+";"+document.getElementById("ad"+i).value+";"+document.getElementById("au"+i).value;
                    c=i+";"+aa;
                    f=false;
                }else{
                    //c=c +"q" +i+";"+document.getElementById("a4"+i).value+";"+document.getElementById("ad"+i).value+";"+document.getElementById("au"+i).value
                    c=c +"q" +i+";"+aa;
                }
                g++;
            }
        }
    }
    rg=null;
    if (window.XMLHttpRequest) {
        rg = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        rg = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(rg!=null){
        rg.onreadystatechange = procesarp;
        var PATH = "/financeBank";
        var url = PATH + "/SCobranza?o=g&a="+a+"&b="+b+"&c="+c+"&f="+g+"&t="+t+"&z="+z;
        rg.open("GET",url,true);
        rg.send(null);
    }
//    }
}

function procesarp(){
    var contenido = document.getElementById('dg');
    contenido.innerHTML="<img src='images/ajax/ajax-loader1.gif' alt='Cargando los datos...'>";
    if (rg.readyState==4 && rg.status==200) {
        contenido.innerHTML = rg.responseText;
        alert("Los datos Han sido Grabado con resultado "+contenido.innerHTML);
    }
    //sleep(1000);
    document.formCobranza.action="comprobanteCobranza.htm?a="+document.getElementById("w_").value;
    document.formCobranza.submit();
}

var rc;
function rco(){
    if(document.getElementById("rc0").value*1==0){
        return;
    }
    document.getElementById("rc0").value="0";
    rc = null;
    var al=document.getElementById("w_").value;
    if (window.XMLHttpRequest) {
        rc = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        rc = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(rc!=null){
        rc.onreadystatechange = procesarRC0;
        var PATH = "/financeBank";
        var url = PATH + "/SCobranza?o=r0&a="+al;
        rc.open("GET",url,true);
        rc.send(null);
    }
}

function rca(){
    if(document.getElementById("rc0").value*1==1){
        return;
    }
    document.getElementById("rc0").value="1";

    rc = null;
    var al=document.getElementById("w_").value;
    if (window.XMLHttpRequest) {
        rc = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        rc = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(rc!=null){
        rc.onreadystatechange = procesarRC;
        var PATH = "/financeBank";
        var url = PATH + "/SCobranza?o=r1&a="+al;
        rc.open("GET",url,true);
        rc.send(null);
    }
}
function procesarRC0(){
    contenido = document.getElementById('cr');
    contenido.innerHTML="Cargando los datos...";
    if (rc.readyState==4 && rc.status==200) {
        contenido.innerHTML = rc.responseText;
    }
    $('#z').maskMoney();
}

function procesarRC(){
    contenido = document.getElementById('cr');
    contenido.innerHTML="Cargando los datos...";
    if (rc.readyState==4 && rc.status==200) {
        contenido.innerHTML = rc.responseText;
    }
}

function evalTF(a){
    var ac=document.getElementById("ctx").innerHTML*1;
    if(a.value==''){
        a.value='0.00'
        }
    var ax=a.value*1;
    if(a.checked==true){
        if(ac>ax){
            document.getElementById("aa5"+(ax+1)).disabled=!document.getElementById("aa5"+(ax+1)).disabled;
        }
        document.getElementById("du"+ax).style.display="block";
        raise("a4"+ax,ax);

    }else{
        if(ac>ax){
            if(document.getElementById("aa5"+(ax+1)).checked){
                alert("No se puede quitar aún, \nquite la fila de abajo primero.");
                a.checked=true;
                return;
            }
            document.getElementById("aa5"+(ax+1)).disabled=!document.getElementById("aa5"+(ax+1)).disabled;
        }
        document.getElementById("du"+ax).style.display="none";
        document.getElementById("ad"+ax).value="0.00";
        document.getElementById("au"+ax).value="0.00";
        calculeZ();
    }
}

function ha(a){
    document.getElementById(a).style.display="block";
}

function aM(i){
    var valor2 = document.getElementById('au'+i).value;
    if(valor2==''){
        valor2='0';
        document.getElementById('au'+i).value='0';
    }else{
        valor2 = valor2.replace(/,/gi, "");
    }
    var valor1=document.getElementById('ad'+i).value;
    if(valor1==''){
        valor1='0';
        document.getElementById('ad'+i).value='0';
    }else{
        valor1 = valor1.replace(/,/gi, "");
    }
    var valor = document.getElementById('a3'+i).value;
    if(valor==''){
        valor='0.00';
        document.getElementById('a3'+i).value='0.00';
    }else{
        valor = valor.replace(/,/gi, "");
    }
    var valor0 = document.getElementById('a4'+i).value;
    if(valor0==''){
        valor0='0.00';
        document.getElementById('a4'+i).value='0.00';
    }else{
        valor0 = valor0.replace(/,/gi, "");
    }
    var res = parseFloat(valor)-parseFloat(valor1);
    if(res==Number.NaN){
        res=0;
    }
    if(res<0){
        window.alert('NO SE PUEDE DESCONTAR MÁS')
        document.getElementById('ad'+i).value='0.00';
        aM(i);
    }else{
        res = res + parseFloat(valor2);
        if(res==Number.NaN){
            res=0;
        }
        var res0=parseFloat(valor0)-parseFloat(valor1)+parseFloat(valor2);
        if(res0==Number.NaN){
            res0=0;
        }
        //var z=parseFloat(document.getElementById('z').value) - parseFloat(valor1) + parseFloat(valor2);
        //document.getElementById('z').value=z.toFixed(2);
        calculeZ();
        var Sres =""+res.toFixed(2);
        var Sres0 =""+res0.toFixed(2);
        if(Sres=="0.00"){
            document.getElementById('di4'+i).innerHTML="<s>"+document.getElementById('a3'+i).value+"</s>&nbsp;"+Sres;
            document.getElementById('dv4'+i).innerHTML="<s>"+document.getElementById('a4'+i).value+"</s>&nbsp;"+currencyFormat2(Sres0,',','.');
            return;
        }
        document.getElementById('di4'+i).innerHTML="<s>"+document.getElementById('a3'+i).value+"</s>&nbsp;"+currencyFormat2(Sres,',','.');
        document.getElementById('dv4'+i).innerHTML="<s>"+document.getElementById('a4'+i).value+"</s>&nbsp;"+currencyFormat2(Sres0,',','.');
    }
}

function calculeZ(){
    var val=document.getElementById("ctx").innerHTML*1;
    var z=0.00;
    for(var i=1;i<=val;i++){
        if(document.getElementById("aa5"+i)){
            if(document.getElementById("aa5"+i).checked){
                z=z+parseFloat(document.getElementById("a4"+i).value)-parseFloat(document.getElementById("ad"+i).value)+parseFloat(document.getElementById("au"+i).value);
            }
        }
    }
    document.getElementById("z").value=z.toFixed(2);
}
