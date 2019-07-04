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
        <title><fmt:message key="orders.title" /></title>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/header_in.jspf" %>
        <main class="mt-5" style="margin-left: 15em;">
            <div class="container" >
            <c:if test="${msg != NULL}">
                <c:choose>
                <c:when test="${success}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        ${msg}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:when>
                <c:when test="${!success}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        ${msg}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:when>
                </c:choose>
            </c:if>
            <c:if test="${(orders == NULL || orders.size() < 1) && (!orders_form || orders_form == NULL)}">
                <%@include file="/WEB-INF/jspf/orders_table_empty.jspf" %>
            </c:if>
            <c:if test="${orders.size() > 0 && !orders_form}">
                <%@include file="/WEB-INF/jspf/orders_table_full.jspf" %>
            </c:if>
            <c:if test="${orders_form}">
                <%@include file="/WEB-INF/jspf/orders_table_form.jspf" %>
            </c:if>
            </div>
        </main>
        <%@include file="/WEB-INF/jspf/script.jspf" %>
    </body>
</html>