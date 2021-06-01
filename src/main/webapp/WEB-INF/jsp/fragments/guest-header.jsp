<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="../../../resources/css/header.css">

        <div class="nav clearfix">
            <div class="left-nav">
                <p><a href="/"><strong>Finder</strong></a></p>
                <p><input id="hashtagInput" type="text" placeholder="Search Hashtags" onkeydown="searchHashtag(event)"></p>
            </div>

            <ul class="right-nav">
                <li><a class="custom-button" href="/login/post/${post.id}">Log In</a></li>
                <li><a class="signup-btn " href="/register/post/${post.id}">Create an Account</a></li>
            </ul>



        </div>

        <script src="../../../resources/js/search.js"></script>