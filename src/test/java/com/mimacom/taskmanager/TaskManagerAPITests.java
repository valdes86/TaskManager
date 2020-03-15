package com.mimacom.taskmanager;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mimacom.taskmanager.controller.dto.TaskDTO;
import com.mimacom.taskmanager.controller.dto.UserDTO;
import com.mimacom.taskmanager.model.Task;
import com.mimacom.taskmanager.model.mapper.TaskMapper;
import com.mimacom.taskmanager.service.TaskService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TaskManagerAPITests {

	private static final String APPLICATION_JSON = "application/json";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	TaskService taskService;

	@Test
	public void createTaskTestNotAuthorized() throws Exception {

		Task task1 = new Task(1L, "Mock", "this is a mocked task", Boolean.TRUE, Boolean.TRUE);

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(task1);

		this.mockMvc
				.perform(post("/task/Create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.characterEncoding("utf-8"))
				.andExpect(status().isUnauthorized());
	}

	@Ignore
	@Test
	public void createTaskTestAthorized() throws Exception {
		TaskDTO taskDTO = new TaskDTO("Mock", "this is a mocked task", Boolean.TRUE, Boolean.TRUE);
		Task task = new Task(1L, "Mock", "this is a mocked task", Boolean.TRUE, Boolean.TRUE);

		String accessToken = obtainAccessToken("user", "password");

		when(taskService.createTask(taskDTO)).thenReturn(task);
		
		  mockMvc.perform(post("/task/Create")
				   .header("X-Authorization", "Bearer " + accessToken)
		           .contentType(MediaType.APPLICATION_JSON)
		           .content(toJson(taskDTO)) 
		           .accept(MediaType.APPLICATION_JSON))
		           .andExpect(status().isCreated())
					.andExpect(content().contentType("application/json"))
					.andExpect(jsonPath("$.idTask", is(1))); 

	}

	@Ignore
	@Test
	public void editTaskTest() throws Exception {
		String accessToken = obtainAccessToken("user", "password");
		
		TaskDTO task = new TaskDTO("Mock(EDITED)", "this is a mocked task");

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(task);

		//when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));

		this.mockMvc
				.perform(put("/task/Edit/{}", 1L)
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json).characterEncoding("utf-8"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is("Mock(EDITED)")));

	}

	@Ignore
	@Test
	public void retrieveTasksTest() throws Exception {
		String accessToken = obtainAccessToken("user", "password");
		List<Task> tasks = new ArrayList<>();
		Task task1 = new Task(1L, "Mock", "this is a mocked task", Boolean.TRUE, Boolean.TRUE);
		Task task2 = new Task(2L, "Mock", "this is another mocked task", Boolean.TRUE, Boolean.TRUE);

		tasks.add(task1);
		tasks.add(task2);

		when(taskService.findAll()).thenReturn(new ArrayList<>(tasks));

		this.mockMvc.perform(get("/task/retrieve")
			.header("Authorization", "Bearer " + accessToken))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON))
			.andExpect(jsonPath("$.length()", is(2)));
	}

	@Ignore
	@Test
	public void retrieveDoneTasksTest() throws Exception {
		String accessToken = obtainAccessToken("user", "password");
		List<Task> tasks = new ArrayList<>();
		Task task1 = new Task(1L, "Mock", "this is a mocked task", Boolean.TRUE, Boolean.TRUE);

		tasks.add(task1);

		when(taskService.findAll()).thenReturn(new ArrayList<>(tasks));

		this.mockMvc.perform(get("/task/retrieve/done").header("Authorization", "Bearer " + accessToken)).andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON)).andExpect(jsonPath("$.length()", is(1)));
	}

	@Ignore
	@Test
	public void removeTasksTaskExists() throws Exception {
		String accessToken = obtainAccessToken("user", "password");

		this.mockMvc.perform(delete("/remove/{}", 1)
				.header("Authorization", "Bearer " + accessToken))
			.andExpect(status().isOk());
	}

	@Ignore
	@Test
	public void removeTasksTaskNotExists() throws Exception {
		String accessToken = obtainAccessToken("user", "password");
		Long tatskId = Long.MAX_VALUE;

		when(taskService.removeTask(tatskId)).thenReturn(null);

		this.mockMvc.perform(delete("/remove/{}", tatskId).header("Authorization", "Bearer " + accessToken)).andExpect(status().isNotFound());
	}

	@Ignore
	@Test
	public void finishTasks() throws Exception {
		String accessToken = obtainAccessToken("user", "password");
		Task task1 = new Task(1L, "Mock", "this is a mocked task", Boolean.TRUE, Boolean.TRUE);

		when(taskService.setFinished(1L)).thenReturn(task1);

		this.mockMvc.perform(put("/task/finish/{}", 1L)
				.header("Authorization", "Bearer " + accessToken)
				.contentType(APPLICATION_JSON))
			.andExpect(content().contentType(APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	// realiza la peticion para autenticarse
	private String obtainAccessToken(String username, String password) throws Exception {

		UserDTO user = new UserDTO("user", "password");
		
		ResultActions result = mockMvc.perform(post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(user))
				.characterEncoding("utf-8"))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json"));

		String resultString = result.andReturn().getResponse().getContentAsString();

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(resultString).get("token").toString();
	}
	
	public static String toJson(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return (String) mapper.writeValueAsString(object);
	}
}
