package com.revature.taskmaster.common.datasource;

import com.revature.taskmaster.task.Task;
import com.revature.taskmaster.task.TaskRepository;
import com.revature.taskmaster.user.User;
import com.revature.taskmaster.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
@Profile("local || test")
public class MockDataInserter implements CommandLineRunner {

    private final UserRepository userRepo;
    private final TaskRepository taskRepo;

    @Autowired
    public MockDataInserter(UserRepository userRepo, TaskRepository taskRepo) {
        this.userRepo = userRepo;
        this.taskRepo = taskRepo;
    }

    @Override
    @Transactional
    public void run(String... args) {

        final String DEFAULT_PASSWORD = "Revature1!";

        User adminUser = new User("Adam", "Inn", "adam.inn@revature.com", "admin", DEFAULT_PASSWORD, User.Role.ADMIN);
        adminUser.setId("admin-user-id");
        adminUser.getMetadata().setActive(true);

        User managerUser = new User("Manny", "Jerr", "manny@revature.com", "manny123", DEFAULT_PASSWORD, User.Role.MANAGER);
        managerUser.setId("manager-user-id");
        managerUser.getMetadata().setActive(true);

        User devUser = new User("Devin", "Loper", "dev.loper@revature.com", "dev", DEFAULT_PASSWORD, User.Role.DEV);
        devUser.setId("dev-user-id");
        devUser.getMetadata().setActive(true);

        User testerUser = new User("Tester", "McTesterson", "tester@revature.com", "tester", DEFAULT_PASSWORD, User.Role.TESTER);
        testerUser.setId("tester-user-id");
        testerUser.getMetadata().setActive(true);

        User inactiveUser = new User("Ann", "Odder", "ann.odder@revature.com", "ann123", DEFAULT_PASSWORD, User.Role.TESTER);
        inactiveUser.setId("inactive-user-id");
        inactiveUser.getMetadata().setActive(false);

        User lockedUser = new User("Loch", "Yoozer", "loch@revature.com", "loch", DEFAULT_PASSWORD, User.Role.LOCKED);
        lockedUser.setId("locked-user-id");
        lockedUser.getMetadata().setActive(true);

        userRepo.saveAll(Arrays.asList(adminUser, managerUser, devUser, testerUser, inactiveUser, lockedUser));

        Task task1 = new Task()
                .setTitle("[API] Login Endpoint")
                .setDescription("The API needs to expose an endpoint at /auth that accepts POST requests containing user credentials in the request body")
                .setPriority(Task.Priority.P2)
                .setPointValue(12)
                .setState(Task.State.BLOCKED)
                .setLabels(Arrays.asList("api", "authentication", "security"))
                .setCreator(managerUser)
                .setAssignees(Arrays.asList(devUser, testerUser));
        task1.getMetadata().setActive(true);

        Task task2 = new Task()
                .setTitle("[API] Register Endpoint")
                .setDescription("The API needs to expose an endpoint at /users that accepts POST requests containing new user information in the request body")
                .setPriority(Task.Priority.P2)
                .setPointValue(5)
                .setState(Task.State.IN_PROGRESS)
                .setLabels(Arrays.asList("api", "registration"))
                .setCreator(managerUser)
                .setAssignees(Arrays.asList(devUser, testerUser));
        task2.getMetadata().setActive(true);

        Task task3 = new Task()
                .setTitle("[API] JWT Generation and Parsing")
                .setDescription("We should research JWT libraries to use for token generation and parsing as a part of our authentication flows")
                .setPriority(Task.Priority.P2)
                .setPointValue(8)
                .setState(Task.State.IN_PROGRESS)
                .setLabels(Arrays.asList("api", "jwt", "security", "research"))
                .setCreator(devUser)
                .setAssignees(Arrays.asList(devUser, testerUser));
        task3.getMetadata().setActive(true);

        Task task4 = new Task()
                .setTitle("[API] Logging Aspect")
                .setDescription("The API needs log method invocations, returns, and exceptions to both a file and the console for debugging")
                .setPriority(Task.Priority.P3)
                .setPointValue(5)
                .setState(Task.State.UNASSIGNED)
                .setLabels(Arrays.asList("api", "aop", "logging"))
                .setCreator(devUser);
        task4.getMetadata().setActive(true);

        taskRepo.saveAll(Arrays.asList(task1, task2, task3, task4));

    }

}
