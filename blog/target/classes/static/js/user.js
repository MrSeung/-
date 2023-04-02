let index = {
	init: function() {
		$("#btn-save").on("click", () => { //function(){}, ()=>{} this를 바인딩하기 위해서!
			this.save();
		});
		$("#btn-update").on("click", () => { //function(){}, ()=>{} this를 바인딩하기 위해서!
			this.update();
		});
	},

	save: function() {
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		//console.log(data);

		//ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청!
		//ajax호출시 defaul가 비동기 호출임.
		$.ajax({
			//회원가입 수행 요청
			type: "POST", //요청 메서드 방식 ex)post,put,delete
			url: "/auth/joinProc",  //요청하는 주소
			data: JSON.stringify(data), //JSON 형태로 변환
			contentType: "application/json; charset=utf-8", // 너에게 보내는 데이터 타입은 이런 타입이야~
			dataType: "json" //응답 온 데이터가 json이라면 javascript Object로 변환해줘
			//서버로부터 응답이 왔을 때 기본적으로 String임.
		}).done(function(resp) { //응답을 성공했을 떄 실행되는 부분
			if (resp.status == 500) {
				alert("이미 존재하는 아이디입니다.");
			} else {

				alert("회원가입이 완료되었습니다.");
				location.href = "/";
			}
		}).fail(function() { //응답을 실패했을 떄 실행되는 부분
			alert(JSON.stringify(error));
		});
	},

	update: function() {
		let data = {
			id: $("#id").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};

		//ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청!
		//ajax호출시 defaul가 비동기 호출임.
		$.ajax({
			//회원가입 수행 요청
			type: "PUT", //요청 메서드 방식 ex)post,put,delete
			url: "/user",  //요청하는 주소
			data: JSON.stringify(data), //JSON 형태로 변환
			contentType: "application/json; charset=utf-8", // 너에게 보내는 데이터 타입은 이런 타입이야~
			dataType: "json" //응답 온 데이터가 json이라면 javascript Object로 변환해줘
			//서버로부터 응답이 왔을 때 기본적으로 String임.
		}).done(function(resp) { //응답을 성공했을 떄 실행되는 부분
			alert("회원정보가 수정되었습니다.");
			location.href = "/";
		}).fail(function() { //응답을 실패했을 떄 실행되는 부분
			alert(JSON.stringify(error));
		});
	}
};
index.init();