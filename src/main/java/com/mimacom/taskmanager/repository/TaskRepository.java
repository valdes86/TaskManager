package com.mimacom.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mimacom.taskmanager.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

}
