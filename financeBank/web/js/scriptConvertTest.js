/* 
 * oscar
 */
/*
 * admincuenta.jsp
 **/
function activeSpecialKey(e) {
    var whichCode = (window.Event) ? e.which : e.keyCode;
    if (whichCode == 13) vercuenta();  // Enter
}

/***
 * admincaja.jsp, calc.jsp, cambiomonedauser.jsp,cobrogiros.jsp
 ***/
function currencyFormat(fld, milSep, decSep, e) {
    var key = '';
    var i =0, j = 0;
    var len =0, len2 = 0;
    var strCheck = '0123456789';
    var aux ='', aux2 = '';
    var whichCode = (window.Event) ? e.which : e.keyCode;
    if (whichCode <= 20) return true;  // Enter
    key = String.fromCharCode(whichCode);  // Get key value from key code
    if (strCheck.indexOf(key) == -1) return false;  // Not a valid key
    len = fld.value.length;
    for(i = 0; i < len; i++)
        if ((fld.value.charAt(i) != '0') && (fld.value.charAt(i) != decSep)) break;
    aux = '';
    for(; i < len; i++)
        if (strCheck.indexOf(fld.value.charAt(i))!=-1) aux += fld.value.charAt(i);
    aux += key;
    len = aux.length;
    if (len == 0) fld.value = '';
    if (len == 1) fld.value = '0'+ decSep + '0' + aux;
    if (len == 2) fld.value = '0'+ decSep + aux;
    if (len > 2) {
        aux2 = '';
        for (j = 0, i = len - 3; i >= 0; i--) {
            if (j == 3) {
                aux2 += milSep;
                j = 0;
            }
            aux2 += aux.charAt(i);
            j++;
        }
        fld.value = '';
        len2 = aux2.length;
        for (i = len2 - 1; i >= 0; i--)
            fld.value += aux2.charAt(i);
        fld.value += decSep + aux.substr(len - 2, len);
    }
    return false;
}

/***
 * admincaja.jsp, cambiomonedauser.jsp,cobrogiros.jsp
 ***/
function currencyFormat2(fld, milSep, decSep) {
    var key = '';
    var i = 0,j = 0;
    var len = 0,len2 = 0;
    var strCheck = '0123456789';
    var aux = '',aux2 = '';
    len = fld.length;
    for(i = 0; i < len; i++)
        if ((fld.charAt(i) != '0') && (fld.charAt(i) != decSep)) break;
    aux = '';
    for(; i < len; i++)
        if (strCheck.indexOf(fld.charAt(i))!=-1) aux += fld.charAt(i);
    aux += key;
    len = aux.length;
    if (len == 0) fld = '';
    if (len == 1) fld = '0'+ decSep + '0' + aux;
    if (len == 2) fld = '0'+ decSep + aux;
    if (len > 2) {
        aux2 = '';
        for (j = 0, i = len - 3; i >= 0; i--) {
            if (j == 3) {
                aux2 += milSep;
                j = 0;
            }
            aux2 += aux.charAt(i);
            j++;
        }
        fld = '';
        len2 = aux2.length;
        for (i = len2 - 1; i >= 0; i--)
            fld += aux2.charAt(i);
        fld += decSep + aux.substr(len - 2, len);
    }
    return fld;
}


/**
 * calc.jsp
 **/
function currencyFormatF(fld, milSep, decSep) {
    var len =0;
    len = fld.value.length;
    if(len==0){
        fld.value='0.00';
        return false;
    }
    if(fld.value=="."){
        fld.value='0.00';
        return false;
    }
    if(fld.value.substring(len-1, len)=="."){
        fld.value=fld.value.substring(0, len-1);
    }
    var ax=parseFloat(fld.value);
    fld.value=ax;
    return false;
}

/**
 * calc.jsp, cambiomonedauser.jsp, 
 **/
