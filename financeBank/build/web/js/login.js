<script type="text/javascript">
if (getCookie("username") != null) {
    document.getElementById("inputtext1").value = getCookie("username");
    document.getElementById("inputtext2").focus();
} else {
    document.getElementById("inputtext1").focus();
}

function saveUsername(theForm) {
    var expires = new Date();
    expires.setTime(expires.getTime() + 24 * 30 * 60 * 60 * 1000); // sets it for approx 30 days.
    setCookie("username",theForm.login.value,expires,"<c:url value="/"/>");
}

function validateForm(form) {
    return validateRequired(form);
}

function passwordHint() {
    if (document.getElementById("inputtext1").value.length == 0) {
        window.alert("<fmt:message key="errors.required"><fmt:param><fmt:message key="label.username"/></fmt:param></fmt:message>");
        document.getElementById("inputtext1").focus();
    } else {
        location.href="<c:url value="/passwordHint.htm"/>?username=" + document.getElementById("inputtext1").value;
    }
}

function required () {
    this.aa = new Array("login", "<fmt:message key="errors.required"><fmt:param><fmt:message key="label.username"/></fmt:param></fmt:message>", new Function ("varName", " return this[varName];"));
    this.ab = new Array("contrasenia", "<fmt:message key="errors.required"><fmt:param><fmt:message key="label.password"/></fmt:param></fmt:message>", new Function ("varName", " return this[varName];"));
}
</script>
