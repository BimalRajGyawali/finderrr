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
                            <p>No hashtags provided</p>

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
            <script src="../../resources/js/solo_post.js"></script>
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