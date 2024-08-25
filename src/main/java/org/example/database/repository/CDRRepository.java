package org.example.database.repository;

import org.example.database.entity.CDR;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CDRRepository extends JpaRepository<CDR, Long> {
    List<CDR> findByStartDateTime(LocalDateTime startDateTime);
    List<CDR> findByUsername(String username);

}
