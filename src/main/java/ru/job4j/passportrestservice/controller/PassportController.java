package ru.job4j.passportrestservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.passportrestservice.entity.Passport;
import ru.job4j.passportrestservice.service.PassportService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/passport/")
public class PassportController {
    private PassportService passportService;
    private ObjectMapper objectMapper;

    public PassportController(PassportService passportService, ObjectMapper objectMapper) {
        this.passportService = passportService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/find")
    public ResponseEntity<List<Passport>> getAll() {
        return new ResponseEntity(passportService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/find", params = {"seria"})
    public ResponseEntity<List<Passport>> findBySeria(
            @RequestParam(value = "seria") String seria) throws Throwable {
        if (seria.length() != 4) {
            throw new IllegalArgumentException("Серия паспорта должна содержать 4 цифры, "
                    + "в вашем запросе " + seria.length());
        }
        return new ResponseEntity(passportService.findPassportsBySeria(seria), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Passport> createPassport(@RequestBody Passport passport) {
        return new ResponseEntity<>(passportService.createPassport(passport), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update", params = {"id"})
    public ResponseEntity<Passport> updatePassport(@RequestBody Passport passport,
                                                   @RequestParam String id) {
        var passportId = Integer.parseInt(id);
        if (passportService.isExisting(passportId)) {
            passport.setId(passportId);
            return new ResponseEntity<>(passportService.updatePassport(passport), HttpStatus.OK);
        } else {
            throw  new IllegalArgumentException("Паспорт с id = " + id + " не найден");
        }

    }

    @DeleteMapping(value = "/delete", params = {"id"})
    public ResponseEntity<Void> deletePassport(@RequestParam String id) {
        var passportId = Integer.parseInt(id);
        if (passportService.deletePassport(passportId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw  new IllegalArgumentException("Паспорт с id = " + id + " не найден");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passport> findPassportById(@PathVariable int id) throws Throwable {
        return new ResponseEntity<>(passportService
                .findPasportById(id)
                .orElseThrow((Supplier<Throwable>) () ->
                        new IllegalArgumentException("Паспорт с id = " + id + " не найден")),
                HttpStatus.OK);
    }

    @GetMapping("/unavailable")
    public ResponseEntity<List<Passport>> findUnavailable() {
        return new ResponseEntity<>(passportService.findUnavailable(), HttpStatus.OK);
    }

    @GetMapping("/find-replaceable")
    public ResponseEntity<List<Passport>> findReplaceablePassports() {
        return new ResponseEntity<>(passportService.findReplaceablePassports(), HttpStatus.OK);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public void exceptionHandler(Exception e,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public void jdbcExceptionHandler(Exception e,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", "Нарушение уникальности пары серия - номер паспорта");
            put("type", e.getClass());
        }}));
    }
}
