<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="../../resources/css/header.css">

        <div class="nav clearfix">
            <div class="left-nav">
                <p><a href="/"><strong>Finder</strong></a></p>
                <p>
                    <input id="hashtagInput" type="text" placeholder="Search Hashtags" onkeydown="searchHashtag(event)">
                </p>
            </div>

            <ul class="right-nav">
                <li><a class="path" href="/"><i class="fa fa-home"></i></a></li>
                <li><a class="path" href="/recommended-hashtags"><i class="fa fa-hashtag"></i></a></li>
                <li><a class="path" href="/explore"><i class="fa fa-globe"></i></a></li>
                <c:choose>

                    <c:when test="${notificationCount == 0}">

                        <li>
                            <a class="" href="/notifications">
                                <i id="noti-bell" class="fa fa-bell" style="position: relative;"></i>
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <a class="" href="/notifications">
                                <i id="noti-bell" class="fa fa-bell" style="position: absolute;"></i>
                                <span id="noti-count">
                                    ${notificationCount}</span>
                            </a>
                        </li>
                    </c:otherwise>
                </c:choose>

                <li>

                    <a href="/create-profile">
                        <c:choose>
                            <c:when test="${sessionScope.profile_pic != null}">

                                <img class="small-img" src="<c:url value=" /resources/uploads/${sessionScope.profile_pic} " />" alt="Profile Picture">
                            </c:when>
                            <c:otherwise>
                                <img class="small-img" src="../../../resources/images/pic.jpeg" alt="Profile Picture">
                            </c:otherwise>
                        </c:choose>
                    </a>
                </li>
                <li><a class="" href="/logout/post/${post.id}"><i class="fa fa-sign-out"></i></a></li>
            </ul>



        </div>
        <script src="/resources/js/notification.js"></script>
        <script>
            var loggedInUserId = '${sessionScope.id}';
            (function fn() {
                var pathname = window.location.href.split("?")[0];
                var paths = document.getElementsByClassName("path");

                for (var i = 0; i < paths.length; i++) {
                    if (pathname == paths[i].href) {
                        paths[i].childNodes[0].style.color = "blue";

                    } else {
                        paths[i].childNodes[0].style.color = "grey";
                    }
                }
            })();
        </script>
        <script src="../../resources/js/search.js"></script>