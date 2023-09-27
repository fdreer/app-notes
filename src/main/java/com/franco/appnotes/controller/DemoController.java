package com.franco.appnotes.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/demo")
public class DemoController {

    public String helloWorld()
    {
        return "Hello World!!";
    }
}
