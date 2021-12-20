// (1) ログイン
function login(event) {
	event.preventDefault();

	if ($("#mem_num").val() == "") {
		alert("IDを入力してください。");
		$("#mem_num").focus();
		return false;
	}
	
	if ($("#mem_password").val() == "") {
		alert("パスワードを入力してください。");
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