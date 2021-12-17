// (1) 로그아웃
function logout() {
	if (confirm('로그아웃 하시겠습니까?')) {
		location.href = "/logout";
	} else {
	}
}

// (2) 숫자입력
function input_number(event) {
	event.preventDefault();
	
	
	if ($("#input_num").val() == "") {
		alert("3자리 숫자를 입력하세요");
		$("#input_num").focus();
		return false;
	}
	
	if($("#input_num").val().length != 3 ) {
    	alert("3자리 숫자를 입력해주세요");
    	return false;
	}
	

	let data = $("#numberInput_form").serialize();
	console.log("데이터 : ",data);
	
	$.ajax({
		type: "POST",
		url: "/game",
		data: data,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		dataType: "json"
	}).done(res => {
		console.log(res);
		let item = `
			<tr>
				<td>${res.data.input_count} 회차</td>
				<td>${res.data.input_number}</td>
				<td>${res.data.check_result}</td>
			</tr>
		`;
		$("#gametbody").append(item);
		$("#input_num").val('');
		let check_result = res.data.check_result;
		if(check_result == "SSS"){
			alert(JSON.stringify(res.message));
			location.href="/game";
		}
		
	}).fail(error => {
		console.log(error);
		alert(error.responseJSON.message);
		
	});

}