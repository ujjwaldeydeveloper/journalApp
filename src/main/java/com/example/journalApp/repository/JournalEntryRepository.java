package com.example.journalApp.repository;

import com.example.journalApp.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, String> {

}


//  controller  -->  services  --> repository

//Controller Layer (API Layer) → Handles HTTP requests/responses.
//Service Layer (Business Logic Layer) → Implements core business rules.
//Repository Layer (Data Access Layer) → Interacts with the database (CRUD).