package com;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    ///tasks にタスクが送られてきたら、この処理をする//
    @PostMapping("/tasks")
    public Task addTask(@RequestBody Task task) {

        System.out.println(task.getName());

        return task;
    }
}