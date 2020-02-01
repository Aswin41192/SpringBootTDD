package com.spring.tdd.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.spring.tdd.model.Todo;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long>{
	public Todo findByTitle(String title);
}
