package com.example.journalApp.controller;

import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;


    @GetMapping
    public List<JournalEntry> getAll() {
        return journalEntryService.getAll();
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry) {
        myEntry.setDate(LocalDateTime.now());
        myEntry.setId(new ObjectId());
        journalEntryService.saveEntry(myEntry);
        return myEntry;
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable Object myId) {
        Optional<JournalEntry> journalEntry = journalEntryService.getByID(myId);
        if(journalEntry.isPresent()) {
            return new ResponseEntity<JournalEntry>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public boolean deleteJournalEntryById(@PathVariable Object myId) {
        journalEntryService.deleteByID(myId);
        return true;
    }

    @PutMapping("id/{myId}")
    public JournalEntry updateJounalById(@PathVariable Object myId, @RequestBody JournalEntry newEntry) {
        JournalEntry old = journalEntryService.getByID(myId).orElse(null);
        if(old != null) {
            old.setTitle((newEntry.getTitle() != null && !newEntry.getTitle().equals("")) ? newEntry.getTitle() : old.getTitle());
            if ((newEntry.getContent() != null && !newEntry.getContent().equals(""))) {
                old.setContent(newEntry.getContent());
            } else {
                old.setContent(old.getContent());
            }

        }
        journalEntryService.saveEntry(old);
        return old;
    }
}

/*



*/
