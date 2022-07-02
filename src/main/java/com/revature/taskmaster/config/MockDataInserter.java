package com.revature.taskmaster.config;

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
import java.util.Collections;
import java.util.UUID;

@Component
@Profile("local")
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
    public void run(String... args) throws Exception {

        User user1 = new User("Adam", "Inn", "adam.inn@revature.com", "admin", "Revature1!", User.Role.ADMIN);
        user1.getMetadata().setActive(true);

        User user2 = new User("Tester", "McTesterson", "tester@revature.com", "tester", "Revature1!", User.Role.TESTER);
        user2.getMetadata().setActive(true);

        userRepo.saveAll(Arrays.asList(user1, user2));

        Task task1 = new Task("Task 1 Title", "Task 1 Description", Task.Priority.P2, 8, Task.State.IN_PROGRESS, Arrays.asList("api", "data access", "jpa", "spring"), user2, Arrays.asList(user1, user2));
        task1.getMetadata().setActive(true);

        Task task2 = new Task("Task 2 Title", "Task 2 Description", Task.Priority.P3, 3, Task.State.IN_PROGRESS, Arrays.asList("ui", "react"), user1, Collections.singletonList(user2));
        task2.getMetadata().setActive(true);

        Task task3 = new Task("Task 3 Title", "Task 3 Description", Task.Priority.P1, 12, Task.State.UNASSIGNED, Arrays.asList("important", "api", "security"), user2);
        task3.getMetadata().setActive(true);

        Task task4 = new Task("Task 4 Title", "Task 4 Description", Task.Priority.P4, 8, Task.State.UNASSIGNED, Arrays.asList("api", "logging"), user2);
        task3.getMetadata().setActive(true);

        taskRepo.saveAll(Arrays.asList(task1, task2, task3, task4));

    }

}
