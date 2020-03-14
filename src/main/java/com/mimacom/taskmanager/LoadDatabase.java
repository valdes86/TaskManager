package com.mimacom.taskmanager;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mimacom.taskmanager.model.Task;
import com.mimacom.taskmanager.model.User;
import com.mimacom.taskmanager.repository.TaskRepository;
import com.mimacom.taskmanager.repository.UserRepository;

@Configuration
@Slf4j
class LoadDatabase {
	
	@Bean
	PasswordEncoder getEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
  private static final String PRELOADING_TEXT = "Preloading ";
 
  @Bean
  CommandLineRunner initDatabase(TaskRepository taskRepository, UserRepository userRepository) {
	 
    return args -> {
      
      log.info(PRELOADING_TEXT + taskRepository.save(new Task("Task1", "First Task created", Boolean.FALSE, Boolean.FALSE)));
      log.info(PRELOADING_TEXT + taskRepository.save(new Task("Task2", "Second Task created", Boolean.FALSE, Boolean.FALSE)));
      log.info(PRELOADING_TEXT + taskRepository.save(new Task("Task3", "Third task created (done task)", Boolean.TRUE, Boolean.FALSE)));
      log.info(PRELOADING_TEXT + taskRepository.save(new Task("Task4", "Forth task created (finished task)", Boolean.FALSE, Boolean.TRUE)));
      log.info(PRELOADING_TEXT + taskRepository.save(new Task("Task5", "Fifth task created (done and finished)", Boolean.TRUE, Boolean.TRUE)));
    
      log.info(PRELOADING_TEXT + userRepository.save(User.builder()
              .username("user")
              .password(getEncoder().encode("password"))
              .roles(Arrays.asList( "ROLE_USER"))
              .build()));
      log.info(PRELOADING_TEXT + userRepository.save(User.builder()
              .username("admin")
              .password(getEncoder().encode("password"))
              .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
              .build()));
    };
  }
}
