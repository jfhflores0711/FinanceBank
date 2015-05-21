/**
 *
 * @author admin
 */

var req2,req2r;

function mTotalCajas(cod,num){
    var Stotal=0;
    for(var i=0;i<TotalCaja;i++){
        var monto=document.getElementById('monto'+cod+i).value
        if(monto==''){
            monto='0.0';
        }else{
            monto = monto.replace(/,/gi, "");
        }
        var res =parseFloat (monto)
        if(document.getElementById('btn_tranferir'+i).disabled){
            window.alert("DESHABILITADO=>"+num);
        }else{
            Stotal=Stotal+res
        }
    }
    return Stotal;
}

function actSaldo(cod,num){
    var valor =mTotalCajas(cod,num);
    var valor1 =document.getElementById('mDisponible'+num).innerHTML;
    if(valor1==''){
        valor1= '0.0';
    }else{
        valor1 = valor1.replace(/,/gi, "");
    }
    var res =parseFloat (valor1)-parseFloat (valor);
    if(res<0){
        window.alert('NO DISPONE DE DINERO SUFICIENTE');
        document.getElementById('montoMoneda'+num).value='0.00';
    }else{
        res=res.toFixed(2);
        var Sres =""+res;
        if(Sres=='0.00'){
            document.getElementById('saldo'+num).innerHTML=Sres;
        }
        document.getElementById('saldo'+num).innerHTML=currencyFormat2(Sres,',','.');
    }
}

function actualizarSaldo(num){
    for(var i=0;i<TotalFilial;i++){
        if(i!=num){
            this.document.getElementById('montoFilial'+i).value='0.00';
        }
    }
    var valor =document.getElementById('montoFilial'+num).value;
    valor = valor.replace(/,/gi, "");
    var valor1 =document.getElementById('disponible').value;
    valor1 = valor1.replace(/,/gi, "");
    var res =parseFloat (valor1)-parseFloat (valor);
    if(res<0){
        window.alert('NO DISPONE DE DINERO SUFICIENTE');
        document.getElementById('montoFilial'+num).value='0.00';
    }else{
        if(res==0){
            document.getElementById('saldo').value='0.00';
        }
        res=res.toFixed(2);
        var Sres =""+res;
        document.getElementById('saldo').value=currencyFormat2(Sres,',','.');
    }
}

var TotalFilial
function numeroTfiliales(n){
    this.TotalFilial=n;
}

function numeroTmonedas(m){
    this.TotalMoneda=m;
    for(var w=0;w<m;w++){
        var mDisp = document.getElementById('mDisponible'+w).innerHTML
        var res =parseFloat (mDisp)
        res=res.toFixed(2);
        document.getElementById('mDisponible'+w).innerHTML=currencyFormat2(res,',','.');
        document.getElementById('saldo'+w).innerHTML=currencyFormat2(res,',','.');
    }
}

function abilitarTxt(idTxt){
    this.document.getElementById(idTxt).disabled=false;
}

function catualizarM(i){
    var valor1 =document.getElementById('montoActual'+i).innerHTML
    valor1 = valor1.replace(/,/gi, "")
    var valor =document.getElementById('montoTranferir'+i).value
    valor = valor.replace(/,/gi, "")
    var res =parseFloat (valor1)-parseFloat (valor)
    if(res<0){
        window.alert('NO DISPONE DE DINERO SUFICIENTE')
        document.getElementById('montoTranferir'+i).value='0.00'
    }else{
        res=res.toFixed(2);
        var Sres =""+res;
        document.getElementById('montoSaldo'+i).innerHTML=currencyFormat2(Sres,',','.');
    }
}

