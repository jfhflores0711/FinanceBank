/**
 *
 * @author admin
 */
var ant;
var idPesonaSelect;
function  select(n,idp){
    this.document.getElementById('nuevo').disabled=false;
    this.document.getElementById('guardar').disabled=true;
    this.document.getElementById('actualizar').disabled=false;
    if(ant==null){
        document.getElementById('a'+n).style.background="#cfe0ff";
    }else{
        document.getElementById('a'+ant).style.background="#FFFFFF";
        document.getElementById('a'+n).style.background="#cfe0ff";
    }
    ant=n;
    idPesonaSelect = idp;//Para Actualización
}

var req2;
function  funcion(id){
    req2=null;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2!=null){
        req2.onreadystatechange = procesarRespuesta2;
        var PATH = "/financeBank";
        var url = PATH + "/SDetallePersona?i="+id;
        req2.open("GET",url,true);
        req2.send(null);
    }
}

function procesarRespuesta2(){
    contenido = document.getElementById('Detalle');
    contenido.innerHTML="Cargando los datos...";
    if (req2.readyState==4 && req2.status==200) {
        contenido.innerHTML = req2.responseText;
    }
}

function Actualizar(){
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2!=null){
        /*var i;
        for (i=0;i<document.fDetalle.estado.length;i++){
            if (document.fDetalle.estado[i].checked)
                break;
        }*/
        var dni = document.fDetalle.dni.value;
        var ruc = document.fDetalle.ruc.value;
        var nombres = document.fDetalle.nombres.value;
        var apellidos = document.fDetalle.apellidos.value;
        var email = document.fDetalle.email.value;
        var ubigeo = document.fDetalle.ubigeo.value;
        var telefono = document.fDetalle.telefono.value;
        var celular = document.fDetalle.celular.value;
        var direccion = document.fDetalle.direccion.value;
        var login = document.fDetalle.login.value;
        var contrasenia = document.fDetalle.contrasenia.value;
        var ipacceso = document.fDetalle.ipacceso.value;
        var grupo = document.fDetalle.grupo[document.fDetalle.grupo.selectedIndex].value;
        var caja = document.fDetalle.caja[document.fDetalle.caja.selectedIndex].value;
        var j;
        for (j=0;j<document.fDetalle.categoria.length;j++){
            if (document.fDetalle.categoria[j].checked)
                break;
        }
        var categoria = document.fDetalle.categoria[j].value;
        req2.onreadystatechange = pa;
        var PATH = "/financeBank";
        var url = PATH + "/SActualizarPersona?idpersona="+idPesonaSelect+
        "&dni="+dni+
        "&ruc="+ruc+
        "&nombres="+nombres+
        "&apellidos="+apellidos+
        "&email="+email+
        "&ubigeo="+ubigeo+
        "&telefono="+telefono+
        "&celular="+celular+
        "&direccion="+direccion+
        "&login="+login+
        "&contrasenia="+contrasenia+
        "&ipacceso="+ipacceso+
        "&grupo="+grupo+
        "&caja="+caja+
        "&categoria="+categoria;
        req2.open("GET",url,true);
        req2.send(null);
    }  
}

function pa(){
    if (req2.readyState==4 && req2.status==200) {
        if(req2.responseText=="OK"){
            alert('¡¡SE ACTUALIZARON LOS DATOS CON EXITO!!');
        }else{
            alert(req2.responseText);
        }
    }
}

function nuevo(){
    this.document.getElementById('guardar').disabled=false;
    this.document.getElementById('actualizar').disabled=true;
    this.document.fDetalle.dni.value="";
    document.getElementById('d').disabled=false;
    this.document.fDetalle.ruc.value="";
    document.getElementById('r').disabled=false;
    this.document.fDetalle.nombres.value="";
    document.getElementById('n').disabled=false;
    this.document.fDetalle.apellidos.value="";
    document.getElementById('a').disabled=false;
    this.document.fDetalle.email.value="";
    document.getElementById('e').disabled=false;
    this.document.fDetalle.ubigeo.value="";
    document.getElementById('u').disabled=false;
    this.document.fDetalle.telefono.value="";
    document.getElementById('t').disabled=false;
    this.document.fDetalle.celular.value="";
    document.getElementById('c').disabled=false;
    this.document.fDetalle.direccion.value="";
    document.getElementById('di').disabled=false;
    this.document.fDetalle.login.value="";
    document.getElementById('l').disabled=false;
    this.document.fDetalle.contrasenia.value="";
    document.getElementById('co').disabled=false;
    this.document.fDetalle.ipacceso.value="*";
    document.getElementById('ip').disabled=false;
    document.getElementById('gr').disabled=false;
    document.getElementById('fi').disabled=false;
    document.getElementById('selcaja').disabled=false;
}

