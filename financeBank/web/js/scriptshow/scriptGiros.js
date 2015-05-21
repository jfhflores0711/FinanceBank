/**
 *
 * @author admin
 */

function clean(er){
    document.getElementById(er+'idpersona').value="";
    document.getElementById(er+'ruc').value="";
    document.getElementById(er+'dni').value="";
    document.getElementById(er+'nombres').value="";
    document.getElementById(er+'apellidos').value="";
    document.getElementById(er+'email').value="";
    document.getElementById(er+'ubigeo').value="";
    document.getElementById(er+'telefono').value="";
    document.getElementById(er+'celular').value="";
    document.getElementById(er+'direccion').value="";
}

function natural(er){
    clean(er);
    document.getElementById(er+'ruc').disabled=true;
    document.getElementById(er+'tr_dni').style.visibility='';
    document.getElementById(er+'td_nombres').style.display='';
    document.getElementById(er+'td_razonsocial').style.display='none';
    document.getElementById(er+'tr_apellidos').style.visibility='';
    document.getElementById(er+'tr_ubigeo').style.visibility='';
    document.getElementById(er+'tr_dni').style.visibility='';
    document.getElementById(er+'tr_dni').style.visibility='';
}

function juridica(er){
    document.getElementById(er+'ruc').disabled='';
    clean(er);
    document.getElementById(er+'tr_dni').style.visibility='hidden';
    document.getElementById(er+'td_nombres').style.display='none';
    document.getElementById(er+'td_razonsocial').style.display='';
    document.getElementById(er+'tr_apellidos').style.visibility='hidden';
    document.getElementById(er+'tr_ubigeo').style.visibility='hidden';
    document.getElementById(er+'tr_dni').style.visibility='hidden';
    document.getElementById(er+'tr_dni').style.visibility='hidden';
}

