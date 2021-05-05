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
<c:if test="${formError}"><p>You Cant Verify Email If You Havent Filled the Form.</p></c:if>
    <form action="/verification1" method="post">
        <div id="login-box">
            
                <h1>Register</h1>
                <c:if test="${fnameError}"><p class="error">First Name cant be empty.</p></c:if>
                <input type="text" id="fname" name="fname" value="${fName}" placeholder="Enter First Name" />
	
				<input type="text" id="mname" name="mname" value="${mName}" placeholder="Enter Middle name" />
				
				<c:if test="${lnameError}"><p class="error">Last Name Cant be empty.</p></c:if>
				<input type="text" id="lname" name="lname" value="${lName}" placeholder="Enter Last Name" />
                
                <c:if test="${emailError}"><p class="error">Input Valid Email</p></c:if>
                <c:if test="${emailExistsError}"><p class="error">Email Already Exists</p></c:if>
                <input type="text" id="email" name="email" value="${email}" placeholder="E-mail" />
                
                <c:if test="${passwordError}"><p class="error">Password Too Short Must be atleast 8 Characters Long.</p></c:if>
                <c:if test="${passwordMismatchError}"><p class="error">The Passwords Dont Match.</p></c:if>
                <input type="password" id="pass" name="password" value="${password}" placeholder="Enter a Password." />
                
                
                <input type="password" id="pass2" name="password2" value="${password2}" placeholder="Enter the Password Again." />
                


                <button type="submit" value="Register">Create your Account</button>

            


         
        </div>
    </form>
</body>

</html>