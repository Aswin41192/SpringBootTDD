package com.spring.tdd.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
		this.userService = new UserService(new ResponseVO(), userRepository);
	}

	@Test
	@DisplayName("Get All Users")
	void testGetAllUsers() {
		// given
		Set<User> users = new HashSet<User>();
		User expectedUser = new User();
		expectedUser.setUsername("TestUserName");
		expectedUser.setPassword("password");
		expectedUser.setExpired(false);
		expectedUser.setLocked(false);
		expectedUser.setRoles("ADMIN");
		users.add(expectedUser);

		// when
		when(userRepository.findAll()).thenReturn(users);

		// then
		ResponseVO responseVO = userService.getAllUsers();
		Set<User> actualUsers = (Set<User>) responseVO.getResponse();
		assertThat(responseVO.isSuccess()).isTrue();
		assertThat(actualUsers).isNotNull();
		assertThat(actualUsers).isInstanceOf(Set.class);
		assertThat(actualUsers).isNotEmpty();
		assertThat(actualUsers).isEqualTo(users);
	}

	@Test
	@DisplayName("Save User")
	public void testSaveUser() {
		User user = new User();
		user.setUsername("User");
		user.setPassword("User");
		user.setRoles("USER");
		user.setAuthorities("VIEW_ACCESS");
		user.setActive(true);
		user.setLocked(false);
		user.setExpired(false);
		
		when(userRepository.save(user)).thenReturn(user);
		
		ResponseVO responseVO = userService.saveUser(user);
		assertThat(responseVO).isNotNull();
		assertThat(responseVO.isSuccess()).isTrue();
		assertThat(responseVO.getResponse()).isNotNull();
		
		User actualUser = (User) responseVO.getResponse();
		assertThat(user.getUsername()).isEqualTo(actualUser.getUsername());
		assertThat(user.isActive()).isEqualTo(actualUser.isActive());
		assertThat(user.getRoles()).isEqualTo(actualUser.getRoles());
		assertThat(user.getAuthorities()).isEqualTo(actualUser.getAuthorities());
	}
	
	@Test
	@DisplayName("Show Error If User Name & Password null")
	public void testSaveUser_ThrowMessage_onNullUsernameAndPassword() {
		ResponseVO responseVO = userService.saveUser(new User());
		
		assertThat(responseVO.isSuccess()).isFalse();
		assertThat(responseVO.getMessage()).contains("Username & Password is mandatory");
	}

}
