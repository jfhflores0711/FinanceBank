/**
 *
 * @author admin
 */

var rd
function despacho(a){
    var e=window.confirm("Desea desembolsar esta solicitud?")
    if(e==false){
        return;
    }
    document.getElementsByName("des"+a).disabled=true;
    document.frmd.action="fechacobranzas.htm?ax="+a;
    document.frmd.submit();
    return;
}

function borrar(a){
    var e=window.confirm("Desea eliminar esta solicitud?")
    if(e==false){
        return;
    }
    document.getElementsByName("eli"+a).disabled=true;
    rg=null;
    if (window.XMLHttpRequest) {
        rg = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        rg = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(rg!=null){
        rg.onreadystatechange = procesarp;
        var PATH = "/financeBank";
        var url = PATH + "/SPrestamo?o=e&a="+a;
        rg.open("GET",url,true);
        rg.send(null);
    }
}
var rg;
function grabarp(){
    if(evaluarpr()){
        document.getElementById("gp").disabled=true;
        var a,b,c,d,e,f,g,h,i,j,z;
        a=document.getElementById("ix").value;
        b=document.getElementById("fs").value;
        c=document.getElementById("fa").value;
        d=document.getElementById("acd").checked?"df":"ff";
        e=document.getElementById("selG")[document.getElementById("selG").selectedIndex].value;
        f=document.getElementById("decx").value;
        g=document.getElementById("mon")[document.getElementById("mon").selectedIndex].value;
        h= $('imp').maskMoney('unmasked')[0];
        i=document.getElementById("intas").value;
        j=document.getElementById("nc").value;
        z=document.getElementById("rus").value;
        rg=null;
        if (window.XMLHttpRequest) {
            rg = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            rg = new ActiveXObject("Microsoft.XMLHTTP");
        }
        if(rg!=null){
            rg.onreadystatechange = procesarp;
            var PATH = "/financeBank";
            var url = PATH + "/SPrestamo?o=g&a="+a+"&b="+b+"&c="+c+"&d="+d+"&e="+e+"&f="+f+"&g="+g+"&h="+h+"&i="+i+"&z="+z+"&j="+j;
            rg.open("GET",url,true);
            rg.send(null);
        }
    }
}

function procesarp(){
    var contenido = document.getElementById('divprestamos');
    contenido.innerHTML="<img src='images/ajax/ajax-loader1.gif' alt='Cargando los datos...'>";
    if (rg.readyState==4 && rg.status==200) {
        contenido.innerHTML = rg.responseText;
    }
}

var Aut = false;
var req2;
function ventanaNuevap() {
    if(document.getElementById("chkVerSaldo").checked==true){
        document.getElementById("chkVerSaldo").checked=false;
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
                Autorizarpr(user,pass);
                alert('¡¡ DATOS ENVIADOS !!');
                if(document.getElementById('res').innerHTML==1){
                    Aut = true;
                    document.getElementById("chkVerSaldo").checked=true;
                    return true;
                }else{
                    document.formPrestamo.chkVerSaldo.checked=false;
                    $('login_error_msg').innerHTML='Usuario o Contrase&ntilde;a incorrecto';
                    $('login_error_msg').show();
                    Windows.focusedWindow.updateHeight();
                    new Effect.Shake(Windows.focusedWindow.getId());
                    return false;
                }
            }
        });
    } else {
        window.alert("No se autoriza la acción");
    }
}

function Autorizarpr(login,contrasenia) {
    //req2=false;
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
    }
}

function procesar1(){
    var contenido = document.getElementById('res');
    contenido.innerHTML="<img src='images/ajax/ajax-loader1.gif' alt='Cargando los datos...'>";
    if (req2.readyState==4 && req2.status==200) {
        contenido.innerHTML = req2.responseText;
    }
}

