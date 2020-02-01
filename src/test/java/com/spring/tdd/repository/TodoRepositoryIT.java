package com.spring.tdd.repository;

import static org.assertj.core.api.Assertions.assertThat;

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
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testFindByTitle() {
		Todo todo = new Todo();
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
		Todo todoToSave = new Todo();
		todoToSave.setTitle("Test Todo");
		todoToSave.setDescription("Test Todo Description");
		
		Todo savedTodo = repository.save(todoToSave);
		assertThat(savedTodo).isNotNull();
		
		Todo foundTodo = repository.findByTitle("Test Todo");
		assertThat(foundTodo).isEqualTo(savedTodo);
		
	}

}
