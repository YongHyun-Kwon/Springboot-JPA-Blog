package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 사용자가 요청-> 응답(HTML 파일)
// @Controller

// 사용자가 요청-> 응답(Data)
@RestController
public class HttpControllerTest {

	private static final String TAG = "HttpControllerTest : ";
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		
		Member m = Member.builder().username("hyun").password("1234").email("hyun@gmail.com").build();
		System.out.println(TAG + "getter : " + m.getId());
		m.setId(5000);
		System.out.println(TAG + "setter : " + m.getId());
		
		return "lombok test";
		
	}// lombokTest
	
	// 인터넷 브라우저 요청은 무조건 get요청밖에 할 수 없다.
	// http://localhost:8080/http/get (select)
	@GetMapping("/http/get")
	public String getTest(Member m) {
		
		return "get요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
		
	}// getTest
	
	// http://localhost:8080/http/post (insert)
	@PostMapping("/http/post") // test/plain, application/json
	public String postTest(@RequestBody Member m) { // MessageConverter (스프링부트)
		
		return "post요청" + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
		
	}// postTest
	
	// http://localhost:8080/http/put (update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		
		return "put요청" + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
		
	}// putTest
	
	// http://localhost:8080/http/delete (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		
		return "delete요청";
		
	}// deleteTest
	
}
