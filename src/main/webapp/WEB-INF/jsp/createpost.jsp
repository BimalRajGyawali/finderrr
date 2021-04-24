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
                            <div id="empty-post-error">

                            </div>
                            <div id="empty-hashtag-error">

                            </div>

                            <div id="create-post-area">
                                <form id="post-form" action="/create-post" method="POST" onsubmit="submitForm(event);">
                                    <input id="editor" />
                                    <input hidden name="post-content" type="text" id="post-content">
                                    <p class="mt-5">
                                        <span>Select Post Status : &nbsp;&nbsp;</span> 
                                        <select name="post-status" id="post-status" class="hashtag-input">
                                            <option value="ongoing" selected>Ongoing</option>
                                            <option value="completed">Completed</option>
                                        </select>
                                    </p>
                                    <p class="mt-5">
                                      <span>  Select Hashtags&nbsp;&nbsp; : &nbsp;&nbsp;</span>
                                        <input type="text" name="hashtags" autocomplete="off" class="hashtag-input"
                                            placeholder="Type hashtag and press Enter" id="hashtags">
                                    </p>
                                   
                                    <div id="hashtags-container">
                                    </div>
                                    <input type="submit" class="btn btn-primary mt-5" value="Post">

                                </form>




                            </div>


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
            <script src="resources/js/hashtag.js"></script>
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
                    let hashtags = "";
                    if (postContent != "") {
                        document.getElementById("post-content").value = postContent;
                        let container = document.querySelector("#hashtags-container");
                        for (let i = 1; i < container.childNodes.length; i++) {
                            hashtags += container.childNodes[i].innerText;
                            if (i != container.childNodes.length - 1) {
                                hashtags += ",";
                            }
                        }
                        if (hashtags != "") {
                            let hashtagInput = document.querySelector("#hashtags");
                            hashtagInput.value = hashtags;
                            event.target.submit();
                        } else {
                            let hashtagError = document.querySelector("#empty-post-error");
                            hashtagError.style.display = 'block';
                            hashtagError.innerHTML = `  <div   class="alert alert-danger alert-dismissible">
                                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                <strong>Failed!</strong> Please provide at least one hashtag.
                            </div>`;


                        }
                    } else {


                        let postError = document.querySelector("#empty-hashtag-error");
                        postError.style.display = 'block';
                        postError.innerHTML = `
                           <div class="alert alert-danger alert-dismissible">
                                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                <strong>Failed!</strong> Post is Empty.
                            </div>`;


                    }
                }





            </script>

            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>



        </body>

        </html>