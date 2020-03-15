package com.mimacom.taskmanager.model.mapper;
import com.mimacom.taskmanager.controller.dto.UserDTO;
import com.mimacom.taskmanager.model.User;
public class UserMapper {

	private UserMapper() {}
	
	public static User toUser (UserDTO userDTO) {
		return new User(userDTO.getUsername(), userDTO.getPassword());		
	}
	
	public static UserDTO toUserDTO (User user) {
		return new UserDTO(user.getUsername(), user.getPassword());		
	}
}
