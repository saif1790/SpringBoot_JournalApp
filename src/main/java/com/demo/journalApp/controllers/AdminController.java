package com.demo.journalApp.controllers;


import com.demo.journalApp.entity.User;
import com.demo.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {


    @Autowired
    private UserService userService;

    @GetMapping(path = "all-users")
    public ResponseEntity<?> getAllUsers()
    {
        List<User> listOfAllUsers = userService.getAllUsersEntries();
        if(listOfAllUsers != null && !listOfAllUsers.isEmpty())
        {
            return new ResponseEntity<>(listOfAllUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/create-admin-user")
    public void createAdmin(@RequestBody User user){
        userService.createAdmin(user);

    }
}
