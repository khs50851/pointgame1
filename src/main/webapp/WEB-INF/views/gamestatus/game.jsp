<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
				<button class="btn btn-info" onclick="logout()">ログアウト</button>
			</div>
		</nav>

		<section class="gamebackground">
			
			<div class="gamerule">
				<!-- 모달을 열기 위한 버튼 -->
				<button type="button" class="btn btn-primary btn-lg" id="openModalBtn">
					ゲームのルール
				</button>
				<!-- 모달 영역 -->
				<div id="modalBox" class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
								<h4 class="modal-title" id="myModalLabel">ゲームのルール</h4>
							</div>
							<div class="modal-body">
								1.重複数字は入力できません。<br>
								2.ログインしたネットショップ会員はこのゲームを1日に1回のみ参加できる。<br>
								3.会員はランダムで決められた0～9の隠れ数字3つを推理する。<br>
								4.ゲームのルールは以下を参照する。<br>
								<br>
								隠れ数字：216<br>
								<br>
								1回目&emsp;830&emsp;はずれ&emsp;当たり数字がないのではずれ<br>
								2回目&emsp;659&emsp;1B&emsp;6の数字は当たったが、入力位置が違うので１B<br>
								3回目&emsp;264&emsp;1S1B&emsp;2の数字と位置が一致したので1S、6の数字は当たった、入力位置が違うので１B<br>
								4回目&emsp;126&emsp;1S2B&emsp;6の数字と位置が一致したので1S、1と2の数字は当たったが、入力位置が違うので２B<br>
								5回目&emsp;216&emsp;当たる&emsp;←3つ全部当たったので会員の勝ち<br>
								<br>
								5.会員の入力チャンスは10回までとする。10回の間で当たった場合は以下のクーポンを会員に発給する。<br>
								入力回数が1回目～5回目の間で当たった場合、1,000円のクーポンを発給<br>
								入力回数が6回目～7回目の間で当たった場合、500円のクーポンを発給<br>
								入力回数が8回目～10回目の間で当たった場合、200円のクーポンを発給<br>
								<br>
								6.会員が勝ったらイベント当たりのポップアップが出て、会員にクーポンが発給される。<br>
								7.10回挑戦しても当たらない場合は会員の負けではずれのポップアップメッセージを出力し、ゲームは終了される。<br>
								
							</div>
							<div class="modal-footer modal_close">
								<button type="button" class="btn btn-primary" id="closeModalBtn">確認</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<c:choose>
				<c:when test = "${point == -1}">
					<div class="mypoint">現在のポイント&emsp; 0</div>
				</c:when>
				<c:otherwise>
					<div class="mypoint">現在のポイント&emsp; ${point }</div>
				</c:otherwise>
			</c:choose>
			
			<div class="hidednumber">隠れ数字&emsp;*&emsp;*&emsp;*</div>
			<div class="inputnumber">
				<form class="form-inline" id="numberInput_form" onsubmit="input_number(event)">
					<input type="hidden" name="member_number"
						value="${member.member_number }" />
					<div class="form-group inputtext1">
						<label for="exampleInputName2">入力</label> 
						<input type="text" maxlength="3" class="form-control input_box" name="enter_num" id="input_num" placeholder="input your numbers" onlyNumber>
					</div>
					<button class="btn btn-default">確認</button>
				</form>

			</div>

			<div class="table-responsive game_table">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>入力回数</th>
							<th>入力情報</th>
							<th>判定結果</th>
						</tr>
					</thead>
					<tbody id="gametbody">
					<c:forEach var="gameSt" items="${list }">
						<tr>
							<td>${gameSt.input_count} 回目</td>
							<c:choose>
								<c:when test = "${gameSt.point == 2}">
									<td>0${gameSt.input_number}</td>
								</c:when>
								<c:otherwise>
									<td>${gameSt.input_number}</td>
								</c:otherwise>
							</c:choose>
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