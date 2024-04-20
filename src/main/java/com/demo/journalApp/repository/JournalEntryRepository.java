package com.demo.journalApp.repository;

import com.demo.journalApp.entity.JournalEntry;
import com.demo.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository  extends MongoRepository<JournalEntry, ObjectId> {

}
