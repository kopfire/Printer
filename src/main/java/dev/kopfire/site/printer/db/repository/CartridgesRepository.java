package dev.kopfire.site.printer.db.repository;

import dev.kopfire.site.printer.db.entity.Cartridge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartridgesRepository extends JpaRepository<Cartridge, Long> {

    @Query(value = "select u from Cartridge u where u.text_qr = :text_qr")
    List<Cartridge> findByQR(@Param("text_qr") String text_qr);


}