var bure;
function buscarpersona(er){
    var dni = '';
    var ruc = '';
    var categoria='';
    var nombres='';
    var apellidos='';
    var idp='';
    if(er=='RF' || er=='RH'){
        dni = document.getElementById("Rdni").value;
        ruc = document.getElementById("Rruc").value;
        nombres=document.getElementById("Rnombres").value;
        apellidos=document.getElementById("Rapellidos").value;
        idp=document.getElementById("Ridpersona").value;
        var j;
        for (j=0;j<document.f1.Rcategoria.length;j++){
            if (document.f1.Rcategoria[j].checked)
                break;
        }
        categoria = document.f1.Rcategoria[j].value;
        if(er=='RH'){
            if(idp.length>5){
                return;
            }
            if(nombres.length<2){
                return;
            }
        }else{
            if(dni.length<7){
                return;
            }
        }
    }else if(er=='EF'){
        dni = document.getElementById("Edni").value;
        ruc = document.getElementById("Eruc").value;
        if(dni.length<7){
            return;
        }
        document.getElementById('forpago2').disabled=true;
        document.getElementById('forpago1').selected=true;
        document.getElementById('detalle_cuenta').innerHTML="";
        document.getElementById('grupo').value="";
        var j;
        for (j=0;j<document.f1.Ecategoria.length;j++){
            if (document.f1.Ecategoria[j].checked)
                break;
        }
        categoria = document.f1.Ecategoria[j].value;
    }else{
        nombres=document.getElementById("Enombres").value;
        apellidos=document.getElementById("Eapellidos").value;
        idp=document.getElementById("Ridpersona").value;
        var j;
        for (j=0;j<document.f1.Ecategoria.length;j++){
            if (document.f1.Ecategoria[j].checked)
                break;
        }
        categoria = document.f1.Ecategoria[j].value;
        if(idp.length>5){
            return;
        }
        if(nombres.length<2){
            return;
        }
    }
    if (window.XMLHttpRequest) {
        bure = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        bure = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(bure!=null){
        var PATH = "/financeBank";
        var url = PATH + "/Sbuscarpersona?dni="+dni+"&categoria="+categoria+"&er="+er+"&ruc="+ruc+"&nom="+nombres+"&ap="+apellidos;
        bure.open("GET",url,true);
        bure.onreadystatechange = procesarRespuesta2;
        bure.send(null);
    }
}

var contenido;
function procesarRespuesta2(){
    contenido = document.getElementById('detalle');
    contenido.innerHTML="Cargando los datos...";
    if (bure.readyState==4 && bure.status==200) {
        var names;
        contenido.innerHTML = bure.responseText;
        if(document.getElementById('detalle').innerHTML=="R0"){
            document.getElementById("Ridpersona").value="";
        }else if(document.getElementById('detalle').innerHTML=="E0"){
            document.getElementById("Eidpersona").value="";
            document.getElementById('forpago2').disabled=true;
        }else{
            if(contenido.innerHTML.length<5)
                return;
            var params = document.getElementById('detalle').innerHTML.split('-');
            for(var i=0; i<params.length; i++) {
                names = params[i].split('=');
                if(names[0].length<2){
                    continue;
                }
                document.getElementById(names[0]).value= names[1];
                document.getElementById('forpago2').disabled=false;
            }
        }
    }
}

function procesar() {
    /********;***DATOS EMISOR ***************/
    var j;
    for (j=0;j<document.f1.Ecategoria.length;j++){
        if (document.f1.Ecategoria[j].checked)
            break;
    }
    var categoria_e = document.f1.Ecategoria[j].value;
    var idpersona_e = document.getElementById('Eidpersona').value;
    var dni_e = document.getElementById("Edni").value;
    if(dni_e.length>0){
        if(dni_e.length!=8){
            window.alert("El dni del Emisor tiene que ser de ocho digitos");
            return;
        }
    }
    var ruc_e = document.getElementById("Eruc").value;
    if(ruc_e.length>0 && idpersona_e==null){
        if(ruc_e.length!=11){
            window.alert("El RUC de Emisor tiene que ser de 11 digitos");
            return;
        }
    }
    var nombres_e = document.getElementById("Enombres").value;
    if(nombres_e.length<2){
        window.alert("El emisor debe ser más espicificado en el nombre/RS");
        return;
    }
    var apellidos_e = document.getElementById("Eapellidos").value;
    var email_e = document.getElementById("Eemail").value;
    var ubigeo_e = document.getElementById("Eubigeo").value;
    var telefono_e = document.getElementById("Etelefono").value;
    var celular_e = document.getElementById("Ecelular").value;
    var direccion_e = document.getElementById("Edireccion").value;
    /***********DATOS RECEPTOR ***************/
    var k;
    for (k=0;k<document.f1.Rcategoria.length;k++){
        if (document.f1.Rcategoria[k].checked)
            break;
    }
    var categoria_r = document.f1.Rcategoria[k].value;
    var idpersona_r = document.getElementById('Ridpersona').value;
    var dni_r = document.getElementById("Rdni").value;
    if(dni_r.length>0){
        if(dni_r.length!=8){
            window.alert("El DNI del RECEPTOR tiene que ser de 8 digitos");
            return;
        }
    }
    var ruc_r = document.getElementById("Rruc").value;
    if(ruc_r.length>0 && idpersona_r==null){
        if(ruc_r.length!=11){
            window.alert("El RUC de RECEPTOR tiene que ser de 11 digitos");
            return;
        }
    }
    var nombres_r = document.getElementById("Rnombres").value;
    if(nombres_r.length<2){
        window.alert("El receptor debe ser más espicificado en el nombre/RS");
        return;
    }
    var apellidos_r = document.getElementById("Rapellidos").value;
    var email_r = document.getElementById("Remail").value;
    var ubigeo_r = document.getElementById("Rubigeo").value;
    var telefono_r = document.getElementById("Rtelefono").value;
    var celular_r = document.getElementById("Rcelular").value;
    var direccion_r = document.getElementById("Rdireccion").value;
    /***************DATOS DEL GIRO**************************/
    var importe =document.getElementById("monto").value;
    if(importe==""){
        importe="0";
    }
    else{
        importe = importe.replace(/,/gi, "");
    }
    var j0;
    for (j0=0;j0<document.f1.forpago.length;j0++){
        if (document.f1.forpago[j0].checked)
            break;
    }
    var fpago_importe = document.f1.forpago[j0].value;
    var j1;
    for (j1=0;j1<document.f1.usuariop.length;j1++){
        if (document.f1.usuariop[j1].checked)
            break;
    }
    var pagador_comision = document.f1.usuariop[j1].value;
    var comision = document.getElementById('monto_comision').value;
    if(document.getElementById('Icomision').checked) {
        if(comision==""){
            comision=0;
        }
        else{
            comision = comision.replace(/,/gi, "");
        }
    }else{
        comision=0;
    }
    var j2;
    for (j2=0;j2<document.f1.formap.length;j2++){
        if (document.f1.formap[j2].checked)
            break;
    }
    var fpago_comision = document.f1.formap[j2].value;
    var cod_moneda = document.f1.moneda[document.f1.moneda.selectedIndex].value;
    var cod_filial = document.f1.filial_recepcion[document.f1.filial_recepcion.selectedIndex].value;
    var  idcuentapersona =0;
    if(document.f1.cuenta!=null && fpago_importe =="CUENTA"){
        var i1 =0;
        for (i1=0;i1<document.f1.cuenta.length;i1++){
            if (document.f1.cuenta[i1].checked)
                break;
        }
        if(i1==document.f1.cuenta.length){
            idcuentapersona=-1;
        } else{
            if(document.f1.cuenta.length==null){
                idcuentapersona= document.f1.cuenta.value;
            }else{
                idcuentapersona= document.f1.cuenta[i1].value;
            }
        }
    }
    if(importe==0){
        window.alert("¡¡EL MONTO A GIRAR DEBE SER MAYO A CERO!!");
    }else{
        if(idcuentapersona==-1){
            window.alert("¡¡DEBE SELECCIONAR UNA CUENTA!!");
        }else{
            var Monto = parseFloat(importe) + parseFloat(comision);
            var d = parseFloat(Saldo) - parseFloat(Monto);
            if(fpago_importe !="CUENTA"){
                d=9999;
            }
            if(d<0){
                window.alert("¡¡NO TIENE DINERO SUFICIENTE EN LA CUENTA!!");
            }else{
                var answer = window.confirm ("¿ESTA SEGURO REALIZAR EL GIRO?");
                if (answer) {
                    req2 = null;
                    if (window.XMLHttpRequest) {
                        req2 = new XMLHttpRequest();
                    } else if (window.ActiveXObject) {
                        req2 = new ActiveXObject("Microsoft.XMLHTTP");
                    }
                    if(req2!=null){
                        var PATH = "/financeBank";
                        var url = PATH + "/Sprocesar?"+
                        "categoria_e="+categoria_e+
                        "&idpersona_e="+idpersona_e+
                        "&dni_e="+dni_e+
                        "&ruc_e="+ruc_e+
                        "&nombres_e="+nombres_e+
                        "&apellidos_e="+apellidos_e+
                        "&email_e="+email_e+
                        "&ubigeo_e="+ubigeo_e+
                        "&telefono_e="+telefono_e+
                        "&celular_e="+celular_e+
                        "&direccion_e="+direccion_e+
                        "&categoria_r="+categoria_r+
                        "&idpersona_r="+idpersona_r+
                        "&dni_r="+dni_r+
                        "&ruc_r="+ruc_r+
                        "&nombres_r="+nombres_r+
                        "&apellidos_r="+apellidos_r+
                        "&email_r="+email_r+
                        "&ubigeo_r="+ubigeo_r+
                        "&telefono_r="+telefono_r+
                        "&celular_r="+celular_r+
                        "&direccion_r="+direccion_r+
                        "&importe="+importe +
                        "&fpago_importe="+fpago_importe +
                        "&comision="+comision +
                        "&fpago_comision="+fpago_comision +
                        "&pagador_comision="+pagador_comision +
                        "&cod_moneda="+cod_moneda+
                        "&cod_filial="+cod_filial+
                        "&idcuentapersona="+idcuentapersona;
                        req2.open("GET",url,true);
                        req2.send(null);
                        window.alert('¡¡SE REALIZO EL GIRO CON EXITO!!');
                        document.f1.submit();
                        window.location="SReciboGiro";
                    }else{
                        window.alert('¡¡NO SE PUEDE REALIZAR EL GIRO!!');
                    }
                }
            }
        }
    }
}

function detalleEmisor() {
    document.getElementById('detalle_cuenta').style.overflowY='auto';
    document.getElementById('detalle_cuenta').style.height='150px';
    var cod_moneda = document.f1.moneda[document.f1.moneda.selectedIndex].value;
    var idpersona_e = document.getElementById('Eidpersona').value;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2!=null){
        var PATH = "/financeBank";
        var url = PATH + "/SBuscarCuenta?idpersona_e="+idpersona_e+"&cod_moneda="+cod_moneda;
        req2.open("GET",url,true);
        req2.onreadystatechange = procesarRes;
        req2.send(null);
    }
}