function currencyFormat_decimal(fld, milSep, decSep, e, n_decimales ) {
    var key = '';
    var i =0, j = 0;
    var len =0, len2 = 0;
    var strCheck = '0123456789';
    var aux ='', aux2 = '';
    var whichCode = (window.Event) ? e.which : e.keyCode;
    if (whichCode <= 18) return true;  // Enter,Delete
    key = String.fromCharCode(whichCode);  // Get key value from key code
    if (strCheck.indexOf(key) == -1) return false;  // Not a valid key
    len = fld.value.length;
    for(i = 0; i < len; i++)
        if ((fld.value.charAt(i) != '0') && (fld.value.charAt(i) != decSep)) break;
    aux = '';
    for(; i < len; i++)
        if (strCheck.indexOf(fld.value.charAt(i))!=-1) aux += fld.value.charAt(i);
    aux += key;
    len = aux.length;
    var n_cero='';
    for(var u=0;u< n_decimales -len;u++){
        n_cero='0'+n_cero;
    }
    if (len == 0){
        fld.value = '';
    }else{
        fld.value = '0'+ decSep + n_cero + aux;
    }
    if (len > n_decimales) {
        aux2 = '';
        for (j = 0, i = len - (n_decimales+1); i >= 0; i--) {
            if (j == 3) {
                aux2 += milSep;
                j = 0;
            }
            aux2 += aux.charAt(i);
            j++;
        }
        fld.value = '';
        len2 = aux2.length;
        for (i = len2 - 1; i >= 0; i--)
            fld.value += aux2.charAt(i);
        fld.value += decSep + aux.substr(len - n_decimales, len);
    }
    return false;
}



/**
 * calc.jsp
 **/
function validar(e){
    var obj=e.srcElement || e.target;
    var tecla_codigo = (document.all) ? e.keyCode : e.which;
    if(tecla_codigo<14)return true;
    var patron =/[\d.]/;
    var tecla_valor = String.fromCharCode(tecla_codigo);
    var control=(tecla_codigo==46 && (/[.]/).test(obj.value))?false:true;
    return patron.test(tecla_valor) &&  control;
}


function filtraParametroRequest(parametro){
    var expreg = ///[_:;<>°!"#$%=?¡¿'*\~{}\()[\]{\\^`|¬´¨]/g;
    parametro = parametro.replace(expreg,"");
    return parametro;
}
function filtraParametro(objeto){
    objeto.value = filtraParametroRequest(objeto.value);
}

function wait(msecs) {
    var start = new Date().getTime();
    var cur = start
    while(cur - start < msecs){
        cur = new Date().getTime();
    }
}
function sleep(millisegundos) {
    var inicio = new Date().getTime();
    while ((new Date().getTime() - inicio) < millisegundos){
//NOne
}
}

function formatCurrency(num) {
    num = num.toString().replace(/\$|\,/g,'');
    if(isNaN(num)) num = "0";
    var cents = Math.floor((num*100+0.5)%100);
    num = Math.floor(num).toString();
    if(cents < 10) cents = "0" + cents;
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
        num = num.substring(0,num.length-(4*i+3))+','+num.substring(num.length-(4*i+3));
    return (num + '.' + cents);
}

