package com.spring.tdd.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.spring.tdd.model.ResponseVO;
import com.spring.tdd.model.User;
import com.spring.tdd.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	UserService userService;
	
	@BeforeEach
	void setUp() {
		this.userService = new UserService(new ResponseVO(),userRepository);
	}
	
	@Test
	void testGetAllUsers() {
		//given
		Set<User> users = new HashSet<User>();
		User expectedUser = new User();
		expectedUser.setUsername("TestUserName");
		expectedUser.setPassword("password");
		expectedUser.setExpired(false);
		expectedUser.setLocked(false);
		expectedUser.setRoles("ADMIN");
		users.add(expectedUser);
		
		//when
		when(userRepository.findAll()).thenReturn(users);
		
		//then
		ResponseVO responseVO = userService.getAllUsers();
		Set<User> actualUsers = (Set<User>) responseVO.getResponse() ;
		assertThat(responseVO.isSuccess()).isTrue();
		assertThat(actualUsers).isNotNull();
		assertThat(actualUsers).isInstanceOf(Set.class);
		assertThat(actualUsers).isNotEmpty();
		assertThat(actualUsers).isEqualTo(users);
	}

}
