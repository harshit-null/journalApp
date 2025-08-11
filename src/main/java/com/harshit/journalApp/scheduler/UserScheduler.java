package com.harshit.journalApp.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harshit.journalApp.cache.AppCache;
import com.harshit.journalApp.entity.JournalEntry;
import com.harshit.journalApp.entity.User;
import com.harshit.journalApp.enums.Sentiments;
import com.harshit.journalApp.model.SentimentData;
import com.harshit.journalApp.repository.UserRepositoryImpl;
import com.harshit.journalApp.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;


    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUserAndSendSaMail() {
        List<User> users = userRepository.getUserForSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiments> sentiments = journalEntries.stream()
                    .filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x -> x.getSentiment())
                    .collect(Collectors.toList());

            Map<Sentiments, Integer> sentimentCounts = new HashMap<>();

            for (Sentiments sentiment : sentiments) {
                if (sentiment != null) {
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
            }

            Sentiments mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiments, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            if (mostFrequentSentiment != null) {
                SentimentData sentimentData = SentimentData.builder()
                        .email(user.getEmail())
                        .sentiment("sentiment for last seven days " + mostFrequentSentiment)
                        .build();

                try {
                    kafkaTemplate.send("my-topic", sentimentData);
                } catch (Exception e) {
                    emailService.sendEmail(sentimentData.getEmail(), "weekly sentiment summaary", sentimentData.getSentiment() );
                }



            }

        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache() {
        appCache.init();
    }



}