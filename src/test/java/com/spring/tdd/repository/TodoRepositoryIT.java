package com.spring.tdd.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.spring.tdd.model.Todo;

@DataJpaTest
class TodoRepositoryIT {

	@Autowired
	private TodoRepository repository;
	
	private Todo todo;
	
	@BeforeEach
	void setUp() throws Exception {
		todo = new Todo();
		todo.setId(990L);
	}

	@Test
	void testFindByTitle() {
		todo.setTitle("Laundry");
		todo.setDescription("Buy clothes from Laundry");
		
		Todo actualTodo=repository.findByTitle("Laundry");
		assertThat(actualTodo).isNotNull();
		assertThat(todo.getDescription()).isEqualTo(actualTodo.getDescription());
		assertThat(actualTodo.getId()).isGreaterThan(0L);
	}
	
	@Test
	@DisplayName("Saving Todo in Database")
	void saveTodo() {
		Todo todo = new Todo();
		todo.setTitle("Test Todo");
		todo.setDescription("Test Todo Description");
		
		Todo savedTodo = repository.save(todo);
		assertThat(savedTodo).isNotNull();
		
		Todo foundTodo = repository.findByTitle("Test Todo");
		assertThat(foundTodo).isEqualTo(savedTodo);
		
	}
	
	@Test
	@DisplayName("Updating Todo in Database")
	void updateTodo() {
		todo.setTitle("Updated Todo");
		todo.setDescription("Updated Description");
		
		Todo todoFromDB=repository.findById(todo.getId()).get();
		todoFromDB.setTitle(todo.getTitle());
		todoFromDB.setDescription(todo.getDescription());
		
		todo=repository.save(todoFromDB);
		Todo actualTodo = repository.findById(todoFromDB.getId()).get();

		assertThat(actualTodo).isEqualTo(todo);
	}
	
	@Test
	@DisplayName("Delete todo from Database")
	public void testDeleteTodo() {
			repository.deleteById(todo.getId());
		
			Optional<Todo> todoFromDB = repository.findById(todo.getId());
			assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(()->todoFromDB.get());
	}

}
