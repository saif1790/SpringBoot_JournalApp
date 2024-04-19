package com.demo.journalApp.controllers;


import com.demo.journalApp.entity.JournalEntry;
import com.demo.journalApp.entity.User;
import com.demo.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/getAllUsers")
    public ResponseEntity<List<User>> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        List<User> allUsersEntries = userService.getAllUsersEntries();
        if (allUsersEntries.size() > 0)
            return new ResponseEntity(allUsersEntries, HttpStatus.OK);
        else
            return new ResponseEntity("No Entries Found", HttpStatus.NOT_FOUND);
    }

    /*@PostMapping(path = "/createUser")
    public ResponseEntity<User> createUser(@RequestBody User userEntry) {
        try {
            User entry = userService.createEntry(userEntry);
            if (entry != null)
                return new ResponseEntity(entry, HttpStatus.CREATED);
            else
                return new ResponseEntity(entry, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }*/

    @GetMapping(path = "id/{myId}")
    public ResponseEntity<User> getUserById(@PathVariable ObjectId myId) {
        Optional<User> userId = userService.getEntryById(myId);
        if (userId != null)
            return new ResponseEntity(userId, HttpStatus.OK);
        else
            return new ResponseEntity("No Entries Found with the id :" + myId, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/deleteEntryById/{myId}")
    public ResponseEntity<?> deleteUserById(@PathVariable ObjectId myId) {
        userService.deleteEntryById(myId);
        return new ResponseEntity("Entry has been deleted by :" + myId, HttpStatus.NO_CONTENT);

    }


    /*@PutMapping(path = "{userName}")
    public ResponseEntity<User> updateUser(@RequestBody User updatedUser,@PathVariable String userName) {
         User userInDB = userService.findByUserName(userName);
         if(userInDB != null)
         {
             userInDB.setUserName(updatedUser.getUserName());
             userInDB.setPassword(updatedUser.getPassword());
             userService.createEntry(userInDB);
             return new ResponseEntity<>(userInDB,HttpStatus.CREATED);
         }
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/


    @PutMapping(path = "update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDB = userService.findByUserName(userName);
        /*userInDB.setUserName(user.getUserName());
        userInDB.setPassword(user.getPassword());*/
        userService.updateUser(userInDB);
        //userService.createEntry(userInDB);
        return new ResponseEntity<>("User with name " + userInDB.getUserName(), HttpStatus.OK);
    }
}
