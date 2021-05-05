<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
	<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
    <style>
        @import url(https://fonts.googleapis.com/css?family=Roboto:400,300,500);

        *:focus {
            outline: none;
        }

        body {
            margin: 0;
            padding: 0;
            background: #DDD;
            font-size: 16px;
            color: #222;
            font-family: 'Roboto', sans-serif;
            font-weight: 300;
        }

        #login-box {
           
            margin: 5% auto;
            width: 600px;
            background: #FFF;
            border-radius: 2px;
            padding: 40px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.4);
        }

       
        h1 {
            margin: 0 0 20px 0;
            font-weight: 300;
            font-size: 28px;
        }

        input[type="text"],
        input[type="password"] {
            display: block;
            box-sizing: border-box;
            margin-bottom: 20px;
            padding: 4px;
            width: 220px;
            height: 32px;
            border: none;
            border-bottom: 1px solid #AAA;
            font-family: 'Roboto', sans-serif;
            font-weight: 400;
            font-size: 15px;
            transition: 0.2s ease;
        }

        input[type="text"]:focus,
        input[type="password"]:focus {
            border-bottom: 2px solid #16a085;
            color: #16a085;
            transition: 0.2s ease;
        }

        button[type="submit"] {
            margin: 10px;
            width: 210px;
            height: 28px;
            background: #16a085;
            border: none;
            border-radius: 2px;
            color: #FFF;
            font-family: 'Roboto', sans-serif;
            font-weight: 500;
            text-transform: uppercase;
            transition: 0.1s ease;
            cursor: pointer;
        }

        input[type="submit"]:hover,
        input[type="submit"]:focus {
            opacity: 0.8;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.4);
            transition: 0.1s ease;
        }

        input[type="submit"]:active {
            opacity: 1;
            box-shadow: 0 1px 2px rgba(0, 0, 0, 0.4);
            transition: 0.1s ease;
        }

     
        </style>

</head>

<body>

    
<form action="/verification2" method="post">
        <div id="login-box">
            
                <h1>Verification:</h1>
                A verification code was sent to your email please enter it here.${sessionScope.code}
               
                <c:if test="${codeError}"><p class="error">The Verification Code didnt Match</p></c:if>
                
                <input type="text" id="verification_code" name="vcode"  placeholder="Enter Verification Code" />
		

  <input type="text" id="fname" name="fname" class="hidden-field" value="${userdetail.user.firstName}"><br>
  

  <input type="text" id="mname" name="mname" class="hidden-field" value="${userdetail.user.middleName}"><br>
  
  <input type="text" id="lname" name="lname" class="hidden-field" value="${userdetail.user.lastName}"><br><br>
  
  <input type="text" id="email" name="email" class="hidden-field" value="${userdetail.email}"><br><br>
  
  <input type="text" id="pass" name="password"  class="hidden-field" value="${userdetail.pass}"><br><br>
  


              <button type="submit" value="verify">Verify</button>

            


         
        </div>
    </form>
</body>

</html>