/**
 *
 * @author admin
 */
var ventana_secundaria;
function abrirVentanac(){
    ventana_secundaria = window.open("contrato.htm?contrato_postback=1&contratoGen="+document.getElementById("contratoGen").value,"contrato","width=800,height=840,menubar=yes,resizable=yes,scrollbars=yes,status=yes");
    document.getElementById("contrato2").disabled=false;
    document.getElementById("sinContrato").disabled=true;
}
function cerrarVentanac(){
    //la referencia de la ventana es el objeto window del popup. Lo utilizo para acceder al método close
    if(ventana_secundaria)
        ventana_secundaria.close()
}

function printDes(){
    window.open("comprobantePrestamo.htm?mensaje=ok", "ticketp", "width=900,height=600,menubar=yes,resizable=yes,scrollbars=yes,status=yes")
}

function contratos(){
    document.getElementById('contrato').disabled=!document.getElementById('contrato').disabled;
    if(!document.getElementById('contrato').disabled){
        document.getElementById('habilitar').style.display='none';
        document.getElementById('inhabilitar').style.display='block';
    }else{
        document.getElementById('habilitar').style.display='block';
        document.getElementById('inhabilitar').style.display='none';
    }
}

