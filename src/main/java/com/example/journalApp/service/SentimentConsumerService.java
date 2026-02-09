package com.example.journalApp.service;

import com.example.journalApp.model.SentimentData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SentimentConsumerService {

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "weekly_sentiments", groupId = "weekly-sentiment-group")
    public void consume(SentimentData sentimentData) {
        try {
            log.info("Consumed sentiment from Kafka for email: {}", sentimentData != null ? sentimentData.getEmail() : "null");
            if (sentimentData == null || sentimentData.getEmail() == null || sentimentData.getEmail().isBlank()) {
                log.warn("Skipping email: missing email in SentimentData");
                return;
            }
            sendEmail(sentimentData);
        } catch (Exception e) {
            log.error("Error processing Kafka message for sentiment email", e);
            throw e;
        }
    }

    private void sendEmail(SentimentData sentimentData) {
        emailService.sendEmail(sentimentData.getEmail(), "Sentiment for Previous week", sentimentData.getSentiment());
    }
}
