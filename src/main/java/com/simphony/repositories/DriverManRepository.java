package com.simphony.repositories;

import com.simphony.entities.DriverMan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverManRepository extends JpaRepository<DriverMan, Long> {
    
}
