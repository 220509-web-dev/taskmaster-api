package com.revature.taskmaster.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.taskmaster.user.dtos.UserRequestPayload;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserRegistrationIntegrationTest {

    private final MockMvc mockMvc;
    private final ObjectMapper jsonMapper;
    private final UserRepository userRepo;

    private final String PATH = "/users";

    private final String CONTENT_TYPE = "application/json";

    @Autowired
    public UserRegistrationIntegrationTest(MockMvc mockMvc, ObjectMapper jsonMapper, UserRepository userRepo) {
        this.mockMvc = mockMvc;
        this.jsonMapper = jsonMapper;
        this.userRepo = userRepo;
    }

    @Test
    void test_registerUser_returns201_givenValidUserRequestPayload() throws Exception {

        UserRequestPayload newUserRequest = new UserRequestPayload();
        newUserRequest.setFirstName("Valid");
        newUserRequest.setLastName("User");
        newUserRequest.setEmail("valid.user@revature.com");
        newUserRequest.setUsername("valid-user");
        newUserRequest.setPassword("Revature1!");

        String requestPayload = jsonMapper.writeValueAsString(newUserRequest);

        mockMvc.perform(post(PATH).contentType(CONTENT_TYPE).content(requestPayload))
               .andExpect(status().isCreated())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.resourceId").exists())
               .andExpect(jsonPath("$.resourceId").isString())
               .andReturn();

        assertTrue(userRepo.existsByUsername("valid-user"));

    }

}
