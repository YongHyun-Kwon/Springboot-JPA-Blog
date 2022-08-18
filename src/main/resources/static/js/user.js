let index = {

	init: function() {
		$('#btn-save').on("click", () => { // function(){}, ()=>{} this를 바인딩하기 위해서
			this.save();
		});
	},

	save: function() {

		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		}

		// console.log(data);

		// ajax호출시 default가 비동기 호출
		// ajax를 이용해 Parameter를 json으로 변경하여 insert 요청
		// ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자동 오브젝트로 변환해준다.
		$.ajax({
			type: "POST",
			url: "/blog/api/user",
			data: JSON.stringify(data), // http body데이터
			contentType: "application/json; charset=utf-8", // body데이터가 어떤 타입인지(MIME)
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면) -> javascript 오브젝트로 변경
		}).done(function(resp) {
			//console.log(resp)
			alert("회원가입이 완료되었습니다.");
			location.href = "/blog";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});

	}
}

index.init()