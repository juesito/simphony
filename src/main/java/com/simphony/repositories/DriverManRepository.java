package com.simphony.repositories;

import com.simphony.entities.DriverMan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DriverManRepository extends JpaRepository<DriverMan, Long> {
  
    @Query("SELECT w FROM DriverMan w WHERE UPPER(w.status) = UPPER('A')")
    public List<DriverMan> findAllEnabled();

}
