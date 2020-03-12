package com.mimacom.taskmanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Entity
@Data
@Table(name = "TASK")
@ApiModel("Task entity")
public class Task {

	@Id
	@GeneratedValue
	@Column(name = "ID_TASK")
	@ApiModelProperty("Task id. value")
	private Long idTask;
	@Column(name = "TITLE")
	@ApiModelProperty("Title of the task")
	@NotNull
	private String title;
	@ApiModelProperty("Description of the task")
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "DONE", columnDefinition = "boolean default false")
	@ApiModelProperty("Flag for DONE property, default: false")
	private Boolean done;
	@Column(name = "FINISHED", columnDefinition = "boolean default false")
	@ApiModelProperty("Flag for FINISHED property, default: false")
	private Boolean finished;

	public Task() {
		super();
	}

	public Task(String title, String description, Boolean done, Boolean finished) {
		super();
		this.title = title;
		this.description = description;
		this.done = done;
		this.finished = finished;
	}
	
	//Getters, setters, hascode and equals constructed by Lombock
}
