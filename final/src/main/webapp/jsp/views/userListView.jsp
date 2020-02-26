<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
</head>

<body>
<jsp:include page="headerUserPage.jsp"/>

<c:if test="${not empty error}">
    <h6 class="pb-30"> <fmt:message key="${requestScope.error}"/> </h6>
</c:if>

<div class="mdl-grid">
    <div class="mdl-cell mdl-cell--12-col">
        <c:choose>
            <c:when test="${not empty users}">
                <table class="mdl-data-table mdl-js-data-table">
                    <thead>
                    <tr>
                        <th class="mdl-data-table__cell--non-numeric"><fmt:message key="user.login"/></th>
                        <th class="mdl-data-table__cell--non-numeric"><fmt:message key="user.role"/></th>
                        <th class="mdl-data-table__cell--non-numeric"><fmt:message key="user.discount"/></th>
                        <th class="mdl-data-table__cell--non-numeric"><fmt:message key="user.blocking"/></th>
                        <th class="mdl-data-table__cell--non-numeric" colspan=3></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="count" value="0" scope="page" />
                    <c:forEach items="${users}" var="u">
                        <tr>
                            <td class="mdl-data-table__cell--non-numeric">${u.login}</td>
                            <td class="mdl-data-table__cell--non-numeric">${u.role}</td>
                            <td class="mdl-data-table__cell--non-numeric">${u.discount.amount}</td>
                            <td class="mdl-data-table__cell--non-numeric">${u.isLocked}</td>
                            <td class="mdl-data-table">
                                <form action="${pageContext.request.contextPath}/" method="POST">
                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                        <input type="hidden" name="userId" value="${u.id}"/>
                                        <input type="hidden" name="commandName" value="postAssignDiscount"/>
                                        <input class="mdl-textfield__input" type="text" name="discountAmount"
                                               pattern="^100(\.[0]{1,2})?|([0-9]|[1-9][0-9])(\.[0-9]{1,2})?$"
                                               id="sample4">
                                        <label class="mdl-textfield__label" for="sample4"><fmt:message key="user.discount"/></label>
                                        <span class="mdl-textfield__error"><fmt:message key="error.invalid.discount"/></span>
                                    </div>
                                </form>
                            </td>
                            <td class="mdl-data-table__cell--non-numeric">
                                <form action="${pageContext.request.contextPath}/" method="POST">
                                    <input type="hidden" name="userId" value="${u.id}"/>
                                    <input type="hidden" name="commandName" value="lockUser"/>
                                    <input class="mdl-button mdl-js-button" type="submit" value="<fmt:message key="label.lock"/>"/>
                                </form>
                            </td>
                            <td class="mdl-data-table__cell--non-numeric">
                                <c:set var="count" value="${count + 1}" scope="page"/>
                                <button  id="show-dialog-btn${count}" type="button" class="mdl-button show-dialog"> <fmt:message key="label.additional.inf"/>  </button>
                                <dialog class="mdl-dialog">
                                    <h6 class="mdl-dialog__title"> ${u.contact.firstName} ${u.contact.lastName} </h6>
                                    <div class="mdl-dialog__content">
                                        <p>
                                                ${u.contact.email}
                                            </br>
                                                ${u.contact.phone}
                                        </p>
                                    </div>
                                    <div class="mdl-dialog__actions">
                                        <button type="button" class="mdl-button close"><fmt:message key="label.back"/></button>
                                    </div>
                                </dialog>
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
    </div>


    <script>
        window.dialogPolyfill = dialogPolyfill;
        var dialog = document.querySelector('dialog');
        var  showDialogButtons = document.querySelectorAll('.show-dialog');
        if (!dialog.showModal) {
            dialogPolyfill.registerDialog(dialog);
        }
        for (var i = 0; i< showDialogButtons.length;i++){
            showDialogButtons[i].addEventListener('click', function() {
                dialog.showModal();
            });
        }
        dialog.querySelector('.close').addEventListener('click', function() {
            dialog.close();
        });
    </script>

</div>
</body>
</html>