function procesarRes(){
    contenido = document.getElementById('detalle_cuenta');
    contenido.innerHTML="Cargando los datos...";
    if (req2.readyState==4 && req2.status==200) {
        contenido.innerHTML = req2.responseText;
    }
}

function efectivo(){
    document.getElementById('detalle_cuenta').innerHTML="";
    document.getElementById('detalle_cuenta').style.overflowY='';
    document.getElementById('detalle_cuenta').style.height='';
}

function porcuenta(){
    detalleEmisor();
}

function calculo_comision(){
    // ********* CALCULANDO EL 1.5 % DEL MONTO ********
    var valor_comision =document.getElementById('p_comision').value
    valor_comision = valor_comision.replace(/,/gi, "")
    if(valor_comision>100){
        window.alert('NO PUEDE SER MAYOR QUE 100%');
        document.getElementById('p_comision').value=1.50;
        calulo_gironeto();
    }
    var valor =document.getElementById('monto').value
    valor = valor.replace(/,/gi, "")
    var res =parseFloat (valor)*parseFloat (valor_comision)/100
    res=res.toFixed(2);
    var Sres =""+res;
    if(document.getElementById('Icomision').checked){
        document.getElementById('monto_comision').value=currencyFormat2(Sres,',','.');
    } else{
        document.getElementById('monto_comision').innerHTML="";
    }
    document.getElementById('simbolo1').innerHTML= document.f1.moneda[document.f1.moneda.selectedIndex].label;
    document.getElementById('simbolo2').innerHTML= document.f1.moneda[document.f1.moneda.selectedIndex].label;
    document.getElementById('simbolo3').innerHTML= document.f1.moneda[document.f1.moneda.selectedIndex].label;
}

