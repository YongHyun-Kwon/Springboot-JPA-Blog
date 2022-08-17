package com.cos.blog.test;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // bean생성자
public class Member {

	// final로 설정하는 건 DB에서 변경할일이 없으면 불변성을 주기위해
	// 하지만 변경해야할 경우 final로 설정하면 안된다.
	private int id;
	private String username;
	private String password;
	private String email;
	
	@Builder
	public Member(int id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
}
