package com.example.impl.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.impl.dto.UserRequest;
import com.example.impl.dto.UserResponse;
import com.example.impl.util.ResponseStructure;

public interface UserService {
	public ResponseEntity<ResponseStructure<UserResponse>> addUser(UserRequest userRequest);
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(int userId);
	public ResponseEntity<ResponseStructure<UserResponse>> findUserByEmail(String email);
	public ResponseEntity<ResponseStructure<UserResponse>> updateUserById(UserRequest request,int userId);
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUserById(int userId);
	public ResponseEntity<ResponseStructure<List<UserResponse>>> findAllUsers();
	

}
