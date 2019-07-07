<%-- 
    Document   : home
    Created on : 12 avr. 2018, 12:24:21
    Author     : Yannick
--%>

<%@ include file="/WEB-INF/jspf/include.jspf" %> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ include file="/WEB-INF/jspf/style.jspf" %>
        <title><spring:message code="home.title" /></title>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/header_in.jspf" %>
         <main class="mt-5" style="margin-left: 15em;">
            <div class="container" >
        <h1><spring:message code="home.headline" /></h1>
		<spring:message code="lang.libelle" /> <a href="?language=en"><spring:message code="lang.en" /></a>|<a href="?language=fr_FR"><spring:message code="lang.fr" /></a>
        </div>
        </main> 
        <%@include file="/WEB-INF/jspf/script.jspf" %>
    </body>
</html>
