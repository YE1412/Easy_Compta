<%-- 
    Document   : actors
    Created on : 14 avr. 2018, 13:39:58
    Author     : Yannick
--%>

<%@ include file="/WEB-INF/jspf/include.jspf" %> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ include file="/WEB-INF/jspf/style.jspf" %>
        <title><spring:message code="actors.title" /></title>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/header_in.jspf" %>
        <main class="mt-5" style="margin-left: 15em;">
            <div class="container" >
        	<h1><spring:message code="actors.headline" /></h1>
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
            <c:if test="${(actors == NULL || actors.size() == 0) && (!actors_form || actors_form == NULL)}">
                <%@include file="/WEB-INF/jspf/actors_table_empty.jspf" %>
            </c:if>
            <c:if test="${actors.size() > 0 && !actors_form}">
                <%@include file="/WEB-INF/jspf/actors_table_full.jspf" %>
            </c:if>
            <c:if test="${actors_form}">
                <%@include file="/WEB-INF/jspf/actors_table_form.jspf" %>
            </c:if>
            </div>
        </main>
        <%@include file="/WEB-INF/jspf/script.jspf" %>
    </body>
</html>
