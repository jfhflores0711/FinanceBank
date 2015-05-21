// JavaScript Document
function MM_swapImgRestore() { //v3.0
    var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
    var d=document; if(d.images){
        if(!d.MM_p) d.MM_p=new Array();
        var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
            if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i]; }
    }
}

function MM_findObj(n, d) { //v4.01
    var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
        d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);
    }
    if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
    for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
    if(!x && d.getElementById) x=d.getElementById(n);
    return x;
}
function MM_swapImage() { //v3.0
    var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
        if ((x=MM_findObj(a[i]))!=null){ document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2]; }
}

function MM_openBrWindow(theURL,winName,features) { //v2.0
    window.open(theURL,winName,features);
}

function bNavegador() {
    if( navigator.appName ) {
        if( navigator.appName == "Microsoft Internet Explorer")  return 1;
        if( navigator.appName == "Netscape")  return 2;
    }
    return 0;
}
var contador = 1;

function AbreVentana(ventana){
    var w=640, h=480; var windowName = new String( contador );
    windowName = "v" + windowName; var x = bNavegador();
    if (window.screen && window.screen.availHeight) {
        h = window.screen.availHeight - 63; // 63
        if( x==2 ) h = h - 11;
        w = window.screen.availWidth - 4;
    } 
    if (ventana==1) window.open("login.htm", windowName, "status=yes,resizable=yes,toolbar=no,scrollbars=yes,top=0,left=0,width=" + w + ",height=" + h, 1 );
}

function enviarconstancia(Pagina){
    v= window.open(Pagina,'EnviaCorreo','scrollbars=yes,resizable=no,width=780,height=480,left=0,top=0');
    v.location.reload(true);
}	

function dibujatabla(){
    if (navigator.platform.indexOf("Win") < 0) {
        if( navigator.appName ) {
            if( navigator.appName == "Microsoft Internet Explorer") { document.write('aaaaa'); }
            if( navigator.appName == "Netscape") { document.write('bbbbbb'); }
        }
    } else { 	
        if( navigator.appName ) {
            if( navigator.appName == "Microsoft Internet Explorer") { document.write('<!--#include file="includes/izquierda.htm"-->'); }
            if( navigator.appName == "Netscape") { document.write('bbbbb'); }
        }
    }
}

function swapClass(obj, newStyle) { obj.className = newStyle; }

function isUndefined(value) { var undef; return value == undef; }

function checkAll(theForm) { // check all the checkboxes in the list
    for (var i=0;i<theForm.elements.length;i++) {
        var e = theForm.elements[i]; var eName = e.name;
        if (eName != 'allbox' && (e.type.indexOf("checkbox") == 0)) { e.checked = theForm.allbox.checked; }
    }
}

function clearForm(frmObj) {
    for (var i = 0; i < frmObj.length; i++) {
        var element = frmObj.elements[i];
        if(element.type.indexOf("text") == 0 || element.type.indexOf("password") == 0) { element.value="";
        } else if (element.type.indexOf("radio") == 0) { element.checked=false;
        } else if (element.type.indexOf("checkbox") == 0) { element.checked = false;
        } else if (element.type.indexOf("select") == 0) { 
            for(var j = 0; j < element.length ; j++) { element.options[j].selected=false; } 
            element.options[0].selected=true;
        }
    }
}

function getFormAsString(frmObj) {
    var query = "";
    for (var i = 0; i < frmObj.length; i++) {
        var element = frmObj.elements[i];
        if (element.type.indexOf("checkbox") == 0 ||
            element.type.indexOf("radio") == 0) {
            if (element.checked) { query += element.name + '=' + escape(element.value) + "&"; }
        } else if (element.type.indexOf("select") == 0) {
            for (var j = 0; j < element.length ; j++) {
                if (element.options[j].selected) { query += element.name + '=' + escape(element.value) + "&"; }
            }
        } else { query += element.name + '=' + escape(element.value) + "&"; }
    }
    return query;
}

function toggleForm(frmObj, iState)  {// 1 visible, 0 hidden
    for(var i = 0; i < frmObj.length; i++) {
        if (frmObj.elements[i].type.indexOf("select") == 0 || frmObj.elements[i].type.indexOf("checkbox") == 0) {
            frmObj.elements[i].style.visibility = iState ? "visible" : "hidden";
        }
    }
}

function opt(txt,val,sel) { this.txt=txt; this.val=val; this.sel=sel;
}

