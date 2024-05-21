package com.demo.journalApp.services;

import com.demo.journalApp.entity.User;
import com.demo.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest
{
    @Autowired
    private UserService userService;

    @Test
    public void testFindByUserName(){
        assertNotNull(userService.findByUserName("Azhar"));

        User userInDB = userService.findByUserName("waseem");
        assertEquals("waseem",userInDB.getUserName());
    }


    @ParameterizedTest
    @ValueSource(strings = {"waseemq","waseem","samir"})
    public void testFindByUserName(String name){
        assertNotNull(userService.findByUserName(name),"failed for "+name);
    }
}
