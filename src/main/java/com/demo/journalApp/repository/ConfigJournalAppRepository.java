package com.demo.journalApp.repository;

import com.demo.journalApp.entity.ConfigJournalApp;
import com.demo.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalApp, ObjectId> {



}
