<%-- 
    Document   : subirArchivo
    Created on : 04-mar-2010, 17:35:35
    Author     : oscar
--%>
<%@ include file="/resources/taglibs.jsp" %>

<html>
    <head>
        <title><fmt:message key="upload.title"/></title><link rel="shortcut icon" href="./images/favicon.ico" />
        <meta name="heading" content="<fmt:message key='upload.heading'/>"/>
        <meta name="menu" content="AdminMenu"/>
    </head>

    <!--
        The most important part is to declare the form's enctype to be "multipart/form-data"
    -->
    <body>
        <spring:bind path="fileUpload.*">
            <c:if test="${not empty status.errorMessages}">
                <div class="error">
                    <c:forEach var="error" items="${status.errorMessages}">
                        <img src="<c:url value="/images/iconWarning.gif"/>"
                             alt="<fmt:message key="icon.warning"/>" class="icon" />
                        <c:out value="${error}" escapeXml="false"/><br />
                    </c:forEach>
                </div>
            </c:if>
        </spring:bind>

        <div class="separator"></div>

        <form:form commandName="fileUpload" method="post" action="subir.htm" enctype="multipart/form-data"
                   onsubmit="return validateFileUpload(this);" id="uploadForm">
            <ul>
                <li class="info">
                    <fmt:message key="upload.message"/>
                </li>
                <li>
                <appfuse:label key="uploadForm.name" styleClass="desc"/>
                <form:errors path="name" cssClass="fieldError"/>
                <form:input path="name" id="name" cssClass="text medium" cssErrorClass="text state error"/>
            </li>
            <li>
            <form:errors path="file" cssClass="fieldError"/>
            <input type="file" name="file" id="file" class="file medium"/>
        </li>
        <li class="buttonBar bottom">
            <input type="submit" name="upload" class="button" onclick="bCancel=false"
                   value="<fmt:message key="button.upload"/>" />
            <input type="submit" name="cancel" class="button" onclick="bCancel=true"
                   value="<fmt:message key="button.cancel"/>" />
        </li>
    </ul>
</form:form>
</body>
</html>
