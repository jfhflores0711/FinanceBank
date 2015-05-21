/* 
 * Oscar
 */

var ant;
var contenido;
var PATH;
var url;
function selected(n){
    if(ant==null){
        document.getElementById('a'+n).style.background="#cfe0ff";
    }
    else{
        document.getElementById('a'+ant).style.background="#FFFFFF";
        document.getElementById('a'+n).style.background="#cfe0ff";
    }
    ant=n;
}

var req;
function mostrarcatalogo(){
    ant=null;    
    var b=document.getElementById("selcatalogo").value;
    if (window.XMLHttpRequest) {
        req = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        req = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req!=null){
        req.onreadystatechange = procesarRespuesta;
        PATH = "/financeBank";
        url = PATH + "/SAdminCatalogo?b="+b+"&a=b";
        req.open("GET",url,true);
        req.send(null);
    }
}

function procesarRespuesta(){
    contenido = document.getElementById('divCatalogoElements');
    contenido.innerHTML="Cargando los datos...";
    if (req.readyState==4 && req.status==200) {
        contenido.innerHTML = req.responseText;
    }
}
var req2;
function verDetalleCatalogo(a){
    var b=document.getElementById("selcatalogo").value;
    document.getElementById("cmdGuardar").disabled=true;
    document.getElementById("cmdActualizar").disabled=false;
    document.getElementById("cmdEliminar").disabled=false;
    req2 = false;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2!=null){
        req2.onreadystatechange = procesarRespuesta2;
        var PATH = "/financeBank";
        var url = PATH + "/SAdminCatalogoDetalle?a="+a+"&b="+b;
        req2.open("GET",url,true);
        req2.send(null);
    }
}

function procesarRespuesta2(){
    contenido = document.getElementById('divCatalogoDetalle');
    contenido.innerHTML="Cargando los datos...";
    if (req2.readyState==4 && req2.status==200) {
        contenido.innerHTML = req2.responseText;
    }
}

function createULfi(codfi){
    var diUL=document.getElementById("ulfi");
    var ulfit=document.getElementById("ulfit").value;
    if(ulfit!="1"){
        diUL.innerHTML="<input id='ulfit' type='hidden' value='0'><select disabled id='selfilial' name='selfilial'><option selected value='" + codfi + "'>" + codfi + "</option></select>"
    }
}

var req1;
function mostrarcaja(){
    ant=null;
    var codfil=document.getElementById("selmiFilial").value;
    if(codfil!='0'){
        document.getElementById("ulfit").value="0";
        createULfi(codfil);
        document.getElementById("cmdGuardar").disabled=true;
        document.getElementById("cmdEliminar").disabled=true;
        document.getElementById("cmdActualizar").disabled=true;
        req1 = null;
        if (window.XMLHttpRequest) {
            req1 = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            req1 = new ActiveXObject("Microsoft.XMLHTTP");
        }
        if(req1!=null){
            req1.onreadystatechange = procesarRespuesta1;
            var PATH = "/financeBank";
            var url = PATH + "/SMostrarCaja?codfili="+codfil;
            req1.open("GET",url,true);
            req1.send(null);
        }
    }
}

function procesarRespuesta1(){
    contenido = document.getElementById('divMisCajas');
    contenido.innerHTML="Cargando los datos...";
    if (req1.readyState==4 && req1.status==200) {
        contenido.innerHTML = req1.responseText;
    }
}

var r1;
function mostrarpersc(){
    ant=null;
    var codfil=document.getElementById("selmiFilial").value;
    var tipoBusqueda,valor;
    tipoBusqueda="_";
    valor="_";
    if(codfil!='0'){
        req1 = false;
        if (window.XMLHttpRequest) {
            req1 = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            req1 = new ActiveXObject("Microsoft.XMLHTTP");
        }
        if(req1!=null){
            req1.onreadystatechange = procesarR1;
            var PATH = "/financeBank";
            var url = PATH + "/SMostrarPersona?a="+codfil+"&b="+tipoBusqueda+"&c="+valor;
            req1.open("GET",url,true);
            req1.send(null);
        }
    }
}

function procesarR1(){
    contenido = document.getElementById('divMisCajas');
    contenido.innerHTML="Cargando los datos...";
    if (req1.readyState==4 && req1.status==200) {
        contenido.innerHTML = req1.responseText;
    }
}

