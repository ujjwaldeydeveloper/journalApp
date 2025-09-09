package com.example.journalApp.service;

import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;


    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getByID(Object id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteByID(Object id) {
        journalEntryRepository.deleteById(id);
    }

//    public void updateEntry(JournalEntry journalEntry) {
//        journalEntryRepository.save(journalEntry)
//    }

}
