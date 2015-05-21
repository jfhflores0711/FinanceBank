
function imprimeContrato(){
    guardar();
    //window.print();
}
function cerrarse(){
    window.close()
}
function guardar(){
    document.getElementById("contrato").value=""+encodeURIComponent(document.getElementById("myInstance1").innerHTML);
    document.formContrato.action="contrato.htm";
    document.formContrato.submit();
}

function preguntar(){
   var h= window.confirm("desea imprimir el contrato?");
   if(h){
       window.print();
   }
}
