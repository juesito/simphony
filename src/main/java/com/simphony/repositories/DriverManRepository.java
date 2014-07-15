package com.simphony.repositories;

import com.simphony.entities.DriverMan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverManRepository extends CrudRepository<DriverMan, Long> {
    
}
