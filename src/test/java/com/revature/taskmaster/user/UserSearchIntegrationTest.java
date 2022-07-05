package com.revature.taskmaster.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserSearchIntegrationTest {

    private final MockMvc mockMvc;

    private final String PATH = "/users";

    private final String CONTENT_TYPE = "application/json";

    @Autowired
    public UserSearchIntegrationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void test_userSearch_returnsOneUser_whenSearchingForKnownId() throws Exception {
        String knownId = "manager-user-id";
        mockMvc.perform(get(PATH + "?id=" + knownId))
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
    void test_userSearch_returnsOneUser_whenSearchingForKnownUsername() throws Exception {
        String knownUsername = "tester";
        mockMvc.perform(get(PATH + "?username=" + knownUsername))
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
    void test_userSearch_returnsOneUser_whenSearchingForKnownEmail() throws Exception {
        String knownEmail = "tester@revature.com";
        mockMvc.perform(get(PATH + "?emailAddress=" + knownEmail))
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
    void test_userSearch_returnsSuccessfully_whenMultipleUsersAreFoundWithSearchParams() throws Exception {
        mockMvc.perform(get(PATH + "?role=TESTER"))
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
    void test_userSearch_returnsSuccessfully_whenNoSearchParamsAreProvided() throws Exception {
        mockMvc.perform(get(PATH))
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
    void test_userSearch_returnsSuccessfully_whenUsingSearchingUsingOfNestedField() throws Exception {
        String nestedFieldQuery = "?metadata.active=false";
        mockMvc.perform(get(PATH + nestedFieldQuery))
                .andExpect(status().isOk())
               .andExpect(header().string("content-type", CONTENT_TYPE))
               .andExpect(header().string("Access-Control-Allow-Origin", "*"))
               .andExpect(header().string("Access-Control-Allow-Methods", "*"))
               .andExpect(header().string("Access-Control-Allow-Headers", "*"))
               .andExpect(jsonPath("$").isArray())
               .andReturn();
    }

    @Test
    void test_userSearch_returns404_whenNoUsersAreFoundWithSearchParams() throws Exception {
        String unknownId = "unknown-user-id";
        mockMvc.perform(get(PATH + "?id=" + unknownId))
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
    void test_userSearch_returns400_whenSearchParamsContainUnknownKey() throws Exception {
        String unknownKeyPair = "?unknown=invalid";
        mockMvc.perform(get(PATH + unknownKeyPair))
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



}
