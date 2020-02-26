<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:directive.attribute name="fieldName" required="false" type="java.lang.String" description="field name "/>
<jsp:directive.attribute name="errors" required="false" type="java.lang.String"
                         description="validation errors"/>
<jsp:directive.attribute name="inputPattern" required="false" type="java.lang.String" description="pattern"/>
<jsp:directive.attribute name="patternTitle" required="false" type="java.lang.String"
                         description="title"/>
<jsp:directive.attribute name="inputPlaceholder" required="false" type="java.lang.String" description="placeholder"/>
<jsp:directive.attribute name="password" required="false" type="java.lang.Boolean" description="password"/>

<div>
    <c:set var="inputType" value="text"/>
    <c:if test="${password eq 'true'}">
        <c:set var="inputType" value="password"/>
    </c:if>
    <input class="form-control txt-field" type="${inputType}" name="${fieldName}"
            placeholder="<fmt:message key="${inputPlaceholder}"/>" onfocus="this.placeholder = ''"
           onblur="this.placeholder = <fmt:message key="${inputPlaceholder}"/>"
    <fmt:message key="${inputPlaceholder}"/>
    <c:if test="${not empty patternTitle}">
        title="<fmt:message key="${patternTitle}"/>"
    </c:if>/>
</div>
<div>
    <c:if test="${not empty errors}">
        <c:forEach items="${errors}" var="error">
                <h6 class="pb-30"><fmt:message key="${error}"/></h6>
        </c:forEach>
    </c:if>
</div>

