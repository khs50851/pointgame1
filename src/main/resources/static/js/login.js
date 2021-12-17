// (1) 로그인
function login(event) {
	event.preventDefault();

	if ($("#mem_num").val() == "") {
		alert("아이디 입력바람");
		$("#mem_num").focus();
		return false;
	}
	
	if ($("#mem_password").val() == "") {
		alert("패스워드 입력바람");
		$("#mem_password").focus();
		return false;
	}

	let data = $("#login_input").serialize();

	$.ajax({
		type: "POST",
		url: "/login",
		data: data,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		dataType: "json"
	}).done(res => {
		location.href = "/game";
	}).fail(error => {
		$("#mem_num").val('');
		$("#mem_password").val('');
		$("#loginerrormsg").css("color", "red");
		$("#loginerrormsg").text(error.responseJSON.message);
	});

}