package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cos.blog.model.User;

//DAO
//자동으로 bean등록이 된다.
// @Repository 생략 가능.
public interface UserRepository extends JpaRepository<User, Integer> {
	//SELECT * FROM user WHERE username =?1
	Optional<User> findByUsername(String username);
	
}

// User을 관리하는 JpaRepository 생성, User 테이블의 pk는 Integer이야.
//JPA Naming 쿼리
//SELECT * FROM user WHERE username =?1 AND password =?2; 가 동작함.
//User findByUsernameAndPassword(String username, String password);

//이렇게도 쓸 수 있음.
/*@Query(value="SELECT * FROM user WHERE username =?1 AND password =?2",nativeQuery=true)
	User login(String username, String password);*/