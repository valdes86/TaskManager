package com.mimacom.taskmanager.model.mapper;

import com.mimacom.taskmanager.controller.dto.TaskDTO;
import com.mimacom.taskmanager.model.Task;

public class TaskMapper {

	/**
	 * Maps a <code>Task</code> object as <code>TaskDTO</code> object
	 * @param task The Task object
	 * @return the TaskDTO object
	 */
	public static TaskDTO toTaskDTO(Task task) {
		return new TaskDTO(task.getTitle(), task.getDescription(), task.getDone(), task.getFinished());		
	}

	/**
	 * Maps a <code>TaskDTO</code> object as <code>Task</code> object
	 * @param task The TaskDTO object
	 * @return the Task object
	 */
	public static Task toTask(TaskDTO task) {
		return new Task(task.getTitle(), task.getDescription(), task.isDone(), task.isFinished());		
	}
}
