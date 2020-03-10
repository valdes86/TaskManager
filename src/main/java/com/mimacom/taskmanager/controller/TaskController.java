package com.mimacom.taskmanager.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mimacom.taskmanager.controller.dto.TaskDTO;
import com.mimacom.taskmanager.model.Task;
import com.mimacom.taskmanager.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TaskService taskService;

	@PostMapping("/create")
	public Task createTask(TaskDTO task) {
		logger.info("Creating Task");
		return taskService.createTask(task);
	}

	@PutMapping("/update/{id}")
	public Task updateTask(TaskDTO task, @PathVariable Long id) {
		logger.info("Updating task: {}", id);
		return taskService.updateTask(task, id);
	}

	@DeleteMapping("/remove/{id}")
	public void removeTask(@PathVariable Long id) {
		logger.info("Removing task: {}", id);
		taskService.removeTask(id);
	}

	@GetMapping("/retrieve")
	public List<Task> retrieveAllTasks() {
		List<Task> tasks = taskService.findAll();
		logger.info("Retrieving {} tasks", tasks.size());
		return tasks;
	}

	@GetMapping("/retrieve/done")
	public List<Task> retrieveAllDone() {
		logger.info("Retrieving all tasks set as done");
		return taskService.findByDone();
	}

	@PutMapping("/finish/{id}")
	public Task markAsFinished(@PathVariable Long id) {
		logger.info("Setting task with id:{} as finished", id);
		return taskService.setFinished(id);
	}
}
