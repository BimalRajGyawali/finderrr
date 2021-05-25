<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Signup</title>
            <link href="<c:url value=" /resources/css/style.css" />" rel="stylesheet">
            <link href="<c:url value=" /resources/css/hashtag.css" />" rel="stylesheet">

            <link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
            <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@500&display=swap" rel="stylesheet">

            <style>
                @import url(https://fonts.googleapis.com/css?family=Roboto:400,300,500);

                */ *:focus {
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
                    position: relative;
                    margin: 5% auto;
                    width: 600px;
                    height: 400px;
                    background: #FFF;
                    border-radius: 2px;
                    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.4);
                }

                .left {
                    position: absolute;
                    top: 0;
                    left: 0;
                    box-sizing: border-box;
                    padding: 40px;
                    width: 300px;
                    height: 400px;
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

            <div id="login-box">
                <div class="left">
                    <form action="/loginsubmit/post/${post_id}" method="post">
                        <h1>Login</h1>
                        <c:if test="${error}">
                            <p class="error">Credentials Incorrect.</p>
                        </c:if>
                        <input type="text" id="email" name="email" placeholder="E-mail" />
                        <input type="password" id="pass" name="password" placeholder="Password" />


                        <button type="submit" value="Login">Login</button>
                    </form>
                    <p style="margin-left:45%;"> OR </p>
                    <form action="/register/post/${post_id}" method="get">
                        <button type="submit" value="signup">Create new account.</button>
                    </form>
                </div>





            </div>

        </body>

        </html>