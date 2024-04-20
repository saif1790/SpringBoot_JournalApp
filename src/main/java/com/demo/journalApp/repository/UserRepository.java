package com.demo.journalApp.repository;

import com.demo.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String username);

    Optional<User> findById(ObjectId id);

    void deleteById(ObjectId id);

}
