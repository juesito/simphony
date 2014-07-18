/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.repositories;

import com.simphony.entities.Population;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Soporte IT
 */
public interface PopulationRepository extends JpaRepository<Population, Long> {
    
    @Query("SELECT p FROM Population p "
        + "WHERE UPPER(p.status) = UPPER('A')")
    public List<Population> findAllEnabled();
}