function calculo_comision2(){
    // ********* CALCULANDO EL S/ 5.00 DEL MONTO MINIMO ********
    var monto_comision =document.getElementById('monto_comision').value
    monto_comision = monto_comision.replace(/,/gi, "")
    var valor =document.getElementById('monto').value
    valor = valor.replace(/,/gi, "")
    var res =(parseFloat (monto_comision)*100)/parseFloat (valor)
    res=res.toFixed(2);
    var Sres =""+res;
    if(document.getElementById('Icomision').checked){
        document.getElementById('simbolo1').innerHTML= document.f1.moneda[document.f1.moneda.selectedIndex].label;
        document.getElementById('p_comision').value=currencyFormat2(Sres,',','.');
    } else{
        document.getElementById('p_comision').value="";
    }
    document.getElementById('simbolo1').innerHTML= document.f1.moneda[document.f1.moneda.selectedIndex].label;
    document.getElementById('simbolo2').innerHTML= document.f1.moneda[document.f1.moneda.selectedIndex].label;
    document.getElementById('simbolo3').innerHTML= document.f1.moneda[document.f1.moneda.selectedIndex].label;
}

function comision(){
    if(document.getElementById('Icomision').checked){
        document.getElementById('p_comision').disabled='';
        document.getElementById('monto_comision').disabled='';
        document.getElementById('usuariop1').checked=true;
        document.getElementById('formap1').checked=true;
        calculo_comision2();
        calulo_gironeto();
        document.getElementById('simbolo1').style.visibility='';
        document.getElementById('tr_usuariop').style.visibility='';
        document.getElementById('tr_formap').style.visibility='';
    }else{
        document.getElementById('p_comision').disabled=true;
        document.getElementById('monto_comision').disabled=true;
        calulo_gironeto();
        document.getElementById('monto_comision').value="5.00";
        document.getElementById('simbolo1').style.visibility='hidden';
        document.getElementById('tr_usuariop').style.visibility='hidden';
        document.getElementById('tr_formap').style.visibility='hidden';
    }
}

