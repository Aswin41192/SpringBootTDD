package com.spring.tdd.services;

import static com.spring.tdd.utils.ResponseUtils.handleFailureResponse;
import static com.spring.tdd.utils.ResponseUtils.throwIllegalArgumentException;
import static com.spring.tdd.utils.ResponseUtils.handleSuccessfulResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.spring.tdd.model.ResponseVO;
import com.spring.tdd.model.Todo;
import com.spring.tdd.repository.TodoRepository;

@RestController
public class TodoService {

	private TodoRepository todoRepository;
	private ResponseVO responseVO;
	
	public TodoService() {
	}
	

	public TodoService(TodoRepository todoRepository, ResponseVO responseVO) {
		super();
		this.todoRepository = todoRepository;
		this.responseVO = responseVO;
	}




	public ResponseVO findAllTodos() {
		List<Todo> todos=new ArrayList<Todo>();
		try {
		todos=(List<Todo>) todoRepository.findAll();
		responseVO = handleSuccessfulResponse(responseVO, todos, "Todos");
		}catch(Exception e) {
			responseVO=handleFailureResponse(responseVO, "Exception Occured While Finding All Todos");
		}
		return responseVO;
	}
	
	public ResponseVO findTodoById(Long id) {
		if(id==null) throwIllegalArgumentException("Todo Id cannot be null");
		try {
			Optional<Todo> todo = todoRepository.findById(id);
			if(todo.isPresent()) {
			responseVO = handleSuccessfulResponse(responseVO, todo.get(), "Todo");
			}else {
				responseVO=handleFailureResponse(responseVO, "Todo Not Found");
			}
			}catch(Exception e) {
				responseVO=handleFailureResponse(responseVO, "Exception Occured While Finding Todo By Id");
			}
			return responseVO;
			}
	
	
	public ResponseVO findTodoByTitle(String title) {
		try {
			Todo todo = todoRepository.findByTitle(title);
			responseVO = handleSuccessfulResponse(responseVO, todo, "Filtered by Title");
		}catch(Exception e) {
			responseVO=handleFailureResponse(responseVO, "Exception Occured While Finding Todo By Title");
		}
		return responseVO;
	}
	
	public ResponseVO saveTodo(@RequestBody Todo todo) {
		if(todo == null) throwIllegalArgumentException("Todo cannot be null");
		try {
			Todo savedTodo = todoRepository.save(todo);
			handleSuccessfulResponse(responseVO, savedTodo, "Todo Saved");
		}catch(Exception e) {
			handleFailureResponse(responseVO, "Exception while saving Todo");
		}
		return responseVO;
	}
	
	public ResponseVO updateTodo(@RequestBody Todo todo) {
		if(todo == null) throw new IllegalArgumentException("Todo cannot be null");
		try {
			if(todoRepository.existsById(todo.getId())) {
				Todo todoFromDB = todoRepository.findById(todo.getId()).get();
				todoFromDB.setTitle(todo.getTitle());
				todoFromDB.setDescription(todo.getDescription());
				todoRepository.save(todoFromDB);
				responseVO=handleSuccessfulResponse(responseVO, todoFromDB, "Successfully Updated");
			}else {
				responseVO = handleFailureResponse(responseVO, "Unable to update todo");
			}
		}catch(Exception e) {
			
		}
		return responseVO;
	}
	
	public ResponseVO deleteTodo(@RequestBody Todo todo) {
		if(todo == null) throw new IllegalArgumentException("Todo cannot be null");
		if(todoRepository.existsById(todo.getId())) {
			todoRepository.deleteById(todo.getId());
			responseVO = handleSuccessfulResponse(responseVO, null, "Successfully Deleted");
		} else {
			responseVO = handleFailureResponse(responseVO, "Unable to delete todo which doesnot exist");
		}
		
		return responseVO;
	}
}
