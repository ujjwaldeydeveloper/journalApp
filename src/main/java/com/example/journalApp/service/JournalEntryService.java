package com.example.journalApp.service;

import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.entity.User;
import com.example.journalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {
            Optional<User> userOptional = userService.findByName(userName);
            if(userOptional.isEmpty()) {
                throw new IllegalArgumentException("User not found: " + userName);
            }
            User user = userOptional.get();

            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            // adding db reference of saved Journal
            user.getJournalEntries().add(saved);
            // saving the user with journal entry
            userService.saveEntry(user);
        } catch (Exception e) {
            throw new RuntimeException("An error ooccurred while saving the entry.", e);
        }
    }

    public void saveUpdateEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getByID(Object id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteByID(Object id, String userName) {
        Optional<User> userOptional = userService.findByName(userName);

        if(userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getJournalEntries() != null) {
                user.getJournalEntries().removeIf(
                        entry -> entry != null &&
                                entry.getId() != null &&
                                entry.getId().toString().equals(id.toString())
                );
            }
            userService.saveEntry(user);
        }
        journalEntryRepository.deleteById(id);
    }

//    public void updateEntry(JournalEntry journalEntry) {
//        journalEntryRepository.save(journalEntry)
//    }

}
