package com.cos.blog.controller;

import java.net.http.HttpHeaders;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class UserController {
	// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/**허용
	// 그냥 주소가 / 이면 index.jsp허용
	// static이하에 있는 /js/**,/css/**,/image/**

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}

	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}

	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}

	@GetMapping("/auth/kakao/callback")
	public @ResponseBody String kakaoCallback(String code) { // 데이터를 리턴해주는 컨트롤러 함수

		// POST방식으로 key=value 데이터를 요청(카카오쪽으로)
		RestTemplate rt = new RestTemplate();
		//헤더 오브젝트 생성
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		// Content-type은 key=value값으로 보내겠다 라는 뜻
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// body데이터를 담을 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "20a21c40b44ec21c4ebb2227ca515014");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code",code);

		// 헤더 값과 바디값을 가진 엔티티 생성
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

		//http 요청하기 - post방식으로 - 그리고 response변수의 응답 받음.
		ResponseEntity<String> response = rt.exchange(
				// 요청주소
				"https://kauth.kakao.com/oauth/token",
				// 요청 메서드가 뭔지
				HttpMethod.POST,
				// 헤더 값과 바디 값
				kakaoTokenRequest,
				// 응답 받을 타입
				String.class);
		
		//jason데이터를 java 오브젝트로 변환하는 과정
		ObjectMapper objectMapper=new ObjectMapper();
		OAuthToken oauthToken=null;
		
		try {
			oauthToken=objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("카카오 엑세스 토큰:"+oauthToken.getAccess_token());

		//사용자 정보 가져오기
		RestTemplate rt2 = new RestTemplate();
		//헤더 오브젝트 생성
		org.springframework.http.HttpHeaders headers2 = new org.springframework.http.HttpHeaders();
		// Content-type은 key=value값으로 보내겠다 라는 뜻
		
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


		// 헤더 값과 바디값을 가진 엔티티 생성
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

		//http 요청하기 - post방식으로 - 그리고 response변수의 응답 받음.
		ResponseEntity<String> response2 = rt2.exchange(
				// 요청주소
				"https://kapi.kakao.com/v2/user/me",
				// 요청 메서드가 뭔지
				HttpMethod.POST,
				// 헤더 값과 바디 값
				kakaoProfileRequest,
				// 응답 받을 타입
				String.class);
		
		ObjectMapper objectMapper2=new ObjectMapper();
		KakaoProfile kakaoProfile=null;
		
		try {
			kakaoProfile=objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("카카오 아이디(번호):"+kakaoProfile.getId());
		System.out.println("카카오 이메일:"+kakaoProfile.getKakaoAccount().getEmail());
		
		return response2.getBody();
	}
	
	

}
