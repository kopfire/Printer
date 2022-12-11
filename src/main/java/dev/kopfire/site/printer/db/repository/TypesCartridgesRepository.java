package dev.kopfire.site.printer.db.repository;

import dev.kopfire.site.printer.db.entity.TypesCartridges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TypesCartridgesRepository extends JpaRepository<TypesCartridges, Long> {

    @Query(value = "select u from TypesCartridges u where u.name = :name")
    TypesCartridges findByName(@Param("name") String name);
}
