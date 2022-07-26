package com.revature.taskmaster.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@SpringBootTest
@AutoConfigureMockMvc
class UserAvailabilityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    private final String PATH = "/users/availability";
    private final String CONTENT_TYPE = "application/json";

    @Test
    void test_checkUsernameAvailability_returns204_givenValidAvailableUsername() throws Exception {
        mockMvc.perform(
                    get(PATH).
                        param("username", "available-username"))
               .andExpect(status().isNoContent())
               .andExpect(header().doesNotExist("content-type"))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andReturn();
    }

    @Test
    void test_checkUsernameAvailability_returns409_givenValidUnavailableUsername() throws Exception {
        mockMvc.perform(
                    get(PATH).
                        param("username", "tester"))
              .andExpect(status().isConflict())
              .andExpect(header().doesNotExist("content-type"))
              .andExpect(header().string("Access-Control-Allow-Origin", "*"))
              .andExpect(header().string("Access-Control-Allow-Methods", "*"))
              .andExpect(header().string("Access-Control-Allow-Headers", "*"))
              .andReturn();
    }

    @Test
    void test_checkEmailAvailability_returns204_givenValidAvailableEmail() throws Exception {
        mockMvc.perform(
                    get(PATH)
                        .param("email", "available-email@revature.com"))
               .andExpect(status().isNoContent())
               .andExpect(header().doesNotExist("content-type"))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andReturn();
    }

    @Test
    void test_checkEmailAvailability_returns409_givenValidUnavailableEmail() throws Exception {
        mockMvc.perform(
                    get(PATH)
                       .param("email", "tester@revature.com"))
               .andExpect(status().isConflict())
               .andExpect(header().doesNotExist("content-type"))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andReturn();
    }

    @Test
    void test_checkAvailability_returns400_whenNoUsernameOrEmailRequestParamsAreProvided() throws Exception {
        mockMvc.perform(get(PATH))
               .andExpect(status().isBadRequest())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("No email or username provided")))
               .andReturn();
    }

    @Test
    void test_checkUsernameAvailability_returns400_givenInvalidUsername() throws Exception {
        String invalidUsername = "t";
        mockMvc.perform(
                    get(PATH)
                        .param("username", "t"))
               .andExpect(status().isBadRequest())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("Usernames must contain at least three characters")))
               .andReturn();
    }

    @Test
    void test_checkEmailAvailability_returns400_givenInvalidEmail() throws Exception {
        mockMvc.perform(
                    get(PATH)
                       .param("email", "not an email"))
               .andExpect(status().isBadRequest())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("A valid email must be provided")))
               .andReturn();
    }

    @Test
    void test_checkAvailabilityEndpoint_returns405_whenIncorrectHttpMethodUsed() throws Exception {
        mockMvc.perform(post(PATH))
               .andExpect(status().isMethodNotAllowed())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("Request method 'POST' not supported")))
               .andReturn();

        mockMvc.perform(put(PATH))
               .andExpect(status().isMethodNotAllowed())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("Request method 'PUT' not supported")))
               .andReturn();

        mockMvc.perform(patch(PATH))
               .andExpect(status().isMethodNotAllowed())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("Request method 'PATCH' not supported")))
               .andReturn();

        mockMvc.perform(delete(PATH))
               .andExpect(status().isMethodNotAllowed())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("Request method 'DELETE' not supported")))
               .andReturn();
    }
}
