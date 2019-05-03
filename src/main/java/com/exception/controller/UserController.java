package com.exception.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exception.exception.ResourceNotFoundException;
import com.exception.exception.ResourceNotFoundException2;
import com.exception.model.User;
import com.exception.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/users")
	public List<User> getUsers(){
		return userRepository.findAll();
	}
	
	/* Exception Handling (Uses predefined structure) */
	
	@GetMapping("/user/{id}")
	public User getUser(@PathVariable("id") int id) {
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException
				("No user found with id="+id));
	}
	
	/* Generic Exception Handling using ControllerAdvice (Uses Custom structure) */
	
	@GetMapping("/user-2/{id}")
	public User getuser(@PathVariable("id") int id) {
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException2
				("No user found with id="+id));
	}
	
	
	/* Handling Validation errors */
	
	@PostMapping("/adduser")
	public ResponseEntity<Object> addUser(@Valid @RequestBody User user, BindingResult result) {
		if(result.hasErrors()) {
			return fieldErrorResponse("Validation Error", getFieldErrorResponse(result));
		}
		User u = userRepository.save(user);
		return new ResponseEntity<Object>(u, HttpStatus.CREATED);
	}
	
	/* Exception Handling */
	
	@DeleteMapping("/delete/{id}")
	public void deleteUser(@PathVariable int id) {
		User u = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException
				("No user found with id="+id));
				userRepository.deleteById(u.getId());
	}
	
	/* Methods for handling validation errors */
	
	final static public ResponseEntity<Object> fieldErrorResponse(String message,Object fieldError){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isSuccess", false);
		map.put("data", null);
		map.put("status", HttpStatus.BAD_REQUEST);
		map.put("message", message);
		map.put("timestamp", new Date());
		map.put("fieldError", fieldError);
		return new ResponseEntity<Object>(map,HttpStatus.BAD_REQUEST);
	}
	
	public static Map<String,Object> getFieldErrorResponse(BindingResult result){
		Map<String, Object> fieldError = new HashMap<String, Object>();
		List<FieldError> errors = result.getFieldErrors();
		for(FieldError error : errors) {
			fieldError.put(error.getField(), error.getDefaultMessage());
		}
		return fieldError;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
