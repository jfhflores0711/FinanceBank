/**
 *
 * @author admin
 */

var req2=null;
function procesarRetiroOtro() {
    var importe =document.getElementById("monto").value;
    if(importe==""){
        importe="0";
    }else{
        importe = importe.replace(/,/gi, "")
    }
    var cod_moneda = document.f1.moneda[document.f1.moneda.selectedIndex].value;
    var lugar_ret = document.f1.lugarR[document.f1.lugarR.selectedIndex].value;
    var motivo_ret =document.getElementById("motivo").value;
    if(importe==0){
        window.alert("¡¡EL MONTO A RETIRAR DEBE SER MAYO A CERO!!")
    }else{
        var answer = window.confirm ("¿ESTA SEGURO REALIZAR EL RETIRO?");
        if (answer){
            req2 = null;
            if (window.XMLHttpRequest) {
                req2 = new XMLHttpRequest();
            }else if (window.ActiveXObject) {
                req2 = new ActiveXObject("Microsoft.XMLHTTP");
            }
            if(req2!=null){
                var PATH = "/financeBank";
                var url = PATH + "/SRetiroOtro?"+
                "importe="+importe +
                "&lugar_ret="+lugar_ret +
                "&motivo_ret="+motivo_ret +
                "&cod_moneda="+cod_moneda;
                req2.open("GET",url,true);
                req2.send(null);
                window.alert('¡¡SE REALIZO EL RETIRO CON EXITO!!');
                document.f1.submit();
            }
        }
    }
}

function val_monto(){
    var disp =document.getElementById('disponible').value
    disp = disp.replace(/,/gi, "");
    disp = parseFloat (disp);
    var dispc =document.getElementById('diaponibleCaja').value
    dispc = dispc.replace(/,/gi, "");
    dispc = parseFloat (dispc);
    var mont =document.getElementById('monto').value;
    if(mont=='')mont='0.00';
    mont = mont.replace(/,/gi, "");
    mont = parseFloat (mont);
    if(mont<=disp && mont <= dispc){
    }else{
        window.alert('NO DISPONE DE ESTA CANTIDAD');
        document.getElementById('monto').value = '0.00';
    }
}

            