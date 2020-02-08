package com.spring.tdd.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.tdd.model.User;
import com.spring.tdd.repository.UserRepository;

@Service
public class UserPrincipalService implements UserDetailsService{
	
	private UserRepository userRepository;
	
	public UserPrincipalService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null ) {
			throw new UsernameNotFoundException("Username not found");
		}
		return new UserPrincipal(user);
	}

}
