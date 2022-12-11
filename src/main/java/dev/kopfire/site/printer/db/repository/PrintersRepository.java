package dev.kopfire.site.printer.db.repository;

import dev.kopfire.site.printer.db.entity.Printer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrintersRepository extends JpaRepository<Printer, Long> {

    @Query(value = "select u from Printer u where u.name = :name")
    Printer findByName(@Param("name") String name);
}
