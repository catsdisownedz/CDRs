package org.example.database.repository;

import org.example.database.entity.CDR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CDRRepository extends JpaRepository<CDR, Long> {
    List<CDR> findAllByOrderByStartTimeAsc();
    List<CDR> findByANUM(String anum);
    List<CDR> findByBNUM(String bnum);

    @Query("SELECT c FROM CDR c WHERE c.anum = :num OR c.bnum = :num")
    List<CDR> findByANUMOrBNUM(@Param("num") String num);

}
