package com.example.journalApp.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;


@Document(collection ="journalEntry")
@Data
public class JournalEntry {
    @Id
    private Object id;
    private String title;
    private String content;
    private LocalDateTime date;

}
