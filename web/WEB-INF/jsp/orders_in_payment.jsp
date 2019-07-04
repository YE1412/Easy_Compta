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
        <title><fmt:message key="orders_in_payment.title" /></title>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/header_in.jspf" %>
        <main class="mt-5" style="margin-left: 15em;">
            <div class="container" >
                <%@include file="/WEB-INF/jspf/payments_orders_table_form.jspf" %>
            </div>
        </main>
        <%@include file="/WEB-INF/jspf/script.jspf" %>
    </body>
</html>