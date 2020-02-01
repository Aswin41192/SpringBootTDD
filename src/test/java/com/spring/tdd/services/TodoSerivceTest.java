package com.spring.tdd.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.spring.tdd.model.ResponseVO;
import com.spring.tdd.model.Todo;
import com.spring.tdd.repository.TodoRepository;

@ExtendWith(MockitoExtension.class)
class TodoSerivceTest {

	private TodoService todoService;
	
	private Todo todo;
	
	@Mock
	private TodoRepository todoRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		todo=new Todo("Dummy Title","Dummy Description");
		todoService = new TodoService(todoRepository, new ResponseVO());
	}

	@Test
	void testFindAllTodos() {
		//given
		ResponseVO expected = new ResponseVO();
		
		Iterable<Todo> todos=new ArrayList<Todo>();
		expected.setResponse(todos);
		expected.setMessage("Todos");
		expected.setSuccess(true);
	
		//when
		when(todoRepository.findAll()).thenReturn(todos);

		//then
		ResponseVO actual=todoService.findAllTodos();
		verify(todoRepository).findAll();
		assertThat(actual).isNotNull();
	}

	@Test
	@DisplayName("Finding Todo By Id")
	void testFindTodoById() {
		ResponseVO expected=new ResponseVO();
		expected.setSuccess(true);
		expected.setResponse(todo);
		
		when(todoRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(todo));
		ResponseVO actual= todoService.findTodoById(1L);
		Todo actualTodo = (Todo) actual.getResponse();
		
		assertThat(actualTodo).isNotNull();
		assertThat(actual.isSuccess()).isEqualTo(true);
		assertThat(actualTodo).isEqualTo(todo);

	}
	
	@Test
	@DisplayName("Throwing Exception when null id is passed to find  a todo")
	void testFindTodoById_throwsException_onNullId() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(()->todoService.findTodoById(null));		
	}
	
	@Test
	@DisplayName("Todo Not Found")
	public void testFindTodoById_Not_Found() {
		when(todoRepository.findById(-1L)).thenReturn(Optional.ofNullable(null));
		
		ResponseVO actualResponseVO=todoService.findTodoById(-1L);
		assertThat(actualResponseVO).isNotNull();
		assertThat(actualResponseVO.isSuccess()).isEqualTo(false);
		assertThat(actualResponseVO.getMessage()).isEqualTo("Todo Not Found!");
	};
	@Test
	void testFindTodoByTitle() {
		when(todoRepository.findByTitle(Mockito.anyString())).thenReturn(todo);
		
		ResponseVO responseVO = todoService.findTodoByTitle("Dummy");
		Todo actualTodo = (Todo) responseVO.getResponse();
		verify(todoRepository).findByTitle(Mockito.anyString());
		assertThat(actualTodo).isNotNull();
		assertThat(actualTodo).isEqualTo(todo);
	}

	@Test
	@Disabled
	void testSaveTodo() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled
	void testUpdateTodo() {
		fail("Not yet implemented");
	}

	@Test	
	@Disabled
	void testDeleteTodo() {
		fail("Not yet implemented");
	}

}
