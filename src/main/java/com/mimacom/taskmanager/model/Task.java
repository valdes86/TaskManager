package com.mimacom.taskmanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "TASK")
public class Task {

	@Id
	@Column(name = "ID_TASK")
	private Long idTask;
	@Column(name = "TITLE")
	private String title;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "DONE")
	private Boolean done;
	@Column(name = "FINISHED")
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
}
