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

            <title>Finder</title>

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
                            <c:if test="${not empty sessionScope.email}">
                                <div class="alert alert-success alert-dismissible">
                                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                    <p class="small">In this section, you will see the posts with hashtags you have followed. To explore more posts, go to <a href="/explore">explore section.</a>
                                    </p>

                                </div>
                            </c:if>


                            <c:choose>
                                <c:when test="${deleteSuccess}">
                                    <div class="alert alert-success alert-dismissible">
                                        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                        <strong>Success!</strong> Post Deleted successfully.
                                    </div>
                                </c:when>

                                <c:when test="${deleteFailure}">
                                    <div class="alert alert-danger alert-dismissible">
                                        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                        <strong>Failed!</strong> Error in deleting post.
                                    </div>
                                </c:when>

                            </c:choose>
                            <div id="follow-error" style="display: none;">

                            </div>


                            <div class="post-card-container">
                                <div class="post-head">
                                    <div class="post-head-left">
                                        <c:choose>
                                            <c:when test="${sessionScope.profile_pic != null}">
                                                <img class="small-img" src="/resources/uploads/${sessionScope.profile_pic}" alt="Profile Picture">
                                            </c:when>
                                            <c:otherwise>
                                                <img class="small-img" src="../../../resources/images/pic.jpeg" alt="Profile Picture">
                                            </c:otherwise>
                                        </c:choose>
                                        <div class="create-post-input ">
                                            <a href="${not empty sessionScope.email ? " /create-post "
                                                : "/login/post/create " }" class="post-input">Want to find someone ?
                                                Create a Post </a>

                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="line"></div>

                            <c:choose>
                                <c:when test="${hasPosts}">
                                    <c:forEach var="post" items="${posts}">


                                        <section class="posts-section">
                                            <div class="post-card-container">
                                                <div class="post-head">
                                                    <div class="post-head-left">

                                                        <c:choose>
                                                            <c:when test="${post.user.profilePic != null}">

                                                                <img class="small-img" src="/resources/uploads/${post.user.profilePic}" alt="Profile Picture">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <img class="small-img" src="../../../resources/images/pic.jpeg" alt="Profile Picture">
                                                            </c:otherwise>
                                                        </c:choose>


                                                        <div class="post-meta">
                                                            <p class="user-name post-user-name">
                                                                <a href="/profile/id/${post.user.id}" class="profileLink">
                                                                    
                                                                        <c:out value="${post.user.firstName} ${post.user.middleName} ${post.user.lastName}  " />
                                                                    
                                                                </a>
                                                                
                                                            </p>
                                                            <p class="desc post-desc">
                                                                <c:out value="${post.user.bio}" />
                                                            </p>
                                                            <p class="desc post-desc">
                                                                <c:choose>
                                                                    <c:when test="${post.durationTillNow.years != 0}">
                                                                        <c:out value="${post.durationTillNow.years} years ago" />
                                                                    </c:when>
                                                                    <c:when test="${post.durationTillNow.months != 0}">
                                                                        <c:out value="${post.durationTillNow.months} months ago" />
                                                                    </c:when>
                                                                    <c:when test="${post.durationTillNow.days != 0}">
                                                                        <c:out value="${post.durationTillNow.days} days ago" />
                                                                    </c:when>
                                                                    <c:when test="${post.durationTillNow.hours != 0}">
                                                                        <c:out value="${post.durationTillNow.hours} hours ago" />
                                                                    </c:when>
                                                                    <c:when test="${post.durationTillNow.minutes != 0}">
                                                                        <c:out value="${post.durationTillNow.minutes} min ago" />
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <c:out value="${post.durationTillNow.seconds} sec ago" />
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                .<span class="post-status ${post.status}">
                                                                    ${post.status}</span>
                                                            </p>

                                                        </div>
                                                    </div>
                                                    <div class="post-head-right">
                                                        <!-- <div class="dot"></div>
                                                            <div class="dot"></div>
                                                            <div class="dot"></div> -->
                                                        <c:if test="${sessionScope.id==post.user.id}"><a href="/editpost/${post.id}">Edit Post</a></c:if>
                                                    </div>
                                                </div>
                                                <div class="post-body">
                                                    <div class="post-content">
                                                        ${post.content}
                                                        <p class="mt-5">
                                                            <c:forEach var="hashtag" items="${post.hashTags}">
                                                                <a href="/posts/hashtag/${hashtag.title}">

                                                                    <c:out value="#${hashtag.title}" />
                                                                </a>
                                                                &nbsp;
                                                            </c:forEach>
                                                        </p>
                                                    </div>
                                                    <div class="line"></div>


                                                    <div class="jr-bar">
                                                        <c:if test="${post.status == 'ongoing'}">
                                                            <c:choose>
                                                                <c:when test="${not empty sessionScope.email}">
                                                                    <form action="/${post.id}/join-requests" method="POST">
                                                                </c:when>

                                                                <c:otherwise>
                                                                    <form action="/login/post/${post.id}" method="GET">
                                                                </c:otherwise>
                                                            </c:choose>


                                                            <input class="interested-button styled-btn" type="submit" value="Send a Join Request">
                                                            </form>

                                                        </c:if>
                                                        <div class="jr-bar-cmt">
                                                            <a class="comment-button" href="/post/${post.id}">Comments (${post.commentsCount})</a>
                                                            <a class="share-button" href="/${post.id}/join-requests">Join Requests (${post.joinRequestsCount})</a>

                                                        </div>


                                                    </div>

                                                    <!-- <p style="margin-left:35px;">
                                                            <c:choose>
                                                                <c:when test="${post.joinRequestsCount > 0}">
                                                                    <a href="/${post.id}/join-requests"><c:out value="${post.joinRequestsCount}" /> people sent join requests</a>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <p>No join requests in this post</p>
                                                                </c:otherwise>
                                                            </c:choose>
                                                            
                                                        </p> -->
                                                    <div class="line"></div>


                                                    <c:if test="${post.status == 'ongoing'}">
                                                        <div class="comment-posting">
                                                            <div class="comment-post-details">
                                                                <c:choose>
                                                                    <c:when test="${sessionScope.profile_pic != null}">
                                                                        <img class="comment-profile-pic" src="/resources/uploads/${sessionScope.profile_pic}" alt="Profile Picture">
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <img class="comment-profile-pic" src="../../../resources/images/pic.jpeg" alt="Profile Picture">
                                                                    </c:otherwise>
                                                                </c:choose>

                                                                <c:choose>
                                                                    <c:when test="${not empty sessionScope.email}">

                                                                        <span class="comment-box2" role="textbox" id="comment" contentEditable=true data-ph="Write A Comment..." onkeydown="commentPost(event, '${post.id}')"></span>

                                                                    </c:when>

                                                                    <c:otherwise>
                                                                        <a href="/login/post/${post.id}">
                                                                            <span class="comment-box2" role="textbox" id="comment" contentEditable=false onkeydown="commentPost(event, '${post.id}')"><span style="margin-left:85px;">Login to Comment</span></span>
                                                                        </a>

                                                                    </c:otherwise>
                                                                </c:choose>



                                                                <div class="invisible-form">
                                                                    <form action="/write-comment" method="post">
                                                                        <input type="text" name="post_id" value="${post.id}">
                                                                        <input type="text" name="comments_count" value="${post.commentsCount}">
                                                                        <input type="text" id="form_input${post.id}" name="form_comment_content">
                                                                        <input type="submit" value="Submit" id="submit_button${post.id}">
                                                                    </form>
                                                                </div>
                                                            </div>

                                                        </div>
                                                    </c:if>



                                                </div>
                                        </section>
                                    </c:forEach>
                                    <c:choose>
                                        <c:when test="${hasOlderPosts}">
                                            <a class="btn btn-primary mt-5 mb-5" href="/?before=${oldestDate}">Show older posts</a>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${not empty sessionScope.email}">
                                                    <p class="small mt-4 mb-5"> You have reached the end of posts with hashtags you have followed.
                                                        <a href="/explore">Explore more posts</a> &nbsp;&nbsp;OR&nbsp;&nbsp;
                                                        <a href="/recommended-hashtags">Follow hashtags</a>&nbsp;&nbsp;OR&nbsp;&nbsp;
                                                        <a href="/">Click to refresh</a></p>
                                                </c:when>
                                                <c:otherwise>
                                                    <p class="mt-4 mb-5"> You have reached the end of posts.
                                                        <a href="/">Click to refresh</a></p>
                                                </c:otherwise>
                                            </c:choose>

                                        </c:otherwise>
                                    </c:choose>

                                </c:when>
                                <c:otherwise>
                                    <p>No posts available with hashtags you have followed.
                                        <a href="/explore">Explore more posts</a> &nbsp;&nbsp;OR&nbsp;&nbsp;
                                        <a href="/recommended-hashtags">Follow hashtags</a>&nbsp;&nbsp;OR&nbsp;&nbsp;
                                        <a href="/">Click to refresh</a></p>
                                </c:otherwise>

                            </c:choose>




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
            <script src="resources/js/solo_post.js"></script>
            <script src="resources/js/ajax.js"></script>

            <script src="../../../resources/js/follow.js"></script>
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>



        </body>

        </html>