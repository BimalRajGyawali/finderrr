<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="custom-card-container">
    <div class="text-head">
        Recommended Hashtags
    </div>
    <div class="followings">
        <c:choose>
            <c:when test="${hasRecommendations}">
                <c:forEach var="recommendedHashTag" items="${recommendedHashTags}">
                    <div class="hashtag">
                        <p><a
                                href="/posts/hashtag/${recommendedHashTag.title}">#${recommendedHashTag.title}</a>
                        </p>
                        <button class="follow-btn" id="${recommendedHashTag.title}"
                            onclick="follow(event)">Follow</button>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>Recommendations will appear here.</p>
            </c:otherwise>
        </c:choose>
        <div class="recommendations">
            <a href="/recommended-hashtags">Manage hashtags</a>
        </div>

    </div>

</div>