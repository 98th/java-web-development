<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="headerUserPage.jsp"/>


<div class="mdl-grid">
    <div class="mdl-cell mdl-cell--12-col">
        <c:choose>
            <c:when test="${not empty drivers}">
                <table class="mdl-data-table mdl-js-data-table">
                    <thead>
                    <tr>
                        <th class="mdl-data-table__cell--non-numeric"><fmt:message key="contact.f.name"/></th>
                        <th class="mdl-data-table__cell--non-numeric"><fmt:message key="contact.l.name"/></th>
                        <th class="mdl-data-table__cell--non-numeric"><fmt:message key="contact.phone"/></th>
                        <th class="mdl-data-table__cell--non-numeric"><fmt:message key="driver.drivingLicenceNum"/></th>
                        <th class="mdl-data-table__cell--non-numeric"><fmt:message key="driver.carModel"/></th>
                        <th class="mdl-data-table__cell--non-numeric"><fmt:message key="user.blocking"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${drivers}" var="d">
                        <tr>
                            <td class="mdl-data-table__cell--non-numeric">${d.contact.firstName}</td>
                            <td class="mdl-data-table__cell--non-numeric">${d.contact.lastName}</td>
                            <td class="mdl-data-table__cell--non-numeric">${d.contact.phone}</td>
                            <td class="mdl-data-table__cell--non-numeric">${d.drivingLicenceNum}</td>
                            <td class="mdl-data-table__cell--non-numeric">${d.car.model}</td>
                            <td class="mdl-data-table__cell--non-numeric">${d.userAccount.isBlocking}</td>
                            <td class="mdl-data-table__cell--non-numeric">
                                <form action="${pageContext.request.contextPath}/" method="POST">
                                    <input type="hidden" name="userId" value="${d.userId}"/>
                                    <input type="hidden" name="commandName" value="lockUser"/>
                                    <input class="mdl-button mdl-js-button" type="submit" value="Block user"/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <fmt:message key="message.no.data"/>
            </c:otherwise>
        </c:choose>
    </div>
</div>