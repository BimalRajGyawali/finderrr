<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head><title>Register</title></head>
<body>
A verification code was sent to your email please enter it here.${sessionScope.code}
<form action="/verification2" method="post">
  <label for="code">Verification Code:</label><br>
  <c:if test="${codeError}">The Verification Code didnt Match<br></c:if>
  <input type="text" id="verification_code" name="vcode"><br>
  
  <input type="text" id="fname" name="fname" hidden value="${userdetail.user.firstName}"><br>
  

  <input type="text" id="mname" name="mname" hidden value="${userdetail.user.middleName}"><br>
  
  <input type="text" id="lname" name="lname" hidden value="${userdetail.user.lastName}"><br><br>
  
  <input type="text" id="email" name="email" hidden value="${userdetail.email}"><br><br>
  
  <input type="text" id="pass" name="password" hidden value="${userdetail.pass}"><br><br>
  
  
  
  
  
  
  <input type="submit" value="Submit">
</form> 



</body>

</html>