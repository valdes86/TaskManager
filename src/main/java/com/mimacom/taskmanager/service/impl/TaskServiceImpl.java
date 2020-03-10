package com.mimacom.taskmanager.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mimacom.taskmanager.controller.dto.TaskDTO;
import com.mimacom.taskmanager.exception.TaskNotFoundException;
import com.mimacom.taskmanager.model.Task;
import com.mimacom.taskmanager.model.mapper.TaskMapper;
import com.mimacom.taskmanager.repository.TaskRepository;
import com.mimacom.taskmanager.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Task createTask(TaskDTO taskDto) {
		Task task = TaskMapper.toTask(taskDto);
		return taskRepository.save(task);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Task updateTask(TaskDTO task, Long taskId) {
		Task originalTask = taskRepository.findById(taskId)
				.orElseThrow(() -> new TaskNotFoundException("Task not found"));

		originalTask.setTitle(task.getTitle());
		originalTask.setDescription(task.getDescription());
		originalTask.setDone(task.isDone());
		originalTask.setFinished(task.isFinished());

		taskRepository.save(originalTask);

		return originalTask;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void removeTask(Long taskId) {
		Task task = taskRepository.findById(taskId)
				.orElseThrow(TaskNotFoundException::new);

		taskRepository.delete(task);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public List<Task> findAll() {
		return taskRepository.findAll();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public List<Task> findByDone() {

		return findAll().stream()
				.filter(Task::getDone)
				.collect(Collectors.toList());
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Task setFinished(Long id) {
		Task task = taskRepository.findById(id)
				.orElseThrow(TaskNotFoundException::new);
		
		task.setFinished(Boolean.TRUE);
		taskRepository.save(task);
		return task;
	}

}
