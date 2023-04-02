package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//ORM-> JAVA(다른언어) Object -> 테이블로 매핑해주는 기술
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@DynamicInsert // null인 필드를 제외하고 insert해줌
@Entity // User 클래스가 MySQL에 테이블이 생성됨.
public class User {

	@Id // Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.(=auto_increment)
	private int id; // 시퀀스,auto_increment

	@Column(nullable = false, length = 30, unique = true) // 칼럼(속성) 설정, unique = true (중복된 값은 들어갈 수 없음)
	private String username; // 아이디

	@Column(nullable = false, length = 100) // 나중에 해쉬해서 암호화된 비밀번호를 데이터베이스에 넣어줄 예정
	private String password;

	@Column(nullable = false, length = 50)
	private String email;

	// @ColumnDefault("'user'") //user로 디폴트함.
	@Enumerated(EnumType.STRING) // DB는 RoleType이 없기 때문에 EnumType이 String이라고 알려줌.
	private RoleType role; // Enum을 쓰는게 좋다. RoleType(ADMIN, USER 값이 들어가 있는 Enum)

	@CreationTimestamp // 시간이 자동으로 입력됨.
	private Timestamp createDate;
}
