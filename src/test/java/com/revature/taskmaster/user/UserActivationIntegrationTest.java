package com.revature.taskmaster.user;

import com.revature.taskmaster.test_utils.MockUserFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static com.revature.taskmaster.test_utils.AssertionMessages.TEST_USER_NOT_FOUND;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class UserActivationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private MockUserFactory mockUserFactory;
    private final String PATH = "/users/activation";
    private final String CONTENT_TYPE = "application/json";

    @Test
    @DirtiesContext
    void test_userActivation_returns204_givenKnownUserId() throws Exception {

        User inactiveUser = mockUserFactory.getInactiveUser();
        assertNotNull(inactiveUser, TEST_USER_NOT_FOUND);

        mockMvc.perform(
                    patch(PATH).
                        param("id", inactiveUser.getId()))
               .andExpect(status().isNoContent())
               .andExpect(header().doesNotExist("content-type"))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andReturn();

        User nowActiveUser = userRepo.findById("inactive-user-id").orElse(null);
        assertNotNull(nowActiveUser);
        assertTrue(nowActiveUser.getMetadata().isActive());

    }

    @Test
    void test_userActivation_returns404_givenUnknownUserId() throws Exception {
        String unknownId = "unknown-id";
        mockMvc.perform(patch(PATH + "?id=" + unknownId))
               .andExpect(status().isNotFound())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("No resource found using the provided search params!")))
               .andReturn();
    }

    @Test
    void test_checkAvailabilityEndpoint_returns405_whenIncorrectHttpMethodUsed() throws Exception {
        mockMvc.perform(get(PATH))
               .andExpect(status().isMethodNotAllowed())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("Request method 'GET' not supported")))
               .andReturn();

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
