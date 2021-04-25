<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head><title>Register</title></head>
<body>
  <c:if test="${formError}">You Cant Verify Email If You Havent Filled the Form.<br></c:if>
<form action="/verification1" method="post">
 

  
  <c:if test="${fnameError}">First Name cant be empty.<br></c:if>
  <label for="fname">First name:</label><br>
  <input type="text" id="fname" name="fname" value="${fName}"><br>
  

  <label for="mname">Middle name:</label><br>
  <input type="text" id="mname" name="mname" value="${mName}"><br>
  
  <c:if test="${lnameError}">Last Name Cant be empty.<br></c:if>
  <label for="lname">Last name:</label><br>
  <input type="text" id="lname" name="lname" value="${lName}"><br><br>
  
  <c:if test="${emailError}">Input Valid Email<br></c:if>
  <c:if test="${emailExistsError}">Email Already Exists<br></c:if>
  <label for="email">Email:</label><br>
  <input type="text" id="email" name="email" value="${email}"><br><br>
  
  <c:if test="${passwordError}">Password Too Short Must be atleast 8 Characters Long.<br></c:if>
  <label for="pass">Password:</label><br>
  <input type="text" id="pass" name="password" value="${password}"><br><br>
  
  <input type="submit" value="Submit">
</form> 



</body>

</html>