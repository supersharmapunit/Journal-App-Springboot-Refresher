package com.punit.journalApp.service;

import com.punit.journalApp.entity.JournalEntry;
import com.punit.journalApp.entity.User;
import com.punit.journalApp.repository.JournalEntryRepository;
import com.punit.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserSerivce {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        this.userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return this.userRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        this.userRepository.deleteById(id);
    }

    public void updateEntry(User user, String username) {
        User userInDB = this.userRepository.findByUsername(username);
        if(userInDB != null) {
            userInDB.setUsername(user.getUsername());
            userInDB.setPassword(user.getPassword());
            this.saveEntry(userInDB);
        }
    }

    public User findByUserName(String username) {
        return this.userRepository.findByUsername(username);
    }

    public void deleteByUsername(String username) {
        this.userRepository.deleteByUsername(username);
    }
}
