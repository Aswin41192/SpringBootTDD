package com.spring.tdd.services;

import static com.spring.tdd.utils.ResponseUtils.handleSuccessfulResponse;

import java.util.HashSet;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spring.tdd.model.ResponseVO;
import com.spring.tdd.model.User;
import com.spring.tdd.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserService {

	private ResponseVO responseVO;
	private UserRepository userRepository;
	
	public UserService(ResponseVO responseVO, UserRepository userRepository) {
		super();
		this.responseVO = responseVO;
		this.userRepository = userRepository;
	}



	@GetMapping
	public ResponseVO getAllUsers() {
		Set<User> users = new HashSet<User>();
		userRepository.findAll().forEach(users::add);
		responseVO = handleSuccessfulResponse(responseVO, users, "Users");
		return responseVO;
		
	}
}