function cleanP(){
    document.getElementById('montoPrestamo').value='0.00';
    document.getElementById('tasaInteres').value='0.00';
    document.getElementById('tasaMoras').value='0.00';
    document.getElementById('plazos').value='1';
}
function buscarP(){
    if(document.formPrestamo.txtBuscar.value==""){
        window.alert("Ingrese algún texto a buscar!");
    }else{
        document.formPrestamo.action='prestamos.htm';
        document.formPrestamo.onsubmit='';
        document.formPrestamo.submit();
        document.formPrestamo.BUSCAR.value='buscando';
        document.formPrestamo.BUSCAR.disabled=true;
        document.getElementById("ajaximgo").style.display='block';
    }
}

function rebusquedap(){
    var seltipo=document.getElementById("cuentadr")[document.getElementById("cuentadr").selectedIndex].value;
    document.getElementById("selTipoBus")[document.getElementById("selTipoBus").selectedIndex].value="dni_ruc";
    document.getElementById("divtxtBuscar").innerHTML=" <input id='txtBuscar' type='text' name='txtBuscar' value='"+seltipo+"' onfocus='blur();' />";
}

/* form*/
function evaluarpr(){
    if(document.getElementById("ix").value=="ERROR"){
        window.alert("Seleccione un tipo de crédito");
        return false;
    }
    if(document.getElementById("rus").value==""){
        window.alert("Seleccione una persona que solicita el crédito");
        return false;
    }
    var cuenta=document.getElementById("pr")[document.getElementById("pr").selectedIndex].value;
    if(cuenta=="0"){
        window.alert("Seleccione un Producto");
        return false;
    }
    if(document.getElementById("fs").value==""){
        window.alert("Ingrese la fecha de solicitud!");
        return false;
    }
    if(document.getElementById("fa").value==""){
        window.alert("Ingrese la fecha de aprobación!");
        return false;
    }
    if(document.getElementById("decx").value==""){
        window.alert("Ingrese el número de cuotas!");
        return false;
    }
    var ea=document.getElementById("decx").value*1;
    if(ea%30!=0){
        window.alert('La cantidad de Días entre cuotas debe ser múltiplo de 30 (1 Mes comercial)');
        return false;
    }
    if(document.getElementById("intas").value=="" || isNaN(document.getElementById("intas").value) || parseFloat(document.getElementById("intas").value)<=0){
        window.alert("Ingrese la tasa de Interés!");
        return false;
    }
    var fca=document.getElementById("fa").value;
    var an=parseInt(fca.substr(6, 4), 10);
    var me=parseInt(fca.substr(3, 2), 10);
    var da=parseInt(fca.substr(0, 2), 10);
    var fc=new Date(an,me-1,da);
    var fh=new Date();
    var gh=new Date(fh.getFullYear(), fh.getMonth(), fh.getDate());
    if(gh>fc){
        window.alert("La fecha de aprobación no puede ser anterior a la actual!");
        return false;
    }
    if(document.getElementById("nc").value==""|| isNaN(document.getElementById("nc").value)){
        window.alert("El número de cuotas!");
        return false;
    }
    var num = $('imp').maskMoney('unmasked')[0];
    if(num=="" || isNaN(num) || parseFloat(num)<=0){
        window.alert("Ingrese el monto correcto a habilitar!");
        return false;
    }else{
        var n=parseFloat(document.getElementById("imp").value);
        var na=parseFloat(document.getElementById("mi").value);
        var nb=parseFloat(document.getElementById("mx").value);
        if(n>nb || n<na){
            window.alert("El producto acepta un rango de monto entre "+na +" y "+nb);
            return false;
        }
        n=parseFloat(document.getElementById("intas").value);
        na=parseFloat(document.getElementById("tii").value);
        nb=parseFloat(document.getElementById("tix").value);
        if(n>nb || n<na){
            window.alert("El producto acepta un rango de TEA entre "+na +" y "+nb);
            return false;
        }
        n=parseFloat(document.getElementById("nc").value);
        na=parseFloat(document.getElementById("nci").value);
        nb=parseFloat(document.getElementById("ncx").value);
        if(n>nb || n<na){
            window.alert("El producto acepta un rango de Cuotas entre "+na +" y "+nb);
            return false;
        }
    }
    var tf = window.confirm("Está seguro de que desea grabar los datos?")
    if (tf == true){
        return true;
    }
    return false
}

