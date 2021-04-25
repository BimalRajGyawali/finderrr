<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head><title>Login</title></head>
<body>

<form action="/loginsubmit" method="post">
 

  

  <c:if test="${error}">Credentials Incorrect<br></c:if>
  <label for="email">Email:</label><br>
  <input type="text" id="email" name="email"><br><br>
  
 
  <label for="pass">Password:</label><br>
  <input type="text" id="pass" name="password"><br><br>
  
  <input type="submit" value="Submit">
</form> 



</body>

</html>