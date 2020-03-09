package com.mimacom.taskmanager;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mimacom.taskmanager.model.Task;
import com.mimacom.taskmanager.repository.TaskRepository;

@Configuration
@Slf4j
class LoadDatabase {

  private static final String PRELOADING_TEXT = "Preloading ";

@Bean
  CommandLineRunner initDatabase(TaskRepository taskRepository) {
    return args -> {
      log.info(PRELOADING_TEXT + taskRepository.save(new Task("Task1", "First Task created", Boolean.FALSE, Boolean.FALSE)));
      log.info(PRELOADING_TEXT + taskRepository.save(new Task("Task2", "Second Task created", Boolean.FALSE, Boolean.FALSE)));
      log.info(PRELOADING_TEXT + taskRepository.save(new Task("Task3", "Third task created (done task)", Boolean.TRUE, Boolean.FALSE)));
      log.info(PRELOADING_TEXT + taskRepository.save(new Task("Task4", "Forth task created (finished task)", Boolean.FALSE, Boolean.TRUE)));
      log.info(PRELOADING_TEXT + taskRepository.save(new Task("Task5", "Fifth task created (done and finished)", Boolean.TRUE, Boolean.TRUE)));
    };
  }
}
