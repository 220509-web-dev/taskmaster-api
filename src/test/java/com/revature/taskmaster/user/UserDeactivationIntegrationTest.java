package com.revature.taskmaster.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class UserDeactivationIntegrationTest {

    private final MockMvc mockMvc;
    private final UserRepository userRepo;
    private final String PATH = "/users";
    private final String CONTENT_TYPE = "application/json";

    @Autowired
    public UserDeactivationIntegrationTest(MockMvc mockMvc, UserRepository userRepo) {
        this.mockMvc = mockMvc;
        this.userRepo = userRepo;
    }

    @Test
    void test_userActivation_returns204_givenKnownUserId() throws Exception {

        User lockedUser = userRepo.findById("locked-user-id").orElse(null);
        assertNotNull(lockedUser, "Test inactive user not found, check mock data inserter to ensure it inserts an inactive user");

        mockMvc.perform(delete(PATH + "?id=" + lockedUser.getId()))
               .andExpect(status().isNoContent())
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andReturn();

        User nowInactiveUser = userRepo.findById("locked-user-id").orElse(null);
        assertNotNull(nowInactiveUser);
        assertFalse(nowInactiveUser.getMetadata().isActive());

    }

    @Test
    void test_userActivation_returns404_givenUnknownUserId() throws Exception {
        String unknownId = "unknown-id";
        mockMvc.perform(delete(PATH + "?id=" + unknownId))
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

}
