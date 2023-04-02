package com.cos.blog.model;

import javax.annotation.Generated;

import lombok.Data;

@Data
public class KakaoProfile {

	public Long id;
	public String connected_at;
	public Properties properties;
	public KakaoAccount kakaoAccount;
	
	@Data
	public class KakaoAccount {

		public Boolean profileNicknameNeedsAgreement;
		public Profile profile;
		public Boolean hasEmail;
		public Boolean emailNeedsAgreement;
		public Boolean isEmailValid;
		public Boolean isEmailVerified;
		public String email;

	}

}

@Data
class Profile {

	public String nickname;

}

@Data
class Properties {

	public String nickname;

}
