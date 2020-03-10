package com.mimacom.taskmanager.controller.dto;


public class TaskDTO {

	private String title;
	private String description;
	private boolean done;
	private boolean finished;
	

	public TaskDTO(String title, String description, boolean done, boolean finished) {
		super();
		this.title = title;
		this.description = description;
		this.done = done;
		this.finished = finished;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	
}
