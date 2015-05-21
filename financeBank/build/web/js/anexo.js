
function imprimeAnexo(){
    //guardarrr();
    window.print();
}
function cerrar(){
    window.close()
}
function guardarrr(){
    document.getElementById("anexo").value=""+encodeURIComponent(document.getElementById("myInstance2").innerHTML);
    document.formAnexo.action="anexoplazofijo.htm";
    document.formAnexo.submit();
}
