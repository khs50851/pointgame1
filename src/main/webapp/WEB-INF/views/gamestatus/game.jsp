<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>PointGame</title>
<link rel="stylesheet" href="/css/style.css">
<link rel="stylesheet"
	href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
	integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p"
	crossorigin="anonymous" />


<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

</head>

<body>
	<div class="container">

		<nav class="game_nav">
			<div class="logout">
				<button class="btn btn-info" onclick="logout()">로그아웃</button>
			</div>
		</nav>

		<section class="gamebackground">
				<div class="mypoint">나의 점수&emsp; ${point }</div>
			<div class="hidednumber">숨겨진 숫자&emsp;*&emsp;*&emsp;*</div>
			<div class="inputnumber">
				<form class="form-inline" id="numberInput_form" onsubmit="input_number(event)">
					<input type="hidden" name="member_number"
						value="${member.member_number }" />
					<div class="form-group inputtext1">
						<label for="exampleInputName2">입력</label> 
						<input type="text" maxlength="3" class="form-control input_box" name="enter_num" id="input_num" placeholder="input your numbers" onlyNumber>
					</div>
					<button class="btn btn-default">確認</button>
				</form>

			</div>

			<div class="table-responsive game_table">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>입력회차</th>
							<th>나의숫자</th>
							<th>판정결과</th>
						</tr>
					</thead>
					<tbody id="gametbody">
					<c:forEach var="gameSt" items="${list }">
						<tr>
							<td>${gameSt.input_count} 회차</td>
							<td>${gameSt.input_number}</td>
							<td>${gameSt.check_result}</td>
						</tr>
					
					</c:forEach>
						
						
					</tbody>

				</table>
			</div>
		</section>
	</div>
	<script src="/js/game.js"></script>
</body>

</html>