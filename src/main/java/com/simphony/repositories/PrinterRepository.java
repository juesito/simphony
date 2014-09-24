
package com.simphony.repositories;


import com.simphony.entities.Printer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author root
 */
public interface PrinterRepository extends JpaRepository<Printer, Long> {
    
}
