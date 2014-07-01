/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.repositories;

import com.simphony.entities.Vendor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author root
 */
@Repository
public interface VendorRepository extends CrudRepository<Vendor, Long>{
    @Query("SELECT p FROM User p "
        + "WHERE LOWER(p.nick) = LOWER(:nick) AND contrasena = (:password)")
    public Vendor login(@Param("nick") String nick, @Param("password") String password);
}
