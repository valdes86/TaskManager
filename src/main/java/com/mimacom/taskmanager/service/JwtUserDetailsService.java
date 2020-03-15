package com.mimacom.taskmanager.service;

import java.util.ArrayList;
import java.util.Arrays;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mimacom.taskmanager.controller.dto.UserDTO;
import com.mimacom.taskmanager.model.Task;
import com.mimacom.taskmanager.model.User;
import com.mimacom.taskmanager.model.mapper.TaskMapper;
import com.mimacom.taskmanager.model.mapper.UserMapper;
import com.mimacom.taskmanager.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());

	}

	public User registerUser(UserDTO userDTO) {
		User user = UserMapper.toUser(userDTO);
		user.setRoles(Arrays.asList("ROLE_USER"));
		return userRepository.save(user);
	}
}
