package com.example.journalApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;


@Document(collection ="journalEntry")
@Data
@NoArgsConstructor // needed for de serialization
public class JournalEntry {
    @Id
    private Object id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;

}