function move(list,to) {
    var total=list.options.length; index = list.selectedIndex;
    if (index == -1) return false;
    if (to == +1 && index == total-1) return false;
    if (to == -1 && index == 0) return false;
    to = index+to; var opts = new Array();
    for (i=0; i<total; i++) { opts[i]=new opt(list.options[i].text,list.options[i].value,list.options[i].selected); }
    tempOpt = opts[to]; opts[to] = opts[index]; opts[index] = tempOpt; list.options.length=0; // clear
    for (i=0;i<opts.length;i++) {
        list.options[i] = new Option(opts[i].txt,opts[i].val); list.options[i].selected = opts[i].sel;
    }
    list.focus();
}

function selectAll(elementId) {
    var element = document.getElementById(elementId); len = element.length;
    if (len != 0) { for (i = 0; i < len; i++) { element.options[i].selected = true; } }
}

function toggleChoice(elementId) {
    var element = document.getElementById(elementId);
    if (element.checked) { element.checked = false; } else { element.checked = true; }
}

function toggleRadio(elementId, index) { var element = document.getElementsByName(elementId)[index]; element.checked = true; }

function openWindow(url, winTitle, winParams) { winName = window.open(url, winTitle, winParams); winName.focus(); }

function openSearch(url, winTitle) {
    var screenWidth = parseInt(screen.availWidth); var screenHeight = parseInt(screen.availHeight);
    var winParams = "width=" + screenWidth + ",height=" + screenHeight; winParams += ",left=0,top=0,toolbar,scrollbars,resizable,status=yes";
    openWindow(url, winTitle, winParams);
}

function setCookie(name,value,expires,path,domain,secure) {
    document.cookie = name + "=" + escape (value) + ((expires) ? "; expires=" + expires.toGMTString() : "") + ((path) ? "; path=" + path : "") + ((domain) ? "; domain=" + domain : "") + ((secure) ? "; secure" : "");
}

/* This function is used to get cookies */
function getCookie(name) {
    var prefix = name + "="; var start = document.cookie.indexOf(prefix);
    if (start==-1) { return null; }
    var end = document.cookie.indexOf(";", start+prefix.length)
    if (end==-1) { end=document.cookie.length; }
    var value=document.cookie.substring(start+prefix.length, end)
    return unescape(value);
}

function deleteCookie(name,path,domain) {
    if (getCookie(name)) { document.cookie = name + "=" + ((path) ? "; path=" + path : "") + ((domain) ? "; domain=" + domain : "") + "; expires=Thu, 01-Jan-70 00:00:01 GMT";}
}

String.prototype.trim = function () { return this.replace(/^\s*(\S*(\s+\S+)*)\s*$/, "$1"); };

function validateRequired(form) {
    var bValid = true; var focusField = null; var i = 0;
    var fields = new Array(); oRequired = new required();
    for (x in oRequired) {
        if ((form[oRequired[x][0]].type == 'text' || form[oRequired[x][0]].type == 'textarea' || form[oRequired[x][0]].type == 'select-one' || form[oRequired[x][0]].type == 'radio' || form[oRequired[x][0]].type == 'password') && form[oRequired[x][0]].value == '') {
            if (i == 0) focusField = form[oRequired[x][0]];
            fields[i++] = oRequired[x][1]; bValid = false;
        }
    }
    if (fields.length > 0) { focusField.focus(); alert(fields.join('\n')); }
    return bValid;
}

function createFormElement(element, type, name, id, value, parent) {
    var e = document.createElement(element);
    e.setAttribute("name", name); e.setAttribute("type", type); e.setAttribute("id", id);
    e.setAttribute("value", value); parent.appendChild(e);
}

function confirmDelete(obj) {
    var msg = "Are you sure you want to delete this " + obj + "?"; ans = confirm(msg);
    if (ans) { return true; } else { return false; }
}

function highlightTableRows(tableId) {
    var previousClass = null; var table = document.getElementById(tableId); var startRow = 0;
    if (!table.getElementsByTagName("thead")[0]) { startRow = 1;  }
    var tbody = table.getElementsByTagName("tbody")[0]; var rows = tbody.getElementsByTagName("tr");
    for (i=startRow; i < rows.length; i++) {
        rows[i].onmouseover = function() { previousClass=this.className;this.className+=' over'; };
        rows[i].onmouseout = function() { this.className=previousClass; };
        rows[i].onclick = function() {
            var cell = this.getElementsByTagName("td")[0]; var link = cell.getElementsByTagName("a")[0];
            if (link.onclick) {
                call = link.getAttribute("onclick"); if (call.indexOf("return ") == 0) { call = call.substring(7); }
                eval(call);
            } else { location.href = link.getAttribute("href"); }
            this.style.cursor="wait";
            return false;
        }
    }
}

function highlightFormElements() {
    addFocusHandlers(document.getElementsByTagName("input")); addFocusHandlers(document.getElementsByTagName("textarea"));
}

