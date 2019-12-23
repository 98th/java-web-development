<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div name="wallet" method="POST" action="app" class="header-right" id="child">
    <form class="form">
        <h6 class="pb-30"><fmt:message key="discount.amount"/>  :  ${sessionScope.user.discount.amount} </h6>
        <h6 class="pb-30"><fmt:message key="wallet.amount"/>   :   ${sessionScope.user.wallet.amount}  </h6>

        <div class="from-group">
            <input type="hidden" name="commandName" value="postFillWallet"/>
            <input class="form-control txt-field"  type="text" name="amount"
                   placeholder="<fmt:message key="wallet.amount"/>"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="wallet.amount"/>">
        </div>
        <div class="form-group">
            <button class=" btn btn-default btn-lg btn-block text-center text-uppercase mdl-textfield__input"
                    pattern="^100(\.[0]{1,2})?|([0-9]|[1-9][0-9])(\.[0-9]{1,2})?$" value="postEditUserInfo"><fmt:message
                    key="label.submit"/></button>
        </div>
    </form>
</div>
