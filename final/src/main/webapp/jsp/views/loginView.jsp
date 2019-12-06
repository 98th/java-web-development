<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/nav" %>

<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
<body>
<form name="loginForm" method="POST" action="app" class="header-right">
    <h4 class="pb-30">Welcome!</h4>
    <form class="form">
        <div class="from-group">
            <input type="hidden" name="commandName" value="postUserLogin" />
            <input class="form-control txt-field" type="text" id="login" name="login" placeholder="<fmt:message key="login"/>"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="login"/>">
            <input class="form-control txt-field" type="password" id="password" name="password" placeholder="<fmt:message key="pass"/>"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="pass"/>">
        </div>
        <div class="form-group">
            <button class="btn btn-default btn-lg btn-block text-center text-uppercase"  value="postUserLogin"><fmt:message key="login.ref"/></button>
        </div>
    </form>
    </div>
    </div>
</form><hr/>
</body></html>
