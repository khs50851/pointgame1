// (1) 로그아웃
function logout() {
	if (confirm('ログアウトしますか?')) {
		location.href = "/logout";
	} else {
	}
}
// 문자입력인지 숫자입력인지 유효성 체크
$('input[onlyNumber]').on('keyup', function () {
    	$(this).val($(this).val().replace(/[^0-9]/g, ""));
	});
// (2) 숫자입력
function input_number(event) {
	event.preventDefault();
	
	if ($("#input_num").val() == "") {
		alert("3桁数字を入力してください。");
		$("#input_num").focus();
		return false;
	}
	

	if($("#input_num").val().length != 3 ) {
    	alert("3桁数字を入力してください。");
    	return false;
	}
	

	let data = $("#numberInput_form").serialize();
	console.log("データ : ",data);
	
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
				<td>${res.data.input_count} 回目</td>
				`
				if(JSON.stringify(res.data.input_number).length==2){
					item += `<td>0${res.data.input_number}</td>`
				}else{
					item += `<td>${res.data.input_number}</td>`
				}
				
				if(res.data.check_result == 'X'){
					item+=`<td>はずれ</td>`;
				}else if(res.data.check_result == 'SSS'){
					item+=`<td>当たる</td>`;
				}
				else{
					item+=`<td>${res.data.check_result}</td>`;
				}
		`
			</tr>
		`;
		$("#gametbody").append(item);
		$("#input_num").val('');
		let check_result = res.data.check_result;
		if(check_result == "SSS"){
			alert(res.message);
			location.href="/game";
		}
		
	}).fail(error => {
		console.log(error);
		
		alert(error.responseJSON.message);
	});

}