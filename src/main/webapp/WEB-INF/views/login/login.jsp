<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PointGame</title>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
        integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous" />
    <!-- 제이쿼리 -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>

<body>
    <div class="container">
        <main class="loginMain">
        <!--로그인섹션-->
            <section class="login">
               <!--로그인박스-->
                <article class="login__form__container">
                   <!--로그인 폼-->
                   <div class="login__form">
                        <h1><img src="/images/icon3.jpg" alt=""></h1>
                        <!--로그인 인풋-->
                        <form class="login__input" id="login_input" onsubmit="login(event)">
                            <input type="text" id="mem_num" name="member_number" placeholder="username"/>
                            <input type="password" id="mem_password" name="member_password" placeholder="password"/>
                            <button>로그인</button>
                        </form>
                        <!--로그인 인풋end-->
                        <div class="emsg" id="loginerrormsg"></div>
                    </div>
                </article>
            </section>
        </main>
    </div>
    <script src="/js/login.js"></script>
</body>

</html>