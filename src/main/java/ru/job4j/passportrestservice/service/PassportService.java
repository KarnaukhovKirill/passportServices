package ru.job4j.passportrestservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.passportrestservice.entity.Passport;
import ru.job4j.passportrestservice.repository.PassportRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PassportService {
    @Value("${time.unavailable.passport}")
    private int month;

    @Autowired
    private PassportRepository passportRepository;

    public List<Passport> findAll() {
        List<Passport> rsl = new ArrayList<>();
        passportRepository.findAll().forEach(rsl::add);
        return rsl;
    }

    public Passport createPassport(Passport passport) {
        return passportRepository.save(passport);
    }

    public Optional<Passport> findPasportById(int id) {
        return passportRepository.findById(id);
    }

    public void deletePassport(int id) {
        passportRepository.deleteById(id);
    }

    public Optional<Passport> findPassportBySeria(String seria) {
        return Optional.ofNullable(passportRepository.findPassportBySeria(seria));
    }

    public List<Passport> findUnavailable() {
        return passportRepository.findUnavailablePassports();
    }

    public List<Passport> findReplaceablePassports() {
        Date date = new Date();
        date.setMonth(date.getMonth() + month);
        System.out.println(date);
        return passportRepository.findReplaceablePassports(date);
    }
}
