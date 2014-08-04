
package com.simphony.repositories;

import com.simphony.entities.SalePoint;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Soporte IT
 */
@Repository
public interface SalePointRepository extends JpaRepository<SalePoint, Long> {

    @Query("SELECT p FROM SalePoint p "
            + "WHERE UPPER(p.status) = UPPER('A')")
    public List<SalePoint> findAllEnabled();
    
    @Query("SELECT s FROM SalePoint s WHERE s.description = (:description)")
    public SalePoint findByDesc(@Param("description") String description);

}
