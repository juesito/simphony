/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.repositories;

import com.simphony.entities.Workers;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Soporte IT
 */
public interface WorkersRepository extends JpaRepository<Workers, Long> {
   
}
