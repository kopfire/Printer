package dev.kopfire.site.printer.db.repository;

import dev.kopfire.site.printer.db.entity.Offices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficesRepository extends JpaRepository<Offices, Long> {
}
