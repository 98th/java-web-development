<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Navigation -->
<nav class="mdl-navigation">
    <a  href="#" onclick="changeLang('be_BY')">
        <img src="${pageContext.request.contextPath}/static/style/img/be.png" style="height: 25px; width:30px;">
    </a>

    <a  href="#" onclick="changeLang('en_US')">
        <img src="${pageContext.request.contextPath}/static/style/img/us.png" style="height: 25px; width:30px;">
    </a>
</nav>