function letras(c,d,u){
    var centenas,decenas,decom;
    var lc="";
    var ld="";
    var lu="";
    centenas=eval(c);
    decenas=eval(d);
    decom=eval(u);
    switch(centenas){
        case 0:
            lc="";
            break;
        case 1:
            if (decenas==0 && decom==0)
                lc="Cien";
            else
                lc="Ciento ";
            break;
        case 2:
            lc="Doscientos ";
            break;
        case 3:
            lc="Trescientos ";
            break;
        case 4:
            lc="Cuatrocientos ";
            break;
        case 5:
            lc="Quinientos ";
            break;
        case 6:
            lc="Seiscientos ";
            break;
        case 7:
            lc="Setecientos ";
            break;
        case 8:
            lc="Ochocientos ";
            break;
        case 9:
            lc="Novecientos ";
            break;
    }
    switch(decenas){
        case 0:
            ld="";
            break;
        case 1:

            switch(decom){
                case 0:
                    ld="Diez";
                    break;
                case 1:
                    ld="Once";
                    break;
                case 2:
                    ld="Doce";
                    break;
                case 3:
                    ld="Trece";
                    break;
                case 4:
                    ld="Catorce";
                    break;
                case 5:
                    ld="Quince";
                    break;
                case 6:
                    ld="Dieciseis";
                    break;
                case 7:
                    ld="Diecisiete";
                    break;
                case 8:
                    ld="Dieciocho";
                    break;
                case 9:
                    ld="Diecinueve";
                    break;
            }
            break;
        case 2:
            ld="Veinte";
            break;
        case 3:
            ld="Treinta";
            break;
        case 4:
            ld="Cuarenta";
            break;
        case 5:
            ld="Cincuenta";
            break;
        case 6:
            ld="Sesenta";
            break;
        case 7:
            ld="Setenta";
            break;
        case 8:
            ld="Ochenta";
            break;
        case 9:
            ld="Noventa";
            break;
    }
    switch(decom){
        case 0:
            lu="";
            break;
        case 1:
            lu="Un";
            break;
        case 2:
            lu="Dos";
            break;
        case 3:
            lu="Tres";
            break;
        case 4:
            lu="Cuatro";
            break;
        case 5:
            lu="Cinco";
            break;
        case 6:
            lu="Seis";
            break;
        case 7:
            lu="Siete";
            break;
        case 8:
            lu="Ocho";
            break;
        case 9:
            lu="Nueve";
            break;
    }
    if (decenas==1){
        return lc+ld;
    }
    if (decenas==0 || decom==0){
        return lc+" "+ld+lu;
    }
    else{
        if(decenas==2){
            ld="Veinti";
            return lc + ld + lu.toLowerCase();
        } else {
            return lc+ld+" y "+lu
        }
    }
}
function getNumberLiteral(n){
    var m0,cm,dm,um,cmi,dmi,umi,ce,de,un,hlp,decimal;
    if (isNaN(n)) {
        alert("La Cantidad debe ser un valor Numérico.");
        return null
    }
    m0= parseInt(n/ 1000000000000);
    rm0=n% 1000000000000;
    m1= parseInt(rm0/100000000000);
    rm1=rm0%100000000000;
    m2= parseInt(rm1/10000000000);
    rm2=rm1%10000000000;
    m3= parseInt(rm2/1000000000);
    rm3=rm2%1000000000;
    cm= parseInt(rm3/100000000);
    r1= rm3%100000000;
    dm= parseInt(r1/10000000);
    r2= r1% 10000000;
    um= parseInt(r2/1000000);
    r3= r2% 1000000;
    cmi=parseInt(r3/100000);
    r4= r3% 100000;
    dmi=parseInt(r4/10000);
    r5= r4% 10000;
    umi=parseInt(r5/1000);
    r6= r5% 1000;
    ce= parseInt(r6/100);
    r7= r6% 100;
    de= parseInt(r7/10);
    r8= r7% 10;
    un= parseInt(r8/1);
    if (n< 1000000000000 && n>=1000000000){
        tmp=n.toString();
        s=tmp.length;
        tmp1=tmp.slice(0,s-9);
        tmp2=tmp.slice(s-9,s);
        tmpn1=getNumberLiteral(tmp1);
        tmpn2=getNumberLiteral(tmp2);
        if(tmpn1.indexOf("Un")>=0)
            pred=" Billón ";
        else
            pred=" Billones ";
        return tmpn1+ pred +tmpn2;
    }
    if (n<10000000000 && n>=1000000){
        mldata=letras(cm,dm,um);
        hlp=mldata.replace("Un","*");
        if (hlp.indexOf("*")<0 || hlp.indexOf("*")>3){
            mldata=mldata.replace("Uno","un");
            mldata+=" Millones ";
        } else {
            mldata="Un Millón ";
        }
        mdata=letras(cmi,dmi,umi);
        cdata=letras(ce,de,un);
        if(mdata!="	") {
            if (n == 1000000) {
                mdata=mdata.replace("Uno","un") + "de";
            } else {
                mdata=mdata.replace("Uno","un")+" mil ";
            }
        }
        return (mldata+mdata+cdata);
    }
    if (n<1000000 && n>=1000){
        mdata=letras(cmi,dmi,umi);
        cdata=letras(ce,de,un);
        hlp=mdata.replace("Un","*");
        if (hlp.indexOf("*")<0 || hlp.indexOf("*")>3){
            mdata=mdata.replace("Uno","un");
            return (mdata +" mil "+cdata);
        }else{
            return ("Mil "+ cdata);
        }
    }
    if (n<1000 && n>=1){
        return (letras(ce,de,un));
    }
    if (n==0) {
        return " Cero";
    }
    return "No disponible"
}

function getNowDate(){
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1;//January is 0!
    var yyyy = today.getFullYear();
    if(dd<10){
        dd='0'+dd
    }
    if(mm<10){
        mm='0'+mm
    }
    return yyyy+'/'+mm+'/'+dd;
}