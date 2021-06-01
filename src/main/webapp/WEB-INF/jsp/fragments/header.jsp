<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


        <!-- 
<nav class="navbar navbar-light bg-light justify-content-between">
    <a  href="/" class="navbar-brand"> <strong>Finder</strong> </a>
    <form class="form-inline" method="POST" action="#" onsubmit="handleSubmit(event);" >
      <input class="form-control mr-sm-2" type="search" placeholder="Search Hashtags" aria-label="Search" id="hashtagInput">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    </form>  
   
    <c:choose>
    	<c:when test="${not empty sessionScope.email}">
         <form action="/logout/post/${post.id}" method="post">
    			<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Logout</button>
		</form>
   		
    	</c:when>    
    	<c:otherwise>
          <form action="/login/post/${post.id}" method="get">
    			<button class="btn btn-outline-success my-2 my-sm-0" type="submit">LogIn</button>
    	  </form>
    	  <form action="/register/post/${post.id}" method="get">
    	   	  <button class="btn btn-outline-success my-2 my-sm-0" type="submit">SignUp</button>
    	</form>
    	</c:otherwise>
	</c:choose>
    
  
    
   	
    
    
  </nav> -->
        <link rel="stylesheet" href="../../resources/css/header.css">

        <div class="nav clearfix">
            <div class="left-nav">
                <p><a href="/"><strong>Finder</strong></a></p>
                <p>
                    <input id="hashtagInput" type="text" placeholder="Search Hashtags">
                </p>
            </div>

            <ul class="right-nav">
                <li><a href="/"><i class="fa fa-home"></i></a></li>
                <li><a href="/recommended-hashtags"><i class="fa fa-hashtag"></i></a></li>
                <li><a href="/"><i class="fa fa-bell"></i></a></li>
                <li>
                    <a href="/create-profile"><img class="small-img" src="../../../resources/uploads/${sessionScope.profile_pic}" alt=""></a>
                </li>
                <li><a class="" href="/logout/post/${post.id}"><i class="fa fa-sign-out"></i></a></li>
            </ul>



        </div>

        <script src="../../resources/js/search.js"></script>