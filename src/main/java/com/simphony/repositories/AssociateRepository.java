package com.simphony.repositories;

import com.simphony.entities.Associate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 *
 * @author Soporte IT
 */
public interface AssociateRepository extends JpaRepository<Associate, Long> {
    
    @Query("SELECT a FROM Associate a WHERE a.keyId = (:keyId)")
    public Associate findByKey(@Param("keyId") String keyId);
    
}
