package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.UserService;

@RestController
public class BoardApiController {
	
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board,@AuthenticationPrincipal PrincipalDetail principal) {
		boardService.글쓰기(board,principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.INTERNAL_SERVER_ERROR.value(),1);
	}
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id,@AuthenticationPrincipal PrincipalDetail principal){
		boardService.글삭제하기(id,principal);
		return new ResponseDto<Integer>(HttpStatus.INTERNAL_SERVER_ERROR.value(),1);
	}
	
	
	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> update(@PathVariable int id,@RequestBody Board board){
		boardService.글수정하기(id,board);
		return new ResponseDto<Integer>(HttpStatus.INTERNAL_SERVER_ERROR.value(),1);
	}
	
	//데이터를 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋음.
	//dto를 사용하지 않은 이유는
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDto<Integer> replySave(@PathVariable int boardId, @RequestBody Reply reply,@AuthenticationPrincipal PrincipalDetail principal) {
		
		boardService.댓글쓰기(principal.getUser(),boardId, reply);
		return new ResponseDto<Integer>(HttpStatus.INTERNAL_SERVER_ERROR.value(),1);
	}
	
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
	public ResponseDto<Integer> replyDelete(@PathVariable int replyId){
		boardService.댓글삭제(replyId);
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
