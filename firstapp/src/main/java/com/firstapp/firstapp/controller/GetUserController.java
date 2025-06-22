package com.firstapp.firstapp.controller;

import com.firstapp.firstapp.entity.UserData;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class GetUserController {

    @PostMapping("/submit")
    public String receiveData(@RequestBody UserData data) {
        System.out.println("Received Data: " + data);
        return "Data received successfully";
    }



}
