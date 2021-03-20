<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.ckeditor.com/ckeditor5/23.1.0/classic/ckeditor.js"></script>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    
    
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
    
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
  				<c:when test="${success}">
   					<div class="alert alert-success alert-dismissible">
 				 		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
 				 		<strong>Success!</strong> Post created successfully.
					</div>
  				</c:when>
  				
  				<c:when test="${failure}">
  					<div class="alert alert-danger alert-dismissible">
 				 		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
 				 		<strong>Failed!</strong> Post created successfully.
					</div>
    			</c:when>
				
				</c:choose>
                
                
                    <div id="create-post-area">
                		<form action="/create-post" method="POST" onsubmit="submitForm(event);">
                		<input id="editor" />
                		<input hidden name="post-content" type="text" id="post-content" > 
                		 <input type="submit" class="btn btn-primary mt-3">
                		</form>    
                                               
                        <div class="line"></div>

                    <c:choose>
                        <c:when test="${hasPosts}">
                           
                        </c:when>
                        <c:otherwise>
                            <c:out value="NO POSTS AVAILABLE"/>
                        </c:otherwise>

                    </c:choose>
					<c:forEach var="post" items="${posts}">


                        <section class="posts-section">
                            <div class="post-card-container">
                                <div class="post-head">
                                    <div class="post-head-left">
                                        <img class="profile-pic" src="/resources/images/pic.jpeg" alt="Card image cap">
                                        <div class="post-meta">
                                            <p class="user-name post-user-name"><c:out value="${post.user.firstName} ${post.user.middleName} ${post.user.lastName}  "/></p>
                                            <p class="desc post-desc"><c:out value="${post.user.bio}"/></p>
                                            <p class="desc post-desc">
                                              <c:choose>
  										 		<c:when test="${post.yearsTillNow != 0}">
   													 <c:out value="${post.yearsTillNow} years ago"/>
  												</c:when>
  												<c:when test="${post.monthsTillNow != 0}">
   													 <c:out value="${post.monthsTillNow} months ago"/>
  												</c:when>
  												<c:when test="${post.hoursTillNow != 0}">
   													 <c:out value="${post.hoursTillNow} hours ago"/>
  												</c:when>
  												<c:when test="${post.minutesTillNow != 0}">
   													 <c:out value="${post.minutesTillNow} min ago"/>
  												</c:when>
  												<c:otherwise>
   													 <c:out value="${post.secondsTillNow} sec ago"/>
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
                                    <hr>
                                    <div>
                                        <div class="interested-button">Join Request</div>
                                        <div class="comment-button">Comment</div>
                                        <div class="share-button">Share</div>
                                    </div>
                                    <hr>
                                    <div class="comment-posting">
                                        <img class="comment-profile-pic" src="resources/images/pic.jpeg"
                                            alt="Card image cap">
                                        <div class="comment-post-details">
                                            <span class="comment-box2" role="textbox" id="comment" contentEditable=true
                                                data-ph="Write A Comment..."
                                                onkeydown="commentPost(event,'commentContainer${post.id}')"></span>
                                        </div>

                                    </div>
                                    <hr>
                                    <div id="commentContainer${post.id}">
                                     
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
    <script src="resources/js/script.js"></script>

    <script>
        let ed;
        ClassicEditor
            .create(document.querySelector('#editor'))
            .then(editor => ed = editor)
            .catch(error => {
                console.error(error);
            })
	
	 function submitForm(event){
	 	event.preventDefault();
	 	let postContent = ed.getData();
	 	if(postContent != ""){
	 		document.getElementById("post-content").value = postContent;
	 		event.target.submit();
	 	
	 	}
	 	
	 
	 }
	




    </script>
  
       <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>



</body>

</html>