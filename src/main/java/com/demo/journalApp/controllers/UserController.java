package com.demo.journalApp.controllers;


import com.demo.journalApp.api.response.WeatherResponse;
import com.demo.journalApp.entity.JournalEntry;
import com.demo.journalApp.entity.User;
import com.demo.journalApp.repository.JournalEntryRepository;
import com.demo.journalApp.services.JournalEntryService;
import com.demo.journalApp.services.UserService;
import com.demo.journalApp.services.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private WeatherService weatherService;

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


    @DeleteMapping(path = "/deleteUserByUsername")
    public ResponseEntity<?> deleteUserByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDB = userService.findByUserName(userName);
        //userService.deleteUserByName(userName);
        List<JournalEntry> journalEntries = userInDB.getJournalEntries();
        for(JournalEntry journalEntry : journalEntries)
        {
            ObjectId id = journalEntry.getId();
            journalEntryService.deleteEntryById(id,userName);
        }
        userService.deleteEntryById(userInDB.getId());
        return new ResponseEntity("User has been deleted :", HttpStatus.NO_CONTENT);
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
        User updatedUser = userService.updateUser(user);
        //userService.createEntry(userInDB);
        return new ResponseEntity<>("User with name " + updatedUser, HttpStatus.OK);
    }

    @GetMapping("/findByUsername")
    public ResponseEntity<?> findByUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        if(userInDb != null)
        {
            return new ResponseEntity<>(userInDb,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("User Not Found",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{cityName}")
    public ResponseEntity<?> greetingUser(@PathVariable String cityName ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("User Name :" + authentication.getName());
        WeatherResponse weatherResponse = weatherService.getWeather(cityName);
        String greeting = "";
        if(weatherResponse != null) {
            greeting = "\n Region  :" + weatherResponse.getLocation().getRegion()+
                    "\n Today temprature  :" + weatherResponse.getCurrent().getTempC() +
                    "\n"+ "Last Temprature update at  :"+weatherResponse.getCurrent().getLastUpdated() + "\n";
        }
        return new ResponseEntity<>("Hi " + authentication.getName() +" " + greeting,HttpStatus.OK);
    }
}
