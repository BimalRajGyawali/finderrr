<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
      <a href="/"><strong>Finder</strong></a>
        <form class="search-box" method="POST" action="#" onsubmit="handleSubmit(event);" >
          <input id="hashtagInput" type="text" placeholder="Search Hashtags"><button class="custom-button">Search</button>
          
        </form>      
    
    </div>
  
    <ul class="right-nav">
        <li><a href="/">Home</a></li>
        <li><a href="/">Followings</a></li>
        <li><a href="/">Notifications</a></li>
        <li><a href="/">Profile</a></li>
        <li ><a  class="custom-button" href="/logout/post/${post.id}">Logout</a></li>
    </ul>

   
  
</div>

<script src="../../resources/js/search.js"></script>