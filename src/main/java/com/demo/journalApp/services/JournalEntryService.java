package com.demo.journalApp.services;

import com.demo.journalApp.entity.JournalEntry;
import com.demo.journalApp.entity.User;
import com.demo.journalApp.repository.JournalEntryRepository;
import com.demo.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class JournalEntryService {

    @Autowired
    private UserService userService;

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    @Transactional
    public JournalEntry createEntry(JournalEntry journalEntry, String userName) {
        User userInDB = userService.findByUserName(userName);
        JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
        userInDB.getJournalEntries().add(savedEntry);
        userService.createEntry(userInDB);
        if (savedEntry != null) {
            return savedEntry;
        } else {
            return savedEntry;
        }
    }

    public List<JournalEntry> getAllJournalEntries() {

        List<JournalEntry> allEntries = journalEntryRepository.findAll();
        if (allEntries.size() > 0)
            return allEntries;
        else
            return allEntries;
    }

    public JournalEntry getEntryById(ObjectId id) {

        JournalEntry journalEntry = journalEntryRepository.findById(id).orElse(null);
        return journalEntry;
    }

    public void deleteEntryById(ObjectId id, String userName) {User userInDB = userService.findByUserName(userName);
        journalEntryRepository.deleteById(id);
        userInDB.getJournalEntries().removeIf(x->x.getId().equals(id));
        userService.createEntry(userInDB);
    }

    public JournalEntry updateEntry(JournalEntry journalEntry) {

        return journalEntryRepository.save(journalEntry);
    }
}
