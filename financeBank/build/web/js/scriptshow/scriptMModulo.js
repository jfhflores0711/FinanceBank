/**
 *
 * @author admin
 */

var req2;
function  funcion(id){
    req2 = null;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2!=null){
        req2.onreadystatechange = procesarRespuesta2;
        var PATH = "/financeBank";
        var url = PATH + "/SNodo?idgrupo="+id;
        req2.open("GET",url,true);
        req2.send(null);
    }
}

function procesarRespuesta2(){
    contenido = document.getElementById('Nodo');
    contenido.innerHTML="Cargando los datos...";
    if (req2.readyState==4 && req2.status==200) {
        contenido.innerHTML = req2.responseText;
    }
}

var ant;
function  select(n){
    if(ant==null){
        document.getElementById('a'+n).style.background="#cfe0ff";
    } else{
        document.getElementById('a'+ant).style.background="#FFFFFF";
        document.getElementById('a'+n).style.background="#cfe0ff";

    }
    ant=n;
}

function checkModulo(){
    var todo = "";
    for (i=0;i<document.fNodo.elements.length;i++){
        if(document.fNodo.elements[i].type == "checkbox"){
            var checkboxes = document.fNodo.elements[i]
            if (checkboxes.checked) {
                todo =checkboxes.value +"=1 "+todo;
            } else{
                todo =checkboxes.value +"=0 "+todo;
            }
        }
    }
    req2 = null;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2!=null){
        var PATH = "/financeBank";
        var url = PATH + "/SActualizarModulos?todo="+todo;
        req2.open("GET",url,true);
        req2.send(null);
    }
}

