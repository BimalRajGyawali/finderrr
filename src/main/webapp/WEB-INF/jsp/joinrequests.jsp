<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

            <!DOCTYPE html>
            <html lang="en" xmlns="http://www.w3.org/1999/html">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <script src="https://cdn.ckeditor.com/ckeditor5/23.1.0/classic/ckeditor.js"></script>

                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
                    rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
                    crossorigin="anonymous">

                <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
                <link rel="stylesheet"
                    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">



                <link href="<c:url value=" /resources/css/style.css" />" rel="stylesheet">

                <link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
                <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@500&display=swap" rel="stylesheet">

                <title>Document</title>

            </head>

            <body>
                <jsp:include page="header.jsp" />
                <div class="custom-container">
                    <div class="main">
                        <div class="custom-row">
                            <div class="col col1">
                                	 <c:choose>
   										 <c:when test="${not empty sessionScope.email}">
         									<div class="custom-card-container">
    									 </c:when>  
    							   
   										 <c:otherwise>
        									<div class="custom-card-container" style="visibility:hidden">
    									 </c:otherwise>
									</c:choose>
                            
                                    <a href="/create-profile"> <img class="custom-card-img" src="/resources/uploads/${sessionScope.profile_pic}" alt="Card image cap"></a>
                                    <div class="custom-card-body">
                                       <p class="user-name"> ${sessionScope.firstname} ${sessionScope.middlename} ${sessionScope.lastname}</p>
                                   	   <p class="desc">${sessionScope.bio}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col col2">
                                <c:choose>
                                    <c:when test="${post != null}">

                                        <c:if test="${joinRequestResponse != null}">
                                            <c:choose>
                                                <c:when test="${joinRequestResponse.successStatus}">
                                                    <div class="alert alert-success alert-dismissible">
                                                        <a href="#" class="close" data-dismiss="alert"
                                                            aria-label="close">&times;</a>
                                                        <p>${joinRequestResponse.responseMessage}</p>
                                                    </div>
                                                </c:when>

                                                <c:otherwise>
                                                    <div class="alert alert-danger alert-dismissible">
                                                        <a href="#" class="close" data-dismiss="alert"
                                                            aria-label="close">&times;</a>
                                                        <p>${joinRequestResponse.responseMessage}</p>
                                                    </div>
                                                </c:otherwise>

                                            </c:choose>

                                        </c:if>




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
                                                                <span class="">. ${post.status}</span>
                                                            </p>

                                                        </div>
                                                    </div>
                                                    <div class="post-head-right">
                                                        <!-- <div class="dot"></div>
                                                        <div class="dot"></div>
                                                        <div class="dot"></div> -->
                                                        <a href="/editpost/${post.id}">Edit Post</a>
                                                    </div>
                                                </div>
                                                <div class="post-body">
                                                    <div class="post-content">
                                                        ${post.content}
                                                        <p class="mt-5">
                                                            <c:forEach var="hashtag" items="${post.hashTags}">
                                                             <a href="/posts/hashtag/${hashtag.title}" ><c:out value="#${hashtag.title}" /></a>
                                                             &nbsp;
                                                            </c:forEach>
                                                        </p>
                                                    </div>
                                                    <div class="line"></div>
                                                    <div>

                                                        
                                                          <c:choose>
   															 <c:when test="${not empty sessionScope.email}">
        														<form  action="/${post.id}/join-requests" method="POST">
    														 </c:when>    
    										
    														 <c:otherwise>
 										       					 <form  action="/login/post/${post.id}" method="GET">
    														 </c:otherwise>
														  </c:choose>
                                                            <!-- Join Request -->


                                                            <input class="interested-button styled-btn" type="submit"
                                                                value="Send a Join Request">
                                                        </form>
                                                        <a class="comment-button" href="/post/${post.id}">Comments
                                                            (${post.commentsCount})</a>
                                                        <a class="share-button" href="/${post.id}/join-requests">Join
                                                            Requests (${post.joinRequestsCount})</a>
                                                    </div>

                                                    <div class="line"></div>
                                                    <c:choose>
                                                        <c:when test="${hasJoinRequests}">
                                                            <p>People who sent join requests to this post</p>

                                                            <c:forEach var="user" items="${post.usersRequestingToJoin}">

                                                                <a href="" class="link post-head-left"
                                                                    style="display: block;">
                                                                    <img class="profile-pic"
                                                                        src="/resources/images/pic.jpeg"
                                                                        alt="Card image cap">
                                                                    <div class="post-meta">
                                                                        <p class="user-name post-user-name mt-4">
                                                                            <c:out
                                                                                value="${user.firstName} ${user.middleName} ${user.lastName}  " />
                                                                        </p>
                                                                        <p class="desc">
                                                                            <c:out
                                                                                value="Student at Nepal College of Information Technology" />
                                                                        </p>
                                                                    </div>
                                                                </a>




                                                            </c:forEach>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <p>No Join Requests in this post.</p>
                                                        </c:otherwise>
                                                    </c:choose>


                                                    </p>

                                                </div>
                                        </section>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="404 error" />
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
                <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
            </body>

            </html>