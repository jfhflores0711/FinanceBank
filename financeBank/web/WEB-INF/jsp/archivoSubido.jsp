<%-- 
    Document   : archivoSubido
    Created on : 05-mar-2010, 12:36:51
    Author     : oscar
--%>

<%@ include file="/resources/taglibs.jsp"%>

<head>
    <title><fmt:message key="display.title"/></title><link rel="shortcut icon" href="./images/favicon.ico" />
    <meta name="heading" content="<fmt:message key='display.heading'/>"/>
    <meta name="menu" content="AdminMenu"/>
</head>

<div class="separator"></div>

<table class="detail" cellpadding="5">
    <tr>
        <th>Friendly Name:</th>
        <td><c:out value="${friendlyName}"/></td>
    </tr>
    <tr>
        <th>Filename:</th>
        <td><c:out value="${fileName}"/></td>
    </tr>
    <tr>
        <th>File content type:</th>
        <td><c:out value="${contentType}"/></td>
    </tr>
    <tr>
        <th>File size:</th>
        <td><c:out value="${size}"/></td>
    </tr>
    <tr>
        <th class="tallCell">File Location:</th>
        <td>The file has been written to: <br />
            <a href="<c:out value="${link}"/>"><c:out value="${location}" escapeXml="false"/></a>
        </td>
    </tr>
    <tr>
        <td></td>
        <td class="buttonBar">
            <input type="button" name="done" id="done" value="Terminar"
                onclick="location.href='index.htm'" />
            <input type="button" style="width: 120px" value="Subir otro archivo"
                onclick="location.href='fileupload.htm'" />
        </td>
    </tr>
</table>