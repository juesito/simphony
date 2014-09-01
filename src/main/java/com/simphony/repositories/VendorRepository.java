/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.repositories;

import com.simphony.entities.Vendor;
import java.util.List;
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
    @Query("SELECT p FROM Vendor p "
        + "WHERE LOWER(p.nick) = LOWER(:nick) AND contrasena = (:password)")
    public Vendor login(@Param("nick") String nick, @Param("password") String password);
    
    @Query("SELECT p FROM Vendor p "
        + "WHERE LOWER(p.nick) = LOWER(:nick) ")
    public Vendor findByNick(@Param("nick") String nick);

    @Query("SELECT w FROM Vendor w WHERE UPPER(w.status) = UPPER('A')")
    public List<Vendor> findAllEnabled();

}
