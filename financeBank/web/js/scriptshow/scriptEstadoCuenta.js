/**
 *
 * @author admin
 */

function regresarMenu(){
    document.frmTicketEstadoCuenta.action="admincuenta.htm";
    document.frmTicketEstadoCuenta.submit();
}

function ifto(){
    if(document.getElementById("ifup").checked){
        document.getElementById("tri").style.visibility='';
        document.getElementById("trt").style.visibility='';
    }else{
        document.getElementById("tri").style.visibility='hidden';
        document.getElementById("trt").style.visibility='hidden';
    }
}

            