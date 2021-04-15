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
            <link rel="stylesheet"
                href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">



            <link href="<c:url value=" /resources/css/style.css" />" rel="stylesheet">
            <link href="<c:url value=" /resources/css/hashtag.css" />" rel="stylesheet">

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
                            <div class="custom-card-container">
                                <img class="custom-card-img" src="/resources/images/pic.jpeg" alt="Card image cap">
                                <div class="custom-card-body">
                                    <p class="user-name"> Bimal Raj Gyawali</p>
                                    <p class="desc">Student at Nepal College of Information Technology</p>
                                </div>
                            </div>
                        </div>
                        <div class="col col2">
                           


                            <div id="create-post-area">
                               <p>Posts tagged under  &nbsp;
                                <a href="/posts/hashtag/${requestedHashTag}" ><c:out value="#${requestedHashTag}" /></a>
                               </p>
                               <div class="line"></div>
                               <p><c:choose >
                                <c:when test="${hasPosts}"></c:when>
                                <c:otherwise>
                                    No Posts tagged under <a href=""><c:out value="#${requestedHashTag}" /></a>

                                </c:otherwise>
                               </c:choose></p>
                                <c:forEach var="post" items="${posts}">


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

                                                    <form action="/${post.id}/join-requests" method="POST">
                                                        <!-- Join Request -->


                                                        <input class="interested-button styled-btn" type="submit"
                                                            value="Send a Join Request">
                                                    </form>
                                                    <a class="comment-button" href="/post/${post.id}">Comments
                                                        (${post.commentsCount})</a>
                                                    <a class="share-button" href="/${post.id}/join-requests">Join
                                                        Requests (${post.joinRequestsCount})</a>
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



                                                <div class="comment-posting">
                                                    <img class="comment-profile-pic" src="/resources/images/pic.jpeg"
                                                        alt="Card image cap">
                                                    <div class="comment-post-details">
                                                        <span class="comment-box2" role="textbox" id="comment"
                                                            contentEditable=true data-ph="Write A Comment..."
                                                            onkeydown="commentPost(event, '${post.id}')"></span>
                                                        <div class="invisible-form">
                                                            <form action="/write-comment" method="post">
                                                                <input type="text" name="post_id" value="${post.id}">
                                                                <input type="text" name="comments_count"
                                                                    value="${post.commentsCount}">
                                                                <input type="text" id="form_input${post.id}"
                                                                    name="form_comment_content">
                                                                <input type="submit" value="Submit"
                                                                    id="submit_button${post.id}">
                                                            </form>
                                                        </div>
                                                    </div>

                                                </div>















                                                <!-- <div class="comment-posting">
                                                    <img class="comment-profile-pic" src="resources/images/pic.jpeg"
                                                        alt="Card image cap">
                                                    <div class="comment-post-details">
                                                        <span class="comment-box2" role="textbox" id="comment"
                                                            contentEditable=true data-ph="Write A Comment..."
                                                            onkeydown="commentPost(event,'commentContainer${post.id}')"></span>
                                                    </div>

                                                </div>
                                                <div class="line"></div>
                                                <div id="commentContainer${post.id}"> -->

                                                <!-- <div class="comment">

                                            <img class="comment-profile-pic" src="../static/images/pic.jpeg"
                                                alt="Card image cap">
                                            <div class="comment-details">
                                                <p class="account-name">Sampanna Pokhara</p>
                                                <p class="comment-data">Yeah To the left of that decimal, I need seven
                                                    figures to play the joint
                                                    Turn up your decibels, peep how I decimate a joint
                                                    Check out my projects like them workers that Section 8 appoints
                                                    And you'll see how I flipped, like exclamation
                                                    pointssdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
                                                    My brothers shoot first as if they never played the point, more two
                                                    guards
                                                    Enough straps to fill four U-Hauls</p>
                                            </div>
                                            <p class="reply-button"><span class="reply-button-clickable">Reply</span>
                                            </p>
                                            <div class="reply">
                                                <p>Ram Sharan</p>
                                                <p>no u are wrong i dont think it works that way my man.</p>
                                            </div>
                                        </div>
                                    </div> -->
                                                <!-- </div> -->


                                            </div>
                                    </section>
                                </c:forEach>

                            </div>
                            <c:if test="${hasPosts }">
                                <a class="btn btn-primary mt-5 mb-5" href="/posts/hashtag/${requestedHashTag}?before=${oldestDate}">Show older posts</a>
                            </c:if>

                        </div>
                        <div class="col col3">
                            <div class="custom-card-container">
                                <div class="text-head">
                                    Recommended Hashtags
                                </div>
                                <div class="followings">
                                    <div class="hashtag">
                                        <p><a href="">#Entertainment</a></p>
                                        <button class="follow-btn">Follow</button>
                                    </div>
                                    <div class="hashtag">
                                        <p><a href="">#Technology</a></p>
                                        <button class="follow-btn">Follow</button>
                                    </div>
                                    <div class="hashtag">
                                        <p><a href="">#Entertainment</a></p>
                                        <button class="follow-btn">Follow</button>
                                    </div>
                                    <div class="hashtag">
                                        <p><a href="">#Technology</a></p>
                                        <button class="follow-btn">Follow</button>
                                    </div>
                                    <div class="hashtag">
                                        <p><a href="">#Technology</a></p>
                                        <button class="follow-btn">Follow</button>
                                    </div>
                                </div>
                                <div class="recommendations">
                                    <a href="">View all recommendations</a>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>

            </div>
            <script src="resources/js/solo_post.js"></script>
            <script src="resources/js/hashtag.js"></script>
            
               
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>



        </body>

        </html>