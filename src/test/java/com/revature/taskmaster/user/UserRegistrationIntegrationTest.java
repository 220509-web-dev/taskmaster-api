package com.revature.taskmaster.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.taskmaster.user.dtos.UserRequestPayload;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    @DirtiesContext
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

        assertTrue(userRepo.existsByUsername(newUserRequest.getUsername()));

    }

    @Test
    void test_registerUser_returns400_givenInvalidUserRequestPayload() throws Exception {

        UserRequestPayload newUserRequest = new UserRequestPayload();
        newUserRequest.setFirstName("");
        newUserRequest.setEmail("invalid-email");
        newUserRequest.setUsername("sa");
        newUserRequest.setPassword("invalid");

        String requestPayload = jsonMapper.writeValueAsString(newUserRequest);

        mockMvc.perform(post(PATH).contentType(CONTENT_TYPE).content(requestPayload))
               .andExpect(status().isBadRequest())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(5)))
               .andExpect(jsonPath("$.messages", hasItem("At least one character must be provided for a first name")))
               .andExpect(jsonPath("$.messages", hasItem("A last name value is expected in the request payload for user creation")))
               .andExpect(jsonPath("$.messages", hasItem("A valid email must be provided")))
               .andExpect(jsonPath("$.messages", hasItem("Usernames must contain at least three characters")))
               .andExpect(jsonPath("$.messages", hasItem("Passwords must be eight characters long and contain at least one of the following: lowercase letter, uppercase letter, number, special character")))
               .andReturn();

        assertFalse(userRepo.existsByUsername(newUserRequest.getUsername()));

    }

    @Test
    void test_registerUser_returns409_givenValidUserRequest_withTakenUsername() throws Exception {

        UserRequestPayload newUserRequest = new UserRequestPayload();
        newUserRequest.setFirstName("Valid");
        newUserRequest.setLastName("User");
        newUserRequest.setEmail("valid.user@revature.com");
        newUserRequest.setUsername("tester");
        newUserRequest.setPassword("Revature1!");

        String requestPayload = jsonMapper.writeValueAsString(newUserRequest);

        mockMvc.perform(post(PATH).contentType(CONTENT_TYPE).content(requestPayload))
               .andExpect(status().isConflict())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("There is already a user with that username!")))
               .andReturn();

        assertTrue(userRepo.existsByUsername(newUserRequest.getUsername()));

    }

    @Test
    void test_registerUser_returns409_givenValidUserRequest_withTakenEmail() throws Exception {

        UserRequestPayload newUserRequest = new UserRequestPayload();
        newUserRequest.setFirstName("Valid");
        newUserRequest.setLastName("User");
        newUserRequest.setEmail("tester@revature.com");
        newUserRequest.setUsername("available-username");
        newUserRequest.setPassword("Revature1!");

        String requestPayload = jsonMapper.writeValueAsString(newUserRequest);

        mockMvc.perform(post(PATH).contentType(CONTENT_TYPE).content(requestPayload))
               .andExpect(status().isConflict())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("There is already a user with that email!")))
               .andReturn();

        assertFalse(userRepo.existsByUsername(newUserRequest.getUsername()));

    }

}
