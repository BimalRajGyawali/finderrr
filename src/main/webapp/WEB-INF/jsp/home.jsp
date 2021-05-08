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
                            <c:choose>
                                <c:when test="${success}">
                                    <div class="alert alert-success alert-dismissible">
                                        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                        <strong>Success!</strong> Post created successfully.
                                    </div>
                                </c:when>

                                <c:when test="${failure}">
                                    <div class="alert alert-danger alert-dismissible">
                                        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                        <strong>Failed!</strong> Error in creating post.
                                    </div>
                                </c:when>

                            </c:choose>

                            <c:choose>
                                <c:when test="${updateSuccess}">
                                    <div class="alert alert-success alert-dismissible">
                                        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                        <strong>Success!</strong> Post updated successfully.
                                    </div>
                                </c:when>

                                <c:when test="${updateFailure}">
                                    <div class="alert alert-danger alert-dismissible">
                                        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                        <strong>Failed!</strong> Error in updating post.
                                    </div>
                                </c:when>

                            </c:choose>
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


                            <div id="create-post-area">
                                <!-- <form id="post-form" action="/create-post" method="POST" onsubmit="submitForm(event);">
                                    <input id="editor" />
                                    <input hidden name="post-content" type="text" id="post-content">
                                    <p class="mt-5">
                                        <span>Hashtags</span> <input type="text" name="hashtags" autocomplete="off"
                                            id="hashtags">
                                    </p>
                                    <div id="hashtags-container">

                                    </div>
                                    <input type="submit" class="btn btn-primary mt-3">

                                </form> -->

                                <div class="post-card-container">
                                    <div class="post-head">
                                        <div class="post-head-left">
                                            <img class="profile-pic" src="/resources/images/pic.jpeg"
                                                alt="Card image cap">
                                            <div class="post-meta ">
                                                <a href="/create-post" class="post-input">Want to find someone ? Create
                                                    a Post </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="line"></div>

                                <c:choose>
                                    <c:when test="${hasPosts}">

                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="NO POSTS AVAILABLE" />
                                    </c:otherwise>

                                </c:choose>
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
                                                            <span class="post-status ${post.status}">.
                                                                ${post.status}</span>
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
                                                            <a href="/posts/hashtag/${hashtag.title}">

                                                                <c:out value="#${hashtag.title}" />
                                                            </a>
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




                                            </div>
                                    </section>
                                </c:forEach>

                            </div>
                            <c:if test="${hasPosts }">
                                <a class="btn btn-primary mt-5 mb-5" href="/?before=${oldestDate}">Show older posts</a>
                            </c:if>

                        </div>
                        <div class="col col3">
                            <div class="custom-card-container">
                                <div class="text-head">
                                    Recommended Hashtags
                                </div>
                                <div class="followings">
                                    <c:choose>
                                        <c:when test="${hasRecommendations}">
                                            <c:forEach var="recommendedHashTag" items="${recommendedHashTags}">
                                            <div class="hashtag">
                                                <p><a href="/posts/hashtag/${recommendedHashTag.title}">#${recommendedHashTag.title}</a></p>
                                                <button class="follow-btn" id="${recommendedHashTag.title}" onclick="follow(event)">Follow</button>
                                            </div>
                                        </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <p>Recommendations will appear here.</p>
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="recommendations">
                                        <a href="/recommended-hashtags">Manage hashtags</a>
                                    </div>
                                
                                </div>
                                
                            </div>
                        </div>
                    </div>

                </div>
            </div>

            </div>
            <script src="resources/js/solo_post.js"></script>
            <script src="resources/js/ajax.js"></script>

            <script>
                function follow(event) {
                    event.preventDefault();
                    let hashtag = event.target.id;
                    let followError = document.getElementById("follow-error");
                    if (!hashtag) {
                        displayError(followError);

                    } else {
                        if (event.target.innerText == "Follow") {
                            postAjax('/follow', { "hashtag": hashtag })
                                .then(data => {
                                    if (data === true) {
                                        event.target.innerText = "Unfollow";
                                        event.target.style.border = "1px solid green";
                                    } else {
                                        displayError(followError);
                                    }
                                })
                                .catch(err => displayError(followError));


                        }else{
                            postAjax('/unfollow', { "hashtag": hashtag })
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