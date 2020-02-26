<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="headerUserPage.jsp"/>

<jsp:include page="headerUserPage.jsp"/>


<div class="mdl-grid">
    <div class="mdl-cell mdl-cell--12-col">
        <c:choose>
            <c:when test="${not empty requests}">
                <table class="mdl-data-table mdl-js-data-table">
                    <thead>
                    <tr>
                        <th class="mdl-data-table__cell--non-numeric"><fmt:message key="request.date"/></th>
                        <th class="mdl-data-table__cell--non-numeric"><fmt:message key="request.price"/></th>
                        <th class="mdl-data-table__cell--non-numeric"><fmt:message key="request.pickLocation"/></th>
                        <th class="mdl-data-table__cell--non-numeric"><fmt:message key="request.dropLocation"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requests}" var="r">
                        <tr>
                            <td class="mdl-data-table__cell--non-numeric">${r.requestDate}</td>
                            <td class="mdl-data-table__cell--non-numeric">${r.price}</td>
                            <td class="mdl-data-table__cell--non-numeric">${r.pickLocation}</td>
                            <td class="mdl-data-table__cell--non-numeric">${r.dropLocation}</td>
                            <td class="mdl-data-table__cell--non-numeric">
                                <form action="${pageContext.request.contextPath}/" method="POST">
                                    <input type="hidden" name="requestId" value="${d.id}"/>
                                    <input type="hidden" name="commandName" value="deleteRequest"/>
                                    <input class="mdl-button mdl-js-button" type="submit" value="delete"/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="text-uppercase">
                    <h1><fmt:message key="message.no.data"/></h1>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>