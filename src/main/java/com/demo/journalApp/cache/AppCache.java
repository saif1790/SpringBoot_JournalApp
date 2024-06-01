package com.demo.journalApp.cache;

import com.demo.journalApp.entity.ConfigJournalApp;
import com.demo.journalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    public enum keys{
        WEATHER_API;
    }

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String,String> cacheMap = new HashMap<>();


    @PostConstruct
    public void init(){
        List<ConfigJournalApp> all = configJournalAppRepository.findAll();
        for(ConfigJournalApp configJournalApp : all)
        {
            cacheMap.put(configJournalApp.getKey(),configJournalApp.getValue());
            System.out.println("========="+cacheMap.keySet());
        }
    }
}
