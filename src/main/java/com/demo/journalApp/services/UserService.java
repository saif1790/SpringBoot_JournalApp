package com.demo.journalApp.services;

import com.demo.journalApp.entity.JournalEntry;
import com.demo.journalApp.entity.User;
import com.demo.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User createUser(User user) {
        user.setUserName(user.getUserName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        User savedEntry = userRepository.save(user);
        if (savedEntry != null) {
            return savedEntry;
        } else {
            return savedEntry;
        }
    }

    public User createEntry(User user) {
        User savedEntry = userRepository.save(user);
        if (savedEntry != null) {
            return savedEntry;
        } else {
            return savedEntry;
        }
    }

    public List<User> getAllUsersEntries() {
        return userRepository.findAll();
    }

    public Optional<User> getEntryById(ObjectId id) {

        Optional<User> userEntry = userRepository.findById(id);
        return userEntry;
    }

    public void deleteEntryById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public User findByUserName(String user) {
        return userRepository.findByUserName(user);
    }

    public User updateUser(User user) {
        user.setUserName(user.getUserName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        User savedEntry = userRepository.save(user);
        if (savedEntry != null) {
            return savedEntry;
        } else {
            return savedEntry;
        }
    }

    public User createAdmin(User user) {
        user.setUserName(user.getUserName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("ADMIN","USER"));
        User savedEntry = userRepository.save(user);
        if (savedEntry != null) {
            return savedEntry;
        } else {
            return savedEntry;
        }
    }
}
