package com.simphony.repositories;

import com.simphony.entities.PayRoll;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 *
 * @author Soporte IT
 */
public interface PayRollRepository extends JpaRepository<PayRoll, Long> {
    
    @Query("SELECT a FROM PayRoll a WHERE a.idPayRoll = (:keyId)")
    public List<PayRoll> findByKey(@Param("keyId") String keyId);
    
}