var req2x;
function buscarcuenta(parameter,tipoB){
    req2x = null;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2!=null){
        req2.onreadystatechange = procesarRespuestax;
        var PATH = "/financeBank";
        var url = PATH + "/SNuevaCuenta?parameter="+parameter+"&tipoB="+tipoB;
        req2.open("GET",url,true);
        req2.send(null);
    }
}

function procesarRespuestax(){
    contenido = document.getElementById('divtemporal');
    contenido.innerHTML="Cargando los datos...";
    if (req2.readyState==4 && req2.status==200) {
        contenido.innerHTML = req2.responseText;
        var cad;
        cad=document.getElementById("divtemporal").innerHTML;
        if(cad!="NO ENCONTRADO"){
            var tablita = cad.split("/");
            var direccion="",celular="",telefono="",ubigeo="",email="",ruc=document.getElementById("txtRUC").value;
            document.getElementById('txtId').value=tablita[0];
            document.getElementById('txtDNI').value=tablita[1];
            if(tablita[2]!=null){
                ruc=tablita[2];
            }
            document.getElementById('txtRUC').value=ruc;
            document.getElementById('txtNombre').value=tablita[3];
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
            document.getElementById('txtId').value="";
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

var req7;
function mostrarpersona(){
    ant=null;
    var tipoBusqueda,valor;
    tipoBusqueda=document.getElementById("selPersona").value;
    valor=document.getElementById("txtPersona").value;
    req7 = false;
    if (window.XMLHttpRequest) {
        req7 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req7 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req7!=null){
        req7.onreadystatechange = procesarRespuesta7;
        var PATH = "/financeBank";
        var url = PATH + "/SMostrarPersona?b="+tipoBusqueda+"&c="+valor;
        req7.open("GET",url,true);
        req7.send(null);
    }
}

function procesarRespuesta7(){
    contenido = document.getElementById('divMisPersonas');
    contenido.innerHTML="Cargando los datos...";
    if (req7.readyState==4 && req7.status==200) {
        contenido.innerHTML = req7.responseText;
    }
}

function validarForm() {
    var a= document.getElementById("txtN").value;
    if(a.length<1){
        window.alert("Falta el nombre del Crédito");
        document.getElementById("txtN").focus();
        return false;
    }
    var b=  document.getElementById("txtD").value;
    if(b.length<1){
        window.alert("Ingrese alguna Descripción!");
        document.getElementById("txtD").focus();
        return false;
    }
    var c= document.getElementById("txtM").value * 1;
    if(isNaN(c)==true){
        window.alert("Campo Monto máximo no válido");
        document.getElementById("txtM").focus();
        return false;
    }
    var d=document.getElementById("txtI").value * 1;
    if(isNaN(d)==true){
        window.alert("Campo Cuotas máximo no válido");
        document.getElementById("txtI").focus();
        return false;
    }
    if((d*10)%10>0){
        window.alert("Campo Cuotas máxima no debe tener decimales");
        document.getElementById("txtI").focus();
        return false;
    }
    var e=document.getElementById("txtA").value * 1;
    if(isNaN(e)==true){
        window.alert("Campo Interés máxima no válido");
        document.getElementById("txtA").focus();
        return false;
    }
    var f=document.getElementById("txtB").value * 1;
    if(isNaN(f)){
        window.alert("Campo Interés Moratoria máxima no válido");
        document.getElementById("txtB").focus();
        return false;
    }
    var g=document.getElementById("txtG").value * 1;
    if(isNaN(g)==true){
        window.alert("Campo Cuotas mínima no válido");
        document.getElementById("txtG").focus();
        return false;
    }
    if((g*10)%10>0){
        window.alert("Campo Cuotas mínima no debe tener decimales");
        document.getElementById("txtG").focus();
        return false;
    }
    var h= document.getElementById("txtE").value * 1;
    if(isNaN(h)==true){
        window.alert("Campo Monto mínimo no válido");
        document.getElementById("txtE").focus();
        return false;
    }
    var i = document.getElementById("txtH").value * 1;
    if(isNaN(i)==true){
        window.alert("Campo Interés mínima no válido");
        document.getElementById("txtH").focus();
        return false;
    }
    var j=document.getElementById("txtJ").value * 1;
    if(isNaN(j)==true){
        window.alert("Campo Días Entre Cuotas no válido");
        document.getElementById("txtJ").focus();
        return false;
    }
    if((j*10)%10>0){
        window.alert("Campo Días entre Cuotas no debe tener decimales");
        document.getElementById("txtJ").focus();
        return false;
    }
    return true;
}

function nuevoCatalogo(){
    var catalogo=document.getElementById("txtcatalogo").value;
    if(catalogo=="FILIAL"){
        document.getElementById("txtCodigo").value="";
        document.getElementById("txtNombre").value="";
        document.getElementById("txtNombre").disabled=false;
        document.getElementById("txtDireccion").value="";
        document.getElementById("txtDireccion").disabled=false;
        document.getElementById("selEstado").value="ACTIVO";
        document.getElementById("txtTelefono").value="";
        document.getElementById("txtTelefono").disabled=false;
        document.getElementById("txtCodNumCuenta").value="";
        document.getElementById("txtCodNumCuenta").readonly=true;
        document.getElementById("cmdGuardar").disabled=false;
        document.getElementById("cmdEliminar").disabled=true;
    } else if(catalogo=="CAJA"){
        var catalogoF=document.getElementById("selmiFilial").value;
        if(catalogoF=='0'){
            window.alert("Seleccione un filial");
        }else{
            createULfi(catalogoF);
            document.getElementById("txtCodigo").value="";
            document.getElementById("txtNombre").value="";
            document.getElementById("txtNombre").disabled=false;
            document.getElementById("selTipo").value="";
            document.getElementById("selEstado").value="ACTIVO";
            document.getElementById("cmdGuardar").disabled=false;
            document.getElementById("cmdEliminar").disabled=true;
        }
    } else if(catalogo=="A"){
        document.getElementById("txtN").value="";
        document.getElementById("txtN").disabled=false;
        document.getElementById("txtD").value="";
        document.getElementById("txtD").disabled=false;
        document.getElementById("txtM").value="";
        document.getElementById("txtM").disabled=false;
        document.getElementById("txtI").value="";
        document.getElementById("txtI").disabled=false;
        document.getElementById("txtA").value="79.586";
        document.getElementById("txtA").disabled=false;
        document.getElementById("txtB").value="70.000";
        document.getElementById("txtB").disabled=false;
        document.getElementById("txtG").value="2";
        document.getElementById("txtG").disabled=false;
        document.getElementById("txtE").value="10.00";
        document.getElementById("txtE").disabled=false;
        document.getElementById("txtH").value="23.872";
        document.getElementById("txtH").disabled=false;
        document.getElementById("txtJ").value="30";
        document.getElementById("txtJ").disabled=false;
        document.getElementById("selJ").disabled=false;
        document.getElementById("selJ").value="2 NO";
        document.getElementById("cmdGuardar").disabled=false;
        document.getElementById("cmdEliminar").disabled=true;
    } else if(catalogo=="PERSONA"){
        document.getElementById("txtId").value="";
        document.getElementById("txtDNI").value="";
        document.getElementById("txtDNI").disabled=false;
        document.getElementById("txtRUC").value="";
        document.getElementById("txtRUC").disabled=false;
        document.getElementById("txtNombre").value="";
        document.getElementById("txtNombre").disabled=false;
        document.getElementById("txtApellidos").value="";
        document.getElementById("txtApellidos").disabled=false;
        document.getElementById("txtEmail").value="";
        document.getElementById("txtEmail").disabled=false;
        document.getElementById("txtUbigeo").value="";
        document.getElementById("txtUbigeo").disabled=false;
        document.getElementById("txtTelefono").value="";
        document.getElementById("txtTelefono").disabled=false;
        document.getElementById("txtCelular").value="";
        document.getElementById("txtCelular").disabled=false;
        document.getElementById("txtUrlFoto").value="";
        document.getElementById("txtUrlFirma").value="";
        document.getElementById("txtDireccion").value="";
        document.getElementById("txtDireccion").disabled=false;
        document.getElementById("selEstado").value="ACTIVO";
        document.getElementById("cmdGuardar").disabled=false;
        document.getElementById("cmdEliminar").disabled=true;
    }
    document.getElementById("cmdActualizar").disabled=true;
}

var req3;
function guardarCatalogo(){
    var catalogo=document.getElementById("txtcatalogo").value;
    var txtNombre="",txtDireccion="",selEstado="",txtTelefono="",seldistrito="";
    var selTipo,selfilial;
    var txtDNI="",txtRUC="",txtApellidos="",txtEmail="",txtUbigeo="";
    var txtCelular="",txtUrlFoto="",txtUrlFirma="";
    var selCategPersona="";
    var txtN,txtD="",txtM="",txtI="",txtA="",txtB="",txtG="",txtE="",txtH="",txtJ="",selJ="",selI="";
    if(catalogo=="FILIAL"){        
        txtNombre=document.getElementById("txtNombre").value;
        txtDireccion=document.getElementById("txtDireccion").value;
        selEstado=document.getElementById("selEstado").value;
        txtTelefono=document.getElementById("txtTelefono").value;
        seldistrito=document.getElementById("seldistrito").value;
    } else if(catalogo=="CAJA"){
        selTipo=document.getElementById("selTipo").value;
        selfilial=document.getElementById("selfilial").value;
        txtNombre=document.getElementById("txtNombre").value;
        var pok=document.getElementById("pok").value;
        if(pok!="NO" && selTipo=="PRIMARY"){
            window.alert("Ya existe una caja Primaria, no se permite \n más de una caja primaria.");
            return;
        }
        selEstado=document.getElementById("selEstado").value;
    } else if(catalogo=="A"){
        if(validarForm()){
            txtN=document.getElementById("txtN").value;
            txtD=document.getElementById("txtD").value;
            txtM=document.getElementById("txtM").value;
            txtI=document.getElementById("txtI").value;
            txtA=document.getElementById("txtA").value;
            txtB=document.getElementById("txtB").value;
            txtG=document.getElementById("txtG").value;
            txtE=document.getElementById("txtE").value;
            txtH=document.getElementById("txtH").value;
            txtJ=document.getElementById("txtJ").value;
            selI=document.getElementById("selJ").value;
            selJ=document.getElementById("selJ").value;
        }else{
            return;
        }
    } else if(catalogo=="PERSONA"){
        if(document.getElementById("txtId").length>5){
            actualizarCatalogo();
            return;
        }
        txtDNI=document.getElementById("txtDNI").value;
        txtRUC=document.getElementById("txtRUC").value;
        txtNombre=document.getElementById("txtNombre").value;
        txtApellidos=document.getElementById("txtApellidos").value;
        txtEmail=document.getElementById("txtEmail").value;
        txtUbigeo=document.getElementById("txtUbigeo").value;
        txtTelefono=document.getElementById("txtTelefono").value;
        txtCelular=document.getElementById("txtCelular").value;
        txtUrlFoto=document.getElementById("txtUrlFoto").value;
        txtUrlFirma=document.getElementById("txtUrlFirma").value;
        txtDireccion=document.getElementById("txtDireccion").value;
        selEstado=document.getElementById("selEstado").value;
        selCategPersona=document.getElementById("selCategPersona").value;
    }
    if (window.XMLHttpRequest) {
        req3 = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        req3 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req3!=null){
        if(catalogo=="FILIAL"){
            if(document.getElementById("txtNombre").value!=""){
                if(document.getElementById("seldepartamento").value!="0"){
                    if(document.getElementById("selprovincia").value!="0"){
                        if(document.getElementById("seldistrito").value!="0"){
                            answer = confirm ("¿ESTA SEGURO QUE DESEA REGISTRAR LA "+catalogo+"?");
                            if (answer){
                                req3.onreadystatechange = procesarRespuesta3;
                                PATH = "/financeBank";
                                url = PATH + "/SIngresarCatalogo?"+
                                "b="+filtraParametroRequestC(txtNombre)+"&c="+filtraParametroRequestC(txtDireccion)+
                                "&d="+selEstado+"&e="+filtraParametroRequestC(txtTelefono)+"&ta=b"+
                                "&f="+seldistrito+"&a="+catalogo;
                            }
                        }else{
                            window.alert("Seleccione Distrito");
                            document.getElementById("seldistrito").focus();
                        }
                    }else{
                        window.alert("Seleccione Provincia");
                        document.getElementById("selprovincia").focus();
                    }
                }else{
                    window.alert("Seleccione Departamento");
                    document.getElementById("seldepartamento").focus();
                }
            }else{
                window.alert("ingrese Nombre Filial");
                document.getElementById("txtNombre").focus();
            }
        } else if(catalogo=="CAJA"){
            if(document.getElementById("txtNombre").value!=""){
                answer = window.confirm ("¿ESTA SEGURO QUE DESEA REGISTRAR LA "+catalogo+"?");
                if (answer){
                    req3.onreadystatechange = procesarRespuesta3;
                    PATH = "/financeBank";
                    url = PATH + "/SIngresarCatalogo?"+
                    "b="+filtraParametroRequestC(txtNombre)+"&h="+selTipo+"&d="+selEstado+
                    "&g="+selfilial+"&a="+catalogo;
                }
            }else{
                window.alert("ingrese Nombre Caja");
                document.getElementById("txtNombre").focus();
            }
        } else if(catalogo=="A"){
            answer = window.confirm ("¿ESTA SEGURO QUE DESEA REGISTRAR NUEVA LÍNEA DE CRÉDITO?");
            if (answer){
                req3.onreadystatechange = procesarRespuesta3;
                PATH = "/financeBank";
                url = PATH + "/SIngresarCatalogo?"+
                "l="+filtraParametroRequestC(txtN)+"&b="+filtraParametroRequestC(txtD)+"&c="+txtM+"&m="+selI+
                "&d="+txtI+"&e="+txtA+"&f="+txtB+"&g="+txtG+"&h="+txtE+"&i="+txtH+"&j="+txtJ+"&k="+selJ+"&a="+catalogo;
            }
        } else if(catalogo=="PERSONA"){
            if(document.getElementById("txtDNI").value!="" || document.getElementById("txtRUC").value!=""){
                if(document.getElementById("txtNombre").value!=""){
                    answer = confirm ("¿ESTA SEGURO QUE DESEA REGISTRAR LA "+catalogo+"?");
                    if (answer) {
                        req3.onreadystatechange = procesarRespuesta3;
                        PATH = "/financeBank";
                        url = PATH + "/SIngresarCatalogo?i="+txtDNI+"&j="+txtRUC+
                        "&b="+filtraParametroRequestC(txtNombre)+"&k="+filtraParametroRequestC(txtApellidos)+"&l="+txtEmail+
                        "&m="+txtUbigeo+"&e="+filtraParametroRequestC(txtTelefono)+"&n="+filtraParametroRequestC(txtCelular)+
                        "&o="+txtUrlFoto+"&p="+txtUrlFirma+"&c="+filtraParametroRequestC(txtDireccion)+
                        "&d="+selEstado+"&q="+selCategPersona+"&a="+catalogo;
                    }
                }else{
                    window.alert("ingrese Nombre o Apellido");
                    document.getElementById("txtNombre").focus();
                }
            }else{
                window.alert("ingrese DNI o RUC");
                document.getElementById("txtDNI").focus();
            }
        }
        req3.open("GET",url,true);
        req3.send(null);
    }
}

function procesarRespuesta3(){
    contenido = document.getElementById('divrptaInsertar');
    contenido.innerHTML="Cargando los datos...";
    if (req3.readyState==4 && req3.status==200) {
        contenido.innerHTML = req3.responseText;
        var existe=document.getElementById("txtExisteCat").value;
        if (existe=="NO"){
            var respuesta=document.getElementById("txtRptaInsertar").value
            if(respuesta=="SI"){
                window.alert("El registro se ha realizado SATISFACTORIAMENTE!!!");
                document.getElementById("cmdGuardar").disabled=true;
                document.getElementById("cmdActualizar").disabled=false;
                nuevoCatalogo();
                mostrarcatalogo();
            }else{
                window.alert("El registro NO SE HA REALIZADO");
            }
        } else if(existe=="SI"){
            window.alert("El registro YA EXISTE (el CODIGO, DNI o RUC ya EXISTE)");
        }
    }
}

var req6;
var answer;
function actualizarCatalogo(){
    var catalogo=document.getElementById("txtcatalogo").value;
    var txtDNI="",txtRUC="",txtApellidos="",txtEmail="",txtUbigeo="";
    var txtCelular="",txtUrlFoto="",txtUrlFirma="";
    var selCategPersona="", txtId="";
    var txtCodigo="",txtNombre="",txtDireccion="",selEstado="",txtTelefono="";
    var txtCodNumCuenta="",seldistrito="";
    var txtN,txtD="",txtM="",txtI="",txtA="",txtB="",txtG="",txtE="",txtH="",txtJ="",selJ="",selI="";
    if(catalogo=="FILIAL"){
        txtCodigo=document.getElementById("txtCodigo").value;
        txtNombre=document.getElementById("txtNombre").value;
        txtDireccion=document.getElementById("txtDireccion").value;
        selEstado=document.getElementById("selEstado").value;
        txtTelefono=document.getElementById("txtTelefono").value;
        if(txtTelefono.length>10){
            window.alert("Máximo se permiten 10 campos");
            return;
        }
        txtCodNumCuenta=document.getElementById("txtCodNumCuenta").value;
        seldistrito=document.getElementById("seldistrito").value;
    } else if(catalogo=="CAJA"){
        var selTipo="";
        var selfilial="";
        txtCodigo=document.getElementById("txtCodigo").value;
        txtNombre=document.getElementById("txtNombre").value;
        selTipo=document.getElementById("selTipo").value;
        var pok=document.getElementById("pok").value;
        if(pok==txtCodigo && selTipo=="SECONDARY"){
            window.alert("La caja primaria no se puede convertir en caja secundaria.")
            return;
        } else if(pok!=txtCodigo && selTipo=="PRIMARY"){
            window.alert("Ya existe una caja Primaria, no se permite \n más de una caja primaria.")
            return;
        }
        selEstado=document.getElementById("selEstado").value;
        selfilial=document.getElementById("selfilial").value;
    } else if(catalogo=="A"){
        if(validarForm()){
            txtN=document.getElementById("txtN").value;
            txtD=document.getElementById("txtD").value;
            txtM=document.getElementById("txtM").value;
            txtI=document.getElementById("txtI").value;
            txtA=document.getElementById("txtA").value;
            txtB=document.getElementById("txtB").value;
            txtG=document.getElementById("txtG").value;
            txtE=document.getElementById("txtE").value;
            txtH=document.getElementById("txtH").value;
            txtJ=document.getElementById("txtJ").value;
            selI=document.getElementById("selJ").value;
            selJ=document.getElementById("selJ").value;
        }else{
            return;
        }
    } else if(catalogo=="PERSONA"){
        txtId=document.getElementById("txtId").value;
        if(txtId.length<3){
            guardarCatalogo();
            return;
        }
        txtDNI=document.getElementById("txtDNI").value;
        txtRUC=document.getElementById("txtRUC").value;
        txtNombre=document.getElementById("txtNombre").value;
        txtApellidos=document.getElementById("txtApellidos").value;
        txtEmail=document.getElementById("txtEmail").value;
        txtUbigeo=document.getElementById("txtUbigeo").value;
        txtTelefono=document.getElementById("txtTelefono").value;
        txtCelular=document.getElementById("txtCelular").value;
        txtUrlFoto=document.getElementById("txtUrlFoto").value;
        txtUrlFirma=document.getElementById("txtUrlFirma").value;
        txtDireccion=document.getElementById("txtDireccion").value;
        selEstado=document.getElementById("selEstado").value;
        selCategPersona=document.getElementById("selCategPersona").value;
    }
    req6 = false;
    if (window.XMLHttpRequest) {
        req6 = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        req6 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req6!=null){
        if(catalogo=="FILIAL"){
            if(document.getElementById("txtCodigo").value!=""){
                if(document.getElementById("txtNombre").value!=""){
                    if(document.getElementById("seldepartamento").value!="0"){
                        if(document.getElementById("selprovincia").value!="0"){
                            if(document.getElementById("seldistrito").value!="0"){
                                answer = confirm ("¿ESTA SEGURO QUE DESEA ACTUALIZAR "+catalogo+"?");
                                if (answer) {
                                    req6.onreadystatechange = procesarRespuesta6;
                                    PATH = "/financeBank";
                                    url = PATH + "/SActualizarCatalogo?b="+txtCodigo+
                                    "&c="+filtraParametroRequestC(txtNombre)+"&d="+filtraParametroRequestC(txtDireccion)+
                                    "&e="+selEstado+"&f="+filtraParametroRequestC(txtTelefono)+
                                    "&g="+txtCodNumCuenta+
                                    "&h="+seldistrito+"&a="+catalogo;
                                }
                            }else{
                                alert("Seleccione Distrito");
                                document.getElementById("seldistrito").focus();
                            }
                        }else{
                            alert("Seleccione Provincia");
                            document.getElementById("selprovincia").focus();
                        }
                    }else{
                        alert("Seleccione Departamento");
                        document.getElementById("seldepartamento").focus();
                    }
                }else{
                    alert("ingrese Nombre Filial");
                    document.getElementById("txtNombre").focus();
                }
            }else{
                alert("ingrese Codigo Filial");
                document.getElementById("txtCodigo").focus();
            }
        } else if(catalogo=="CAJA"){
            if(document.getElementById("txtCodigo").value!=""){
                if(document.getElementById("txtNombre").value!=""){
                    answer = confirm ("¿ESTA SEGURO QUE DESEA ACTUALIZAR "+catalogo+"?");
                    if (answer) {
                        req6.onreadystatechange = procesarRespuesta6;
                        PATH = "/financeBank";
                        url = PATH + "/SActualizarCatalogo?b="+txtCodigo+
                        "&c="+filtraParametroRequestC(txtNombre)+"&i="+selTipo+"&e="+selEstado+
                        "&j="+selfilial+"&a="+catalogo;
                    }
                }else{
                    window.alert("ingrese Nombre Caja");
                    document.getElementById("txtNombre").focus();
                }
            }else{
                window.alert("ingrese Codigo Caja");
                document.getElementById("txtCodigo").focus();
            }
        } else if(catalogo=="A"){
            txtId=document.getElementById("txtId").value;
            answer = confirm ("¿ESTA SEGURO QUE DESEA ACTUALIZAR "+catalogo+"?");
            if (answer) {
                req6.onreadystatechange = procesarRespuesta6;
                PATH = "/financeBank";
                url = PATH + "/SActualizarCatalogo?l="+filtraParametroRequestC(txtN)+"&b="+filtraParametroRequestC(txtD)+"&c="+txtM+"&m="+selI+
                "&d="+txtI+"&e="+txtA+"&f="+txtB+"&g="+txtG+"&h="+txtE+"&i="+txtH+"&j="+txtJ+"&k="+selJ+"&a="+catalogo+"&z="+txtId;
            }
        } else if(catalogo=="PERSONA"){
            if(document.getElementById("txtDNI").value!="" || document.getElementById("txtRUC").value!=""){
                if(document.getElementById("txtNombre").value!=""){
                    var answer = confirm ("¿ESTA SEGURO QUE DESEA ACTUALIZAR "+catalogo+"?");
                    if (answer) {
                        req6.onreadystatechange = procesarRespuesta6;
                        PATH = "/financeBank";
                        url = PATH + "/SActualizarCatalogo?k="+txtDNI+"&l="+txtRUC+
                        "&c="+filtraParametroRequestC(txtNombre)+"&n="+filtraParametroRequestC(txtApellidos)+"&o="+txtEmail+
                        "&p="+txtUbigeo+"&f="+filtraParametroRequestC(txtTelefono)+"&q="+filtraParametroRequestC(txtCelular)+
                        "&r="+txtUrlFoto+"&s="+txtUrlFirma+"&d="+filtraParametroRequestC(txtDireccion)+
                        "&e="+selEstado+"&u="+selCategPersona+
                        "&a="+catalogo+"&m="+txtId;
                    }
                }else{
                    alert("ingrese Nombre o Apellido");
                    document.getElementById("txtNombre").focus();
                }
            }else{
                alert("ingrese DNI o RUC");
                document.getElementById("txtDNI").focus();
            }
        }
        req6.open("GET",url,true);
        req6.send(null);
    }
}

function procesarRespuesta6(){
    contenido = document.getElementById('divrptaInsertar');
    contenido.innerHTML="Cargando los datos...";
    if (req6.readyState==4 && req6.status==200) {
        contenido.innerHTML = req6.responseText;
        var respuesta=document.getElementById("txtRptaInsertar").value
        if(respuesta!=null && respuesta=="SI"){
            window.alert("La Actualizacion se ha realizado SATISFACTORIAMENTE!!!");
            document.getElementById("cmdGuardar").disabled=true;
            document.getElementById("cmdActualizar").disabled=false;
            mostrarcatalogo();
            mostrarcatalogo();
        }else{
            window.alert("LA ACTUALIZACION NO SE HA REALIZADO");
        }
    }
}

var req4;
function listarProvincia(){
    var iddep;
    iddep=document.getElementById("seldepartamento").value;
    document.getElementById("selprovincia").disabled=false;
    if(iddep!="(Seleccione Departamento)"){
        document.getElementById("seldistrito").disabled=true;
        req4 = false;
        if (window.XMLHttpRequest) {
            req4 = new XMLHttpRequest();
        }
        else if (window.ActiveXObject) {
            req4 = new ActiveXObject("Microsoft.XMLHTTP");
        }
        if(req4!=null){
            req4.onreadystatechange = procesarRespuesta4;
            var PATH = "/financeBank";
            var url = PATH + "/SListarProvincia?iddep="+iddep;
            req4.open("GET",url,true);
            req4.send(null);
        }
    }else{
        document.getElementById("selprovincia").disabled=true;
        document.getElementById("seldistrito").disabled=true;
    }
}

function procesarRespuesta4(){
    contenido = document.getElementById('divprovincia');
    contenido.innerHTML="Cargando los datos...";
    if (req4.readyState==4 && req4.status==200) {
        contenido.innerHTML = req4.responseText;
    }
//document.cuadro.submit();
}

var req5;
function listarDistrito(){
    var iddep;
    iddep=document.getElementById("selprovincia").value;
    document.getElementById("seldistrito").disabled=false;
    if (window.XMLHttpRequest) {
        req5 = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        req5 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req5!=null){

        req5.onreadystatechange = procesarRespuesta5;
        var PATH = "/financeBank";
        var url = PATH + "/SListarDistrito?iddep="+iddep;
        req5.open("GET",url,true);
        req5.send(null);
    }
}

function procesarRespuesta5(){
    contenido = document.getElementById('divDistrito');
    contenido.innerHTML="Cargando los datos...";
    if (req5.readyState==4 && req5.status==200) {
        contenido.innerHTML = req5.responseText;
    }
}

var req8;
function eliminarCatalogo(){
    var catalogo;
    var txtCodigo="";
    catalogo=document.getElementById("txtcatalogo").value;
    if(catalogo=="FILIAL"||catalogo=="CAJA"){        
        txtCodigo=document.getElementById("txtCodigo").value;
    } else if(catalogo=="A"||catalogo=="PERSONA"){
        txtCodigo=document.getElementById("txtId").value;
    }
    if (window.XMLHttpRequest) {
        req8 = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        req8 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req8!=null){
        var nm="",ll="";
        if(catalogo=="FILIAL"){
            nm="FILIAL"
            ll="?txtCodigo="+txtCodigo+"&txtcatalogo="+catalogo;
        } else if(catalogo=="CAJA"){
            nm="CAJA"
            ll="?txtCodigo="+txtCodigo+"&txtcatalogo="+catalogo;
        } else if(catalogo=="PERSONA"){
            nm="PERSONA"
            ll="?txtId="+txtCodigo+"&txtcatalogo="+catalogo;
        } else if(catalogo=="A"){
            nm="CRÉDITO"
            ll="?txtId="+txtCodigo+"&txtcatalogo="+catalogo;
        }else{
            return;
        }
        answer = confirm ("¿ESTA SEGURO QUE DESEA ELIMINAR El "+nm+"?");
        if (answer) {
            req8.onreadystatechange = procesarRespuesta8;
            PATH = "/financeBank";
            url = PATH + "/SEliminarCatalogo"+ll;
        }
        req8.open("GET",url,true);
        req8.send(null);
    }
}

function procesarRespuesta8(){
    //alert('dentro de la funcion procesra respuesta')
    contenido = document.getElementById('divrptaInsertar');
    contenido.innerHTML="Cargando los datos...";
    if (req8.readyState==4 && req8.status==200) {
        contenido.innerHTML = req8.responseText;
        var respuesta=document.getElementById("txtRptaInsertar").value
        if(respuesta=="SI"){
            window.alert("EL Registro fue ELIMINADO Satisfactoriamente!!!");
            document.getElementById("cmdGuardar").disabled=true;
            document.getElementById("cmdActualizar").disabled=false;
            nuevoCatalogo();
            mostrarcatalogo();
        }else{
            window.alert("LA ELIMINACION NO SE PUDO REALIZAR \n"+document.getElementById("r").innerHTML);
        }
    }
}