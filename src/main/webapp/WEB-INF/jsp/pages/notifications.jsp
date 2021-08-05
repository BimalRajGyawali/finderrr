<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <!DOCTYPE html>
        <html lang="en" xmlns="http://www.w3.org/1999/html">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <script src="https://cdn.ckeditor.com/ckeditor5/23.1.0/classic/ckeditor.js"></script>

            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">



            <link href="<c:url value=" /resources/css/style.css " />" rel="stylesheet">
            <link href="<c:url value=" /resources/css/hashtag.css " />" rel="stylesheet">

            <link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
            <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@500&display=swap" rel="stylesheet">

            <title>Posts</title>

        </head>

        <body>
            <c:choose>
                <c:when test="${not empty sessionScope.email}">
                    <jsp:include page="../fragments/header.jsp" />
                </c:when>

                <c:otherwise>
                    <jsp:include page="../fragments/guest-header.jsp" />
                </c:otherwise>
            </c:choose>
            <div class="custom-container">
                <div class="main">
                    <div class="custom-row">
                        <div class="col col1">
                            <c:choose>
                                <c:when test="${not empty sessionScope.email}">
                                    <jsp:include page="../fragments/left-bar.jsp" />
                                </c:when>

                                <c:otherwise>

                                    <div class="custom-card-container" style="visibility: hidden;">
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="col col2">
                            <div id="follow-error" style="display: none;">

                            </div>
                            <section>

                                <c:choose>
                                    <c:when test="${hasNotifications}">


                                        <c:forEach var="n" items="${notifications}">
                                            <div class="noti-card">
                                                <div class="noti-img">
                                                    <c:choose>
                                                        <c:when test="${n.initiator.profilePic != null}">

                                                            <img class="small-img" src="/resources/uploads/${n.initiator.profilePic}" alt="Profile Picture">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img class="small-img" src="../../../resources/images/pic.jpeg" alt="Profile Picture">
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div class="noti-desc">
                                                    <c:choose>
                                                        <c:when test="${n.seen}">
                                                            <c:choose>
                                                                <c:when test="${n.notificationType eq 'comment'}">
                                                                    <p><a href="/notifications/${n.id}/">
                                                    ${n.initiator.firstName} ${n.initiator.middleName} ${n.initiator.lastName}
                                                    commented on your post.
                                                </a>
                                                                    </p>
                                                                </c:when>
                                                                <c:when test="${n.notificationType eq 'joinrequest'}">
                                                                    <p><a href="/notifications/${n.id}">
                                                        ${n.initiator.firstName} ${n.initiator.middleName} ${n.initiator.lastName}
                                                        sent a join request on your post.
                                                    </a>
                                                                    </p>

                                                                </c:when>

                                                            </c:choose>
                                                            <span class="desc">
                                                <c:choose>
                                                    <c:when test="${n.durationTillNow.years != 0}">
                                                        <c:out value="${n.durationTillNow.years} years ago" />
                                                    </c:when>
                                                    <c:when test="${n.durationTillNow.months != 0}">
                                                        <c:out value="${n.durationTillNow.months} months ago" />
                                                    </c:when>
                                                    <c:when test="${n.durationTillNow.days != 0}">
                                                        <c:out value="${n.durationTillNow.days} days ago" />
                                                    </c:when>
                                                    <c:when test="${n.durationTillNow.hours != 0}">
                                                        <c:out value="${n.durationTillNow.hours} hours ago" />
                                                    </c:when>
                                                    <c:when test="${n.durationTillNow.minutes != 0}">
                                                        <c:out value="${n.durationTillNow.minutes} min ago" />
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:out value="${n.durationTillNow.seconds} sec ago" />
                                                    </c:otherwise>
                                                </c:choose></span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:choose>
                                                                <c:when test="${n.notificationType eq 'comment'}">
                                                                    <p>
                                                                        <a href="/notifications/${n.id}/">
                                                                            <strong>${n.initiator.firstName} ${n.initiator.middleName} ${n.initiator.lastName}
                                                        commented on your post.</strong>
                                                                        </a>
                                                                    </p>
                                                                </c:when>
                                                                <c:when test="${n.notificationType eq 'joinrequest'}">
                                                                    <p>
                                                                        <a href="/notifications/${n.id}">
                                                                            <strong>  ${n.initiator.firstName} ${n.initiator.middleName} ${n.initiator.lastName}
                                                        sent a join request on your post.</strong>
                                                                        </a>
                                                                    </p>

                                                                </c:when>

                                                            </c:choose>
                                                            <span class="desc" style="font-weight: bold;">
                                                <c:choose>
                                                    <c:when test="${n.durationTillNow.years != 0}">
                                                        <c:out value="${n.durationTillNow.years} years ago" />
                                                    </c:when>
                                                    <c:when test="${n.durationTillNow.months != 0}">
                                                        <c:out value="${n.durationTillNow.months} months ago" />
                                                    </c:when>
                                                    <c:when test="${n.durationTillNow.days != 0}">
                                                        <c:out value="${n.durationTillNow.days} days ago" />
                                                    </c:when>
                                                    <c:when test="${n.durationTillNow.hours != 0}">
                                                        <c:out value="${n.durationTillNow.hours} hours ago" />
                                                    </c:when>
                                                    <c:when test="${n.durationTillNow.minutes != 0}">
                                                        <c:out value="${n.durationTillNow.minutes} min ago" />
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:out value="${n.durationTillNow.seconds} sec ago" />
                                                    </c:otherwise>
                                                </c:choose>
                                                </span>


                                                        </c:otherwise>

                                                    </c:choose>


                                                </div>




                                            </div>

                                        </c:forEach>

                                        <c:choose>
                                            <c:when test="${hasOlderNotifications}">
                                                <a class="btn btn-primary mt-5 mb-5" href="/notifications?before=${oldestDate}">Show older notifications</a>
                                            </c:when>
                                            <c:otherwise>

                                                <p class="mt-4 mb-5 small"> You have reached the end of notifications.
                                                    <a href="/notifications">Go to top</a></p>
                                            </c:otherwise>
                                        </c:choose>


                                    </c:when>
                                    <c:otherwise>
                                        <p>No notifications available.</p>
                                    </c:otherwise>
                                </c:choose>




                            </section>

                        </div>
                        <div class="col col3">

                            <c:if test="${not empty sessionScope.email}">
                                <jsp:include page="../fragments/right-bar.jsp" />
                            </c:if>

                        </div>
                    </div>

                </div>
            </div>

            </div>
            <script src="../../resources/js/hashtag.js"></script>
            <script src="../../resources/js/ajax.js"></script>

            <script>
                function follow(event) {
                    event.preventDefault();
                    let hashtag = event.target.id;
                    let followError = document.getElementById("follow-error");
                    if (!hashtag) {
                        displayError(followError);

                    } else {
                        if (event.target.innerText == "Follow") {
                            postAjax('/follow', {
                                    "hashtag": hashtag
                                })
                                .then(data => {
                                    if (data === true) {
                                        event.target.innerText = "Unfollow";
                                        event.target.style.border = "1px solid green";
                                    } else {
                                        displayError(followError);
                                    }
                                })
                                .catch(err => displayError(followError));


                        } else {
                            postAjax('/unfollow', {
                                    "hashtag": hashtag
                                })
                                .then(data => {
                                    if (data === true) {
                                        event.target.innerText = "Follow";
                                        event.target.style.border = "1px solid blue";
                                    } else {
                                        displayError(followError);
                                    }
                                })
                                .catch(err => displayError(followError));
                        }

                    }



                }
            </script>



            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>



        </body>

        </html>