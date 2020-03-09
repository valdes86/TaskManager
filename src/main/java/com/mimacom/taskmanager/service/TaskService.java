package com.mimacom.taskmanager.service;

import java.util.List;

import com.mimacom.taskmanager.controller.dto.TaskDTO;
import com.mimacom.taskmanager.model.Task;

public interface TaskService {

	Task createTask(TaskDTO task);
	Task updateTask(TaskDTO task, Long taskId);
	void removeTask(Long id);
	List<Task> findAll();
	List<Task> findByDone();
	Task setFinished(Long id);
	
}
