let index = {
	init: function() {
		$("#btn-save").on("click", () => { //function(){}, ()=>{} this를 바인딩하기 위해서!
			this.save();
		});
		$("#btn-delete").on("click", () => { //function(){}, ()=>{} this를 바인딩하기 위해서!
			this.deleteById();
		});
		$("#btn-update").on("click", () => { //function(){}, ()=>{} this를 바인딩하기 위해서!
			this.update();
		});
		$("#btn-reply-save").on("click", () => { //function(){}, ()=>{} this를 바인딩하기 위해서!
			this.replySave();
		});
	},

	save: function() {
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};
		//console.log(data);

		//ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청!
		//ajax호출시 defaul가 비동기 호출임.
		$.ajax({
			//글쓰기 요청
			type: "POST", //요청 메서드 방식 ex)post,put,delete
			url: "/api/board",  //요청하는 주소
			data: JSON.stringify(data), //JSON 형태로 변환
			contentType: "application/json; charset=utf-8", // 너에게 보내는 데이터 타입은 이런 타입이야~
			dataType: "json" //응답 온 데이터가 json이라면 javascript Object로 변환해줘
			//서버로부터 응답이 왔을 때 기본적으로 String임.
		}).done(function(resp) { //응답을 성공했을 떄 실행되는 부분
			alert("작성이 완료됐습니다.");
			location.href = "/";
		}).fail(function() { //응답을 실패했을 떄 실행되는 부분
			alert(JSON.stringify(error));
		});
	},

	deleteById: function() {
		let id = $("#id").text();
		$.ajax({
			//글 삭제
			type: "DELETE", //요청 메서드 방식 ex)post,put,delete
			url: "/api/board/" + id,  //요청하는 주소
			dataType: "json" //응답 온 데이터가 json이라면 javascript Object로 변환해줘
			//서버로부터 응답이 왔을 때 기본적으로 String임.
		}).done(function(resp) { //응답을 성공했을 떄 실행되는 부분
			alert("글이 삭제됐습니다.");
			location.href = "/";
		}).fail(function() { //응답을 실패했을 떄 실행되는 부분
			alert(JSON.stringify(error));
		});
	},

	update: function() {
		let id = $("#id").val();

		let data = {
			title: $("#title").val(),
			content: $("#content").val(),
		};
		//console.log(data);

		//ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청!
		//ajax호출시 defaul가 비동기 호출임.
		$.ajax({
			//글쓰기 요청
			type: "PUT", //요청 메서드 방식 ex)post,put,delete
			url: "/api/board/" + id,  //요청하는 주소
			data: JSON.stringify(data), //JSON 형태로 변환
			contentType: "application/json; charset=utf-8", // 너에게 보내는 데이터 타입은 이런 타입이야~
			dataType: "json" //응답 온 데이터가 json이라면 javascript Object로 변환해줘
			//서버로부터 응답이 왔을 때 기본적으로 String임.
		}).done(function(resp) { //응답을 성공했을 떄 실행되는 부분
			alert("수정이 완료됐습니다.");
			location.href = "/";
		}).fail(function() { //응답을 실패했을 떄 실행되는 부분
			alert(JSON.stringify(error));
		});
	},
	
	replySave: function() {
		let data = {
			content: $("#reply--content").val()
		};
		
		let boardId=$("#boardId").val();

		//ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청!
		//ajax호출시 defaul가 비동기 호출임.
		$.ajax({
			//글쓰기 요청
			type: "POST", //요청 메서드 방식 ex)post,put,delete
			url:`/api/board/${boardId}/reply`,  //요청하는 주소
			data: JSON.stringify(data), //JSON 형태로 변환
			contentType: "application/json; charset=utf-8", // 너에게 보내는 데이터 타입은 이런 타입이야~
			dataType: "json" //응답 온 데이터가 json이라면 javascript Object로 변환해줘
			//서버로부터 응답이 왔을 때 기본적으로 String임.
		}).done(function(resp) { //응답을 성공했을 떄 실행되는 부분
			alert("댓글 작성이 완료됐습니다.");
			location.href = `/board/${boardId}`;
		}).fail(function() { //응답을 실패했을 떄 실행되는 부분
			alert(JSON.stringify(error));
		});
	},
	replyDelete: function(boardId, replyId) {
		$.ajax({
			//글쓰기 요청
			type: "DELETE", //요청 메서드 방식 ex)post,put,delete
			url:`/api/board/${boardId}/reply/${replyId}`,  //요청하는 주소
			contentType: "application/json; charset=utf-8", // 너에게 보내는 데이터 타입은 이런 타입이야~
			dataType: "json" //응답 온 데이터가 json이라면 javascript Object로 변환해줘
			//서버로부터 응답이 왔을 때 기본적으로 String임.
		}).done(function(resp) { //응답을 성공했을 떄 실행되는 부분
			alert("댓글이 삭제되었습니다.");
			location.href = `/board/${boardId}`;
		}).fail(function() { //응답을 실패했을 떄 실행되는 부분
			alert(JSON.stringify(error));
		});
	}


};
index.init();