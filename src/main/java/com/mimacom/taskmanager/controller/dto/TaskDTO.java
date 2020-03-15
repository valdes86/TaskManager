package com.mimacom.taskmanager.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

	private String title;
	private String description;
	private boolean done;
	private boolean finished;
	
	public TaskDTO (String title, String description) {
		this.title = title;
		this.description = description;
	}
}
