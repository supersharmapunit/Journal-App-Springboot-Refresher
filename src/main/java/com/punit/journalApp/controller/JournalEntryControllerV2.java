package com.punit.journalApp.controller;

import com.punit.journalApp.entity.JournalEntry;
import com.punit.journalApp.service.JournalEntrySerivce;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntrySerivce journalEntrySerivce;

    @GetMapping
    public List<JournalEntry> getAll() {
        return this.journalEntrySerivce.getAllJournal();
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry entry) {
        entry.setDate(LocalDateTime.now());
        this.journalEntrySerivce.saveEntry(entry);
        return entry;
    }

    @GetMapping("/id/{id}")
    public JournalEntry getEntryById(@PathVariable ObjectId id) {
        return this.journalEntrySerivce.findById(id).orElse(null);
    }

    @DeleteMapping("/id/{id}")
    public boolean deleteEntryById(@PathVariable ObjectId id) {
        this.journalEntrySerivce.deleteById(id);
        return true;
    }

    @PutMapping("/id/{id}")
    public JournalEntry updateEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry entry) {
        return  this.journalEntrySerivce.updateEntry(id, entry);
    }
}
