<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${durationTillNow.years != 0}">
        <c:out value="${durationTillNow.years} years ago" />
    </c:when>
    <c:when test="${durationTillNow.months != 0}">
        <c:out value="${durationTillNow.months} months ago" />
    </c:when>
    <c:when test="${durationTillNow.days != 0}">
        <c:out value="${durationTillNow.days} days ago" />
    </c:when>
    <c:when test="${durationTillNow.hours != 0}">
        <c:out value="${durationTillNow.hours} hours ago" />
    </c:when>
    <c:when test="${durationTillNow.minutes != 0}">
        <c:out value="${durationTillNow.minutes} min ago" />
    </c:when>
    <c:otherwise>
        <c:out value="${durationTillNow.seconds} sec ago" />
    </c:otherwise>
</c:choose>
.