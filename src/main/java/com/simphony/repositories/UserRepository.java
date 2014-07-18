/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.repositories;

import com.simphony.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author root
 */
public interface UserRepository extends JpaRepository<User, Long>{
    
    @Query("SELECT p FROM User p "
        + "WHERE LOWER(p.nick) = LOWER(:nick) AND contrasena = (:password)")
    public User login(@Param("nick") String nick, @Param("password") String password);
   
        @Query("SELECT u FROM User u "
        + "WHERE UPPER(u.status) = UPPER('A')")
    public List<User> findAllEnabled();

}
