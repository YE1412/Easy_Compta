<%-- 
    Document   : register
    Created on : 9 avr. 2018, 19:50:52
    Author     : Yannick
--%>

<%@ include file="/WEB-INF/jspf/include.jspf" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ include file="/WEB-INF/jspf/style.jspf" %>
        <title><fmt:message key="register.title" /></title>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/header_out.jspf" %>
       <main class="mt-3">
        <div class="container">
        <!-- Material form register -->
        <form:form action="register" modelAttribute="userForm" >
            <p class="h4 text-center mb-4">Formulaire d'inscription</p>
            
            <form:hidden path="id" />
            <spring:bind path="lastname" >
            <!-- Material input text -->
            <div class="md-form ${status.error ? 'has-error' : ''}"> 
                <i class="fa fa-user prefix grey-text"></i>
                <!-- <input type="text" id="materialFormRegisterNameEx" class="form-control"> -->
                <form:input path="lastname" class="form-control" />
                <label for="lastname">Votre Nom*</label>
                <form:errors path="lastname" cssClass="error" />
            </div>
            </spring:bind>
            <!-- Material input text -->
            <spring:bind path="firstname" >
            <div class="md-form ${status.error ? 'has-error' : ''}">
                <i class="fa fa-user prefix grey-text"></i>
                <form:input path="firstname" class="form-control"/>
                <label for="firstname">Votre Prénom*</label>
                <form:errors path="firstname" cssClass="error" />
            </div>
            </spring:bind>
            <spring:bind path="login" >
            <!-- Material input text -->
            <div class="md-form ${status.error ? 'has-error' : ''}">
                <i class="fa fa-user prefix grey-text"></i>
                <form:input path="login" class="form-control"/>
                <label for="login">Votre Login*</label>
                <form:errors path="login" cssClass="error" />
            </div>
            </spring:bind>
            <spring:bind path="mail" >
            <!-- Material input email -->
            <div class="md-form ${status.error ? 'has-error' : ''}">
                <i class="fa fa-envelope prefix grey-text"></i>
                <form:input path="mail" class="form-control"/>
                <label for="mail">Votre adresse électronique*</label>
                <form:errors path="mail" cssClass="error" />
            </div>
            </spring:bind>
            <spring:bind path="mail_confirm" >
            <!-- Material input email -->
            <div class="md-form ${status.error ? 'has-error' : ''}">
                <i class="fa fa-exclamation-triangle prefix grey-text"></i>
                <form:input path="mail_confirm" class="form-control"/>
                <label for="mail_confirm" >Confirmation de votre adresse électronique*</label>
                <form:errors path="mail_confirm" cssClass="error" />
            </div>
            </spring:bind>
            <spring:bind path="password" >
            <!-- Material input password -->
            <div class="md-form ${status.error ? 'has-error' : ''}">
                <i class="fa fa-lock prefix grey-text"></i>
                <form:password path="password" class="form-control" length="10" mdbCharCounter="" />
                <label for="password">Votre mot de passe*</label>
                <form:errors path="password" cssClass="error" />
            </div>
            </spring:bind>
            <spring:bind path="password_confirm" >
            <!-- Material input password -->
            <div class="md-form ${status.error ? 'has-error' : ''}">
                <i class="fa fa-exclamation-triangle prefix  grey-text"></i>
                <form:password path="password_confirm" class="form-control"/>
                <label for="password_confirm">Confirmation de votre mot de passe*</label>
                <form:errors path="password_confirm" cssClass="error" />
            </div>
            </spring:bind>
            <div class="text-center mt-4">
                <button class="btn btn-primary" type="submit">S'enregistrer</button>
            </div>
        </form:form>
        <!-- Material form register -->
        </div> 
       </main>
        <%@ include file="/WEB-INF/jspf/script.jspf" %>
    </body>
</html>
