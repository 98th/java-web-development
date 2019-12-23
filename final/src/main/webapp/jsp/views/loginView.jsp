<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/nav" %>
<form name="loginForm" method="POST" action="app" class="header-right" id="child" >
    <h4 class="pb-30"><fmt:message key="label.welcome"/></h4>
    <form class="form">
        <div class="from-group">
            <input type="hidden" name="commandName" value="postUserLogin" />
            <input class="form-control txt-field" type="text" name="login" placeholder="<fmt:message key="user.login"/>"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="user.login"/>">
            <input class="form-control txt-field" type="password"  name="password" placeholder="<fmt:message key="user.pass"/>"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="user.pass"/>">
        </div>
        <div class="form-group">
            <button class="btn btn-default btn-lg btn-block text-center text-uppercase"  value="postUserLogin"><fmt:message key="links.login"/></button>
        </div>
    </form>
    </div>
    </div>
</form>

