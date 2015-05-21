//BCP NUMPAD 1.0 (teclado virtual)
//sistemas de atencion - dixan martinez 

function clear2(obj)
{
    if (obj) obj.value= '';
}

function numKeyPressed2(obj, id, ocontrol)
{
    n= getValue(obj, id);
    if (ocontrol && (ocontrol.value.length < 7) ) ocontrol.value += n;
}

function numKeyPressed2_4(obj, id, ocontrol)
{
    n= getValue(obj, id);
    if (ocontrol && (ocontrol.value.length < 4) ) ocontrol.value += n;
}

function numKeyPressed2_6(obj, id, ocontrol)
{
    n= getValue(obj, id);
    if (ocontrol && (ocontrol.value.length < 6) ) ocontrol.value += n;
}

function getValue(obj, id)
{  
    return eval( obj + '.numpressed(' +id+ ');' )
}

function show()
{
    this.reorder();
    this.writenumpad();
}

function numpressed(j)
{
    return this.C[j];
}

function reorder()
{
    tmp= new Array(this.BC);
    for (i=0; i<this.BC; i++) tmp[i]=i
	
    this.C= Array(this.BC);
		
    for (i=this.BC-1; i>=0; i--)
    {
        index = Math.floor(Math.random() * i);
        this.C[i]= tmp[index]
        if ( index != i )
            tmp[index]= tmp[i];
    }
}

function writenumpadbut(j)
{
    but_id= this.numpad_bprefix+j;
    s= '\<td width="25">\<font>\<a id="'+but_id+'" href="javascript:numKeyPressed(\''+this.name+'\','+j+')" class="size8">'+ '&nbsp;&nbsp;' +this.C[j]+ '&nbsp;&nbsp;' +'\</a>\</font>\</td>'
	
    document.write(s)
}

function writenumpadtr_in()
{
    document.write('\<tr height="20" align="center">')
}

function writenumpadtr_out()
{
    document.write('\</tr>')
}

function writenumpad()
{
    document.write('\<table width="75" BORDER="1" cellspacing="1" cellpadding="0" bordercolor="#FF944C">')
	
    for (i=0; i< this.BC; i++)
    {
        if (i%3==0) this.writenumpadtr_in()
        this.writenumpadbut(i)
        if (i%3==2) this.writenumpadtr_out()
    }
	
    document.write('\<td BGCOLOR="#FFEFE5" colspan="2">\<font>\<a id="bclear" href="javascript:clear(\''+this.name+'\')" class="size8">&nbsp;&nbsp;limpiar&nbsp;&nbsp;\</a>\</font>\</td>')
	
    document.write('\</tr>\</table>')
}

function numpad(numpad_id)
{
    this.BC= 10;
	
    this.reorder= reorder;
    this.show= show;
    this.numpressed= numpressed;
	
    this.writenumpad= writenumpad
    this.writenumpadbut= writenumpadbut
    this.writenumpadtr_in= writenumpadtr_in
    this.writenumpadtr_out= writenumpadtr_out
	
    if (numpad_id == '')
        alert('error: numpad_id empty')
	
    this.numpad_bprefix= numpad_id
	
    this.name= 'np_' + numpad_id
}
