<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
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

            <link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
            <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@500&display=swap" rel="stylesheet">

            <title>Post - Comments</title>

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
                            <c:choose>
                                <c:when test="${success}">
                                    <div class="alert alert-success alert-dismissible">
                                        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                        <strong>Success!</strong> Comment added successfully.
                                    </div>
                                </c:when>

                                <c:when test="${failure}">
                                    <div class="alert alert-danger alert-dismissible">
                                        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                        <strong>Failed!</strong> Comment process Failed.
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
                                                    <c:out value="${post.user.firstName} ${post.user.middleName} ${post.user.lastName}  " />
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
                                                        <c:when test="${comment.daysTillNow != 0}">
                                                            <c:out value="${comment.daysTillNow} d ago" />
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
                                                    <a href="/posts/?hashtag=${hashtag.title}">
                                                        <c:out value="#${hashtag.title}" />
                                                    </a>
                                                    &nbsp;
                                                </c:forEach>
                                            </p>
                                        </div>
                                        <div class="line"></div>
                                        <div class="jr-bar">

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
                                            <div class="jr-bar-cmt">
                                                <a class="comment-button" href="/post/${post.id}">Comments (${post.commentsCount})</a>
                                                <a class="share-button" href="/${post.id}/join-requests">Join Requests (${post.joinRequestsCount})</a>

                                            </div>


                                        </div>
                                        <div class="line"></div>
                                        <div class="comment-posting">
                                            <c:if test="${not empty sessionScope.email}">
                                                <c:choose>
                                                    <c:when test="${sessionScope.profile_pic != null}">
                                                        <img class="comment-profile-pic" src="/resources/uploads/${sessionScope.profile_pic}" alt="Profile Picture">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img class="comment-profile-pic" src="../../../resources/images/pic.jpeg" alt="Profile Picture">
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>

                                            <div class="comment-post-details">
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
                                        <hr>

                                        <c:forEach var="comment" items="${post.comments}">
                                            <div id="commentContainer${post.id}">

                                                <div class="comment">
                                                    <c:choose>
                                                        <c:when test="${comment.user.profilePic == null}">
                                                            <img class="small-img" src="../../../resources/images/pic.jpeg" alt="Profile Picture">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img class="small-img" src="../../../resources/uploads/${comment.user.profilePic}" alt="Profile Picture">
                                                        </c:otherwise>
                                                    </c:choose>


                                                    <div class="comment-details">
                                                        <p class="account-name">${comment.user.firstName} ${comment.user.middleName} ${comment.user.lastName}</p>

                                                        <p class="comment-data">${comment.content}</p>
                                                        <p class="comment-data desc post-desc">

                                                            <c:choose>
                                                                <c:when test="${comment.yearsTillNow != 0}">
                                                                    <c:out value="${comment.yearsTillNow} y ago" />
                                                                </c:when>
                                                                <c:when test="${comment.monthsTillNow != 0}">
                                                                    <c:out value="${comment.monthsTillNow} m ago" />
                                                                </c:when>
                                                                <c:when test="${comment.daysTillNow != 0}">
                                                                    <c:out value="${comment.daysTillNow} d ago" />
                                                                </c:when>
                                                                <c:when test="${comment.hoursTillNow != 0}">
                                                                    <c:out value="${comment.hoursTillNow} h ago" />
                                                                </c:when>
                                                                <c:when test="${comment.minutesTillNow != 0}">
                                                                    <c:out value="${comment.minutesTillNow} min ago" />
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:out value="${comment.secondsTillNow} sec ago" />
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </p>
                                                    </div>
                                                    <!--   <p class="reply-button"><span class="reply-button-clickable">Reply</span>
                                            </p> -->
                                                    <!-- <div class="reply">
                                                <p>Ram Sharan</p>
                                                <p>no u are wrong i dont think it works that way my man.</p>
                                            </div> -->
                                                </div>
                                            </div>
                                        </c:forEach>

                                    </div>


                                </div>
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

            </div>
            <script src="<c:url value=" /resources/js/solo_post.js " />"></script>



            <script>
                let ed;
                ClassicEditor
                    .create(document.querySelector('#editor'))
                    .then(editor => ed = editor)
                    .catch(error => {
                        console.error(error);
                    })

                function submitForm(event) {
                    event.preventDefault();
                    let postContent = ed.getData();
                    if (postContent != "") {
                        document.getElementById("post-content").value = postContent;
                        event.target.submit();

                    }


                }
            </script>
            <script src="../../../resources/js/ajax.js"></script>
            <script src="../../../resources/js/follow.js"></script>
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>



        </body>

        </html>