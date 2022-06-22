package com.revature.taskmaster.controllers;

import com.revature.taskmaster.dtos.NewTaskRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController // implies @Controller at the class-level and @ResponseBody for all method return types
@RequestMapping("/test")
public class TestController {

    @GetMapping // handles GET requests to /taskmaster/test
    public String sanity() {
        return "/test works!";
    }

    @GetMapping("/example-1") // handles GET requests to /taskmaster/test/example-1
    public String example1() {
        return "/test/example-1 works!";
    }

    @GetMapping("/example-2") // handles GET requests to /taskmaster/test/example-2?something=123
    public String example2(@RequestParam("something") String key) {
        return "Value provided in exampleKey: " +  key;
    }

    @GetMapping("/example-3/{pathVar}") // handles GET requests to /taskmaster/test/example-3/abc
    public String example3(@PathVariable String pathVar) {
        return "Value provided in pathVar: " + pathVar;
    }

    @GetMapping("/example-4") // handles GET requests to /taskmaster/test/example-4 (expects a request header with the name "myHeader"
    public String example4(@RequestHeader String myHeader) {
        return "Value provided in myHeader: " + myHeader;
    }

    @ResponseStatus(HttpStatus.CREATED) // handles POST requests to /taskmaster/test/example-5 (expects a valid NewTaskRequest JSON in the request body)
    @PostMapping(value = "/example-5", consumes = "application/json", produces = "application/json")
    public NewTaskRequest example5(@RequestBody NewTaskRequest task) {
        task.setId(UUID.randomUUID().toString());
        return task;
    }

}