var req1;
function mostrarcaja(){
    var codfil=document.getElementById("selmiFilial").value;
    if(codfil!='0'){
        req1 = null;
        if (window.XMLHttpRequest) {
            req1 = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            req1 = new ActiveXObject("Microsoft.XMLHTTP");
        }
        if(req1!=null){
            req1.onreadystatechange = procesarRespuesta1;
            var PATH = "/financeBank";
            var url = PATH + "/SMostrarCaja?inselect=ok&codfili="+codfil;
            req1.open("GET",url,true);
            req1.send(null);
        }
    }
}

function procesarRespuesta1(){
    contenido = document.getElementById('fiCaja');
    contenido.innerHTML="Cargando los datos...";
    if (req1.readyState==4 && req1.status==200) {
        contenido.innerHTML = req1.responseText;
    }
}

function eg(){
    if(document.getElementById('d').value=="" && document.getElementById('r').value==""){
        alert("Debe llenar un DNI o R.U.C. para continuar");
        return;
    }
    if(document.getElementById('n').value==""){
        alert("Debe llenar un nombre");
        return;
    }
    if(document.getElementById('a').value=="" && document.getElementById('r').value==""){
        alert("Debe llenar los apellidos");
        return;
    }
    if(document.getElementById('u').value==""){
        alert("Debe llenar un ubigeo");
        return;
    }
    if(document.getElementById('di').value==""){
        alert("Debe llenar la Direccion");
        return;
    }
    if(document.getElementById('n').value==""){
        alert("Debe llenar un login");
        return;
    }
    if(document.getElementById('co').value==""){
        alert("Debe llenar una contraseña");
        return;
    }

    var c="ninguno";
    var porNombre=document.getElementsByName("categoria");
    for(var i=0;i<porNombre.length;i++) {
        if(porNombre[i].checked) c=porNombre[i].value;
    }
    var dni = document.getElementById("d").value;
    var ruc = document.getElementById("r").value;
    var nombre = document.getElementById("n").value;
    var ape = document.getElementById("a").value;
    var ubigeo = document.getElementById("u").value;
    var dir = document.getElementById("di").value;
    var email = document.getElementById("e").value;
    var tel = document.getElementById("t").value;
    var cel = document.getElementById("c").value;
    var login = document.getElementById("l").value;
    var cont = document.getElementById("co").value;
    var ip = document.getElementById("ip").value;
    var grupo = document.getElementById("gr").value;
    var caja = document.getElementById("selcaja").value;

    req1 = null;
    if (window.XMLHttpRequest) {
        req1 = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        req1 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req1!=null){
        req1.onreadystatechange = pr1;
        var PATH = "/financeBank";
        var url = PATH + "/SAddUser?ca="+c+"&d="+dni+"&r="+ruc+"&n="+nombre+"&a="+ape
            +"&u="+ubigeo+"&di="+dir+"&e="+email+"&t="+tel+"&c="+cel+"&l="+login
            +"&co="+cont+"&ip="+ip+"&gr="+grupo+"&caja="+caja;
        req1.open("GET",url,true);
        req1.send(null);
    }
}

function pr1(){
    contenido = document.getElementById('re');
    contenido.innerHTML="Cargando los datos...";
    if (req1.readyState==4 && req1.status==200) {
        contenido.innerHTML = req1.responseText;
        if(contenido.innerHTML=="OK"){
            alert("Se ha Guardado correctamente");
              document.fDetalle.submit();
        }else{
            alert(contenido.innerHTML);
        }
    }
}