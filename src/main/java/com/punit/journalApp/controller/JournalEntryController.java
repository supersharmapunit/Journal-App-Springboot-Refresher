package com.punit.journalApp.controller;

import com.punit.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/_journal")
public class JournalEntryController {

    private Map<ObjectId, JournalEntry > journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll() {
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry entry) {
        journalEntries.put(entry.getId(), entry);
        return entry;
    }

    @GetMapping("/id/{id}")
    public JournalEntry getEntryById(@PathVariable Long id) {
        return journalEntries.get(id);
    }

    @DeleteMapping("/id/{id}")
    public JournalEntry deleteEntryById(@PathVariable Long id) {
        return journalEntries.remove(id);
    }

    @PutMapping("/id/{id}")
    public boolean deleteEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry entry) {
        journalEntries.put(id, entry);
        return  true;
    }
}
