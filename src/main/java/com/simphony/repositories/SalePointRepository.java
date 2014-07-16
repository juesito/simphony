
package com.simphony.repositories;

import com.simphony.entities.SalePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Soporte IT
 */
@Repository
public interface SalePointRepository extends JpaRepository<SalePoint, Long> {
    
}
