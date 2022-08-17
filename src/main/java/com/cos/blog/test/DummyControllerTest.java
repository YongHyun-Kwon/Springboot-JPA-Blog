package com.cos.blog.test;

import java.util.List;

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

// html파일이 아니라 data를 리턴해주는 controller = RestController
@RestController
public class DummyControllerTest {

	@Autowired // 의존성 주입(DI)
	private UserRepository userRepository;

	// save함수는 id를 전달하지 않으면 insert를 해주고
	// save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	// save함수는 id를 전달하면 id에 대한 데이터가 없으면 insert를 한다.
	// email, password

	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {

		try {

			userRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {

			return "삭제에 실패하였습니다. 해당 id는 존재하지 않습니다.";

		} // end catch

		return "삭제되었습니다. id : " + id;

	}// delete

	@Transactional // 함수 종료시에 자동 commit이 된다.
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { // json 데이터를 요청 -> Java
																					// Object(MessageConverter의
		User user = userRepository.findById(id).orElseThrow(() -> {

			return new IllegalArgumentException("수정에 실패하였습니다.");

		});

		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());

		// userRepository.save(requestUser);

		// 더티 체킹
		return user;

	}// updateUser

	// 전체 조회는 Parameter을 받을 필요가 없음
	@GetMapping("/dummy/users")
	public List<User> userList() {

		return userRepository.findAll();

	}// userList

	// 한페이지당 10건 데이터를 return
	@GetMapping("/dummy/user/page")
	public List<User> pageList(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

		Page<User> pagingUsers = userRepository.findAll(pageable);

		List<User> users = pagingUsers.getContent();

		return users;

	}// pageList

	// {id} 주소로 Parameter로 전달 받을 수 있음.
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {

		// 현재 user DB에 id가 3번까지 있을 경우 user/4를 찾으면 DB에서 못찾을 경우 user가 null이 된다.
		// return 할때 null이 되기 때문에 문제가 발생할 수 있음
		// Optional로 객체를 감싸서 가져오기에 null인지 판단해서 return 해야한다.
		User user = userRepository.findById(id).orElseThrow(() -> {

			return new IllegalArgumentException("해당 사용자는 존재하지 않습니다.");

		});

		// 요청 : 웹 브라우저
		// user 객체 = java object
		// 변환 ( 웹브라우저가 이해할 수 있는 데이터 ) -> json
		// 스프링부트 = MessageConverter이 응답시에 자동 작동
		// 만약 java object를 return하면 MessageConverter가 Jackson 라이브러리를 호출
		// user 오브젝트를 json으로 변환해 브라우저에 던진다.
		return user;

	}

	// http의 body에 username, password, email 데이터를 가지고(요청)
	@PostMapping("/dummy/join")
	public String join(User user) { // key = value(약속된 규칙)

		user.setRole(RoleType.USER);
		userRepository.save(user);

		return "회원가입이 완료되었습니다.";
	}// join

}
