package com.example.myapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello! My first Spring Boot App!";
    }

    @GetMapping("/name")
public String name() {
    return "こんにちは！宏好さん";
}
}