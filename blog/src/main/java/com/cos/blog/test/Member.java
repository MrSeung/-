package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Getter
//@Setter
@Data //getter,setter 모두 생성
@AllArgsConstructor //전체 생성자 생성
@NoArgsConstructor  //빈 생성자 생성
@Builder  //생성자를 마음대로 만들 수 있음.

public class Member {
	private int id;
	private String username;
	private String password;
	private String email;
	
}