function rpemisor(){
    document.getElementById('tr_formap').style.visibility='';
}

function rpreceptor(){
    document.getElementById('tr_formap').style.visibility='hidden';
}

function calulo_gironeto(){
    var check;
    if(document.getElementById('Icomision').checked){
        check='ACTIVO';
    }else{
        check='INACTIVO';
    }
    var i;
    for (i=0;i<document.f1.usuariop.length;i++){
        if (document.f1.usuariop[i].checked)
            break;
    }
    var usp = document.f1.usuariop[i].value;
    var j;
    for (j=0;j<document.f1.formap.length;j++){
        if (document.f1.formap[j].checked)
            break;
    }
    var forp = document.f1.formap[j].value;
    if(check=='ACTIVO'){
        if(usp=='PEMISOR'){
            if(forp=='EFECTIVO'){
                document.getElementById('giro_neto').value=document.getElementById('monto').value;
                var valor =document.getElementById('monto').value;
                valor = valor.replace(/,/gi, "");
                var valor1 =document.getElementById('monto_comision').value;
                valor1 = valor1.replace(/,/gi, "");
                var res =parseFloat (valor)+parseFloat(valor1);
                res=res.toFixed(2);
                var Sres =""+res;
                document.getElementById('total').value=currencyFormat2(Sres,',','.');
            } else {
                document.getElementById('total').value=document.getElementById('monto').value;
                var valor =document.getElementById('monto').value;
                valor = valor.replace(/,/gi, "");
                var valor1 =document.getElementById('monto_comision').value;
                valor1 = valor1.replace(/,/gi, "");
                var res =parseFloat (valor)-parseFloat(valor1);
                res=res.toFixed(2);
                var Sres =""+res;
                document.getElementById('giro_neto').value=currencyFormat2(Sres,',','.');
            }
        }else{
            document.getElementById('giro_neto').value=document.getElementById('monto').value
            document.getElementById('total').value=document.getElementById('monto').value
        }
    }else{
        // ********* SIN COMISION ********
        document.getElementById('giro_neto').value=document.getElementById('monto').value
        document.getElementById('total').value=document.getElementById('monto').value
    }
}

function actmoneda(){
    document.getElementById('simbolo1').innerHTML= document.f1.moneda[document.f1.moneda.selectedIndex].label;
    document.getElementById('simbolo2').innerHTML= document.f1.moneda[document.f1.moneda.selectedIndex].label;
    document.getElementById('simbolo3').innerHTML= document.f1.moneda[document.f1.moneda.selectedIndex].label;
    efectivo();
}

var Saldo=-1;
function cuentaSaldo(n){
    Saldo=n;
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
            window.alert('¡¡ DATOS ENVIADOS !!');
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

var req2;
function sExtornar(id,tipo) {
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

