<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/nav" %>


<%@ page import="by.training.taxi.ApplicationConstants" %>
<%@ page import="by.training.taxi.role.Role" %>

<!DOCTYPE html>
<!-- user info -->
<body>
<div>
    <c:if test="${requestScope.user.contact.firstName != null}">
        ${requestScope.user.contact.firstName }
    </c:if>
    <c:if test="${requestScope.user.contact.lastName != null}">
        ${requestScope.user.contact.lastName}
    </c:if>
    <c:if test="${requestScope.user.driver.drivingLicenceNum != null}">
        ${requestScope.user.driver.drivingLicenceNum}
    </c:if>
    <c:if test="${requestScope.user.driver.car.licencePlateNum != null}">
        ${requestScope.user.driver.car.licencePlateNum }
    </c:if>
    <c:if test="${requestScope.user.driver.car.model != null}">
        ${requestScope.user.driver.car.model }
    </c:if>
</div>
<div bgcolor="#f9d700" class="home-about-area section-gap" >
    <!-- user info here -->
    hi user!
    <div class="columns is-centered">
        <div class="column is-two-thirds">
            <div class="columns">
                <div class="column is-half" style="float: left">

                    <h4 class="pb-30">Book now!</h4>
                    <form class="form">
                        <div class="from-group">
                            <input class="form-control txt-field" type="text" id="pickLocation" name="pickLocation" placeholder="<fmt:message key="pickLocation"/>"
                                   onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="pickLocation"/>">
                            <input class="form-control txt-field" type="text" id="dropLocation" name="dropLocation" placeholder="<fmt:message key="dropLocation"/>"
                                   onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="dropLocation"/>">
                        </div>
                        <div class="form-group">
                            <div class="default-select" id="default-select">
                                <select>
                                    <option value="" disabled selected hidden>From Destination</option>
                                    <option value="1">Destination One</option>
                                    <option value="2">Destination Two</option>
                                    <option value="3">Destination Three</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="default-select" id="default-select2">
                                <select>
                                    <option value="" disabled selected hidden>To Destination</option>
                                    <option value="1">Destination One</option>
                                    <option value="2">Destination Two</option>
                                    <option value="3">Destination Three</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group dates-wrap">
                                <input id="datepicker2" class="dates form-control"  placeholder="Date & time" type="text">
                                <div class="input-group-prepend">
                                    <span  class="input-group-text"><span class="lnr lnr-calendar-full"></span></span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <button class="btn btn-default btn-lg btn-block text-center text-uppercase">book</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        </section>
    </div>
</div>
</body>
</html>