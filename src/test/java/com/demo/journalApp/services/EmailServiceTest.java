package com.demo.journalApp.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.AccessType;

@SpringBootTest
public class EmailServiceTest
{
    @Autowired
    private EmailService emailService;

    @Test
    public void sendEmailTest(){
        emailService.sendEmail("saif.tech1790@gmail.com","Email from JournalAPP","Hi Saif,This is 2nd email email");
    }
}
