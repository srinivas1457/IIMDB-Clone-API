package com.example.impl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.impl.dto.UserRequest;
import com.example.impl.dto.UserResponse;
import com.example.impl.service.UserService;
import com.example.impl.util.ResponseStructure;

import jakarta.validation.Valid;

@RestController
//@RequestMapping("/users")
@RequestMapping
public class UserController {
	
	@Autowired
	private UserService service;
	
	
	@PostMapping("/users")
	public ResponseEntity<ResponseStructure<UserResponse>> addUser(@RequestBody @Valid UserRequest userRequest) {
		return service.addUser(userRequest);
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(@PathVariable int userId){
		return service.findUserById(userId);
	}
	
	@GetMapping(params ="/users/{email}")
	public ResponseEntity<ResponseStructure<UserResponse>> findUserByEmail(@PathVariable String email){
		return service.findUserByEmail(email);
	}
	
	@PutMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> updateUserById(@RequestBody @Valid UserRequest userRequest,@PathVariable int userId)
	{
		return service.updateUserById(userRequest, userId );
	}
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUserById(@PathVariable int userId){
		return service.deleteUserById(userId);
	}
	
	@GetMapping("/users")
	public ResponseEntity<ResponseStructure<List<UserResponse>>> findAllUsers(){
		return service.findAllUsers();
	}
	

}
