/**
 *
 * @author admin
 */

function reset_onclick(x){
    document.formc.reset();
    postcode();
}
function postcode(){}
function eequerystring(){
    var querystring=document.location.search;
    if(querystring.length>0){
        variables=(querystring.substring(1)).split("&");
        var variable;
        var key;
        var value;
        for(var ii=0;ii<variables.length;ii++){
            variable=variables[ii].split("=");
            key=unescape(variable[0]);
            value=unescape(variable[1]);
            if(document.formc[key]!=null){
                document.formc[key].value=value;
            }
        }
    }
}
function initial_update(){
    postcode('');
    eequerystring();
}