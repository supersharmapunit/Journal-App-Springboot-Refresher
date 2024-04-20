package com.punit.journalApp.controller;

import com.punit.journalApp.entity.JournalEntry;
import com.punit.journalApp.entity.User;
import com.punit.journalApp.service.JournalEntrySerivce;
import com.punit.journalApp.service.UserSerivce;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntrySerivce journalEntrySerivce;
    @Autowired
    private UserSerivce userSerivce;

    @GetMapping("/{username}")
    public ResponseEntity<List<JournalEntry>> getAll(@PathVariable String username) {
        User user = this.userSerivce.findByUserName(username);
        List<JournalEntry> all = user.getJournalEntries();
        if(all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{username}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry, @PathVariable String username) {
        try {
            this.journalEntrySerivce.saveEntry(entry, username);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId id) {
        Optional<JournalEntry> entry = this.journalEntrySerivce.findById(id);
        if(entry.isPresent()) {
            return ResponseEntity.ok(entry.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/id/{username}/{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId id, @PathVariable String username) {
        this.journalEntrySerivce.deleteById(id, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{username}/{id}")
    public ResponseEntity<?> updateEntryById(@PathVariable ObjectId id, @PathVariable String username, @RequestBody JournalEntry entry) {
        Optional<JournalEntry> journalEntry = Optional.ofNullable(this.journalEntrySerivce.updateEntry(id, username,entry));
        if(journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}