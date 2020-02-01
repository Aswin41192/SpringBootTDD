package com.spring.tdd.services;

import static com.spring.tdd.utils.ResponseUtils.handleFailureResponse;
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
		if(id==null) throw new IllegalArgumentException("Todo Id cannot be null");
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
		return null;
	}
	
	public ResponseVO updateTodo(@RequestBody Todo todo) {
		return null;
	}
	
	public ResponseVO deleteTodo(@RequestBody Todo todo) {
		return null;
	}
}
