package dev.kopfire.site.printer.db.repository;

import dev.kopfire.site.printer.db.entity.Housings;
import dev.kopfire.site.printer.db.entity.Offices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficesRepository extends JpaRepository<Offices, Long> {

    @Query(value = "select u from Offices u where u.housing = :housing and u.name = :name")
    Offices findByHouseAndName(@Param("housing") Housings housing, @Param("name") String name);
}
