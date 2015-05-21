/**
 *
 * @author admin
 */

function activarTicket(){
    var answer = window.confirm ("¿Si Desea Primero suba imagen de foto o firma del Cliente?");
    if (answer){
        document.frmiraticket.submit();
    }
}

function tean(){
    var va=document.getElementById("txtTasa").value;
    document.getElementById("im").innerHTML=round(100*(Math.pow(eeparseFloat(va)/100 + 1, 1.0 / 12) - 1.0), 4);
    document.getElementById("tea").innerHTML=round(100*(Math.pow(1+eeparseFloat(document.getElementById("im").innerHTML)/100,12)-1), 4);
    //document.getElementById("ia").innerHTML=round(100*(Math.pow(1 + eeparseFloat(va) / 1200, 12) - 1.0),4);
    document.getElementById("id").innerHTML=round(100*(Math.pow(eeparseFloat(va)/100+ 1, 1.0 / 360) - 1.0), 8);
}

var req4;
function activarformfichero(){
    req4 = null;
    if (window.XMLHttpRequest) {
        req4 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req4 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req4!=null){
        var PATH = "/financeBank";
        var url = PATH + "/uploadFichero";
        req4.open("GET",url,true);
        req4.send(null);
    }
}

function pprocesarRespuesta(){
    contenido = document.getElementById('divficheroo');
    contenido.innerHTML="Cargando los datos...";
    if (req4.readyState==4 && req4.status==200) {
        contenido.innerHTML = req4.responseText;
    }
}

var req2;
function buscarcuenta(parameter,tipoB){
    req2 = null;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2!=null){
        req2.onreadystatechange = procesarRespuesta;
        var PATH = "/financeBank";
        var url = PATH + "/SNuevaCuenta?parameter="+parameter+"&tipoB="+tipoB;
        req2.open("GET",url,true);
        req2.send(null);
    }
}

function procesarRespuesta(){
    contenido = document.getElementById('divtemporal');
    contenido.innerHTML="Cargando los datos...";
    if (req2.readyState==4 && req2.status==200) {
        contenido.innerHTML = req2.responseText;
        var cad;
        cad=document.getElementById("divtemporal").innerHTML;
        if(cad!="NO ENCONTRADO"){
            document.getElementById('txtPersonaExiste').value="SI EXISTE";
            var tablita = cad.split("/");
            var direccion="",celular="",telefono="",ubigeo="",email="",ruc=document.getElementById("txtRUC").value;
            document.getElementById('txtdatapersona').value=tablita[0];
            document.getElementById('txtDNI').value=tablita[1];
            if(tablita[2]!=null){
                ruc=tablita[2];
            }
            document.getElementById('txtRUC').value=ruc;
            document.getElementById('txtNombre').value=tablita[3].replace("&amp;", "&", "gi");
            document.getElementById('txtApellidos').value=tablita[4];
            if(tablita[5]!=null){
                email=tablita[5];
            }
            document.getElementById('txtEmail').value=email;
            if(tablita[6]!=null){
                ubigeo=tablita[6];
            }
            document.getElementById('txtUbigeo').value=ubigeo;
            if(tablita[7]!=null){
                telefono=tablita[7];
            }
            document.getElementById('txtTelefono').value=telefono;
            if(tablita[8]!=null){
                celular=tablita[8];
            }
            document.getElementById('txtCelular').value=celular;
            if(tablita[9]!=null){
                direccion=tablita[9];
            }
            document.getElementById('txtDireccion').value=direccion;
        }else{
            document.getElementById('txtPersonaExiste').value="NO EXISTE";
            document.getElementById('txtdatapersona').value="";
            document.getElementById('txtRUC').value=document.getElementById("txtRUC").value;
            document.getElementById('txtNombre').value="";
            document.getElementById('txtApellidos').value="";
            document.getElementById('txtEmail').value="";
            document.getElementById('txtUbigeo').value="";
            document.getElementById('txtTelefono').value="";
            document.getElementById('txtCelular').value="";
            document.getElementById('txtDireccion').value="";
        }

    }
}

