package com.mimacom.taskmanager.model.mapper;

import com.mimacom.taskmanager.controller.dto.TaskDTO;
import com.mimacom.taskmanager.model.Task;

public class TaskMapper {

	public static TaskDTO toTaskDTO(Task task) {
		return new TaskDTO(task.getTitle(), task.getDescription(), task.getDone(), task.getFinished());		
	}
	public static Task toTask(TaskDTO task) {
		return new Task(task.getTitle(), task.getDescription(), task.isDone(), task.isFinished());		
	}
}
