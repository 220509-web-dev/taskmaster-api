package com.revature.taskmaster.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.taskmaster.test_utils.MockTokenGenerator;
import com.revature.taskmaster.user.dtos.UserRequestPayload;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserUpdateIntegrationTest {

    private final MockMvc mockMvc;
    private final MockTokenGenerator mockTokenGenerator;
    private final UserRepository userRepo;
    private final ObjectMapper jsonMapper;
    private final String PATH = "/users";
    private final String CONTENT_TYPE = "application/json";

    @Autowired
    public UserUpdateIntegrationTest(MockMvc mockMvc, MockTokenGenerator mockTokenGenerator, UserRepository userRepo, ObjectMapper jsonMapper) {
        this.mockMvc = mockMvc;
        this.mockTokenGenerator = mockTokenGenerator;
        this.userRepo = userRepo;
        this.jsonMapper = jsonMapper;
    }

    @Test
    @DirtiesContext
    void test_userUpdate_returns204_givenValidUserRequestPayload_usingAdminToken() throws Exception {

        UserRequestPayload updatedUserRequest = new UserRequestPayload();
        updatedUserRequest.setId("manager-user-id");
        updatedUserRequest.setFirstName("Updated");
        updatedUserRequest.setLastName("Updated");
        updatedUserRequest.setEmail("updated@revature.com");
        updatedUserRequest.setUsername("updated");
        updatedUserRequest.setPassword("Upd4ted!");
        updatedUserRequest.setRole("TESTER");

        mockMvc.perform(
                    patch(PATH)
                        .header("Authorization", mockTokenGenerator.getAdminToken())
                        .contentType(CONTENT_TYPE)
                        .content(jsonMapper.writeValueAsString(updatedUserRequest)))
               .andExpect(status().isNoContent())
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andReturn();

        User updatedUser = userRepo.findById(updatedUserRequest.getId()).orElse(null);
        assertNotNull(updatedUser);
        assertEquals(updatedUserRequest.getFirstName(), updatedUser.getFirstName());
        assertEquals(updatedUserRequest.getLastName(), updatedUser.getLastName());
        assertEquals(updatedUserRequest.getEmail(), updatedUser.getEmailAddress());
        assertEquals(updatedUserRequest.getUsername(), updatedUser.getUsername());
        assertEquals(updatedUserRequest.getPassword(), updatedUser.getPassword());
        assertEquals(updatedUserRequest.getRole(), updatedUser.getRole().name());

    }

    @Test
    @DirtiesContext
    void test_userUpdate_returns204_givenValidUserRequestPayload_usingResourceOwnerToken() throws Exception {

        UserRequestPayload updatedUserRequest = new UserRequestPayload();
        updatedUserRequest.setId("manager-user-id");
        updatedUserRequest.setFirstName("Updated");
        updatedUserRequest.setLastName("Updated");
        updatedUserRequest.setEmail("updated@revature.com");
        updatedUserRequest.setUsername("updated");
        updatedUserRequest.setPassword("Upd4ted!");

        mockMvc.perform(
                   patch(PATH)
                       .header("Authorization", mockTokenGenerator.getManagerToken())
                       .contentType(CONTENT_TYPE)
                       .content(jsonMapper.writeValueAsString(updatedUserRequest)))
               .andExpect(status().isNoContent())
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andReturn();

        User updatedUser = userRepo.findById(updatedUserRequest.getId()).orElse(null);
        assertNotNull(updatedUser);
        assertEquals(updatedUserRequest.getFirstName(), updatedUser.getFirstName());
        assertEquals(updatedUserRequest.getLastName(), updatedUser.getLastName());
        assertEquals(updatedUserRequest.getEmail(), updatedUser.getEmailAddress());
        assertEquals(updatedUserRequest.getUsername(), updatedUser.getUsername());
        assertEquals(updatedUserRequest.getPassword(), updatedUser.getPassword());
        assertEquals(User.Role.MANAGER, updatedUser.getRole());

    }

    @Test
    void test_userUpdate_returns409_givenValidUserRequestPayload_withTakenEmail_usingResourceOwnerToken() throws Exception {

        UserRequestPayload updatedUserRequest = new UserRequestPayload();
        updatedUserRequest.setId("manager-user-id");
        updatedUserRequest.setEmail("tester@revature.com");

        User userBeforeUpdate = userRepo.findById(updatedUserRequest.getId()).orElse(null);
        assertNotNull(userBeforeUpdate);

        mockMvc.perform(
                   patch(PATH)
                       .header("Authorization", mockTokenGenerator.getManagerToken())
                       .contentType(CONTENT_TYPE)
                       .content(jsonMapper.writeValueAsString(updatedUserRequest)))
               .andExpect(status().isConflict())
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("There is already a user with that email!")))
               .andReturn();

        User updatedUser = userRepo.findById(updatedUserRequest.getId()).orElse(null);
        assertNotNull(updatedUser);
        assertEquals(userBeforeUpdate.getFirstName(), updatedUser.getFirstName());
        assertEquals(userBeforeUpdate.getLastName(), updatedUser.getLastName());
        assertEquals(userBeforeUpdate.getEmailAddress(), updatedUser.getEmailAddress());
        assertEquals(userBeforeUpdate.getUsername(), updatedUser.getUsername());
        assertEquals(userBeforeUpdate.getPassword(), updatedUser.getPassword());
        assertEquals(User.Role.MANAGER, updatedUser.getRole());

    }

    @Test
    void test_userUpdate_returns409_givenValidUserRequestPayload_withTakenUsername_usingResourceOwnerToken() throws Exception {

        UserRequestPayload updatedUserRequest = new UserRequestPayload();
        updatedUserRequest.setId("manager-user-id");
        updatedUserRequest.setUsername("tester");

        User userBeforeUpdate = userRepo.findById(updatedUserRequest.getId()).orElse(null);
        assertNotNull(userBeforeUpdate);

        mockMvc.perform(
                   patch(PATH)
                       .header("Authorization", mockTokenGenerator.getManagerToken())
                       .contentType(CONTENT_TYPE)
                       .content(jsonMapper.writeValueAsString(updatedUserRequest)))
               .andExpect(status().isConflict())
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("There is already a user with that username!")))
               .andReturn();

        User updatedUser = userRepo.findById(updatedUserRequest.getId()).orElse(null);
        assertNotNull(updatedUser);
        assertEquals(userBeforeUpdate.getFirstName(), updatedUser.getFirstName());
        assertEquals(userBeforeUpdate.getLastName(), updatedUser.getLastName());
        assertEquals(userBeforeUpdate.getEmailAddress(), updatedUser.getEmailAddress());
        assertEquals(userBeforeUpdate.getUsername(), updatedUser.getUsername());
        assertEquals(userBeforeUpdate.getPassword(), updatedUser.getPassword());
        assertEquals(User.Role.MANAGER, updatedUser.getRole());

    }

    @Test
    void test_userUpdate_returns403_givenValidUserRequestPayload_usingNonResourceOwnerToken() throws Exception {


        UserRequestPayload updatedUserRequest = new UserRequestPayload();
        updatedUserRequest.setId("manager-user-id");
        updatedUserRequest.setLastName("Updated");

        User userBeforeUpdate = userRepo.findById(updatedUserRequest.getId()).orElse(null);
        assertNotNull(userBeforeUpdate);

        mockMvc.perform(
                   patch(PATH)
                       .header("Authorization", mockTokenGenerator.getTesterToken())
                       .contentType(CONTENT_TYPE)
                       .content(jsonMapper.writeValueAsString(updatedUserRequest)))
               .andExpect(status().isForbidden())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("You do not have the proper permissions to perform that operation.")))
               .andReturn();

        User updatedUser = userRepo.findById(updatedUserRequest.getId()).orElse(null);
        assertNotNull(updatedUser);
        assertEquals(userBeforeUpdate.getFirstName(), updatedUser.getFirstName());
        assertEquals(userBeforeUpdate.getLastName(), updatedUser.getLastName());
        assertEquals(userBeforeUpdate.getEmailAddress(), updatedUser.getEmailAddress());
        assertEquals(userBeforeUpdate.getUsername(), updatedUser.getUsername());
        assertEquals(userBeforeUpdate.getPassword(), updatedUser.getPassword());
        assertEquals(userBeforeUpdate.getRole(), updatedUser.getRole());

    }

}
