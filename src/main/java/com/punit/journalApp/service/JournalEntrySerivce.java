package com.punit.journalApp.service;

import com.punit.journalApp.entity.JournalEntry;
import com.punit.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntrySerivce {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry entry) {
        this.journalEntryRepository.save(entry);
    }

    public List<JournalEntry> getAllJournal() {
        return this.journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return this.journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        this.journalEntryRepository.deleteById(id);
    }

    public JournalEntry updateEntry(ObjectId id, JournalEntry entry) {
        Optional<JournalEntry> oldEntry = this.journalEntryRepository.findById(id);
        if(oldEntry.isPresent()) {
            oldEntry.get().setTitle(entry.getTitle() != null && !entry.getTitle().equals("") ? entry.getTitle() : oldEntry.get().getTitle());
            oldEntry.get().setContent(entry.getContent() != null && !entry.getContent().equals("") ? entry.getContent() : oldEntry.get().getContent());
            this.journalEntryRepository.save(oldEntry.get());
            return oldEntry.get();
        } else {
            return null;
        }
    }
}
