package com.mimacom.taskmanager;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mimacom.taskmanager.model.Task;
import com.mimacom.taskmanager.model.User;
import com.mimacom.taskmanager.repository.TaskRepository;
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
	
	@MockBean
	TaskRepository taskRepository;

	@Test
	public void createTaskTestNotAuthorized() throws Exception{
		
		Task task1 = new Task(1L, "Mock", "this is a mocked task", Boolean.TRUE, Boolean.TRUE);

		 ObjectMapper objectMapper = new ObjectMapper();
	        String json = objectMapper.writeValueAsString(task1);
		
	        this.mockMvc.perform(post("/task/Create")
	        		.contentType(MediaType.APPLICATION_JSON)
				    .content(json)
				    .characterEncoding("utf-8"))
					.andExpect(status().isOk())
					.andExpect(content().contentType(APPLICATION_JSON))
					.andExpect(jsonPath("$.idTask", is(1)));
	}
	
	@Test
	public void createTaskTestAthorized() throws Exception {
		Task task1 = new Task(1L, "Mock", "this is a mocked task", Boolean.TRUE, Boolean.TRUE);

		 ObjectMapper objectMapper = new ObjectMapper();
	        String json = objectMapper.writeValueAsString(task1);

	    String accessToken = obtainAccessToken ("user", "password");
	    
		this.mockMvc.perform(post("/task/Create").contentType(APPLICATION_JSON)
						.header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON)
					    .content(json)
					    .characterEncoding("utf-8"))
						.andExpect(status().isOk())
						.andExpect(content().contentType(APPLICATION_JSON))
						.andExpect(jsonPath("$.idTask", is(1)));

	}

	@Test
	public void editTaskTest() throws Exception {

		Task task1 = new Task(1L, "Mock", "this is a mocked task", Boolean.TRUE, Boolean.TRUE);
		Task task2 = new Task(1L, "Mock(EDITED)", "this is a mocked task", Boolean.TRUE, Boolean.TRUE);
		
		ObjectMapper objectMapper = new ObjectMapper();
        String json= objectMapper.writeValueAsString(task2);
		
		when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
		
        this.mockMvc
				.perform(put("/task/Edit/{}", 1).contentType(APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
			    .content(json)
			    .characterEncoding("utf-8"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.idTask", is(task1.getIdTask().intValue())))
				.andExpect(jsonPath("$.title", is("Mock(EDITED)")));

	}

	@Test
	public void retrieveTasksTest() throws Exception {

		List<Task> tasks = new ArrayList<>();
		Task task1 = new Task(1L, "Mock", "this is a mocked task", Boolean.TRUE, Boolean.TRUE);
		Task task2 = new Task("Mock", "this is another mocked task");

		tasks.add(task1);
		tasks.add(task2);

		when(taskService.findAll()).thenReturn(new ArrayList<>(tasks));

		this.mockMvc.perform(get("/task/retrieve"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.length()", is(2)));
	}

	@Test
	public void retrieveDoneTasksTest() throws Exception {

		List<Task> tasks = new ArrayList<>();
		Task task1 = new Task(1L, "Mock", "this is a mocked task", Boolean.TRUE, Boolean.TRUE);
		
		tasks.add(task1);

		when(taskService.findAll()).thenReturn(new ArrayList<>(tasks));

		this.mockMvc.perform(get("/task/retrieve/done")).andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.length()", is(1)));
	}

	@Test
	public void removeTasksTaskExists() throws Exception {

		when(taskService.removeTask(1L)).thenReturn(new Task());

		this.mockMvc.perform(delete("/remove/{}", 1)).andExpect(status().isOk());
	}

	@Test
	public void removeTasksTaskNotExists() throws Exception {

		Long tatskId = Long.MAX_VALUE;

		when(taskService.removeTask(tatskId)).thenReturn(null);

		this.mockMvc.perform(delete("/remove/{}", tatskId)).andExpect(status().isNotFound());
	}

	@Test
	public void finishTasks() throws Exception {
		Task task1 = new Task(1L, "Mock", "this is a mocked task", Boolean.TRUE, Boolean.TRUE);
		
		when(taskService.setFinished(1L)).thenReturn(task1);
		
		this.mockMvc.perform(put("/task/finish/{}", 1L))
			.andExpect(content().contentType(APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	private String obtainAccessToken(String username, String password) throws Exception {
	 
		User user = new User("user", "password");
		
		ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);
        
	    ResultActions result 
	      = mockMvc.perform(post("/authenticate")
	    		  .contentType(MediaType.APPLICATION_JSON)
				  .content(json)
				  .characterEncoding("utf-8"))
				  .andExpect(status().isOk())
				  .andExpect(content().contentType(APPLICATION_JSON));
	 
	    String resultString = result.andReturn().getResponse().getContentAsString();
	 
	    JacksonJsonParser jsonParser = new JacksonJsonParser();
	    return jsonParser.parseMap(resultString).get("access_token").toString();
	}
}
