<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <!DOCTYPE html>
        <html lang="en" xmlns="http://www.w3.org/1999/html">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <script src="https://cdn.ckeditor.com/ckeditor5/23.1.0/classic/ckeditor.js"></script>

            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
                integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
                crossorigin="anonymous">

            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">


            <link href="<c:url value=" /resources/css/style.css" />" rel="stylesheet">

            <link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
            <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@500&display=swap" rel="stylesheet">

            <title>Document</title>

        </head>

        <body>
            <div class="custom-container">
                <div class="main">
                    <div class="custom-row">
                        <div class="col col1">
                            <div class="custom-card-container">
                                <img class="custom-card-img" src="/resources/images/pic.jpeg" alt="Card image cap">
                                <div class="custom-card-body">
                                    <p class="user-name"> Bimal Raj Gyawali</p>
                                    <p class="desc">Student at Nepal College of Information Technology</p>
                                </div>
                            </div>
                        </div>
                        <div class="col col2">
             <c:choose>
                <c:when test="${post != null}">
                    <section class="posts-section">
                        <div class="post-card-container">
                            <div class="post-head">
                                <div class="post-head-left">
                                    <img class="profile-pic" src="/resources/images/pic.jpeg"
                                        alt="Card image cap">
                                    <div class="post-meta">
                                        <p class="user-name post-user-name">
                                            <c:out
                                                value="${post.user.firstName} ${post.user.middleName} ${post.user.lastName}  " />
                                        </p>
                                        <p class="desc post-desc">
                                            <c:out value="${post.user.bio}" />
                                        </p>
                                        <p class="desc post-desc">
                                            <c:choose>
                                                <c:when test="${post.yearsTillNow != 0}">
                                                    <c:out value="${post.yearsTillNow} y ago" />
                                                </c:when>
                                                <c:when test="${post.monthsTillNow != 0}">
                                                    <c:out value="${post.monthsTillNow} m ago" />
                                                </c:when>
                                                <c:when test="${post.daysTillNow != 0}">
                                                    <c:out value="${post.daysTillNow} d ago" />
                                                </c:when>
                                                <c:when test="${post.hoursTillNow != 0}">
                                                    <c:out value="${post.hoursTillNow} h ago" />
                                                </c:when>
                                                <c:when test="${post.minutesTillNow != 0}">
                                                    <c:out value="${post.minutesTillNow} min ago" />
                                                </c:when>
                                                <c:otherwise>
                                                    <c:out value="${post.secondsTillNow} sec ago" />
                                                </c:otherwise>
                                            </c:choose>
        
                                        </p>
        
                                    </div>
                                </div>
                                <div class="post-head-right">
                                    <div class="dot"></div>
                                    <div class="dot"></div>
                                    <div class="dot"></div>
                                </div>
                            </div>
                            <div class="post-body">
                                ${post.content}
                                <div class="line"></div>
                                <div>
                                    <form action="/${post.id}/join-requests" method="POST">
                                        <!-- Join Request -->
                                        <input class="interested-button styled-btn" type="submit"
                                            value="Join Request">
                                    </form>
                                    <div class="comment-button">Comment</div>
                                    <div class="share-button">Share</div>
                                </div>
                                <div class="line"></div>
                                <p style="margin-left:35px;">
                                    <c:choose>
                                        <c:when test="${post.joinRequestsCount > 0}">
                                            <a href="/${post.id}/join-requests"><c:out value="${post.joinRequestsCount}" /> people sent join requests</a>
                                            
                                            <c:forEach var="user" items="${post.usersRequestingToJoin}">
                                                <p>${user.id} ${user.firstName} ${user.middleName} ${user.lastName}</p>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <p>No join requests in this post</p>
                                        </c:otherwise>
                                    </c:choose>
                                    
                                </p>
                               
                            </div>
                    </section>
                </c:when>
                <c:otherwise>
                    <c:out value="404 error"/>
                </c:otherwise>
            </c:choose>
            </div>
          