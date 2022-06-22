package com.revature.taskmaster;

import com.revature.taskmaster.entities.Task;
import com.revature.taskmaster.entities.User;
import com.revature.taskmaster.repos.TaskRepository;
import com.revature.taskmaster.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@SpringBootApplication
public class TaskmasterApp implements CommandLineRunner {

    private final UserRepository userRepo;
    private TaskRepository taskRepo;

    @Autowired
    public TaskmasterApp(UserRepository userRepo, TaskRepository taskRepo) {
        this.userRepo = userRepo;
        this.taskRepo = taskRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(TaskmasterApp.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        User user1 = new User(UUID.randomUUID().toString(), "Adam", "Inn", "adam.inn@revature", "admin", "revature", User.Role.ADMIN);
        User user2 = new User(UUID.randomUUID().toString(), "Tester", "McTesterson", "tester@revature", "tester", "test-password", User.Role.TESTER);
        userRepo.saveAll(Arrays.asList(user1, user2));

        Task task1 = new Task("Task 1 Title", "Task 1 Description", 8, user2, Arrays.asList(user1, user2), "started");
        Task task2 = new Task("Task 2 Title", "Task 2 Description", 3, user1, Collections.singletonList(user2), "started");
        Task task3 = new Task("Task 3 Title", "Task 3 Description", 12, user2, Collections.emptyList(), "not started");
        taskRepo.saveAll(Arrays.asList(task1, task2, task3));

        System.out.println("===========================================================");

        System.out.println(userRepo.findUserByUsernameAndPassword("admin", "revature"));
        System.out.println(taskRepo.findTasksByLabel("not started"));
        System.out.println(taskRepo.customQuery(8));

    }
}
