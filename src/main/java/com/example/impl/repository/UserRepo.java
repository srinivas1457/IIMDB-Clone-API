package com.example.impl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import com.example.impl.dto.UserResponse;
import com.example.impl.entity.User;
import com.example.impl.util.ResponseStructure;

public interface UserRepo extends JpaRepository<User, Integer>{

	public User findUserByEmail(String email);
	
}
