package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청했을 때 html파일로 응답을 해주는 컨트롤러
//@Controller

// 사용자가 요청했을 때 data로 응답을 해주는 컨트롤러.
@RestController
public class HttpControllerTest {
	
	//인터넷 브라우저 요청은 get만 가능하다.
	//다른 요청은 405에러코드 발생
	//다른 요청들은 postman에서 확인 가능하다.
	
	//Builder 사용법
	Member m= Member.builder().username("홍길동").build();
	
	//http://localhost:8080/http/get
	@GetMapping("/http/get")
	public String getTest(Member m) {//?id=1&username=ssar&password=1231&email=odiekf@naver.com
									 //MessageConverter(스트링부트)가 각각의 값들을 알맞게 맵핑해줌.
		return "get요청 : "+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	
	//http://localhost:8080/http/post
	@PostMapping("/http/post")
	
	//x-www-form-urlencoded 사용법
	/*public String postTest(Member m) {
		return "post요청 : " +m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}*/
	
	//raw 사용법 (text/plain)
	/*public String postTest(@RequestBody String text) {
		return "post요청 : " +text;
	}*/
	
	//raw 사용법 (application/json)
	public String postTest(@RequestBody Member m) {
		return "post요청 : " +m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	
	//http://localhost:8080/http/put
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put요청 :"+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	
	//http://localhost:8080/http/delete
	@DeleteMapping("/http/delete")
	public String deleteTest(@RequestBody Member m) {
		return "delete요청 :"+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
}
