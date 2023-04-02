package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.blog.config.auth.PrincipalDetailService;

//아래 세개는 세트다
@Configuration // 빈등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@EnableWebSecurity // 시큐리티 필터 추가 = 스프링 시큐리티가 활성화 되어 있는데 어떤 설정을 해당 파일에서 하겠다는 뜻
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권하 및 인증을 미리 체크하는 것
public class SecurityConfig {
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean // 리턴하는 new BCryptPasswordEncoder() 값을 스프링이 관리함.
	BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	//시큐리티가 대신 로그인해줄 때 password를 가로채는데
	//해당 password가 어떤 값으로 해쉬가 되어 회원가입이 되었는지 알아야
	//같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음.
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		//principalDetailService에게 password의 해쉬값을 알려준다~
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}

	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable() // csrf토큰 비활성화(테스트 시 걸어두는게 좋음)
				.authorizeRequests() //요청이 들어오면~
				.antMatchers("/", "/css/**", "/image/**","/js/**","/auth/**")
				.permitAll() //auth쪽으로는 누구든 들어올 수 있다.
				.anyRequest() //이게 아닌 다른 모든 요청은~
				.authenticated() //인증이 되어야돼
				.and()
				.formLogin()
				.loginPage("/auth/loginForm") // 인증이 필요한 사람들은 전부 여기 페이지로 가세요~
				.loginProcessingUrl("/auth/loginProc") //스프링 시큐리티가 해당 주소로 오는 로그인 요청을 가로채서 대신 로그인해준다.
				.defaultSuccessUrl("/"); //요청이 정상이라면 "/"로 이동한다.
				
		return http.build();
	}

}