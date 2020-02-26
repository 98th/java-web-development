<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="headerUserPage.jsp"/>

<body>
<c:if test="${not empty error}">
    <h6 class="pb-30"> <fmt:message key="${requestScope.error}"/> </h6>
</c:if>

<div class="mdl-grid">
    <div class="mdl-cell mdl-cell--12-col">
        <c:choose>
            <c:when test="${not empty user.wallets}">
                <table class="mdl-data-table mdl-js-data-table">
                    <thead>
                    <tr>
                        <th class="mdl-data-table__cell--non-numeric">№</th>
                        <th class="mdl-data-table__cell--non-numeric"><fmt:message key="wallet.amount"/></th>
                        <th class="mdl-data-table__cell--non-numeric" colspan=3></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${user.wallets}" var="w">
                        <tr>
                            <td class="mdl-data-table__cell--non-numeric">${w.id}</td>
                            <td class="mdl-data-table__cell--non-numeric">${w.amount}</td>
                            <td class="mdl-data-table">
                                <form action="${pageContext.request.contextPath}/" method="POST">
                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                        <input type="hidden" name="walletId" value="${w.id}"/>
                                        <input type="hidden" name="commandName" value="postFillWallet"/>
                                        <input class="mdl-textfield__input" type="text" name="amount"
                                               pattern="^100(\.[0]{1,2})?|([0-9]|[1-9][0-9])(\.[0-9]{1,2})?$"
                                               id="sample4">
                                        <label class="mdl-textfield__label" for="sample4"><fmt:message key="wallet.amount"/></label>
                                        <span class="mdl-textfield__error"><fmt:message key="error.invalid.amount"/></span>
                                    </div>
                                </form>
                            </td>
                            <td class="mdl-data-table">
                                <form action="${pageContext.request.contextPath}/" method="POST">
                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                        <input type="hidden" name="walletId" value="${w.id}"/>
                                        <input type="hidden" name="commandName" value="postDeleteWallet"/>
                                        <input class="mdl-button mdl-js-button" type="submit" value="<fmt:message key="label.delete"/>"/>
                                    </div>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <h4 class="text-white "><fmt:message key="message.no.data"/></h4>
            </c:otherwise>
        </c:choose>
        <div id="child">
            <form action="${pageContext.request.contextPath}/" method="POST">
                <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                    <input type="hidden" name="walletId" value="${w.id}"/>
                    <input type="hidden" name="commandName" value="postAddWallet"/>
                    <input class="primary-btn text-uppercase" type="submit" value="<fmt:message key="wallet.add"/>"/>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
