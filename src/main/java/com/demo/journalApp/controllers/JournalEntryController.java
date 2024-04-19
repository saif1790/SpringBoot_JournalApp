package com.demo.journalApp.controllers;


import com.demo.journalApp.entity.JournalEntry;
import com.demo.journalApp.entity.User;
import com.demo.journalApp.services.JournalEntryService;
import com.demo.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/getAllJournalEntries")
    public ResponseEntity<List<JournalEntry>> journalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        System.out.println("journalEntriesOfUser"+userName);
        User userInDB = userService.findByUserName(userName);
            List<JournalEntry> allJournalEntries = userInDB.getJournalEntries();
            if (allJournalEntries.size() > 0)
                return new ResponseEntity(allJournalEntries, HttpStatus.OK);
            else
                return new ResponseEntity("No Entries Found", HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/createEntry")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry) {
        journalEntry.setLocalDateTime(LocalDateTime.now());
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            JournalEntry entry = journalEntryService.createEntry(journalEntry,userName);
                return new ResponseEntity(entry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        JournalEntry entryById = journalEntryService.getEntryById(myId);
        if (entryById != null)
            return new ResponseEntity(entryById, HttpStatus.OK);
        else
            return new ResponseEntity("No Entries Found with the id :" + myId, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/deleteEntryById/{userName}/{myId}")
    public ResponseEntity<String> deleteJournalEntryById(@PathVariable ObjectId myId,@PathVariable String userName ) {
        journalEntryService.deleteEntryById(myId,userName);
        return new ResponseEntity("Entry has been deleted by :" + myId, HttpStatus.OK);

    }

    @PutMapping(path = "id/{userName}/{myId}")
    public ResponseEntity<JournalEntry> updateJournalEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry updatedEntry,@PathVariable String userName) {
        updatedEntry.setLocalDateTime(LocalDateTime.now());
        JournalEntry oldEntry = journalEntryService.getEntryById(myId);
        if (oldEntry != null) {
            oldEntry.setTitle(updatedEntry.getTitle() != null
                    && !updatedEntry.getTitle().equals("") ? updatedEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(updatedEntry.getContent() != null && !updatedEntry.getContent().equals("") ? updatedEntry.getContent() : oldEntry.getContent());
            JournalEntry entry = journalEntryService.updateEntry(oldEntry);
            //JournalEntry entry = journalEntryService.createEntry(oldEntry);
            if (entry != null)
                return new ResponseEntity<>(entry,HttpStatus.OK);
        }
        return new ResponseEntity("No Entries Found :" , HttpStatus.NOT_FOUND);
    }
}
