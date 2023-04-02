package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  // Controller라는 어노테이션이 붙으면 메서드들은 파일을 리턴한다.
public class TempControllerTest {
	
	//http://localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		
		System.out.println("tempHome()");
		//파일리턴 기본경로 : src/main/resources/static
		//리턴명 : /home.html
		//return "home.html";  // 이렇게 리턴하게 되면 
							 // =>src/main/resources/statichome.html을 리턴해서 오류남.
		return "/home.html";
	}
	
	@GetMapping("/temp/img")
	public String tempIng() {
		return "/옷차림.jpg";
	}
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		//prefix : /WEB-INF/views/
		//suffix : .jsp
		//풀네임 : /WEB-INF/views/test.jsp
		
		return "test";
	}
}
