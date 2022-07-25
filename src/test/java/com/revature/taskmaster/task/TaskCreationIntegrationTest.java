package com.revature.taskmaster.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.taskmaster.common.dtos.ResourceCreationResponse;
import com.revature.taskmaster.task.dtos.TaskRequestPayload;
import com.revature.taskmaster.test_utils.MockTokenFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class TaskCreationIntegrationTest {

    private final MockMvc mockMvc;
    private final MockTokenFactory mockTokenFactory;
    private final TaskRepository taskRepo;
    private final ObjectMapper jsonMapper;
    private final String PATH = "/tasks";
    private final String CONTENT_TYPE = "application/json";

    @Autowired
    public TaskCreationIntegrationTest(MockMvc mockMvc, MockTokenFactory mockTokenFactory, TaskRepository taskRepo, ObjectMapper jsonMapper) {
        this.mockMvc = mockMvc;
        this.mockTokenFactory = mockTokenFactory;
        this.taskRepo = taskRepo;
        this.jsonMapper = jsonMapper;
    }

    @Test
    void test_createNewTask_returns201_givenValidTaskRequestPayload_noAssignee() throws Exception {

        TaskRequestPayload newTaskRequest = new TaskRequestPayload();
        newTaskRequest.setTitle("Valid Task Title");
        newTaskRequest.setDescription("This is a valid task description.");
        newTaskRequest.setPriority(4);
        newTaskRequest.setPointValue(5);
        newTaskRequest.setDueDate(LocalDate.now().plusDays(14));
        newTaskRequest.setLabels(Arrays.asList("ready to start", "valid", "test"));

        MvcResult result = mockMvc.perform(
                                    post(PATH)
                                        .header("Authorization", mockTokenFactory.getDevToken())
                                        .contentType(CONTENT_TYPE)
                                        .content(jsonMapper.writeValueAsString(newTaskRequest)))
                                  .andExpect(status().isCreated())
                                  .andExpect(header().string("content-type", CONTENT_TYPE))
                                  .andExpect(header().string("Access-Control-Allow-Origin", "*"))
                                  .andExpect(header().string("Access-Control-Allow-Methods", "*"))
                                  .andExpect(header().string("Access-Control-Allow-Headers", "*"))
                                  .andExpect(jsonPath("$.resourceId").exists())
                                  .andExpect(jsonPath("$.resourceId").isString())
                                  .andReturn();

        String newTaskResourceId = jsonMapper.readValue(result.getResponse().getContentAsString(), ResourceCreationResponse.class).getResourceId();

        assertTrue(taskRepo.existsById(newTaskResourceId));

    }

    @Test
    void test_createNewTask_returns201_givenValidTaskRequestPayload_withAssignees() throws Exception {

        TaskRequestPayload newTaskRequest = new TaskRequestPayload();
        newTaskRequest.setTitle("Valid Task Title");
        newTaskRequest.setDescription("This is a valid task description.");
        newTaskRequest.setPriority(4);
        newTaskRequest.setPointValue(5);
        newTaskRequest.setDueDate(LocalDate.now().plusDays(14));
        newTaskRequest.setLabels(Arrays.asList("ready to start", "valid", "test"));
        newTaskRequest.setAssigneeIds(Arrays.asList("dev-user-id", "tester-user-id"));

        MvcResult result = mockMvc.perform(
                                    post(PATH)
                                        .header("Authorization", mockTokenFactory.getDevToken())
                                        .contentType(CONTENT_TYPE)
                                        .content(jsonMapper.writeValueAsString(newTaskRequest)))
                                  .andExpect(status().isCreated())
                                  .andExpect(header().string("content-type", CONTENT_TYPE))
                                  .andExpect(header().string("Access-Control-Allow-Origin", "*"))
                                  .andExpect(header().string("Access-Control-Allow-Methods", "*"))
                                  .andExpect(header().string("Access-Control-Allow-Headers", "*"))
                                  .andExpect(jsonPath("$.resourceId").exists())
                                  .andExpect(jsonPath("$.resourceId").isString())
                                  .andReturn();

        String newTaskResourceId = jsonMapper.readValue(result.getResponse().getContentAsString(), ResourceCreationResponse.class).getResourceId();

        assertTrue(taskRepo.existsById(newTaskResourceId));

    }

    @Test
    void test_createNewTask_returns401_givenValidTaskRequestPayload_butUnknownCreator() throws Exception {

        TaskRequestPayload newTaskRequest = new TaskRequestPayload();
        newTaskRequest.setTitle("Valid Task Title");
        newTaskRequest.setDescription("This is a valid task description.");
        newTaskRequest.setPriority(4);
        newTaskRequest.setPointValue(5);
        newTaskRequest.setDueDate(LocalDate.now().plusDays(14));
        newTaskRequest.setLabels(Arrays.asList("ready to start", "valid", "test"));
        newTaskRequest.setAssigneeIds(Arrays.asList("dev-user-id", "tester-user-id"));

        mockMvc.perform(
                    post(PATH)
                        .header("Authorization", mockTokenFactory.getUnknownUserToken())
                        .contentType(CONTENT_TYPE)
                        .content(jsonMapper.writeValueAsString(newTaskRequest)))
               .andExpect(status().isUnauthorized())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andReturn();

    }

    @Test
    void test_createNewTask_returns422_givenValidTaskRequestPayload_butUnknownAssignee() throws Exception {

        TaskRequestPayload newTaskRequest = new TaskRequestPayload();
        newTaskRequest.setTitle("Valid Task Title");
        newTaskRequest.setDescription("This is a valid task description.");
        newTaskRequest.setPriority(4);
        newTaskRequest.setPointValue(5);
        newTaskRequest.setDueDate(LocalDate.now().plusDays(14));
        newTaskRequest.setLabels(Arrays.asList("ready to start", "valid", "test"));
        newTaskRequest.setAssigneeIds(Arrays.asList("dev-user-id", "unknown-user-id"));

        mockMvc.perform(
                   post(PATH)
                       .header("Authorization", mockTokenFactory.getDevToken())
                       .contentType(CONTENT_TYPE)
                       .content(jsonMapper.writeValueAsString(newTaskRequest)))
               .andExpect(status().isUnprocessableEntity())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andReturn();

    }

    @Test
    void test_createNewTask_returns400_givenInvalidTaskRequestPayload() throws Exception {

        TaskRequestPayload newTaskRequest = new TaskRequestPayload();
        newTaskRequest.setId("task-id-not-expected-on-creation");
        newTaskRequest.setTitle("");
        newTaskRequest.setPriority(5);
        newTaskRequest.setPointValue(101);
        newTaskRequest.setDueDate(LocalDate.now().minusDays(14));
        newTaskRequest.setState("DONE");


        mockMvc.perform(
                   post(PATH)
                       .header("Authorization", mockTokenFactory.getDevToken())
                       .contentType(CONTENT_TYPE)
                       .content(jsonMapper.writeValueAsString(newTaskRequest)))
               .andExpect(status().isBadRequest())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(8)))
               .andExpect(jsonPath("$.messages", hasItem("No id is expected in the payload for creation requests")))
               .andExpect(jsonPath("$.messages", hasItem("Task titles must not be empty and no more than 50 characters long")))
               .andExpect(jsonPath("$.messages", hasItem("A description value is expected in the request payload for task creation")))
               .andExpect(jsonPath("$.messages", hasItem("The provided priority level is unknown or invalid")))
               .andExpect(jsonPath("$.messages", hasItem("Task point values must be in the inclusive range: 1 - 100")))
               .andExpect(jsonPath("$.messages", hasItem("Task due dates must be a date in the future")))
               .andExpect(jsonPath("$.messages", hasItem("No task state is expected in the request payload for task creation - defaults to UNASSIGNED")))
               .andExpect(jsonPath("$.messages", hasItem("At least one label string is expected in the request payload for task creation")))
               .andReturn();

    }
    @Test
    void test_createNewTask_returns400_givenInvalidJsonPayload() throws Exception {

        String requestPayload = "{invalid}";

        mockMvc.perform(post(PATH)
                           .contentType(CONTENT_TYPE)
                           .content(requestPayload))
               .andExpect(status().isBadRequest())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("Unexpected character found in request payload")))
               .andReturn();

    }


    @Test
    void test_createNewTask_returns415_givenUnsupportedMediaTypePayload() throws Exception {

        String requestPayload = "<TaskRequestPayload>" +
                                    "<title>Task Title</title>" +
                                    "<description>Task Description</description>" +
                                    "<priority>4</priority>" +
                                    "<pointValue>8</pointValue>" +
                                    "<dueDate>" + LocalDate.now().plusDays(14) + "</dueDate>" +
                                    "<state>UNASSIGNED</state>" +
                                    "<title>Task Title</title>" +
                                "</TaskRequestPayload>";

        mockMvc.perform(post(PATH).contentType("application/xml").content(requestPayload))
               .andExpect(status().isUnsupportedMediaType())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("An unsupported media type was provided to the endpoint")))
               .andReturn();

    }

}
