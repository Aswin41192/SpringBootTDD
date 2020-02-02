package com.spring.tdd.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
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
		assertThat(actualResponseVO.getMessage()).isEqualTo("Todo Not Found");
	};
	
	@Test
	@DisplayName("Finding Todo by title")
	void testFindTodoByTitle() {
		when(todoRepository.findByTitle(Mockito.anyString())).thenReturn(todo);
		
		ResponseVO responseVO = todoService.findTodoByTitle("Dummy");
		Todo actualTodo = (Todo) responseVO.getResponse();
		verify(todoRepository).findByTitle(Mockito.anyString());
		assertThat(actualTodo).isNotNull();
		assertThat(actualTodo).isEqualTo(todo);
	}

	@Test
	@DisplayName("Saving Todo")
	void testSaveTodo() {
		Todo savedTodo = new Todo();
		savedTodo.setTitle("Test Todo");
		savedTodo.setDescription("Test Todo Description");
		
		when(todoRepository.save(Mockito.any(Todo.class))).thenReturn(savedTodo);
		
		ResponseVO responseVO = todoService.saveTodo(savedTodo);
		assertThat(responseVO).isNotNull();
		
		Todo actualTodo = (Todo) responseVO.getResponse();
		assertThat(savedTodo.getTitle()).isEqualTo(actualTodo.getTitle());
		assertThat(savedTodo.getDescription()).isEqualTo(actualTodo.getDescription());
	}
	
	@Test
	@DisplayName("Throw Exception when todo is null")
	public void testSaveTodo_throwExceptionWhenTodoIsNull() {
		Todo todo =null;
		assertThatExceptionOfType(IllegalArgumentException.class)
		.isThrownBy(()->todoService.saveTodo(todo)).withMessageContaining("Todo cannot be null");
	}

	@Test
	@DisplayName("Updating todo")
	void testUpdateTodo() {
		todo.setId(1L);
		todo.setTitle("Updated title");
		todo.setDescription("Updated Description");
		
		when(todoRepository.existsById(1L)).thenReturn(true);
		when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
		when(todoRepository.save(todo)).thenReturn(todo);
		
		ResponseVO responseVO = todoService.updateTodo(todo);
		Todo actualTodo =(Todo) responseVO.getResponse();
		
		assertThat(actualTodo).isNotNull();
		assertThat(responseVO).isNotNull();
		assertThat(responseVO.isSuccess()).isTrue();
		assertThat(actualTodo.getDescription()).isEqualTo(todo.getDescription());
		assertThat(actualTodo.getTitle()).isEqualTo(todo.getTitle());
	}
	
	@Test
	@DisplayName("Throw Exception while todo to update is not in Database")
	void testUpdateTodo_showFailure_whenIdNotExists() {
		Todo todoToUpdate = new Todo();
		todoToUpdate.setId(-1L);
		
		when(todoRepository.existsById(-1L)).thenReturn(false);
		
		ResponseVO responseVO = todoService.updateTodo(todoToUpdate);
		assertThat(responseVO.isSuccess()).isFalse();
		assertThat(responseVO.getMessage()).contains("Unable to update todo");
	}
	
	@Test
	@DisplayName("Throw Exception when todo to update is null")
	void testUpdateTodo_throwException_OnNull(){
		assertThatExceptionOfType(IllegalArgumentException.class)
		.isThrownBy(()->todoService.updateTodo(null)).withMessageContaining("Todo cannot be null");
	}

	@Test	
	@DisplayName("Throw Exception when todo to delete is null")
	void testDeleteTodo_throwException_WhenTodoIsNull() {
		assertThatExceptionOfType(IllegalArgumentException.class)
		.isThrownBy(()->todoService.deleteTodo(null)).withMessageContaining("Todo cannot be null");
	}
	
	@Test
	@DisplayName("Show Failure Message when todo to delete doesnot exist")
	void testDeleteTodo_whenTodoNotExists() {
		when(todoRepository.existsById(todo.getId())).thenReturn(false);
		
		ResponseVO responseVO = todoService.deleteTodo(todo);
		assertThat(responseVO).isNotNull();
		assertThat(responseVO.isSuccess()).isFalse();
		assertThat(responseVO.getMessage()).isEqualTo("Unable to delete todo which doesnot exist");
	}
	
	@Test
	@DisplayName("Delete Todo")
	void testDeleteTodo() {
		
		when(todoRepository.existsById(todo.getId())).thenReturn(true);
		doNothing().when(todoRepository).deleteById(todo.getId());
		
		ResponseVO actual = todoService.deleteTodo(todo);
		
		assertThat(actual).isNotNull();
		assertThat(actual.isSuccess()).isTrue();
		assertThat(actual.getMessage()).isEqualTo("Successfully Deleted");
		
	}
	

}
