package com.punit.journalApp.service;

import com.punit.journalApp.entity.JournalEntry;
import com.punit.journalApp.entity.User;
import com.punit.journalApp.repository.JournalEntryRepository;
import com.punit.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntrySerivce {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserSerivce userSerivce;

    @Transactional
    public void saveEntry(JournalEntry entry, String username) {
        try {
            User user = this.userSerivce.findByUserName(username);
            System.out.println(user.toString());
            entry.setDate(LocalDateTime.now());
            JournalEntry saved = this.journalEntryRepository.save(entry);
            user.getJournalEntries().add(saved);
            this.userSerivce.saveEntry(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("An error occurred while saving the entry.", e);
        }
    }

    public List<JournalEntry> getAllJournal() {
        return this.journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return this.journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String username) {
        User user = this.userSerivce.findByUserName(username);
        user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
        this.userSerivce.saveEntry(user);
        this.journalEntryRepository.deleteById(id);
    }

    public JournalEntry updateEntry(ObjectId id, String username, JournalEntry entry) {
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
