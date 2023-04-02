package com.cos.blog.controller;

import javax.xml.bind.helpers.PrintConversionEventImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping({ "", "/" })
	//스프링에서는 데이터를 가져갈때 Model이 필요함.
	public String index(Model model,@PageableDefault(size=3,sort="id",direction = Sort.Direction.DESC)Pageable pageable) {
		model.addAttribute("boards", boardService.글목록(pageable));
		return "index"; //viewResolver 작동! viewResolver는 model을 들고 이동함.
						//viewResolver는 index 앞뒤에 /WEB-INF/views/와 jsp를 달아준다.

	}
	@GetMapping("/board/{id}")
	public String finById(@PathVariable int id,Model model) {
		model.addAttribute("board",boardService.글상세보기(id));
		return "board/detail";
		
	}
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/updateForm";
	}
	
	//USER권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
}
