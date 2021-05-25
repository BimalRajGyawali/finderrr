<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="custom-card-container">
    <div class="custom-card-body">
    
    
        <a href="/view-profile"> <img class="custom-card-img"
            src="/resources/uploads/${sessionScope.profile_pic}" alt="Card image cap"></a>
    
<p class="user-name"> ${sessionScope.firstname} ${sessionScope.middlename}  ${sessionScope.lastname}</p>
    <p class="desc">${sessionScope.bio}</p>
</div>
</div>