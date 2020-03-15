package com.mimacom.taskmanager.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

	//Getters, setters, hascode and equals constructed by Lombock
}
