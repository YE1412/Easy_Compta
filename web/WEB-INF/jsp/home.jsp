<%-- 
    Document   : home
    Created on : 12 avr. 2018, 12:24:21
    Author     : Yannick
--%>

<%@ include file="/WEB-INF/jspf/include.jspf" %> 
<c:url var="projectSource" value="https://github.com/YE1412/Easy_Compta.git" />
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
        <div class="row" >
			<spring:message code="lang.libelle" /> <a href="?language=en"><spring:message code="lang.en" /></a>|<a href="?language=fr_FR"><spring:message code="lang.fr" /></a>
        </div>
        <div class="row">
        	<div class="col"></div>
        	<div class="col"><blockquote class="blockquote text-right">
				  <p class="mb-0"><spring:message code="home.paragraph1" /></p>
				  <%-- <footer class="blockquote-footer"><spring:message code="home.source_project" /><a href="${projectSource}" >${projectSource}</a></footer> --%>
				</blockquote>
			</div>
        </div>
        <div class="row">
        	<div class="col"><blockquote class="blockquote text-left">
				  <p class="mb-0"><spring:message code="home.paragraph2" /></p>
				  <%-- <footer class="blockquote-footer"><spring:message code="home.source_project" /><a href="${projectSource}" >${projectSource}</a></footer> --%>
				</blockquote>
			</div>
			<div class="col"></div>
        </div>
        <div class="row">
        	<div class="col"></div>
        	<div class="col"><blockquote class="blockquote text-right">
				  <p class="mb-0"><spring:message code="home.paragraph3" /></p>
				  <%-- <footer class="blockquote-footer"><spring:message code="home.source_project" /><a href="${projectSource}" >${projectSource}</a></footer> --%>
				</blockquote>
			</div>
        </div>
        <div class="row">
        	<div class="col"><blockquote class="blockquote text-left">
				  <p class="mb-0"><spring:message code="home.paragraph4" /></p>
				  <%-- <footer class="blockquote-footer"><spring:message code="home.source_project" /><a href="${projectSource}" >${projectSource}</a></footer> --%>
				</blockquote>
			</div>
			<div class="col"></div>
        </div>
        <div class="row">
        	<div class="col"></div>
        	<div class="col"><blockquote class="blockquote text-left">
				  <p class="mb-0"><spring:message code="home.paragraph5" /></p>
				  <%-- <footer class="blockquote-footer"><spring:message code="home.source_project" /><a href="${projectSource}" >${projectSource}</a></footer> --%>
				</blockquote>
			</div>
        </div>
        </div>
        <u><spring:message code="home.source_project" /></u><a href="${projectSource}" >${projectSource}</a>
        </main> 
        <%@include file="/WEB-INF/jspf/script.jspf" %>
    </body>
</html>
