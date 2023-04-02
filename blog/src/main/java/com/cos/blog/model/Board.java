package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false, length=100)
	private String title;
	
	@Lob
	private String content; //섬머노트 라이브러리, <html>태그가 섞여서 디자인됨.
	
	//@ColumnDefault("0")
	private int count; //조회수
	
	@ManyToOne(fetch = FetchType.EAGER)  // Many=Board, One=User (즉, 한명의 유저는 여러 개의 게시물을 작성할 수 있다)
										 // fetch = FetchType.EAGER 무조건 불러와라.
	 									 // fetch = FetchType.LAZY 필요할 때 불러와.(기본 값)
	@JoinColumn(name="userId")  //필드명 설정
	private User user; //DB는 오브젝트를 저장할 수 없다. 그렇기 때문에 자동으로 FK(int형)로 저장됨 (자바는 오브젝트를 저장할 수 있다.)
	// User 클래스를 참조하기 때문에 자동으로 FK가 만들어짐.
	
	@OneToMany(mappedBy = "board",fetch = FetchType.EAGER,cascade=CascadeType.REMOVE) //mappedBy : 연관관계의 주인이 아니다.(난 FK가 아니에요) DB에 칼럼을 만들지 마세요.
	@JsonIgnoreProperties({"board","user"}) //reply안에 있는 board는 호출이 안됨.
	@OrderBy("id desc")
	private List<Reply> replys;
	
	@CreationTimestamp //현재 시간이 자동으로 들어감.
	private Timestamp createDate;

}
