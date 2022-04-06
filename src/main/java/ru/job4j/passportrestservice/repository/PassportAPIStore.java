package ru.job4j.passportrestservice.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ru.job4j.passportrestservice.entity.Passport;
import java.util.Collections;
import java.util.List;

@Repository
public class PassportAPIStore {
    @Value("${api-url}")
    private String url;

    private final RestTemplate restTemplate;

    public PassportAPIStore(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Passport> findAll() {
        return getList(String.format(
                "%s/find", url
        ));
    }

    private List<Passport> getList(String url) {
        List<Passport> body = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Passport>>() {
                }
        ).getBody();
        return body == null ? Collections.emptyList() : body;
    }

    public Passport findPassportBySeria(String seria) {
        return restTemplate.getForEntity(String.format("%s/find?seria=%s", url, seria),
                Passport.class)
                .getBody();
    }

    public Passport createPassport(Passport passport) {
        return restTemplate.postForEntity(String.format("%s/save", url), passport, Passport.class)
                .getBody();
    }

    public Passport updatePassport(Passport passport, String passportId) {
        return restTemplate.exchange(String.format("%s/update?id=%s", url, passportId),
                HttpMethod.PUT,
                new HttpEntity<>(passport),
                Passport.class).getBody();
    }

    public boolean deletePassport(String id) {
        return restTemplate.exchange(String.format("%s/delete?id=%s", url, id),
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class)
                .getStatusCode() == HttpStatus.OK;
    }

    public Passport findPassportById(String id) {
        return restTemplate.getForEntity(String.format("%s/%s", url, id), Passport.class).getBody();
    }

    public List<Passport> findUnavailable() {
        return getList(String.format(
                "%s/unavailable", url
        ));
    }

    public List<Passport> findReplaceablePassports() {
        return getList(String.format(
                "%s/find-replaceable", url
        ));
    }
}
