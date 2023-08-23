package com.example;

import com.example.dto.TaskDTO;
import com.example.enums.TaskStatus;
import com.example.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private TaskService taskService;

	@Test
	void contextLoads() {
		TaskDTO taskDTO = new TaskDTO();
		taskDTO.setTitle("Task1");
		taskDTO.setStatus(TaskStatus.ACTIVE);
		taskDTO.setContent("Content1");
		taskService.createTask(taskDTO);
	}


	@Test
	void getAll(){
		taskService.taskList();
	}



}
