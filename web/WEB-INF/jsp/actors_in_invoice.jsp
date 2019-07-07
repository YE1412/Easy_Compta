<%-- 
    Document   : orders
    Created on : 15 avr. 2018, 00:18:13
    Author     : Yannick
--%>

<%@ include file="/WEB-INF/jspf/include.jspf" %> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ include file="/WEB-INF/jspf/style.jspf" %>
        <title><spring:message code="actors_in_invoice.title" /></title>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/header_in.jspf" %>
        <main class="mt-5" style="margin-left: 15em;">
            <div class="container" >
            	<h1><spring:message code="actors_in_invoice.headline"></spring:message></h1>
                <%@include file="/WEB-INF/jspf/invoices_actors_table_form.jspf" %>
            </div>
        </main>
        <%@include file="/WEB-INF/jspf/script.jspf" %>
    </body>
</html>