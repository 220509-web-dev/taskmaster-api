package com.revature.taskmaster.user;

import com.revature.taskmaster.test_utils.MockTokenGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class UserDeactivationIntegrationTest {

    private final MockMvc mockMvc;
    private final UserRepository userRepo;
    private final MockTokenGenerator mockTokenGenerator;
    private final String PATH = "/users";
    private final String CONTENT_TYPE = "application/json";
    private static final String MOCK_USER_NOT_LOADED = "Test active user not found, check mock data inserter to ensure it inserts an active user";

    @Autowired
    public UserDeactivationIntegrationTest(MockMvc mockMvc, UserRepository userRepo, MockTokenGenerator mockTokenGenerator) {
        this.mockMvc = mockMvc;
        this.userRepo = userRepo;
        this.mockTokenGenerator = mockTokenGenerator;
    }

    @Test
    @DirtiesContext
    void test_userDeactivation_returns204_givenKnownUserId_usingAdminToken() throws Exception {

        User activeManagerUser = userRepo.findById("manager-user-id").orElse(null);
        assertNotNull(activeManagerUser, MOCK_USER_NOT_LOADED);

        mockMvc.perform(
                    delete(PATH)
                        .header("Authorization", mockTokenGenerator.getAdminToken())
                        .param("id", activeManagerUser.getId()))
               .andExpect(status().isNoContent())
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andReturn();

        User nowInactiveUser = userRepo.findById(activeManagerUser.getId()).orElse(null);
        assertNotNull(nowInactiveUser);
        assertFalse(nowInactiveUser.getMetadata().isActive());

    }

    @Test
    @DirtiesContext
    void test_userDeactivation_returns204_givenKnownUserId_usingResourceOwnerToken() throws Exception {

        User activeTesterUser = userRepo.findById("tester-user-id").orElse(null);
        assertNotNull(activeTesterUser, MOCK_USER_NOT_LOADED);

        mockMvc.perform(
                   delete(PATH)
                       .header("Authorization", mockTokenGenerator.getTesterToken())
                       .param("id", activeTesterUser.getId()))
               .andExpect(status().isNoContent())
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andReturn();

        User nowInactiveUser = userRepo.findById(activeTesterUser.getId()).orElse(null);
        assertNotNull(nowInactiveUser);
        assertFalse(nowInactiveUser.getMetadata().isActive());

    }

    @Test
    void test_userDeactivation_returns403_givenKnownUserId_usingNonResourceOwnerToken() throws Exception {

        User activeManagerUser = userRepo.findById("manager-user-id").orElse(null);
        assertNotNull(activeManagerUser, MOCK_USER_NOT_LOADED);

        mockMvc.perform(
                   delete(PATH)
                       .header("Authorization", mockTokenGenerator.getDevToken())
                       .param("id", activeManagerUser.getId()))
               .andExpect(status().isForbidden())
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("You do not have the proper permissions to perform that operation.")))
               .andReturn();

        User stillActiveUser = userRepo.findById(activeManagerUser.getId()).orElse(null);
        assertNotNull(stillActiveUser);
        assertTrue(stillActiveUser.getMetadata().isActive());

    }

    @Test
    void test_userDeactivation_returns404_givenUnknownUserId_usingAdminToken() throws Exception {
        mockMvc.perform(
                   delete(PATH)
                       .header("Authorization", mockTokenGenerator.getAdminToken())
                       .param("id", "unknown-id"))
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
    void test_userDeactivation_returns403_givenUnknownUserId_usingAnyNonAdminToken() throws Exception {
        for (Map.Entry<User.Role, String> roleTokenEntries : mockTokenGenerator.getRoleTokens().entrySet()) {
            if (roleTokenEntries.getKey().equals(User.Role.ADMIN)) continue;
            mockMvc.perform(
                       delete(PATH)
                           .header("Authorization", roleTokenEntries.getValue())
                           .param("id", "unknown-id"))
                   .andExpect(status().isForbidden())
                   .andExpect(header().string("content-type", CONTENT_TYPE))
                   .andExpect(header().string("Access-Control-Allow-Origin", "*"))
                   .andExpect(header().string("Access-Control-Allow-Methods", "*"))
                   .andExpect(header().string("Access-Control-Allow-Headers", "*"))
                   .andExpect(jsonPath("$.messages").isArray())
                   .andExpect(jsonPath("$.messages", hasSize(1)))
                   .andExpect(jsonPath("$.messages", hasItem("You do not have the proper permissions to perform that operation.")))
                   .andReturn();
        }
    }

}
