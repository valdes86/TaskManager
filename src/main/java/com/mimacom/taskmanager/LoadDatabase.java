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

			// TASKS
			log.info(PRELOADING_TEXT + taskRepository.save(Task.builder().title("Task1").description("First Task")
					.done(Boolean.FALSE).finished(Boolean.FALSE).build()));
			log.info(PRELOADING_TEXT + taskRepository.save(Task.builder().title("Task2").description("Second Task ")
					.done(Boolean.FALSE).finished(Boolean.FALSE).build()));
			log.info(PRELOADING_TEXT + taskRepository.save(Task.builder().title("Task3")
					.description("Third task created (done task)").done(Boolean.TRUE).finished(Boolean.FALSE).build()));
			log.info(PRELOADING_TEXT + taskRepository
					.save(Task.builder().title("Task4").description("Fourth task created (finished task)")
							.done(Boolean.FALSE).finished(Boolean.TRUE).build()));
			log.info(PRELOADING_TEXT + taskRepository
					.save(Task.builder().title("Task5").description("Fifth task created (done and finished)")
							.done(Boolean.TRUE).finished(Boolean.TRUE).build()));

			// USERS
			log.info(PRELOADING_TEXT + userRepository.save(User.builder().username("user")
					.password(getEncoder().encode("password")).roles(Arrays.asList("ROLE_USER")).build()));
			log.info(PRELOADING_TEXT
					+ userRepository.save(User.builder().username("admin").password(getEncoder().encode("password"))
							.roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN")).build()));
		};
	}
}
