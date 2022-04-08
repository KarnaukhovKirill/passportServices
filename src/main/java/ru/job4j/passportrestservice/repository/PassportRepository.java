package ru.job4j.passportrestservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.passportrestservice.entity.Passport;
import java.util.Date;
import java.util.List;

@Repository
public interface PassportRepository extends CrudRepository<Passport, Integer> {
    @Modifying
    int deletePassportById(int id);

    List<Passport> findPassportsBySeria(String seria);

    @Query("from Passport p where p.dateExpiry < current_date ")
    List<Passport> findUnavailablePassports();

    @Query("from Passport p where p.dateExpiry between current_date and :timeToUnavailable")
    List<Passport> findReplaceablePassports(@Param("timeToUnavailable") Date time);
}
