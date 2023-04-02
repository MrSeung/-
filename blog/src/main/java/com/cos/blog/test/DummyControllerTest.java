package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//data를 리턴해주는 controller=RestControl
@RestController
public class DummyControllerTest {
	
	@Autowired //의존성 주입(DI)
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			return"삭제에 실패했습니다. 해당 id는 DB에 존재하지않습니다.";
		}
		
		return"삭제되었습니다.id:"+id;
	}
	
	//email, passeword 수정
	@PutMapping("/dummy/user/{id}")
	@Transactional
	//@RequestBody : json 데이터를 받기위해 필요함.
	//json 데이터를 입력해서 요청했는데,MessageConverter의 Jackson라이브러리가 Java Object로 변환해서 받아줌.
	public User updateUser(@PathVariable int id,@RequestBody User requestUser) { 
		System.out.println("id:"+id);
		System.out.println("password:"+requestUser.getPassword());
		System.out.println("email:"+requestUser.getEmail());
		
		
		User user=userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패했습니다.");
		}); //=>영속화 시킴.
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//save함수
		//id를 전달하지 않는다면 새로운 데이터를 insert.
		//id를 전달하고, 해당 id에 대한 데이터가 있다면 기존의 데이터를 update.
		//id를 전달하고, 해당 id에 대한 데이터가 없다면 기존의 데이터를 insert(=null로 세팅됨).
		//userRepository.save(requestUser);
		
		//더티 체킹
		return user;
	}
	
	//http://localhost:8000/blog/dummy/users
	@GetMapping("/dummy/users")
	public List<User>list(){
		return userRepository.findAll();
	}
	
	//한페이지당 2건의 데이터를 리턴받아 볼 예정
	@GetMapping("/dummy/user")
								//size = 2건씩 들고올 것이다, sort= id를 기준으로 분류하고, direction=최신 순으로.
	public List<User> pageList(@PageableDefault(size=2,sort="id",direction = Sort.Direction.DESC)Pageable pageable){
		Page<User> pagingUser = userRepository.findAll(pageable);
		
		//첫페이지인지 마지막 페이지인지 알 수 있음.
		/*if(pagingUser.isLast()) {	
		}*/
		
		List<User> users=pagingUser.getContent();
		return users;
	}
	
	//{id}주소로 파라메터를 전달받을 수 있음.
	//http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//user 4번을 찾을 때 내가 DB에서 못찾아오게 되면 user가 null이 될 거잖아?
		//그럼 return null이 리턴이 되자나.. 그럼 프로그램에 문제가 있지 않겠니??
		//Optional로 너의 User객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해!
		
		//User user= userRepository.findById(id).get(); 3개의 데이터가 있는 상태에서 id 4를 가져오면 문제 발생.
		//findById(id)의 id값이 범위 밖의 값이라면 orElseThrow실행됨.
		User user= userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
		@Override
		public IllegalArgumentException get() {
			// TODO Auto-generated method stub
			return new IllegalArgumentException("해당 유저는 없습니다.");
		}
		});
		
		//람다식
		/*User user=userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("해당 유저는 없습니다.id:"+id);
		});*/
		
		//요청은 웹브라우저가 했다. 하지만 웹브라우저는 html만 이해할 수 있음. 자바 오브젝트는 이해를 못한다.
		//user 객체 = 자바 오브젝트
		//변환(웹브라우저가 이해할 수 있는 데이터)->json
		//스프링부트 = MessageConverter라는 애가 응답 시에 자동으로 작동함
		//만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson라이브러리를 호출해서
		//user오브젝트를 json으로 변환해서 브라우저에게 던져줌.
		return user;
	}

	
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("username : "+user.getUsername());
		System.out.println("password : "+user.getPassword());
		System.out.println("email : "+user.getEmail());
		
		user.setRole(RoleType.USER);
		userRepository.save(user); //데이터베이스에 user 테이블이 생성됨.
		return "회원가입 완료되었습니다.";
	}
	
	
}
