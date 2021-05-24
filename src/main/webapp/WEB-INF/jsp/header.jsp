<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


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
    
  
    
   	
    
    
  </nav>

  <script>
      function handleSubmit(event){
         
        event.preventDefault();
        let hashtagInput = document.querySelector("#hashtagInput");
      
        if(hashtagInput.value == ""){
            hashtagInput.placeholder = "Please type hashtag ";
            return false;
        }
        window.location = "/posts/hashtag/"+hashtagInput.value;
      }
  </script>