function actualiza(tipo){
    if('Natural'==tipo){
        document.getElementById("rbPersona").value=tipo;
    }else if('Juridica'==tipo){
        document.getElementById("rbJuridica").value=tipo;
    }
}

function habilitarPrestamo(){
    var cuenta=document.getElementById("cuentaCte")[document.getElementById("cuentaCte").selectedIndex].value;
    if(cuenta!="sinSeleccion"){
        var xMc=cuenta.split("(")
        var sSt=xMc[0].substring(xMc[0].indexOf(" "), xMc[0].length);
        document.getElementById("monedasP").innerHTML="<select name='MONEDA'><option value='"+xMc[1].substring(0,xMc[1].indexOf(")"))+"' selected>"+sSt+"</option></select>";
        document.getElementById("prestamofill").style.display="block";
        document.getElementById("submitPrestamo").style.display="block";
    }
    else{
        document.getElementById("monedasP").innerHTML="";
        window.alert("Seleccione una cuenta válida!");
        document.getElementById("prestamofill").style.display="none";
        document.getElementById("submitPrestamo").style.display="none";
    }
}

function validartxtBusquedap(){
    var seltipo=document.getElementById("selTipoBus")[document.getElementById("selTipoBus").selectedIndex].value;
    if(seltipo=="ruc"){
        document.getElementById("divtxtBuscar").innerHTML=" <input id='txtBuscar' type='text' name='txtBuscar' value='' onkeypress='return soloNumeroTelefonico(event);' tabindex='1' />";
    }else if(seltipo=="dni"){
        document.getElementById("divtxtBuscar").innerHTML=" <input id='txtBuscar' type='text' name='txtBuscar' value='' onkeypress='return solonumeros(event);' tabindex='1' />";
    }else if(seltipo=="ape" || seltipo=="nom"){
        document.getElementById("divtxtBuscar").innerHTML=" <input id='txtBuscar' type='text' name='txtBuscar' value='' tabindex='1' />";
    }else{
        document.getElementById("divtxtBuscar").innerHTML=" <input id='txtBuscar' type='text' name='txtBuscar' value='No se puede realizar la búsqueda' readonly tabindex='1' />";
    }
    document.getElementById("divlistPers").innerHTML="";
    document.getElementById("divlistPers").style.height="0px";
    document.getElementById("tableMisSels").innerHTML="<input type='hidden' value='' id='rus' />";
    document.getElementById("txtBuscar").focus();
}



var req4;
function verpp(){
    var contenido, tipoBusqueda="";
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
        var url = PATH + "/SPrestamo?o=b&a="+contenido.trim()+"&b="+tipoBusqueda;
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
        if(tamTabla>=7){
            document.getElementById('divlistPers').style.overflow="auto";
            document.getElementById('divlistPers').style.height="320px";
        }
        else{
            document.getElementById('divlistPers').style.overflow="";
            document.getElementById('divlistPers').style.height="";
        }
    }
}


function mostrarcuentadetallep(ind) {
    //var contenido=ind;
    document.getElementById("tableMisSels").innerHTML="<center>"
    + "<fieldset style='border-width:3px'>"
    + "<legend><b>USUARIO SELECCIONADO:</b></legend>"
    + "<table id='ts' class='tabla' width='100%'>"
    + "<tr class='modo1'>"
    + "<td style='color:red;font-size: 12px'><b>"
    + " "+ (document.getElementById("ttdDNI"+ind).innerHTML.length>5?document.getElementById("ttdDNI"+ind).innerHTML:document.getElementById("ttdRUC"+ind).innerHTML)
    + " - "+ document.getElementById("ttdN"+ind).innerHTML
    + " "+ document.getElementById("ttdA"+ind).innerHTML
    +"<input type='hidden' value='"+document.getElementById("tdIdPersona"+ind).innerHTML+"' id='rus' />"
    + "</b></td>"
    + "</tr>"
    + "</table>"
    + "</fieldset>"
    + "</center>";
}


