/* 
 * Oscar
 */

var eeisus=0;
var eetrue="VERDADERO";
var eefalse="FALSO";
var eedec=".";
var eeth=",";
var eedecreg=new RegExp("\\.","g");
var eethreg=new RegExp(",","g");
var eecurrencyreg=new RegExp("\u20ac","g");
var eepercentreg=new RegExp("%","g");
var fmtdaynamesshort=new Array("dom","lun","mar","mié","jue","vie","sáb");
var fmtdaynameslong=new Array("domingo","lunes","martes","miércoles","jueves","viernes","sábado");
var fmtmonthnamesshort=new Array("ene","feb","mar","abr","may","jun","jul","ago","sep","oct","nov","dic");
var fmtmonthnameslong=new Array("enero","febrero","marzo","abril","mayo","junio","julio","agosto","septiembre","octubre","noviembre","diciembre");
var fmtstrings=new Array(","," ","S/.","pta","\u20ac","/","$");
var fmtdate6=new Array(34,33,25,2);
var fmtdate7=new Array(25,2,33,35);
var fmtdate8=new Array(25,2,33,36);
var fmtdate9=new Array(7,37,3,37,11);
var fmtdate10=new Array(38,25,2);

function myIsNaN(x){
    return(isNaN(x)||(typeof x=='number'&&!isFinite(x)));
}

function round(n,nd){
    if(isFinite(n)&&isFinite(nd)){
        var sign_n=(n<0)?-1:1;
        var abs_n=Math.abs(n);
        var factor=Math.pow(10,nd);
        return sign_n*Math.round(abs_n*factor)/factor;
    }else{
        return NaN;
    }
}

function b2s(b){
    return b?eetrue:eefalse;
}

function eeparseFloat(str){
    str=String(str).replace(eethreg,"");
    var res=parseFloat(str);
    if(isNaN(res)){
        return 0;
    }else{
        return res;
    }
}

function eeparsePercent(str){
    var parts=String(str).split('%');
    var tmp=String(parts[0]).replace(eedecreg,".");
    var res=parseFloat(tmp)/100;
    if(isNaN(res)){
        return 0;
    }else{
        return res;
    }
}

var near0RegExp=new RegExp("[.](.*0000000|.*9999999)");
function eedisplayFloat(x){
    if(myIsNaN(x)){
        return Number.NaN;
    }else{
        var str=String(x);
        if(near0RegExp.test(str)){
            x=round(x,8);
            str=String(x);
        }
        return str.replace(/\./g,eedec);
    }
}

function eedisplayFloatV(x){
    if(x=="")return x;
    if(isFinite(x)){
        return eedisplayFloat(x);
    }else{
        return x;
    }
}

function eedisplayFloatND(x,nd){
    if(myIsNaN(x)){
        return Number.NaN;
    }else{
        var res=round(x,nd);
        if(nd>0){
            var str=String(res);
            if(str.indexOf('e')!=-1)
                return str;
            if(str.indexOf('E')!=-1)
                return str;
            var parts=str.split('.');
            if(parts.length<2){
                var decimals=('00000000000000').substring(0,nd);
                return(parts[0]).toString()+eedec+decimals;
            }else{
                var decimals=((parts[1]).toString()+'00000000000000').substring(0,nd);
                return(parts[0]).toString()+eedec+decimals;
            }
        }else{
            return res;
        }
    }
}

function eedisplayPercentND(x,nd){
    if(myIsNaN(x)){
        return Number.NaN;
    }else{
        return eedisplayFloatND(x*100,nd)+'%';
    }
}

function eeparseFloatTh(str){
    str=String(str).replace(eethreg,"");
    str=String(str).replace(eedecreg,".");
    var res=parseFloat(str);
    if(isNaN(res)){
        return 0;
    }else{
        return res;
    }
}

function eedisplayFloatNDTh(x,nd){
    if(myIsNaN(x)){
        return Number.NaN;
    }else{
        var res=round(x,nd);
        if(nd>0){
            var str=String(res);
            if(str.indexOf('e')!=-1)
                return str;
            if(str.indexOf('E')!=-1)
                return str;
            var parts=str.split('.');
            var res2=eeinsertThousand(parts[0].toString());
            if(parts.length<2){
                var decimals=('00000000000000').substring(0,nd);
                return(res2+eedec+decimals);
            }else{
                var decimals=((parts[1]).toString()+'00000000000000').substring(0,nd);
                return(res2+eedec+decimals);
            }
        }else{
            return(eeinsertThousand(res.toString()));
        }
    }
}

var eeparseFloatVreg=new RegExp("^ *-?[0-9.]+ *$");
function eeparseFloatV(str){
    if(str=="")
        return str;
    str=String(str).replace(eedecreg,".");
    if(!eeparseFloatVreg.test(str)){
        return str;
    }
    var res=parseFloat(str);
    if(isNaN(res)){
        return str;
    }else{
        return res;
    }
}

function eeinsertThousand(whole){
    if(whole==""||whole.indexOf("e")>=0){
        return whole;
    }else{
        var minus_sign="";
        if(whole.charAt(0)=="-"){
            minus_sign="-";
            whole=whole.substring(1);
        }
        var res="";
        var str_length=whole.length-1;
        for(var ii=0;ii<=str_length;ii++){
            if(ii>0&&ii%3==0){
                res=eeth+res;
            }
            res=whole.charAt(str_length-ii)+res;
        }
        return minus_sign+res;
    }
}

function numero(){
    if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;
}


function esDecimal(valor) {
    var result = true;
    var strValidChars = "0123456789.";
    var strChar;
    var cantDec = 0;
    var charEvent;
    var digitosDec = 0;
    if(navigator.appName=="Netscape"){
        charEvent = window.event.which
        } else {
        charEvent = window.event.keyCode
        }
    if(charEvent==46) {
        cantDec++;
    }
    if(charEvent > 31 && (charEvent < 46 || (charEvent>46 && charEvent<48) || charEvent> 57)) {
        result=false;
    }
    if(result){
        var pasoDecimal = false;
        // Para validar el punto decimal.-
        for (i=0; i<valor.length; i++){
            strChar = valor.charAt(i);
            if(strValidChars.indexOf(strChar)==-1){
                result = false;
            }
            if(strChar=='.'){
                cantDec++;
                pasoDecimal=true;
                digitosDec++;
            }else{
                if(pasoDecimal){
                    digitosDec++;
                }
            }
            if(cantDec>1) {
                result=false;
                break;
            }
        }
    }
    return result;
}

function redondearDecimales( objeto ){
    objeto.value = RedondeoDecimal( objeto.value , 2);
}

function RedondeoDecimal(numero, decimales){
    var m= Math.pow(10,decimales);
    var n2 = Math.round(numero * m);
    var n3 = n2/m;
    var n4 = ""+n3;
    var punto = false;
    var tiene = 0;
    var oneChar = '';
    var faltan = 0;
    for(var i=0; i<n4.length; i++){
        oneChar = n4.charAt(i);
        if (oneChar == ".")	{
            punto = true;
            continue;
        }
        if(punto)	{
            tiene++;
        }
    }
    faltan = decimales - tiene;
    if(tiene==0){
        n4 = n4 + ".";
    }
    for(var i=0; i<faltan; i++){
        n4 = n4 + "0";
    }
    return( n4 );
}

