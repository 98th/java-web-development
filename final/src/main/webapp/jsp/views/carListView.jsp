<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/nav" %>

<jsp:include page="headerUserPage.jsp"/>



<form class="form" style="position:absolute; margin-top:-8.5px;" action="${pageContext.request.contextPath}/" method="POST">
    <div class="from-group"/>
    <input type="hidden" name="commandName" value="postCarRequirement"/>
        <input type="image" id="1" src="${pageContext.request.contextPath}/static/style/img/pet-friendly.png" class="img-icons"
               value="pet-friendly"name="requirementType"/>

        <input type="image" id="2" src="${pageContext.request.contextPath}/static/style/img/booster.png" class="img-icons"
               value="baby-seat" name="requirementType"/>

        <input type="image" id="3" src="${pageContext.request.contextPath}/static/style/img/baby-seat.png" class="img-icons"
               value="booster" name="requirementType"/>
    </div>
</form>

<div class="mdl-grid">
    <div class="mdl-cell mdl-cell--12-col">
        <c:choose>
            <c:when test="${not empty freeCars}">
                <table class="mdl-data-table mdl-js-data-table">
                    <thead>
                    <tr>
                        <th class="mdl-data-table__cell--non-numeric"><fmt:message key="driver.carModel"/></th>
                        <th class="mdl-data-table__cell--non-numeric"><fmt:message key="driver.carColor"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${freeCars}" var="c">
                        <tr>
                            <td class="mdl-data-table__cell--non-numeric">${c.model}</td>
                            <td class="mdl-data-table__cell--non-numeric">${c.color}</td>
                            <td class="mdl-data-table__cell--non-numeric">
                                <form action="${pageContext.request.contextPath}/" method="POST">
                                    <input type="hidden" name="carId" value="${c.id}"/>
                                    <input type="hidden" name="commandName" value="postRequestInfo"/>
                                    <input class="mdl-button mdl-js-button" type="submit" value=<fmt:message key="label.confirm"/>/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="text-uppercase">
                    <h1><fmt:message key="message.no.cars"/></h1>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>