var re2;
function selec(){
    re2 = null;
    var al=document.getElementById("pr")[document.getElementById("pr").selectedIndex].value;
    if (window.XMLHttpRequest) {
        re2 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        re2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(re2!=null){
        re2.onreadystatechange = procesarR2;
        var PATH = "/financeBank";
        var url = PATH + "/SPrestamo?o=s&a="+al;
        re2.open("GET",url,true);
        re2.send(null);
    }
}
function procesarR2(){
    contenido = document.getElementById('dcar');
    contenido.innerHTML="Cargando los datos...";
    if (re2.readyState==4 && re2.status==200) {
        contenido.innerHTML = re2.responseText;
    }
}
function teap(){
    var rate=document.getElementById("intas").value;
    document.getElementById("temp").innerHTML=round(100*(Math.pow(1+eeparseFloat(rate)/100,1/12)-1), 4);
    document.getElementById("tedp").innerHTML=round(100*(Math.pow(1+eeparseFloat(rate)/100,1/360)-1), 4);
    document.getElementById("intas").value=round(parseFloat(rate),2);
}

function tea(){
    var rate=document.getElementById("tasaIntSim").value;
    document.getElementById("tem").innerHTML=round(100*(Math.pow(1+eeparseFloat(rate)/100,1/12)-1), 4);
    document.getElementById("ted").innerHTML=round(100*(Math.pow(1+eeparseFloat(rate)/100,1/360)-1), 4);
    
}
function taem(){
    var va=document.getElementById("tasaMoras").value;
    document.getElementById("taem").innerHTML=round(100*(Math.pow(1+eeparseFloat(va)/1200,12)-1), 2);
}

function sim(){
    var a=document.getElementById("txtf").value;
    var b=document.getElementById("ncs").value*1;
    var c=document.getElementById("numcuotas").value*1;
    var d=document.getElementById("tasaIntSim").value*1;
    var e=document.getElementById("diacuotas").value*1;
    var f=document.getElementById("gs1").checked;
    //var g=document.getElementById("gs2").value;
    if(isNaN(b)){
        window.alert('Valor incorrecto');
        return;
    }
    if(isNaN(c)){
        window.alert('Valor incorrecto');
        return;
    }
    if(isNaN(d)){
        window.alert('Valor incorrecto');
        return;
    }
    if(isNaN(e)){
        window.alert('Valor incorrecto');
        return;
    }
    if(b==0 || c<=0 || d<=0 || e<=0){
        window.alert('Los valores deben ser mayores que cero');
        return;
    }
    var s="<TABLE FRAME=VOID CELLSPACING=0 COLS=7 RULES=NONE BORDER=0>"+
    "<COLGROUP><COL WIDTH=86><COL WIDTH=86><COL WIDTH=86><COL WIDTH=86><COL WIDTH=86><COL WIDTH=86><COL WIDTH=86></COLGROUP>"+
    "<TBODY>"+
    "<TR>"+
    "<TD STYLE='border-top: 3px solid #000000; border-left: 3px solid #000000' WIDTH=86 HEIGHT=23 ALIGN=CENTER><B><FONT SIZE=1>PLAZOS</FONT></B></TD>"+
    "<TD STYLE='border-top: 3px solid #000000' WIDTH=86 ALIGN=CENTER><B><FONT SIZE=1>FECHA</FONT></B></TD>"+
    "<TD STYLE='border-top: 3px solid #000000' WIDTH=86 ALIGN=CENTER><B><FONT SIZE=1>SALDO CAPITAL</FONT></B></TD>"+
    "<TD STYLE='border-top: 3px solid #000000' WIDTH=86 ALIGN=CENTER><B><FONT SIZE=1>INTERESES</FONT></B></TD>"+
    "<TD STYLE='border-top: 3px solid #000000' WIDTH=86 ALIGN=CENTER><B><FONT SIZE=1>AMORTI ZACIÓN</FONT></B></TD>"+
    "<TD STYLE='border-top: 3px solid #000000' WIDTH=86 ALIGN=CENTER><B><FONT SIZE=1>CUOTA</FONT></B></TD>"+
    "<TD STYLE='border-top: 3px solid #000000; border-right: 3px solid #000000' WIDTH=86 ALIGN=CENTER><B><FONT SIZE=1>OTROS COSTOS</FONT></B></TD>"+
    "</TR>"+
    "<TR>"+
    "<TD STYLE='border-bottom: 3px solid #000000; border-left: 3px solid #000000' ALIGN=center>0</TD>"+
    "<TD STYLE='border-bottom: 3px solid #000000' ALIGN=LEFT></TD>"+
    "<TD STYLE='border-bottom: 3px solid #000000' ALIGN=RIGHT>"+ -b +"</TD>"+
    "<TD STYLE='border-bottom: 3px solid #000000' ALIGN=LEFT></TD>"+
    "<TD STYLE='border-bottom: 3px solid #000000' ALIGN=LEFT></TD>"+
    "<TD STYLE='border-bottom: 3px solid #000000' ALIGN=LEFT></TD>"+
    "<TD STYLE='border-bottom: 3px solid #000000; border-right: 3px solid #000000' ALIGN=right></TD>"+
    "</TR>";
    if(a.length<10){
        window.alert('Fecha no válida');
        return;
    }
    //var aa=new Date(parseInt(a.substr(6, 10)),parseInt(a.substring(3, 5))-1,parseInt(a.substring(0, 2)));
    var ia = 0;//Interes
    var sa = 0;//Saldo
    var ca = 0;//Capital
    var it = 0;
    var ct = 0;
    var cat = 0;
    var cx = 0;
    if(f){
        var fa=a;
        var cu=round(pmt(Math.pow(1+eeparseFloat(d)/100,1/360)-1,c,-b,e),2);
        sa=b;
        for(var q=1;q<=c;q++){
            ia=round(sa*(Math.pow(1+eeparseFloat(d)/100, e/360)-1), 2);
            it+=ia;
            ca=round(cu-ia, 2);
            cat +=ca;
            cx=sa;
            sa=round(sa-ca, 2);
            fa=addToDateFull(fa, e);
            s +="<TR>";
            if(q==1){
                ct +=cu;
                s +="<TD STYLE='border-top: 3px solid #000000; border-left: 3px solid #000000' ALIGN=CENTER>";
                s +=q+"";
                s +="</TD>";
                s +="<TD STYLE='border-top: 3px solid #000000' ALIGN=RIGHT>"+fa+"</TD>"+
                "<TD STYLE='border-top: 3px solid #000000' ALIGN=RIGHT>"+padNmbOnlyDecFull(cx,2,"0")+"</TD>"+
                "<TD STYLE='border-top: 3px solid #000000' ALIGN=RIGHT>"+padNmbOnlyDecFull(ia,2,"0")+"</TD>"+
                "<TD STYLE='border-top: 3px solid #000000' ALIGN=RIGHT>"+padNmbOnlyDecFull(ca,2,"0")+"</TD>"+
                "<TD STYLE='border-top: 3px solid #000000' ALIGN=RIGHT>"+padNmbOnlyDecFull(cu,2,"0")+"</TD>"+
                "<TD STYLE='border-top: 3px solid #000000; border-right: 3px solid #000000' ALIGN=RIGHT>0.00</TD>";
            }else if(q==c){
                if (sa!=0){
                    ca=round(ca+sa,2);
                    cat +=round(sa, 2);
                    sa=0;
                }
                ct +=ca+ia;
                s +="<TD STYLE='border-bottom: 3px solid #000000; border-left: 3px solid #000000' ALIGN=center>";
                s +=q+"";
                s +="</TD>";
                s +="<TD STYLE='border-bottom: 3px solid #000000' ALIGN=RIGHT>"+fa+"</TD>"+
                "<TD STYLE='border-bottom: 3px solid #000000' ALIGN=RIGHT>"+padNmbOnlyDecFull(cx,2,"0")+"</TD>"+
                "<TD STYLE='border-bottom: 3px solid #000000' ALIGN=RIGHT>"+padNmbOnlyDecFull(ia,2,"0")+"</TD>"+
                "<TD STYLE='border-bottom: 3px solid #000000' ALIGN=RIGHT>"+padNmbOnlyDecFull(ca,2,"0")+"</TD>"+
                "<TD STYLE='border-bottom: 3px solid #000000' ALIGN=RIGHT>"+padNmbOnlyDecFull(round(ca+ia,2),2,"0")+"</TD>"+
                "<TD STYLE='border-bottom: 3px solid #000000; border-right: 3px solid #000000' ALIGN=RIGHT>0.00</TD>";
            }else{
                ct +=cu;
                s +="<TD STYLE='border-left: 3px solid #000000' ALIGN=center>";
                s +=q+"";
                s +="</TD>";
                s +="<TD ALIGN=RIGHT>"+fa+"</TD>"+
                "<TD ALIGN=RIGHT>"+padNmbOnlyDecFull(cx,2,"0")+"</TD>"+
                "<TD ALIGN=RIGHT>"+padNmbOnlyDecFull(ia,2,"0")+"</TD>"+
                "<TD ALIGN=RIGHT>"+padNmbOnlyDecFull(ca,2,"0")+"</TD>"+
                "<TD ALIGN=RIGHT>"+padNmbOnlyDecFull(cu,2,"0")+"</TD>"+
                "<TD STYLE='border-right: 3px solid #000000' ALIGN=RIGHT>0.00</TD>";
            }
            s+="</TR>";
        }
    }else{
        if(e%30!=0){
            window.alert('La cantidad de Días entre cuotas debe ser múltiplo de 30 (1 Mes comercial)');
            return;
        }
        var ff=a;
        var de=0.0;
        var ff1=ff;
        for(var sal=1;sal<=c;sal++){
            var dc=0;
            ff=ff1;
            ff1=sumaMesFull(ff, e/30);
            dc=difDiasFull(a, ff1);
            de = de + (Math.pow(1 / Math.pow(1+eeparseFloat(d)/100, 1/360), dc));
        }
        ff1=a;
        var dp=0;
        if(de==0.0){
            de=1;
        }
        var ce=round(b/de, 2);
        sa=b;
        for(var r=1;r<=c;r++){
            ff=ff1;
            ff1=sumaMesFull(ff, e/30);
            dp=difDiasFull(ff, ff1);
            ia=round(sa*(Math.pow(1+eeparseFloat(d)/100, dp/360)-1), 2);
            it+=ia;
            ca=round(ce-ia,2);
            cat +=ca;
            cx=sa;
            sa=round(sa-ca,2);
            s +="<TR>";
            if(r==1){
                ct +=ce;
                s +="<TD STYLE='border-top: 3px solid #000000; border-left: 3px solid #000000' HEIGHT=17 ALIGN=center>";
                s +=r+"";
                s +="</TD>";
                s +="<TD STYLE='border-top: 3px solid #000000' ALIGN=RIGHT>"+ff1+"</TD>"+
                "<TD STYLE='border-top: 3px solid #000000' ALIGN=RIGHT>"+padNmbOnlyDecFull(cx,2,"0")+"</TD>"+
                "<TD STYLE='border-top: 3px solid #000000' ALIGN=RIGHT>"+padNmbOnlyDecFull(ia,2,"0")+"</TD>"+
                "<TD STYLE='border-top: 3px solid #000000' ALIGN=RIGHT>"+padNmbOnlyDecFull(ca,2,"0")+"</TD>"+
                "<TD STYLE='border-top: 3px solid #000000' ALIGN=RIGHT>"+padNmbOnlyDecFull(ce,2,"0")+"</TD>"+
                "<TD STYLE='border-top: 3px solid #000000; border-right: 3px solid #000000' ALIGN=RIGHT>0.00</TD>";
            }else if(r==c){
                if (sa!=0.0){
                    ca=round(ca+sa,2);
                    cat +=round(sa, 2);
                    sa=0;
                }
                ct +=ca+ia;
                s +="<TD STYLE='border-bottom: 3px solid #000000; border-left: 3px solid #000000' HEIGHT=17 ALIGN=center>";
                s +=r+"";
                s +="</TD>";
                s +="<TD STYLE='border-bottom: 3px solid #000000' ALIGN=RIGHT>"+ff1+"</TD>"+
                "<TD STYLE='border-bottom: 3px solid #000000' ALIGN=RIGHT>"+padNmbOnlyDecFull(cx,2,"0")+"</TD>"+
                "<TD STYLE='border-bottom: 3px solid #000000' ALIGN=RIGHT>"+padNmbOnlyDecFull(ia,2,"0")+"</TD>"+
                "<TD STYLE='border-bottom: 3px solid #000000' ALIGN=RIGHT>"+padNmbOnlyDecFull(ca,2,"0")+"</TD>"+
                "<TD STYLE='border-bottom: 3px solid #000000' ALIGN=RIGHT>"+padNmbOnlyDecFull(round(ca+ia,2),2,"0")+"</TD>"+
                "<TD STYLE='border-bottom: 3px solid #000000; border-right: 3px solid #000000' ALIGN=RIGHT>0.00</TD>";
            }else{
                ct +=ce;
                s +="<TD STYLE='border-left: 3px solid #000000' HEIGHT=17 ALIGN=center>";
                s +=r+"";
                s +="</TD>";
                s +="<TD ALIGN=RIGHT>"+ff1+"</TD>"+
                "<TD ALIGN=RIGHT>"+padNmbOnlyDecFull(cx,2,"0")+"</TD>"+
                "<TD ALIGN=RIGHT>"+padNmbOnlyDecFull(ia,2,"0")+"</TD>"+
                "<TD ALIGN=RIGHT>"+padNmbOnlyDecFull(ca,2,"0")+"</TD>"+
                "<TD ALIGN=RIGHT>"+padNmbOnlyDecFull(ce,2,"0")+"</TD>"+
                "<TD STYLE='border-right: 3px solid #000000' ALIGN=RIGHT>0.00</TD>";
            }
            s+="</TR>";
        }
    }
    s +=
    "<TR>"+
    "<TD STYLE='border-top: 3px solid #000000; border-bottom: 3px solid #000000; border-left: 3px solid #000000' HEIGHT=20 ALIGN=RIGHT><B><FONT SIZE=1>TOTALES</FONT></B></TD>"+
    "<TD STYLE='border-top: 3px solid #000000; border-bottom: 3px solid #000000' ALIGN=RIGHT><B><FONT SIZE=3><BR></FONT></B></TD>"+
    "<TD STYLE='border-top: 3px solid #000000; border-bottom: 3px solid #000000' ALIGN=RIGHT><B><FONT SIZE=3><BR></FONT>0.00</TD>"+
    "<TD STYLE='border-top: 3px solid #000000; border-bottom: 3px solid #000000' ALIGN=RIGHT><B><FONT SIZE=3><BR></FONT>"+round(it, 2)+"</TD>"+
    "<TD STYLE='border-top: 3px solid #000000; border-bottom: 3px solid #000000' ALIGN=RIGHT><B><FONT SIZE=3><BR></FONT>"+round(cat, 2)+"</TD>"+
    "<TD STYLE='border-top: 3px solid #000000; border-bottom: 3px solid #000000' ALIGN=RIGHT><B><FONT SIZE=3><BR></FONT>"+round(ct, 2)+"</TD>"+
    "<TD STYLE='border-top: 3px solid #000000; border-bottom: 3px solid #000000; border-right: 3px solid #000000' ALIGN=RIGHT><B><FONT SIZE=1><BR></FONT></B></TD>"+
    "</TR>"+
    "</TBODY>"+
    "</TABLE>";
    document.getElementById("resultSim").innerHTML=s;
}

function pmt(rate, nper, pv,d) {
    if (rate == 0) {
        return -pv / nper;
    } else {
        rate=Math.pow((1 + rate), d)-1;
        var pvif = Math.pow(1 + rate, nper);
        var fvifa = (pvif - 1) / rate;
        return ((-pv * pvif) / fvifa);
    }
}

function incDateFull(sFec0){
    var nDia = parseInt(sFec0.substr(0, 2), 10);
    var nMes = parseInt(sFec0.substr(3, 2), 10);
    var nAno = parseInt(sFec0.substr(6, 4), 10);
    nDia += 1;
    if (nDia > finMesFull(nMes, nAno)){
        nDia = 1;
        nMes += 1;
        if (nMes == 13){
            nMes = 1;
            nAno += 1;
        }
    }
    return makeDateFormatFull(nDia, nMes, nAno);
}

function makeDateFormatFull(nDay, nMonth, nYear){
    var sRes;
    sRes = padNmbFull(nDay, 2, "0") + "/" + padNmbFull(nMonth, 2, "0") + "/" + padNmbFull(nYear, 4, "0");
    return sRes;
}
function padNmbFull(nStr, nLen, sChr){
    var sRes = String(nStr);
    if(String(nStr).length<nLen)
        for (var rm = 0; rm < (nLen - String(nStr).length); rm++){
            sRes = sChr + sRes;
        }
    return sRes;
}

function padNmbOnlyDecFull(nStr, nLen, sChr){
    var sRes = String(nStr);
    if(sRes.indexOf(".")!=-1){
        var a=sRes.split(".");
    }else{
        var a=sRes.split(",");
    }
    if(a.length<2)
        return sRes+".00";
    if(a[1].length<nLen)
        for (var rm = 0; rm < (nLen - a[1].length); rm++){
            a[1] = a[1]  + sChr;
        }
    return a[0]+"."+a[1];
}

function addToDateFull(sFec0, sInc){
    var nInc = Math.abs(parseInt(sInc));
    var sRes = sFec0;
    if (nInc >= 0){
        for (var k = 0; k < nInc; k++){
            sRes = incDateFull(sRes);
        }
    }
    return sRes;

}
var aFinMesFull = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
function finMesFull(nMes, nAno){
    return aFinMesFull[nMes - 1] + (((nMes == 2) && (nAno % 4) == 0)? 1: 0);
}

function sumaMesFull(sF,n){
    var n0=Math.abs(parseInt(n, 10));
    var sF0=sF;
    for(var i=0;i<n0;i++){
        sF0=incMesFull(sF0);
    }
    return sF0;
}

function incMesFull(sFec0){
    var nDia = parseInt(sFec0.substr(0, 2), 10);
    var nMes = parseInt(sFec0.substr(3, 2), 10);
    var nAno = parseInt(sFec0.substr(6, 4), 10);
    //
    if(nDia==31)nDia=30;
    nMes += 1;
    if (nMes == 13){
        nMes = 1;
        nAno += 1;
    }
    return makeDateFormatFull(nDia, nMes, nAno);
}

function difDiasFull(f0,f1){
    var d0=new Date(parseInt(f0.substr(6, 4), 10),parseInt(f0.substr(3, 2), 10)-1,parseInt(f0.substr(0, 2), 10));
    var d1=new Date(parseInt(f1.substr(6, 4), 10),parseInt(f1.substr(3, 2), 10)-1,parseInt(f1.substr(0, 2), 10));
    var d=d0-d1;
    return Math.abs(d)/(24*60*60*1000);
}
