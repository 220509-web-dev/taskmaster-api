package com.revature.taskmaster.user;

import com.revature.taskmaster.common.datasource.EntitySearcher;
import com.revature.taskmaster.common.dtos.ResourceCreationResponse;
import com.revature.taskmaster.common.util.exceptions.ResourcePersistenceException;
import com.revature.taskmaster.user.dtos.UserRequestPayload;
import com.revature.taskmaster.user.dtos.UserResponsePayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService sut; // System Under Test
    private final UserRepository mockUserRepo = mock(UserRepository.class);
    private final EntitySearcher mockEntitySearcher = mock(EntitySearcher.class);

    @BeforeEach
    public void setup() {
        reset(mockUserRepo, mockEntitySearcher);
        sut = new UserService(mockUserRepo, mockEntitySearcher);
    }

    @Test
    void test_fetchAllUsers_returnsListOfUserResponsePayloads_providedRepoReturnsUsers() {

        // Arrange (set up the test scenario)
        List<User> mockUsers = Arrays.asList(
            new User("mock-user-id-1", "First-1", "Last-1", "Email-1", "Username-1", "Password-1", User.Role.ADMIN),
            new User("mock-user-id-2", "First-2", "Last-2", "Email-2", "Username-2", "Password-2", User.Role.DEV),
            new User("mock-user-id-3", "First-3", "Last-3", "Email-3", "Username-3", "Password-3", User.Role.MANAGER),
            new User("mock-user-id-4", "First-4", "Last-4", "Email-4", "Username-4", "Password-4", User.Role.TESTER),
            new User("mock-user-id-5", "First-5", "Last-5", "Email-5", "Username-5", "Password-5", User.Role.LOCKED)
        );
        when(mockUserRepo.findAll()).thenReturn(mockUsers);

        // Act (invoke the method you wish to test on the SUT)
        List<UserResponsePayload> actual = sut.fetchAllUsers();

        // Assert (assert that the result is what was expected and that mocks were called appropriately)
        assertEquals(mockUsers.size(), actual.size());
        verify(mockUserRepo, times(1)).findAll();

    }

    @Test
    void test_createUser_throwsResourcePersistenceException_givenThatProvidedUsernameIsTaken() {

        // Arrange
        UserRequestPayload dummyRequest = new UserRequestPayload();
        dummyRequest.setFirstName("First");
        dummyRequest.setLastName("Last");
        dummyRequest.setEmail("valid@email.com");
        dummyRequest.setUsername("valid-username");
        dummyRequest.setPassword("p4$$W0rd");
        when(mockUserRepo.existsByUsername(dummyRequest.getUsername())).thenReturn(true);

        // Act
        ResourcePersistenceException thrown = assertThrows(ResourcePersistenceException.class, () -> {
            sut.createUser(dummyRequest);
        });

        // Assert
        assertEquals("There is already a user with that username!", thrown.getMessage());
        verify(mockUserRepo, times(1)).existsByUsername(dummyRequest.getUsername());
        verify(mockUserRepo, times(0)).existsByEmailAddress(anyString());
        verify(mockUserRepo, times(0)).save(any());

    }

    @Test
    void test_createUser_throwsResourcePersistenceException_givenThatProvidedEmailIsTaken() {

        // Arrange
        UserRequestPayload dummyRequest = new UserRequestPayload();
        dummyRequest.setFirstName("First");
        dummyRequest.setLastName("Last");
        dummyRequest.setEmail("valid@email.com");
        dummyRequest.setUsername("valid-username");
        dummyRequest.setPassword("p4$$W0rd");
        when(mockUserRepo.existsByUsername(dummyRequest.getUsername())).thenReturn(false);
        when(mockUserRepo.existsByEmailAddress(dummyRequest.getEmail())).thenReturn(true);

        // Act
        ResourcePersistenceException thrown = assertThrows(ResourcePersistenceException.class, () -> {
            sut.createUser(dummyRequest);
        });

        // Assert
        assertEquals("There is already a user with that email!", thrown.getMessage());
        verify(mockUserRepo, times(1)).existsByUsername(dummyRequest.getUsername());
        verify(mockUserRepo, times(1)).existsByEmailAddress(dummyRequest.getEmail());
        verify(mockUserRepo, times(0)).save(any());

    }

    @Test
    void test_createUser_returnsResourceCreationResponse_givenProvidedNewUserInfoIsValid() {

        // Arrange
        UserRequestPayload dummyRequest = new UserRequestPayload();
        dummyRequest.setFirstName("First");
        dummyRequest.setLastName("Last");
        dummyRequest.setEmail("valid@email.com");
        dummyRequest.setUsername("valid-username");
        dummyRequest.setPassword("p4$$W0rd");
        when(mockUserRepo.existsByUsername(dummyRequest.getUsername())).thenReturn(false);
        when(mockUserRepo.existsByEmailAddress(dummyRequest.getEmail())).thenReturn(false);
        when(mockUserRepo.save(any(User.class))).thenReturn(any(User.class));

        // Act
        ResourceCreationResponse actual = sut.createUser(dummyRequest);

        // Assert
        assertNotNull(actual);
        assertNotNull(actual.getResourceId());
        verify(mockUserRepo, times(1)).existsByUsername(dummyRequest.getUsername());
        verify(mockUserRepo, times(1)).existsByEmailAddress(dummyRequest.getEmail());
        verify(mockUserRepo, times(1)).save(any(User.class));

    }


}
