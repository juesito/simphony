
package com.simphony.repositories;

import com.simphony.entities.WorkCenter;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
/**
 *
 * @author root
 */
public interface WorkCenterRepository extends JpaRepository<WorkCenter, Long> {
    
    @Query("SELECT w FROM WorkCenter w "
        + "WHERE UPPER(w.status) = UPPER('A')")
    public List<WorkCenter> findAllEnabled();
}
