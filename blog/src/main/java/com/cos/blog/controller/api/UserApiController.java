package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("UserApiController실행");
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.INTERNAL_SERVER_ERROR.value(),1);
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user,@AuthenticationPrincipal PrincipalDetail principalDetail){
		userService.회원수정(user,principalDetail);
		//여기서는 트랜잭션이 종료되기 때문에 DB에 값이 변경됐지만,
		//세션값은 변경되지 않은 상태이기 떄문에 직접 세션값을 변경해줘야됨.
		
		
		return new ResponseDto<Integer>(HttpStatus.INTERNAL_SERVER_ERROR.value(),1);
	}
	

	/*
	 //전통적인 로그인 방식
	@PostMapping("/api/user/login")
	public ResponseDto<Integer> login(@RequestBody User user,HttpSession session) {
		System.out.println("UserApiController실행:login호출됨");
		user.setRole(RoleType.USER);
		User principal = userService.로그인(user); //principal : 접근주체 라는 뜻
		
		if(principal!=null) {
			session.setAttribute("principal", principal);
		}
		
		return new ResponseDto<Integer>(HttpStatus.INTERNAL_SERVER_ERROR.value(),1);
	}*/
}
