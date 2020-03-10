package com.mimacom.taskmanager.service;

import java.util.List;

import com.mimacom.taskmanager.controller.dto.TaskDTO;
import com.mimacom.taskmanager.model.Task;

public interface TaskService {

	/**
	 * Saves a new task into BD 
	 * 
	 * @param task The task
	 * @return the task if saved, null otherwise
	 */
	Task createTask(TaskDTO task);

	/**
	 * Updates a task 
	 * 
	 * @param task the task to save
	 * @param taskId the id of the task to update
	 * @return
	 */
	Task updateTask(TaskDTO task, Long taskId);
	
	/**
	 * Deletes a task
	 * 
	 * @param id The id of the task to remove
	 */
	void removeTask(Long id);
	
	/**
	 * Finds all the saved tasks
	 * 
	 * @return List of saved tasks
	 */
	List<Task> findAll();
	
	/**
	 * Finds all the task with done status set to true
	 * 
	 * @return List of done tasks
	 */
	List<Task> findByDone();
	
	/**
	 * Given a task, sets it as finished
	 * 
	 * @param id The task id
	 * @return Task that has been set as finished
	 */
	Task setFinished(Long id);
	
}
