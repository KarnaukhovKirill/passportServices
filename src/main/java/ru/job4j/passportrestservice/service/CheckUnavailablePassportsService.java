package ru.job4j.passportrestservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;
import ru.job4j.passportrestservice.entity.Passport;
import ru.job4j.passportrestservice.repository.PassportRepository;

@Component
public class CheckUnavailablePassportsService {
    @Autowired
    private PassportRepository passportRepository;

    @Autowired
    private KafkaTemplate<Integer, String> template;

    @Scheduled(fixedDelayString = "${ScheduledCheckPassports}")
    public void sendEmail() {
        var passports = passportRepository.findUnavailablePassports();
        for (Passport passport : passports) {
            template.send("unavailablePassports",
                    "Мы нашли просроченный паспорт с id = "
                            + passport.getId()
                            + ". Сделайте с этим что-нибудь!");
        }
    }
}
