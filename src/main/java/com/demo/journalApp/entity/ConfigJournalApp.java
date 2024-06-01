package com.demo.journalApp.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "config_journal_app")
@NoArgsConstructor
public class ConfigJournalApp {

    private String key;
    private String value;
}
