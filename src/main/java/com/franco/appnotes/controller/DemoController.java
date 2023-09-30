package com.franco.appnotes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

    @GetMapping
    public ResponseEntity<Map<String, String>> helloWorld()
    {
        return new ResponseEntity<>(
                Map.of("Hello", "World!"),
                HttpStatus.OK
        );
    }
}