function mostrarDataAPF(){
    var tipocuenta="";
    tipocuenta = document.getElementById("selTipoCuenta") [document.getElementById("selTipoCuenta").selectedIndex].value;
    if(tipocuenta=="CPF"){
        document.getElementById("divNumDia").innerHTML="<font color='#385b88' style='font-size: 12px;'><b>Numero de Dias:</b></font>";
        document.getElementById("divNumeroDias").innerHTML="<input type='text' id='txtNumDias' name='txtNumDias' onkeypress='return solonumeros(event);' value='' maxlength='20' onblur='calcularTiempoPF();'>";
        document.getElementById("divTiempo").innerHTML="<font color='#385b88' style='font-size: 12px;'><b>Tiempo de Plazo Fijo:</b></font>";
        document.getElementById("divTipoTiempo").innerHTML="<input type='text' id='txtTiempoPF' name='txtTiempoPF' onkeypress='return solodecimales(event);' value='' maxlength='50' readonly>";
    }else{
        document.getElementById("divNumDia").innerHTML="";
        document.getElementById("divNumeroDias").innerHTML="";
        document.getElementById("divTitulo").innerHTML="";
        document.getElementById("divTiempo").innerHTML="";
        document.getElementById("divTipoTiempo").innerHTML="";
    }
}

var req5;
function calcularTiempoPF(){
    var numdias;
    numdias=document.getElementById("txtNumDias").value;
    req5 = null;
    if (window.XMLHttpRequest) {
        req5 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req5 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req5 != null){
        req5.onreadystatechange = pprocesar;
        var PATH = "/financeBank";
        var url = PATH + "/SCalcularPF?numdias="+numdias;
        req5.open("GET",url,true);
        req5.send(null);
    }
}

function pprocesar(){
    contenido = document.getElementById('divf');
    contenido.innerHTML="Cargando los datos...";
    if (req5.readyState==4 && req5.status==200) {
        contenido.innerHTML = req5.responseText;
    }
    document.getElementById("txtTiempoPF").value=document.getElementById("divf").innerHTML;
}

function activarform(){
    if(document.getElementById("txtDNI").value!="" || document.getElementById("txtRUC").value!=""){
        var cat;
        if(document.getElementById("txtDNI").value!="")
            cat="N";
        else{
            cat="J";
        }
        var j;
        for (j=0;j<document.formulario.txtcategoria.length;j++){
            if (document.formulario.txtcategoria[j].checked)
                break;
        }
        var categoria = document.formulario.txtcategoria[j].value;
        if(cat=="N"){
            if(document.getElementById("txtNombre").value!=""){
                if(document.getElementById("txtApellidos").value!=""){
                    if(document.getElementById("txtMontoInicial").value!=""){
                        if(document.getElementById("txtTasa").value!=""){
                            if("CPF"==document.getElementById("selTipoCuenta").value){
                                if(document.getElementById("txtNumDias").value!=""){
                                    if(document.getElementById("txtTiempoPF").value!=""){
                                        var answer = confirm ("¿ESTA SEGURO QUE DESEA REGISTRAR NUEVA CUENTA?");
                                        if (answer){
                                            document.formulario.action="SGenerarNuevaCuenta?selCategoriaPer="+categoria;
                                            document.formulario.submit();
                                            window.alert("Su cuenta se ha creado exitosamente");
                                            document.getElementById("cmdGuardar").disabled=true;
                                        }
                                    }else{
                                        alert("Por favor ingrese Fecha Plazo Fijo");
                                        document.formulario.txtTiempoPF.focus();
                                    }
                                }else{
                                    alert("Por favor ingrese numero de dias Plazo Fijo");
                                    document.formulario.txtNumDias.focus();
                                }
                            }else{
                                var answer = confirm ("¿ESTA SEGURO QUE DESEA REALIZAR REGISTRO?");
                                if (answer){
                                    document.formulario.action="SGenerarNuevaCuenta?selCategoriaPer="+categoria;
                                    document.formulario.submit();
                                    alert("Su cuenta se ha creado exitosamente");
                                    document.getElementById("cmdGuardar").disabled=true;
                                }
                            }
                        }else{
                            alert("Por favor Ingrese Interes");
                            document.formulario.txtTasa.focus();
                        }
                    }else{
                        alert("Por favor Ingrese Monto Inicial");
                        document.formulario.txtMontoInicial.focus();
                    }
                }else {
                    alert("Por favor Ingrese sus Apellidos");
                    document.formulario.txtApellidos.focus();
                }
            }else{
                alert("Por favor Ingrese su Nombre");
                document.formulario.txtNombre.focus();
            }
        }else{
            if(document.getElementById("txtNombre").value!=""){
                if(document.getElementById("txtMontoInicial").value!=""){
                    if(document.getElementById("txtTasa").value!=""){
                        if("CPF"==document.getElementById("selTipoCuenta").value){
                            if(document.getElementById("txtNumDias").value!=""){
                                if(document.getElementById("txtTiempoPF").value!=""){
                                    var answer = confirm ("¿ESTA SEGURO QUE DESEA REGISTRAR NUEVA CUENTA?");
                                    if (answer){
                                        document.formulario.action="SGenerarNuevaCuenta?selCategoriaPer="+categoria;
                                        document.formulario.submit();
                                        alert("Su cuenta se ha creado exitosamente");
                                        document.getElementById("cmdGuardar").disabled=true;
                                    }
                                }else{
                                    alert("Por favor ingrese Fecha Plazo Fijo");
                                    document.formulario.txtTiempoPF.focus();
                                }
                            }else{
                                alert("Por favor ingrese numero de dias Plazo Fijo");
                                document.formulario.txtNumDias.focus();
                            }
                        }else{
                            var answer = confirm ("¿ESTA SEGURO QUE DESEA REALIZAR REGISTRO?");
                            if (answer){
                                document.formulario.action="SGenerarNuevaCuenta?selCategoriaPer="+categoria;
                                document.formulario.submit();
                                window.alert("Su cuenta se ha creado exitosamente");
                                document.getElementById("cmdGuardar").disabled=true;
                            }
                        }
                    }else{
                        alert("Por favor Ingrese Interes");
                        document.formulario.txtTasa.focus();
                    }
                }else{
                    alert("Por favor Ingrese Monto Inicial");
                    document.formulario.txtMontoInicial.focus();
                }
            }else {
                alert("Por favor Ingrese Razón Social");
                document.formulario.txtApellidos.focus();
            }
        }
    }else{
        alert("Por favor Ingrese su DNI/RUC");
        document.formulario.txtDNI.focus();
    }
}


