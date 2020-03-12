package com.mimacom.taskmanager.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mimacom.taskmanager.controller.dto.TaskDTO;
import com.mimacom.taskmanager.model.Task;
import com.mimacom.taskmanager.service.TaskService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/task")
@Api(value="Task")
public class TaskController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TaskService taskService;

	@PostMapping("/create")
	@ApiOperation(value="Creates a new task", response=Task.class)
	@ApiResponses(value= {
			@ApiResponse(code=200, message="task creation success"), 
			@ApiResponse(code=404, message="Task not found"),
			@ApiResponse(code=500, message="Server error")
	})
	public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDto) {
		logger.info("Creating Task");
		Task task = taskService.createTask(taskDto);
		return task == null? ResponseEntity.notFound().build() : ResponseEntity.ok(task);	
	}

	@PutMapping("/update/{id}")
	@ApiOperation(value="Updates a task", response=Task.class)
	@ApiResponses(value= {
			@ApiResponse(code=200, message="Task update succes"),
			@ApiResponse(code=404, message="Task not found"),
			@ApiResponse(code=500, message="Server error")
	})
	public ResponseEntity<Task> updateTask(@RequestBody TaskDTO taskDto, @PathVariable Long id) {
		logger.info("Updating task: {}", id);
		Task task = taskService.updateTask(taskDto, id);
		return task == null? ResponseEntity.notFound().build() : ResponseEntity.ok(task);

	}

	@DeleteMapping("/remove/{id}")
	@ApiOperation(value="Removes a task")
	@ApiResponses(value= {
			@ApiResponse(code=200, message="Task remove success"),
			@ApiResponse(code=404, message="Task not found"),
			@ApiResponse(code=500, message="Server error")
	})
	public ResponseEntity<Task> removeTask(@PathVariable Long id) {
		logger.info("Removing task: {}", id);
		Task task = taskService.removeTask(id);
		return task == null? ResponseEntity.notFound().build() : ResponseEntity.ok(task);	
	}

	@GetMapping("/retrieve")
	@ApiOperation(value="Retrieves all tasks", response=Task.class, responseContainer="List")
	@ApiResponses(value= {
			@ApiResponse(code=200, message="All tasks retrieved succesfully"),
			@ApiResponse(code=204, message="No content retrieved"),
			@ApiResponse(code=500, message="Server error")
	})
	public ResponseEntity<List<Task>> retrieveAllTasks() {
		logger.info("Retrieving all tasks");
		List<Task> tasks = taskService.findAll();
		logger.info("Retrieving {} tasks", tasks.size());
		return tasks.isEmpty()?ResponseEntity.noContent().build():ResponseEntity.ok(tasks);
	}

	@GetMapping("/retrieve/done")
	@ApiOperation(value="Retrieves all tasks set as done", response=Task.class, responseContainer="List")
	@ApiResponses(value= {
			@ApiResponse(code=200, message="All tasks set as done retrieved succesfully"),
			@ApiResponse(code=204, message="No content retrieved"),
			@ApiResponse(code=500, message="Server error")
	})
	public ResponseEntity<List<Task>> retrieveAllDone() {
		logger.info("Retrieving all tasks set as done");
		List<Task> tasks = taskService.findByDone();
		logger.info("Retrieving {} tasks set as done", tasks.size());
		return tasks.isEmpty()?ResponseEntity.noContent().build():ResponseEntity.ok(tasks);

	}

	@PutMapping("/finish/{id}")
	@ApiOperation(value="Sets a task as finished", response=Task.class)
	@ApiResponses(value= {
			@ApiResponse(code=200, message="Task set as finished"),
			@ApiResponse(code=404, message="Task not found"),
			@ApiResponse(code=500, message="Server error")
	})
	public ResponseEntity<Task> markAsFinished(@PathVariable Long id) {
		logger.info("Setting task with id:{} as finished", id);
		Task task = taskService.setFinished(id);
		return task == null? ResponseEntity.notFound().build() : ResponseEntity.ok(task);
				
	}
}
