<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <div class="custom-card-container left-bar">
            <div class="custom-card-body">


                <a href="/profile/id/${sessionScope.id}">
                    <c:choose>
                        <c:when test="${sessionScope.profile_pic != null}">
                            <img class="custom-card-img" src="/resources/uploads/${sessionScope.profile_pic}" alt="Profile Picture">
                        </c:when>
                        <c:otherwise>
                            <img class="custom-card-img" src="../../../resources/images/pic.jpeg" alt="Profile Picture">
                        </c:otherwise>
                    </c:choose>

                </a>

                <p class="user-name"> ${sessionScope.firstname} ${sessionScope.middlename} ${sessionScope.lastname}</p>
                <p class="desc">${sessionScope.bio}</p>
            </div>
        </div>