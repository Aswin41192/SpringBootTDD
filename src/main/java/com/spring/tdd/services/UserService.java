package com.spring.tdd.services;

import static com.spring.tdd.utils.ResponseUtils.handleSuccessfulResponse;
import static com.spring.tdd.utils.ResponseUtils.handleFailureResponse;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PostMapping
	public ResponseVO saveUser(@RequestBody User user) {
		if(null == user) {
			throw new IllegalArgumentException("Invaild user");
		}
		if(user.getUsername() == null || user.getPassword() == null) {
			responseVO = handleFailureResponse(responseVO, "Username & Password is mandatory");
			return responseVO;
		}
		if(userRepository.findByUsername(user.getUsername())!=null) {
			responseVO = handleFailureResponse(responseVO, "User Name Already Taken");
			return responseVO;
		}
		try {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		User savedUser = userRepository.save(user);
		responseVO = handleSuccessfulResponse(responseVO, savedUser, "User Saved Successfully");
		} catch(Exception e) {
			e.printStackTrace();
			handleFailureResponse(responseVO,"Unable to save user");
		}
		return responseVO;
	}
}
