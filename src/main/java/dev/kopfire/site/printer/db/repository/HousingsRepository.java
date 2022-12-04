package dev.kopfire.site.printer.db.repository;

import dev.kopfire.site.printer.db.entity.Housings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HousingsRepository extends JpaRepository<Housings, Long> {

    @Query(value = "select u from Housings u where u.id = :id")
    Housings findByID(@Param("id") Long id);
}
