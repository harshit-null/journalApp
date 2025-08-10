package com.harshit.journalApp.service;

import com.harshit.journalApp.model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;



@Service
public class SentimentConsumerService {
    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "my-topic", groupId = "my-group" )
    public void consume(SentimentData sentimentData){
        sendMail(sentimentData);
    }

    private void sendMail(SentimentData sentimentData){
        emailService.sendEmail(sentimentData.getEmail(),"last week sentiment data", sentimentData.getSentiment());
    }


}
