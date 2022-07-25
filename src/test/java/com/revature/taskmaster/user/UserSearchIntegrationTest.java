package com.revature.taskmaster.user;

import com.revature.taskmaster.test_utils.MockTokenGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserSearchIntegrationTest {

    private final MockMvc mockMvc;

    private final MockTokenGenerator mockTokenGenerator;

    private final String PATH = "/users";

    private final String CONTENT_TYPE = "application/json";

    @Autowired
    public UserSearchIntegrationTest(MockMvc mockMvc, MockTokenGenerator mockTokenGenerator) {
        this.mockMvc = mockMvc;
        this.mockTokenGenerator = mockTokenGenerator;
    }

    @Test
    void test_userSearch_returnsOneUser_whenSearchingForKnownId_usingAdminToken() throws Exception {
        mockMvc.perform(
                    get(PATH)
                        .header("Authorization", mockTokenGenerator.getAdminToken())
                        .param("id", "manager-user-id"))
               .andExpect(status().isOk())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$", hasSize(1)))
               .andReturn();
    }

    @Test
    void test_userSearch_returnsOneUser_whenSearchingForKnownId_usingResourceOwnerToken() throws Exception {
        mockMvc.perform(
                   get(PATH)
                       .header("Authorization", mockTokenGenerator.getManagerToken())
                       .param("id", "manager-user-id"))
               .andExpect(status().isOk())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$", hasSize(1)))
               .andReturn();
    }

    @Test
    void test_userSearch_returnsOneUser_whenSearchingForKnownUsername_usingAdminToken() throws Exception {
        mockMvc.perform(
                    get(PATH)
                        .header("Authorization", mockTokenGenerator.getAdminToken())
                        .param("username", "tester"))
               .andExpect(status().isOk())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$", hasSize(1)))
               .andReturn();
    }

    @Test
    void test_userSearch_returnsOneUser_whenSearchingForKnownUsername_usingResourceOwnerToken() throws Exception {
        mockMvc.perform(
                   get(PATH)
                       .header("Authorization", mockTokenGenerator.getTesterToken())
                       .param("username", "tester"))
               .andExpect(status().isOk())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$", hasSize(1)))
               .andReturn();
    }

    @Test
    void test_userSearch_returnsOneUser_whenSearchingForKnownEmail_usingAdminToken() throws Exception {
        mockMvc.perform(
                    get(PATH)
                        .header("Authorization", mockTokenGenerator.getAdminToken())
                        .param("emailAddress", "tester@revature.com"))
               .andExpect(status().isOk())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$", hasSize(1)))
               .andReturn();
    }

    @Test
    void test_userSearch_returnsOneUser_whenSearchingForKnownEmail_usingResourceOwnerToken() throws Exception {
        mockMvc.perform(
                   get(PATH)
                       .header("Authorization", mockTokenGenerator.getTesterToken())
                       .param("emailAddress", "tester@revature.com"))
               .andExpect(status().isOk())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$", hasSize(1)))
               .andReturn();
    }

    @Test
    void test_userSearch_returnsSuccessfully_whenMultipleUsersAreFoundWithSearchParams_usingAdminToken() throws Exception {
        mockMvc.perform(
                    get(PATH)
                        .header("Authorization", mockTokenGenerator.getAdminToken())
                       .param("role", "TESTER"))
               .andExpect(status().isOk())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$", hasSize(2)))
               .andReturn();
    }

    @Test
    void test_userSearch_returns403_whenMultipleUsersAreFoundWithSearchParams_usingResourceOwnerToken() throws Exception {
        mockMvc.perform(
                       get(PATH)
                               .header("Authorization", mockTokenGenerator.getTesterToken())
                               .param("role", "TESTER"))
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

    @Test
    void test_userSearch_returnsSuccessfully_whenNoSearchParamsAreProvided_usingAdminToken() throws Exception {
        mockMvc.perform(
                    get(PATH)
                        .header("Authorization", mockTokenGenerator.getAdminToken()))
               .andExpect(status().isOk())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$", hasSize(6)))
               .andReturn();
    }

    @Test
    void test_userSearch_returnsSuccessfully_whenUsingSearchingUsingOfNestedField_usingAdminToken() throws Exception {
        mockMvc.perform(
                    get(PATH)
                        .header("Authorization", mockTokenGenerator.getAdminToken())
                       .param("metadata.active", "false"))
                .andExpect(status().isOk())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$").isArray())
               .andReturn();
    }

    @Test
    void test_userSearch_returns404_whenNoUsersAreFoundWithSearchParams_usingAdminToken() throws Exception {
        mockMvc.perform(
                    get(PATH)
                        .header("Authorization", mockTokenGenerator.getAdminToken())
                       .param("id", "unknown-user-id"))
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
    void test_userSearch_returns400_whenSearchParamsContainUnknownKey_usingAdminToken() throws Exception {
        mockMvc.perform(
                    get(PATH)
                        .header("Authorization", mockTokenGenerator.getAdminToken())
                       .param("unknown", "invalid"))
               .andExpect(status().isBadRequest())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("No attribute with name: unknown found on entity: User")))
               .andReturn();
    }

    @Test
    void test_userSearch_returns401_whenNoSearchParamsAreProvided_usingNoToken() throws Exception {
        mockMvc.perform(get(PATH))
               .andExpect(status().isUnauthorized())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("There was no authorization token found on the request")))
               .andReturn();
    }

    @Test
    void test_userSearch_returns401_whenSearchParamsAreProvided_usingNoToken() throws Exception {
        mockMvc.perform(
                    get(PATH)
                       .param("id", "locked-used-id"))
               .andExpect(status().isUnauthorized())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("There was no authorization token found on the request")))
               .andReturn();
    }

    @Test
    void test_userSearch_returns401_whenSearchParamsAreProvided_usingBadToken() throws Exception {
        mockMvc.perform(
                   get(PATH)
                       .header("Authorization", mockTokenGenerator.getUnknownUserToken())
                       .param("id", "locked-used-id"))
               .andExpect(status().isUnauthorized())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasSize(1)))
               .andExpect(jsonPath("$.messages", hasItem("Could not parse provided token")))
               .andReturn();
    }

    @Test
    void test_userSearch_returns403_whenNoSearchParamsAreProvided_usingNonAdminToken() throws Exception {
        for (Map.Entry<User.Role, String> roleTokenEntry : mockTokenGenerator.getRoleTokens().entrySet()) {
            if (roleTokenEntry.getKey().equals(User.Role.ADMIN)) continue;
            mockMvc.perform(
                       get(PATH)
                           .header("Authorization", roleTokenEntry.getValue()))
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

    @Test
    void test_userSearch_returns403_whenSearchParamsAreProvided_usingNonAdminToken() throws Exception {
        for (Map.Entry<User.Role, String> roleTokenEntry : mockTokenGenerator.getRoleTokens().entrySet()) {
            if (roleTokenEntry.getKey().equals(User.Role.ADMIN)) continue;
            mockMvc.perform(
                           get(PATH)
                               .header("Authorization", roleTokenEntry.getValue())
                               .param("id", "locked-user-id"))
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