function TransferirMonto(n){
    var CodigoMoneda=document.getElementById('CodigoMoneda'+n).innerHTML
    var MontoTransferir =document.getElementById('montoTranferir'+n).value
    MontoTransferir = MontoTransferir.replace(/,/gi, "")
    var MontoSaldo =document.getElementById('montoSaldo'+n).innerHTML
    MontoSaldo = MontoSaldo.replace(/,/gi, "")
    var CajaDestino = document.getElementById('Caja'+n)[document.getElementById('Caja'+n).selectedIndex].value;
    req2 = null;
    if (window.XMLHttpRequest) {
        req2 = new XMLHttpRequest();
    }else if (window.ActiveXObject) {
        req2 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(req2!=null){
        var PATH = "/financeBank";
        var url = PATH +"/STransferirEntreCajas"+
        "?CodigoMoneda="+CodigoMoneda+
        "&MontoTransferir="+MontoTransferir+
        "&MontoSaldo="+MontoSaldo+
        "&CajaDestino="+CajaDestino;
        req2.open("GET",url,true);
        req2.send(null);
    }
    document.foper.submit();
}

function CerrarCaja(){
    window.alert("Las filiales ya no se pueden cerrarse");
}

function formaterMontos(m){
    this.TotalMoneda=m;
    for(var w=0;w<m;w++){
        var montoActual = document.getElementById('montoActual'+w).innerHTML
        var resMontoActual =parseFloat (montoActual)
        if(resMontoActual==0){
            document.getElementById('montoActual'+w).innerHTML="0.00";
            document.getElementById('montoSaldo'+w).innerHTML="0.00";
        }else{
            resMontoActual=resMontoActual.toFixed(2);
            resMontoActual=resMontoActual+"";
            document.getElementById('montoActual'+w).innerHTML=currencyFormat2(resMontoActual,',','.');
            document.getElementById('montoSaldo'+w).innerHTML=currencyFormat2(resMontoActual,',','.');
        }
    }
}

function add(){
    var codmoneda = document.fpatrimonio.moneda[document.fpatrimonio.moneda.selectedIndex].value;
    var addpatrimonio =document.getElementById('addpatrimonio').value;
    if(addpatrimonio==''){
        addpatrimonio='0.0';
    }else{
        addpatrimonio = addpatrimonio.replace(/,/gi, "");
    }
    if(addpatrimonio==0.0 || addpatrimonio=='0.00'){
        window.alert("No se puede agregar el valor "+addpatrimonio);
        return;
    }
    var answer = confirm ("¿ESTA SEGURO DE INCREMENTAR EL PATRIMONIO?");
    if (answer){
        req2 = null;
        if (window.XMLHttpRequest) {
            req2 = new XMLHttpRequest();
        }else if (window.ActiveXObject) {
            req2 = new ActiveXObject("Microsoft.XMLHTTP");
        }
        if(req2!=null){
            var PATH = "/financeBank";
            var url = PATH +"/SAddPatrimonio?addpatrimonio="+addpatrimonio+"&codmoneda="+codmoneda;
            req2.open("GET",url,true);
            req2.send(null);
        }
        window.alert('¡¡PATRIMONIO ACTUALIZADO CON EXITO!!');
        document.fpatrimonio.submit();
    }
}

function agregarMfilial(codfilial,n){
    var codmoneda = document.fpatrimonio.moneda[document.fpatrimonio.moneda.selectedIndex].value;
    var montoFilial =document.getElementById('montoFilial'+n).value;
    if(montoFilial==''){
        montoFilial='0.0';
    }else{
        montoFilial = montoFilial.replace(/,/gi, "");
    }
    if(parseFloat(montoFilial)==Number.NaN){
        montoFilial='0';
    }
    if(parseFloat(montoFilial)==0){
        window.alert("No se puede actualizar el monto");
        return;
    }
    var answer = window.confirm ("¿ESTÁ SEGURO DE AGREGAR ESTE MONTO?");
    if (answer){
        req2 = null;
        if (window.XMLHttpRequest) {
            req2 = new XMLHttpRequest();
        }else if (window.ActiveXObject) {
            req2 = new ActiveXObject("Microsoft.XMLHTTP");
        }
        if(req2!=null){
            var PATH = "/financeBank";
            var url = PATH +"/SAgregarMfilial?codfilial="+codfilial+"&montoFilial="+montoFilial+"&codmoneda="+codmoneda;
            req2.open("GET",url,true);
            req2.send(null);
        }
        window.alert('¡¡MONTO DE FILIAL ACTUALIZADO CON ÉXITO!!');
    }
}

function cerrarfilial(codfilial,n){
    window.alert("Esta Operación ya no está permitida \n Que las Filiales transfieran el capital existente");
    return;
    var codmoneda = document.fpatrimonio.moneda[document.fpatrimonio.moneda.selectedIndex].value;
    var montoFilial =document.getElementById('montoFilial'+n).value;
    montoFilial = montoFilial.replace(/,/gi, "");
    var answer = confirm ("¿ESTA SEGURO DE CERRAR FILIAL ?");
    if (answer) {
        req2 = null;
        if (window.XMLHttpRequest) {
            req2 = new XMLHttpRequest();
        }else if (window.ActiveXObject) {
            req2 = new ActiveXObject("Microsoft.XMLHTTP");
        }
        if(req2!=null){
            var PATH = "/financeBank";
            var url = PATH +"/SCerrarFilial?codfilial="+codfilial+"&codmoneda="+codmoneda;
            req2.open("GET",url,true);
            req2.send(null);
        }
        window.alert('¡¡PATRIMONIO ACTUALIZADO CON EXITO!!')
    }
}

