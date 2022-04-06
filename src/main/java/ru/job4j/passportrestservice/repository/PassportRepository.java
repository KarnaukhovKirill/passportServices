package ru.job4j.passportrestservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.passportrestservice.entity.Passport;
import java.util.Date;
import java.util.List;

@Repository
public interface PassportRepository extends CrudRepository<Passport, Integer> {
    public Passport findPassportBySeria(String seria);

    @Query("from Passport p where p.date < current_date ")
    public List<Passport> findUnavailablePassports();

    @Query("from Passport p where p.date between current_date and :timeToUnavailable")
    public List<Passport> findReplaceablePassports(@Param("timeToUnavailable") Date time);
}
