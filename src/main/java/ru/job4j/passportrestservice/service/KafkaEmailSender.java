package ru.job4j.passportrestservice.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaEmailSender {

    @KafkaListener(topics = {"unavailablePassports"})
    public void sendEmails(ConsumerRecord<Integer, String> input) {
        System.out.println(input.value());
    }
}
