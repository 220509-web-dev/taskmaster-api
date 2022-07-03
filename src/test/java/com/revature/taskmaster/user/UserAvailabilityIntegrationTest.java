package com.revature.taskmaster.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserAvailabilityIntegrationTest {

    private final MockMvc mockMvc;

    private final String path = "/users/availability";

    @Autowired
    public UserAvailabilityIntegrationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void test_checkUsernameAvailability_returns204_givenValidAvailableUsername() throws Exception {
        String availableUsername = "available-username";
        mockMvc.perform(get(path + "?username=" + availableUsername))
                .andExpect(status().is(204))
                .andReturn();
    }

    @Test
    void test_checkUsernameAvailability_returns409_givenValidUnavailableUsername() throws Exception {
        String unavailableUsername = "tester";
        mockMvc.perform(get(path + "?username=" + unavailableUsername))
              .andExpect(status().is(409))
              .andReturn();
    }

    @Test
    void test_checkEmailAvailability_returns204_givenValidAvailableEmail() throws Exception {
        String availableEmail = "available-email@revature.com";
        mockMvc.perform(get(path + "?email=" + availableEmail))
               .andExpect(status().is(204))
               .andReturn();
    }

    @Test
    void test_checkEmailAvailability_returns409_givenValidUnavailableEmail() throws Exception {
        String unavailableEmail = "tester@revature.com";
        mockMvc.perform(get(path + "?email=" + unavailableEmail))
               .andExpect(status().is(409))
               .andReturn();
    }

    @Test
    void test_checkAvailability_returns400_whenNoUsernameOrEmailRequestParamsAreProvided() throws Exception {
        mockMvc.perform(get(path))
               .andExpect(status().is(400))
               .andReturn();
    }

    @Test
    void test_checkUsernameAvailability_returns409_givenInvalidUsername() throws Exception {
        String invalidUsername = "t";
        mockMvc.perform(get(path + "?username=" + invalidUsername))
               .andExpect(status().is(400))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("Usernames must contain at least three characters")))
               .andReturn();
    }

    @Test
    void test_checkEmailAvailability_returns409_givenInvalidEmail() throws Exception {
        String invalidEmail = "not an email";
        mockMvc.perform(get(path + "?email=" + invalidEmail))
               .andExpect(status().is(400))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("A valid email must be provided")))
               .andReturn();
    }

    @Test
    void test_checkAvailabilityEndpoint_returns405_whenIncorrectHttpMethodUsed() throws Exception {
        mockMvc.perform(post(path))
               .andExpect(status().is(405))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("Request method 'POST' not supported")))
               .andReturn();

        mockMvc.perform(put(path))
               .andExpect(status().is(405))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("Request method 'PUT' not supported")))
               .andReturn();

        mockMvc.perform(patch(path))
               .andExpect(status().is(405))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("Request method 'PATCH' not supported")))
               .andReturn();

        mockMvc.perform(delete(path))
               .andExpect(status().is(405))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("Request method 'DELETE' not supported")))
               .andReturn();
    }
}
