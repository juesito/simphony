
package com.simphony.beans;

import com.simphony.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author root
 */
@Component
public class RouteService {
    
    @Autowired
    private RouteRepository repository;

    public RouteRepository getRepository() {
        return repository;
    }

    public void setRepository(RouteRepository repository) {
        this.repository = repository;
    }
        
}
