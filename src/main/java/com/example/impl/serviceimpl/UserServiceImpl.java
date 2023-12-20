package com.example.impl.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.impl.dto.UserRequest;
import com.example.impl.dto.UserResponse;
import com.example.impl.entity.User;
import com.example.impl.exception.UserNotFoundByEmailException;
import com.example.impl.exception.UserNotFoundByIdException;
import com.example.impl.exception.UsersDataNotPresent;
import com.example.impl.repository.UserRepo;
import com.example.impl.service.UserService;
import com.example.impl.util.ResponseStructure;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo repo;

	private User convertUser(UserRequest userRequest) {
		User user = new User();
		user.setUserName(userRequest.getUserName());
		user.setEmail(userRequest.getEmail());
		user.setPassword(userRequest.getPassword());
		user.setUserDOB(userRequest.getUserDOB());
		return user;
	}

	private UserResponse convertUserResponse(User user) {
		UserResponse userResponse = new UserResponse();
		userResponse.setUserId(user.getUserId());
		userResponse.setUserName(user.getUserName());
		userResponse.setEmail(user.getEmail());
		userResponse.setUserDOB(user.getUserDOB());
		return userResponse;
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> addUser(UserRequest userRequest) {
		User user = convertUser(userRequest);

		User user2 = repo.save(user);

		UserResponse userResponse = convertUserResponse(user2);

		ResponseStructure<UserResponse> structure = new ResponseStructure<UserResponse>();
		structure.setStatusCode(HttpStatus.CREATED.value());
		structure.setMessage("Student Data Inserted Successfully !");
		structure.setData(userResponse);

		return new ResponseEntity<ResponseStructure<UserResponse>>(structure, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(int userId) {
		Optional<User> optional = repo.findById(userId);
		if (optional.isPresent()) {
			User user = optional.get();

			UserResponse response = convertUserResponse(user);

			ResponseStructure<UserResponse> structure = new ResponseStructure<>();
			structure.setStatusCode(HttpStatus.FOUND.value());
			structure.setMessage("User Data Found Successfully");
			structure.setData(response);

			return new ResponseEntity<ResponseStructure<UserResponse>>(structure, HttpStatus.FOUND);

		} else {
			throw new UserNotFoundByIdException("Id NotFound to Fetch the Data !!");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> findUserByEmail(String email) {
		User user = repo.findUserByEmail(email);
		if (user != null) {

			UserResponse response = convertUserResponse(user);

			ResponseStructure<UserResponse> structure = new ResponseStructure<>();
			structure.setStatusCode(HttpStatus.FOUND.value());
			structure.setMessage("User Data Found Successfully");
			structure.setData(response);

			return new ResponseEntity<ResponseStructure<UserResponse>>(structure, HttpStatus.FOUND);

		} else {
			throw new UserNotFoundByEmailException("Email NotFound to Fetch the Data !!");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> updateUserById(UserRequest userRequest, int userId) {

		Optional<User> optional = repo.findById(userId);
		if (optional.isPresent()) {
			User existingUser = optional.get();

			User user = convertUser(userRequest);
			user.setUserId(existingUser.getUserId());

			User user2 = repo.save(user);

			UserResponse response = convertUserResponse(user2);

			ResponseStructure<UserResponse> structure = new ResponseStructure<>();
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setMessage("User Data Updated Successfully");
			structure.setData(response);

			return new ResponseEntity<ResponseStructure<UserResponse>>(structure, HttpStatus.OK);
		} else {
			throw new UserNotFoundByIdException("ID NotFound to Update the Data !!");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUserById(int userId) {
		Optional<User> optional = repo.findById(userId);
		if (optional.isPresent()) {
			User user = optional.get();
			repo.delete(user);

			UserResponse response = convertUserResponse(user);

			ResponseStructure<UserResponse> structure = new ResponseStructure<>();
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setMessage("User Data Deleted Successfully !!");
			structure.setData(response);

			return new ResponseEntity<ResponseStructure<UserResponse>>(structure, HttpStatus.OK);

		} else {
			throw new UserNotFoundByIdException("Id NotFound to Delete the Data !!");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<List<UserResponse>>> findAllUsers() {

		List<User> userList = repo.findAll();
		if (!userList.isEmpty()) {
			List<UserResponse> list = new ArrayList<>();
			for (User user : userList) {
				UserResponse response = convertUserResponse(user);
				list.add(response);
			}

			ResponseStructure<List<UserResponse>> structure = new ResponseStructure<>();
			structure.setStatusCode(HttpStatus.FOUND.value());
			structure.setMessage("User Records Found");
			structure.setData(list);

			return new ResponseEntity<ResponseStructure<List<UserResponse>>>(structure, HttpStatus.FOUND);
		} else {
			throw new UsersDataNotPresent("No Users Data Present!!");
		}
	}

}