//var req5;
function enviarsession(){
    var foto="",firma="";
    req5 = null;
    if (window.XMLHttpRequest) {
        req5 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req5 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req5!=null){
        var PATH = "/financeBank";
        var url = PATH + "/SFicheroup?sfoto="+foto+"&sfirma="+firma;
        req5.open("GET",url,true);
        req5.send(null);
    }
}

function limpiar(){
    document.getElementById("cmdsubir").disabled=true;
}

function waa(){
    var asa;
    window.alert(asa);
}

function cleanx(er){
    this.document.getElementById(er+'idpersona').value="";
    this.document.getElementById(er+'RUC').value="";
    this.document.getElementById(er+'DNI').value="";
    this.document.getElementById(er+'Nombre').value="";
    this.document.getElementById(er+'Apellidos').value="";
    this.document.getElementById(er+'Email').value="";
    this.document.getElementById(er+'Ubigeo').value="";
    this.document.getElementById(er+'Telefono').value="";
    this.document.getElementById(er+'Celular').value="";
    this.document.getElementById(er+'Direccion').value="";
}

function naturalx(er){
    cleanx(er)
    this.document.getElementById(er+'RUC').disabled=true;
    this.document.getElementById(er+'tr_dni').style.visibility='';
    this.document.getElementById(er+'td_nombres').style.display='';
    this.document.getElementById(er+'td_razonsocial').style.display='none';
    this.document.getElementById(er+'tr_apellidos').style.visibility='';
    this.document.getElementById(er+'tr_ubigeo').style.visibility='';
    this.document.getElementById(er+'tr_dni').style.visibility='';
}

function juridicax(er){
    this.document.getElementById(er+'RUC').disabled='';
    cleanx(er)
    this.document.getElementById(er+'tr_dni').style.visibility='hidden';
    this.document.getElementById(er+'td_nombres').style.display='none';
    this.document.getElementById(er+'td_razonsocial').style.display='';
    this.document.getElementById(er+'tr_apellidos').style.visibility='hidden';
    this.document.getElementById(er+'tr_ubigeo').style.visibility='hidden';
    this.document.getElementById(er+'tr_dni').style.visibility='hidden';
}

function buscarpersonax(er){
    var dni = document.getElementById(er+"DNI").value;
    var ruc = document.getElementById(er+"RUC").value;
    var j;
    for (j=0;j<document.formulario.txtcategoria.length;j++){
        if (document.formulario.txtcategoria[j].checked)
            break;
    }
    var categoria = document.formulario.txtcategoria[j].value;
    req2 = false;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2!=null){
        var PATH = "/financeBank";
        var url = PATH + "/Sbuscarpersona?dni="+dni+"&categoria="+categoria+"&er="+er+"&ruc="+ruc;
        req2.open("GET",url,true);
        req2.onreadystatechange = procesarRespuesta2x;
        req2.send(null);

    }
}

function procesarRespuesta2x(){
    contenido = document.getElementById('detalle');
    contenido.innerHTML="Cargando los datos...";
    if (req2.readyState==4 && req2.status==200) {
        var values = new Array();
        var names;
        contenido.innerHTML = req2.responseText;
        var params = document.getElementById('detalle').innerHTML.split('-');
        for(i=0; i<params.length; i++) {
            names = params[i].split('=');
            values[names[0]] = names[1];
            document.getElementById(names[0]).value= names[1];
        }
    }
}