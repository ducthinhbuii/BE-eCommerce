package com.example.ecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/")
    public String helloWorld(){
        return "Hello World!";
    }

    @GetMapping("/test")
    public String test(){
        String serviceAccountPath = System.getProperty("user.dir");
        return serviceAccountPath;
    }
}
