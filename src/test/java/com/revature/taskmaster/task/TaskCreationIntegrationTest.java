package com.revature.taskmaster.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
public class TaskCreationIntegrationTest {

    private final MockMvc mockMvc;
    private final ObjectMapper jsonMapper;
    private final TaskRepository taskRepo;
    private final String PATH = "/tasks";
    private final String CONTENT_TYPE = "application/json";

    @Autowired
    public TaskCreationIntegrationTest(MockMvc mockMvc, ObjectMapper jsonMapper, TaskRepository taskRepo) {
        this.mockMvc = mockMvc;
        this.jsonMapper = jsonMapper;
        this.taskRepo = taskRepo;
    }

    // TODO implement positive and negative test scenarios for task creation

}
