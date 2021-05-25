<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<link rel="stylesheet" href="../../../resources/css/header.css">

  <div class="nav clearfix">
    <div class="left-nav">
      <a href="/"><strong>Finder</strong></a>
        <form class="search-box" method="POST" action="#" onsubmit="handleSubmit(event);" >
          <input id="hashtagInput" type="text" placeholder="Search Hashtags"><button class="custom-button">Search</button>
          
        </form>      
    
    </div>
  
    <ul class="right-nav">
        <li style="margin-left: 200px;"><a  class="custom-button" href="/login/post/${post.id}">Log In</a></li>
        <li ><a  class="signup-btn " href="/register/post/${post.id}">Create an Account</a></li>
    </ul>

   
  
</div>

<script src="../../../resources/js/search.js"></script>