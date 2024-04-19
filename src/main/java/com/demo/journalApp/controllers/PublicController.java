package com.demo.journalApp.controllers;


import com.demo.journalApp.entity.User;
import com.demo.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/helth-check")
    public String getHealthCheck(){

        return "OK";
    }

    @PostMapping(path = "/createUser")
    public ResponseEntity<User> createUser(@RequestBody User userEntry) {
        try {
            User entry = userService.createUser(userEntry);
            if (entry != null)
                return new ResponseEntity(entry, HttpStatus.CREATED);
            else
                return new ResponseEntity(entry, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