function addFocusHandlers(elements) {
    for (var i=0; i < elements.length; i++) {
        if (elements[i].type != "button" && elements[i].type != "submit" &&
            elements[i].type != "reset" && elements[i].type != "checkbox" && elements[i].type != "radio") {
            if (!elements[i].getAttribute('readonly') && !elements[i].getAttribute('disabled')) {
                elements[i].onfocus=function() { this.style.backgroundColor='#ffd';this.select() };
                elements[i].onmouseover=function() { this.style.backgroundColor='#ffd' };
                elements[i].onblur=function() { this.style.backgroundColor=''; }
                elements[i].onmouseout=function() { this.style.backgroundColor=''; }
            }
        }
    }
}

function radio(clicked){
    var form = clicked.form;
    var checkboxes = form.elements[clicked.name];
    if (!clicked.checked || !checkboxes.length) { clicked.parentNode.parentNode.className=""; return false; }
    for (i=0; i<checkboxes.length; i++) {
        if (checkboxes[i] != clicked) { checkboxes[i].checked=false; checkboxes[i].parentNode.parentNode.className=""; }
    }
    clicked.parentNode.parentNode.className="over";
    return true;
}
window.defaultStatus=document.title;

function BuscaRucs(nruc, rzpart){
    var of = window.showModalDialog("busquedaRUC.htm","Búsqueda de RUC","dialogWidth:43;dialogHeight:22;center:1");
    if (of!=null){ nruc.value = of.id; rzpart.innerHTML = of.de ; }
}

function BuscaRNC(noexp,rz){
    var of = window.showModalDialog("busquedaRNC.htm","Búsqueda de RNC","dialogWidth:43;dialogHeight:22;center:1");
    if (of!=null){ noexp.value = of.id; rz.innerHTML = of.de; document.all("rz_").value = of.de; }
}

function lapso(){ setTimeout("Refrescar()",300000); }

function EliminarFilasTabla(nameTable,NumRowsDel,NumRowsMin,btnDisabled) {
    var v1 = document.all(nameTable);
    for(i = 1 ; i<=NumRowsDel;i++){
        if (v1.rows.length > NumRowsMin){v1.deleteRow();}
    }
    if (btnDisabled != ''){
        if (v1.rows.length > NumRowsMin) { document.all(btnDisabled).disabled = false; }
        else { document.all(btnDisabled).disabled = true; }
    }
}

function Refrescar(){ window.location.reload(); }

function isDigito (ls_car){ return ((ls_car >="0") && (ls_car<="9"))}

function isNumber (ls_cadena){
    var i;
    for (i = 0; i < ls_cadena.length; i++){
        if (!isDigito(ls_cadena.charAt(i))) return false;
    }
    return true;
}

function Blanco(ls_cadena){ var i;
    if (vacio(ls_cadena)) return true;
    for (i = 0; i < ls_cadena.length; i++){
        if (ls_cadena.charAt(i) != " ") return false;
    }
    return true;
}

function vacio(ls_cadena){
    if(ls_cadena==null || ls_cadena=="") return true;
    return false;
}

function isRUC(valor){
    var suma = 0; var x = 6;
    for (var i=0; i<valor.length-1;i++){
        if ( i == 4 ) x = 8;
        var digito = valor.charAt(i) - '0'; x = x - 1;
        if ( i==0 ) suma += (digito*x); else suma += (digito*x);
    }
    var resto = suma % 11; resto = 11 - resto;
    if ( resto >= 10) resto = resto - 10;
    if ( resto == valor.charAt( valor.length-1 ) - '0' ){ return true; }
    return false;
}

function ValidarBlanco(ls_obj,ls_mensage){
    if (Blanco(ls_obj.value)) {alert("Por favor ingrese " + ls_mensage);
        ls_obj.value="";ls_obj.select(); return false;
    }
    return true;
}

function ValidarAncho11 (ls_obj,ls_mensage){
    if (ls_obj.value.length != 11) { alert("Ingrese Numero correcto en el campo " + ls_mensage + " de 11 	caracteres"); ls_obj.select(); return false;
    } else return true;
}

function ValidarNumerico (ls_obj,ls_mensage){
    if (!(isNumber(ls_obj.value))) {
        ls_obj.select(); alert("Ingrese Numero correcto en el campo " + ls_mensage );
        ls_obj.select();
        return false
    } else return true;
}

function ValidarRUC (ls_obj){
    if (!(isRUC(ls_obj.value))) { ls_obj.select(); alert("Ingrese numero de RUC correcto"); ls_obj.select();
        return false
    } else return true;
}
//-->