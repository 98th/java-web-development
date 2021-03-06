<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/nav" %>

<!DOCTYPE html>
<html>
<header id="header">
    <div class="header-top"></div>
    <div class="container main-menu">
        <div class="row justify-content-between d-flex"  style="float: right;">
            <nav id="nav-menu-container">
                <ul class="nav-menu">
                    <nav:nav-links />
                    <nav:lang/>
                </ul>
            </nav><!-- #nav-menu-container -->
        </div>
    </div>
</header><!-- #header -->
</html>