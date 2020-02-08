package com.spring.tdd;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.spring.tdd.model.User;
import com.spring.tdd.repository.UserRepository;

@SpringBootApplication
public class SpringBootTddApplication implements CommandLineRunner {
	
	private UserRepository userRepository;
	
	public SpringBootTddApplication(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTddApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User admin = new User();
		admin.setUsername("admin");
		admin.setPassword(new BCryptPasswordEncoder().encode("Admin123"));
		admin.setActive(true);
		admin.setRoles("ADMIN");
		admin.setAuthorities("ALL_ACCESS");
		admin.setExpired(false);
		admin.setLocked(false);
		
		if(null == userRepository.findByUsername(admin.getUsername()))
			userRepository.save(admin);
	}

}
